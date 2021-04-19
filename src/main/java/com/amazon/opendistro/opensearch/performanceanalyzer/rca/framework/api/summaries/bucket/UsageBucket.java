/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 *
 * Modifications Copyright OpenSearch Contributors. See
 * GitHub history for details.
 */

/*
 * Copyright 2020-2021 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.amazon.opendistro.opensearch.performanceanalyzer.rca.framework.api.summaries.bucket;


import com.amazon.opendistro.opensearch.performanceanalyzer.grpc.Resource;

/**
 * A UsageBucket is associated with a {@link Resource} and identifies the state of that Resource. We
 * use these buckets to identify when we have the bandwidth to scale a particular resource out or
 * in.
 *
 * <p>{@link UsageBucket#UNDER_UTILIZED} means that the {@link Resource} is barely being used at all
 * and may be a good candidate for scaling in.
 *
 * <p>{@link UsageBucket#HEALTHY_WITH_BUFFER} means that the {@link Resource} is healthy and there
 * is room to increase the pressure on this resource if need be.
 *
 * <p>{@link UsageBucket#HEALTHY} means that the {@link Resource} is in a healthy state. Resources
 * in this bucket should probably be left alone.
 *
 * <p>{@link UsageBucket#UNHEALTHY} means that the {@link Resource} is under high pressure. Actions
 * should be taken to help reduce the pressure.
 */
public enum UsageBucket {
    UNKNOWN,
    UNDER_UTILIZED,
    HEALTHY_WITH_BUFFER,
    HEALTHY,
    UNHEALTHY
}
