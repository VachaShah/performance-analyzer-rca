package com.amazon.opendistro.elasticsearch.performanceanalyzer.rca.store.metric.temperature.capacity;

import com.amazon.opendistro.elasticsearch.performanceanalyzer.rca.framework.core.temperature.TemperatureDimension;
import com.amazon.opendistro.elasticsearch.performanceanalyzer.rca.framework.core.temperature.TemperatureVector;
import com.amazon.opendistro.elasticsearch.performanceanalyzer.rca.store.metric.temperature.capacity.calculators.TotalNodeTemperatureCalculator;

public class ShardSizePeakUsageTemperatureCalculator extends TotalNodeTemperatureCalculator {

    public ShardSizePeakUsageTemperatureCalculator() {
        super(TemperatureDimension.Shard_Size_In_Bytes);
    }
}
