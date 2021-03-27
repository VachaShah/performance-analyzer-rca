package com.amazon.opendistro.opensearch.performanceanalyzer.rca.integTests.tests.jvmsizing.validator;

import static org.junit.Assert.assertNotEquals;

import com.amazon.opendistro.opensearch.performanceanalyzer.decisionmaker.actions.HeapSizeIncreaseAction;
import com.amazon.opendistro.opensearch.performanceanalyzer.rca.persistence.actions.PersistedAction;
import org.junit.Assert;

public class HeapSizeIncreaseNonBreachingValidator extends HeapSizeIncreaseValidator {

  @Override
  public boolean checkDbObj(Object object) {
    // It could well be the case that no RCA has been triggered so far, and thus no table exists.
    // This is a valid outcome.
    if (object == null) {
      return true;
    }

    PersistedAction persistedAction = (PersistedAction) object;
    return checkPersistedAction(persistedAction);
  }

  @Override
  public boolean checkPersistedAction(PersistedAction persistedAction) {
    Assert.assertNotEquals(HeapSizeIncreaseAction.NAME, persistedAction.getActionName());

    return true;
  }
}
