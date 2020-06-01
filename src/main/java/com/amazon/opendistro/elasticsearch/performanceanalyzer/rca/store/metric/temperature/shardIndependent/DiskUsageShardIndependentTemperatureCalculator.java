package com.amazon.opendistro.elasticsearch.performanceanalyzer.rca.store.metric.temperature.shardIndependent;

import com.amazon.opendistro.elasticsearch.performanceanalyzer.rca.framework.core.temperature.TemperatureVector;
import com.amazon.opendistro.elasticsearch.performanceanalyzer.rca.store.metric.temperature.shardIndependent.calculators.ShardIndependentTemperatureCalculator;

public class DiskUsageShardIndependentTemperatureCalculator extends
        ShardIndependentTemperatureCalculator {
    public DiskUsageShardIndependentTemperatureCalculator() {
        super(TemperatureVector.Dimension.Shard_Size_In_Bytes);
    }
}
