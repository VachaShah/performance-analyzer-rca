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

package com.amazon.opendistro.opensearch.performanceanalyzer.rca.configs;


import com.amazon.opendistro.opensearch.performanceanalyzer.rca.framework.core.RcaConf;

public class HotShardRcaConfig {
    public static final String CONFIG_NAME = "hot-shard-rca";

    private Double cpuUtilizationThreshold;
    private Double ioTotThroughputThreshold;
    private Double ioTotSysCallRateThreshold;

    public static final double DEFAULT_CPU_UTILIZATION_THRESHOLD = 0.01;
    public static final double DEFAULT_IO_TOTAL_THROUGHPUT_THRESHOLD_IN_BYTE_PER_SEC = 250000.0;
    public static final double DEFAULT_IO_TOTAL_SYSCALL_RATE_THRESHOLD_PER_SEC = 0.01;

    public HotShardRcaConfig(final RcaConf rcaConf) {
        cpuUtilizationThreshold =
                rcaConf.readRcaConfig(
                        CONFIG_NAME,
                        HotShardRcaConfig.RCA_CONF_KEY_CONSTANTS.CPU_UTILIZATION_THRESHOLD,
                        DEFAULT_CPU_UTILIZATION_THRESHOLD,
                        (s) -> (s > 0),
                        Double.class);
        ioTotThroughputThreshold =
                rcaConf.readRcaConfig(
                        CONFIG_NAME,
                        HotShardRcaConfig.RCA_CONF_KEY_CONSTANTS
                                .IO_TOT_THROUGHPUT_THRESHOLD_IN_BYTES,
                        DEFAULT_IO_TOTAL_THROUGHPUT_THRESHOLD_IN_BYTE_PER_SEC,
                        (s) -> (s > 0),
                        Double.class);
        ioTotSysCallRateThreshold =
                rcaConf.readRcaConfig(
                        CONFIG_NAME,
                        HotShardRcaConfig.RCA_CONF_KEY_CONSTANTS
                                .IO_TOT_SYSCALL_RATE_THRESHOLD_PER_SECOND,
                        DEFAULT_IO_TOTAL_SYSCALL_RATE_THRESHOLD_PER_SEC,
                        (s) -> (s > 0),
                        Double.class);
    }

    public double getCpuUtilizationThreshold() {
        return cpuUtilizationThreshold;
    }

    public double getIoTotThroughputThreshold() {
        return ioTotThroughputThreshold;
    }

    public double getIoTotSysCallRateThreshold() {
        return ioTotSysCallRateThreshold;
    }

    public static class RCA_CONF_KEY_CONSTANTS {
        public static final String CPU_UTILIZATION_THRESHOLD = "cpu-utilization";
        public static final String IO_TOT_THROUGHPUT_THRESHOLD_IN_BYTES =
                "io-total-throughput-in-bytes";
        public static final String IO_TOT_SYSCALL_RATE_THRESHOLD_PER_SECOND =
                "io-total-syscallrate-per-second";
    }
}
