/*
 * Copyright 2021 Amazon.com, Inc. or its affiliates. All Rights Reserved.
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

package com.amazon.opendistro.opensearch.performanceanalyzer.rca.integTests.framework.annotations;


import com.amazon.opendistro.opensearch.performanceanalyzer.rca.integTests.framework.configs.HostTag;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This specifies a table for a given metric. This annotation is a sub-field of the AMetric
 * annotation.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ATable {
    // Which host should emit this metric
    HostTag[] hostTag();

    // The data in tabular form.
    ATuple[] tuple();
}
