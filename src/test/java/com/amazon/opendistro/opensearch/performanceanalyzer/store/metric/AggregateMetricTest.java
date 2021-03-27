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

package com.amazon.opendistro.opensearch.performanceanalyzer.store.metric;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.amazon.opendistro.opensearch.performanceanalyzer.metrics.AllMetrics;
import com.amazon.opendistro.opensearch.performanceanalyzer.metricsdb.MetricsDB;
import com.amazon.opendistro.opensearch.performanceanalyzer.rca.framework.api.Metric;
import com.amazon.opendistro.opensearch.performanceanalyzer.rca.framework.api.flow_units.MetricFlowUnit;
import com.amazon.opendistro.opensearch.performanceanalyzer.rca.framework.api.metrics.CPU_Utilization;
import com.amazon.opendistro.opensearch.performanceanalyzer.rca.framework.core.Queryable;
import com.amazon.opendistro.opensearch.performanceanalyzer.rca.spec.MetricsDBProviderTestHelper;
import com.amazon.opendistro.opensearch.performanceanalyzer.rca.store.metric.AggregateMetric;
import java.util.Arrays;
import org.junit.Test;

public class AggregateMetricTest {
    private static final String TABLE_NAME = CPU_Utilization.NAME;

    @Test
    public void testGroupByOneColumn() throws Exception {
        Queryable queryable = new MetricsDBProviderTestHelper(false);

        ((MetricsDBProviderTestHelper) queryable)
                .addNewData(TABLE_NAME, Arrays.asList("shard1", "index3", "bulk", "primary"), 1);
        ((MetricsDBProviderTestHelper) queryable)
                .addNewData(TABLE_NAME, Arrays.asList("shard2", "index3", "bulk", "primary"), 1);
        ((MetricsDBProviderTestHelper) queryable)
                .addNewData(TABLE_NAME, Arrays.asList("shard1", "index3", "bulk", "primary"), 1);
        ((MetricsDBProviderTestHelper) queryable)
                .addNewData(TABLE_NAME, Arrays.asList("shard2", "index3", "other", "primary"), 3);
        ((MetricsDBProviderTestHelper) queryable)
                .addNewData(TABLE_NAME, Arrays.asList("shard1", "index3", "other", "primary"), 3);
        ((MetricsDBProviderTestHelper) queryable)
                .addNewData(TABLE_NAME, Arrays.asList("shard2", "index3", "other", "primary"), 3);
        Metric testMetric =
                new AggregateMetric(
                        1,
                        TABLE_NAME,
                        AggregateMetric.AggregateFunction.SUM,
                        MetricsDB.AVG,
                        AllMetrics.CommonDimension.OPERATION.toString());
        MetricFlowUnit flowUnit = testMetric.gather(queryable);
        assertFalse(flowUnit.getData() == null || flowUnit.getData().isEmpty());
        assertEquals("other", flowUnit.getData().get(0).getValue(0, String.class));
        assertEquals("9.0", flowUnit.getData().get(0).getValue(1, String.class));
        assertEquals("bulk", flowUnit.getData().get(1).getValue(0, String.class));
        assertEquals("3.0", flowUnit.getData().get(1).getValue(1, String.class));
    }

    @Test
    public void testGroupByTwoColumns() throws Exception {
        Queryable queryable = new MetricsDBProviderTestHelper(false);

        ((MetricsDBProviderTestHelper) queryable)
                .addNewData(TABLE_NAME, Arrays.asList("shard1", "index3", "bulk", "primary"), 4);
        ((MetricsDBProviderTestHelper) queryable)
                .addNewData(TABLE_NAME, Arrays.asList("shard2", "index3", "bulk", "primary"), 1);
        ((MetricsDBProviderTestHelper) queryable)
                .addNewData(TABLE_NAME, Arrays.asList("shard1", "index3", "bulk", "primary"), 1);
        ((MetricsDBProviderTestHelper) queryable)
                .addNewData(TABLE_NAME, Arrays.asList("shard2", "index3", "other", "primary"), 3);
        ((MetricsDBProviderTestHelper) queryable)
                .addNewData(TABLE_NAME, Arrays.asList("shard1", "index3", "other", "primary"), 3);
        ((MetricsDBProviderTestHelper) queryable)
                .addNewData(TABLE_NAME, Arrays.asList("shard3", "index3", "other", "primary"), 10);
        Metric testMetric =
                new AggregateMetric(
                        1,
                        TABLE_NAME,
                        AggregateMetric.AggregateFunction.SUM,
                        MetricsDB.AVG,
                        AllMetrics.CommonDimension.SHARD_ID.toString(),
                        AllMetrics.CommonDimension.OPERATION.toString());
        MetricFlowUnit flowUnit = testMetric.gather(queryable);
        assertFalse(flowUnit.getData() == null || flowUnit.getData().isEmpty());
        assertEquals("shard3", flowUnit.getData().get(0).get(0, String.class));
        assertEquals("other", flowUnit.getData().get(0).get(1, String.class));
        assertEquals("10.0", flowUnit.getData().get(0).get(2, String.class));
        assertEquals("shard1", flowUnit.getData().get(1).get(0, String.class));
        assertEquals("bulk", flowUnit.getData().get(1).get(1, String.class));
        assertEquals("5.0", flowUnit.getData().get(1).get(2, String.class));
    }

    @Test
    public void testOrderByMax() throws Exception {
        Queryable queryable = new MetricsDBProviderTestHelper(false);

        ((MetricsDBProviderTestHelper) queryable)
                .addNewData(TABLE_NAME, Arrays.asList("shard1", "index3", "bulk", "primary"), 1);
        ((MetricsDBProviderTestHelper) queryable)
                .addNewData(TABLE_NAME, Arrays.asList("shard2", "index3", "bulk", "primary"), 4);
        ((MetricsDBProviderTestHelper) queryable)
                .addNewData(TABLE_NAME, Arrays.asList("shard1", "index3", "bulk", "primary"), 1);
        ((MetricsDBProviderTestHelper) queryable)
                .addNewData(TABLE_NAME, Arrays.asList("shard2", "index3", "other", "primary"), 3);
        ((MetricsDBProviderTestHelper) queryable)
                .addNewData(TABLE_NAME, Arrays.asList("shard1", "index3", "other", "primary"), 3);
        ((MetricsDBProviderTestHelper) queryable)
                .addNewData(TABLE_NAME, Arrays.asList("shard2", "index3", "other", "primary"), 5);
        Metric testMetric =
                new AggregateMetric(
                        1,
                        TABLE_NAME,
                        AggregateMetric.AggregateFunction.MAX,
                        MetricsDB.AVG,
                        AllMetrics.CommonDimension.OPERATION.toString());
        MetricFlowUnit flowUnit = testMetric.gather(queryable);
        assertFalse(flowUnit.getData() == null || flowUnit.getData().isEmpty());
        assertEquals("other", flowUnit.getData().get(0).get(0, String.class));
        assertEquals("5.0", flowUnit.getData().get(0).get(1, String.class));
        assertEquals("bulk", flowUnit.getData().get(1).get(0, String.class));
        assertEquals("4.0", flowUnit.getData().get(1).get(1, String.class));
    }
}
