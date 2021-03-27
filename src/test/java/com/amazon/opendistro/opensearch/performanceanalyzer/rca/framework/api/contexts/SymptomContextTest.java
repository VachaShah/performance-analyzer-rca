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

package com.amazon.opendistro.opensearch.performanceanalyzer.rca.framework.api.contexts;


import com.amazon.opendistro.opensearch.performanceanalyzer.rca.framework.api.Resources;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SymptomContextTest {
    private SymptomContext uut;

    @Before
    public void setup() {
        uut = new SymptomContext(Resources.State.HEALTHY);
    }

    @Test
    public void testGeneric() {
        SymptomContext generic = SymptomContext.generic();
        Assert.assertSame(Resources.State.UNKNOWN, generic.getState());
    }
}
