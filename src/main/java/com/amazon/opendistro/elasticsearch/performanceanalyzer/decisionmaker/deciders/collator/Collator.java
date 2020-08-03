/*
 * Copyright 2020 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 */

package com.amazon.opendistro.elasticsearch.performanceanalyzer.decisionmaker.deciders.collator;

import com.amazon.opendistro.elasticsearch.performanceanalyzer.decisionmaker.actions.Action;
import com.amazon.opendistro.elasticsearch.performanceanalyzer.decisionmaker.actions.ImpactVector;
import com.amazon.opendistro.elasticsearch.performanceanalyzer.decisionmaker.actions.ImpactVector.Impact;
import com.amazon.opendistro.elasticsearch.performanceanalyzer.decisionmaker.deciders.Decider;
import com.amazon.opendistro.elasticsearch.performanceanalyzer.decisionmaker.deciders.Decision;
import com.amazon.opendistro.elasticsearch.performanceanalyzer.rca.store.rca.cluster.NodeKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Collator collects and prunes the candidate decisions from each decider so that their impacts are
 * aligned
 *
 * <p>Decisions can increase or decrease pressure on different key resources on an Elasticearch
 * node. This is encapsulated in each Action via the {@link ImpactVector}. Since each decider
 * independently evaluates its decision, it is possible to have conflicting ImpactVectors from
 * actions across deciders.
 *
 * <p>The collator prunes them to ensure we only take actions that either increase, or decrease
 * pressure on a particular node. To resolve conflicts, we prefer stability over performance.
 */
public class Collator extends Decider {

  public static final String NAME = "collator";

  /* Deciders can choose to publish decisions at different frequencies based on the
   * type of resources monitored and rca signals. The collator should however, not introduce any
   * unnecessary delays. As soon as a decision is taken, it should be evaluated and published downstream.
   */
  private static final int collatorFrequency = 1; // Measured in terms of number of evaluationIntervalPeriods

  private final List<Decider> deciders;

  private final ActionGrouper actionGrouper;

  public Collator(long evalIntervalSeconds, Decider... deciders) {
    this(evalIntervalSeconds, new SingleNodeImpactActionGrouper(), deciders);
  }

  public Collator(long evalIntervalSeconds, ActionGrouper actionGrouper, Decider... deciders) {
    super(evalIntervalSeconds, collatorFrequency);
    this.deciders = Arrays.asList(deciders);
    this.actionGrouper = actionGrouper;
  }

  @Override
  public String name() {
    return NAME;
  }

  @Override
  public Decision operate() {
    List<Action> proposedActions = getAllProposedActions();
    Map<NodeKey, List<Action>> actionsByNode = actionGrouper.groupByNodeId(proposedActions);
    List<Action> prunedActions = new ArrayList<>();
    actionsByNode.forEach((k, v) -> prunedActions.addAll(polarize(k, v)));

    Decision finalDecision = new Decision(System.currentTimeMillis(), NAME);
    finalDecision.addAllActions(prunedActions);
    return finalDecision;
  }

  @NonNull private List<Action> getAllProposedActions() {
    final List<Action> proposedActions = new ArrayList<>();
    if (deciders != null) {
      for (final Decider decider : deciders) {
        List<Decision> decisions = decider.getFlowUnits();
        if (decisions != null) {
          decisions.forEach(decision -> proposedActions.addAll(decision.getActions()));
        }
      }
    }
    return proposedActions;
  }

  private List<Action> polarize(final NodeKey nodeKey, List<Action> actions) {
    List<Action> pressureIncreasingActions = new ArrayList<>();
    List<Action> pressureNonIncreasingActions = new ArrayList<>();

    for (final Action action : actions) {
      ImpactVector impactVector = action.impact().getOrDefault(nodeKey, new ImpactVector());

      // Classify the action as pressure increasing action if the impact for any dimension is
      // increasing pressure.
      if (impactVector.getImpact()
                      .values()
                      .stream()
                      .anyMatch(impact -> impact == Impact.INCREASES_PRESSURE)) {
        pressureIncreasingActions.add(action);
      } else {
        pressureNonIncreasingActions.add(action);
      }
    }

    // If there are any actions that decrease pressure for a node, prefer that over list of
    // actions that increase pressure.
    if (pressureNonIncreasingActions.size() > 0) {
      return pressureNonIncreasingActions;
    }

    // Return list of actions that increase pressure only if no decider has proposed an action
    // that will relieve pressure for this node.
    return pressureIncreasingActions;
  }
}
