/*
 * Copyright 2019-2021 Amazon.com, Inc. or its affiliates. All Rights Reserved.
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

package com.amazon.opendistro.opensearch.performanceanalyzer.reader;


import com.amazon.opendistro.opensearch.performanceanalyzer.metrics.AllMetrics;
import com.amazon.opendistro.opensearch.performanceanalyzer.metrics.PerformanceAnalyzerMetrics;
import com.amazon.opendistro.opensearch.performanceanalyzer.reader_writer_shared.Event;
import com.google.common.annotations.VisibleForTesting;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class MetricPropertiesConfig {

    /**
     * Find files under /dev/shm/performanceanalyzer/TS_BUCKET/metricPathElements
     *
     * @param metricPathElements path element array
     * @return a list of Files
     */
    static FileHandler createFileHandler(String... metricPathElements) {
        return new FileHandler() {
            @Override
            public List<File> findFiles4Metric(long startTimeThirtySecondBucket) {
                List<File> ret = new ArrayList<File>(1);
                StringBuilder sb = new StringBuilder();
                sb.append(getRootLocation());
                sb.append(startTimeThirtySecondBucket);

                for (String element : metricPathElements) {
                    sb.append(File.separator);
                    sb.append(element);
                }
                File metricFile = new File(sb.toString());
                if (metricFile.exists()) {
                    ret.add(metricFile);
                }
                return ret;
            }

            public List<Event> getMetricData(Map<String, List<Event>> metricDataMap) {
                Objects.requireNonNull(metricDataMap);
                List<Event> entries = metricDataMap.get(metricPathElements[0]);
                return (entries == null ? Collections.emptyList() : entries);
            }
        };
    }

    public static class ShardStatFileHandler extends FileHandler {
        @Override
        public List<File> findFiles4Metric(long timeBucket) {
            File indicesFolder =
                    new File(
                            this.getRootLocation()
                                    + File.separator
                                    + timeBucket
                                    + File.separator
                                    + PerformanceAnalyzerMetrics.sIndicesPath);

            if (!indicesFolder.exists()) {
                return Collections.emptyList();
            }

            List<File> metricFiles = new ArrayList<>();

            File[] files = indicesFolder.listFiles();
            if (files != null) {
                for (File indexFolder : files) {
                    if (indexFolder != null) {
                        File[] shardIdFiles = indexFolder.listFiles();
                        if (shardIdFiles != null) {
                            for (File shardIdFile : shardIdFiles) {
                                metricFiles.add(shardIdFile);
                            }
                        }
                    }
                }
            }
            return metricFiles;
        }

        // An example shard data can be:
        // ^indices/nyc_taxis/29
        // {"current_time":1566413966497}
        // {"Indexing_ThrottleTime":0,"Cache_Query_Hit":0,"Cache_Query_Miss":0,"Cache_Query_Size":0,
        // "Cache_FieldData_Eviction":0,"Cache_FieldData_Size":0,"Cache_Request_Hit":0,
        // "Cache_Request_Miss":0,"Cache_Request_Eviction":0,"Cache_Request_Size":0,"Refresh_Event":2,
        // "Refresh_Time":0,"Flush_Event":0,"Flush_Time":0,"Merge_Event":0,"Merge_Time":0,
        // "Merge_CurrentEvent":0,"Indexing_Buffer":0,"Segments_Total":0,"Segments_Memory":0,
        // "Terms_Memory":0,"StoredFields_Memory":0,"TermVectors_Memory":0,"Norms_Memory":0,
        // "Points_Memory":0,"DocValues_Memory":0,"IndexWriter_Memory":0,"VersionMap_Memory":0,"Bitset_Memory":0}$
        public List<Event> getMetricData(Map<String, List<Event>> metricDataMap) {
            Objects.requireNonNull(metricDataMap);
            return metricDataMap.computeIfAbsent(
                    PerformanceAnalyzerMetrics.sIndicesPath, k -> Collections.emptyList());
        }

        @Override
        public String filePathRegex() {
            // getRootLocation() may or may not end with File.separator.  So
            // I put ? next to File.separator.
            return getRootLocation()
                    + File.separator
                    + "?\\d+"
                    + File.separator
                    + PerformanceAnalyzerMetrics.sIndicesPath
                    + File.separator
                    + "(.*)"
                    + File.separator
                    + "(\\d+)";
        }
    }

    private final Map<AllMetrics.MetricName, MetricProperties> metricName2Property;

    private static final MetricPropertiesConfig INSTANCE = new MetricPropertiesConfig();

    private Map<AllMetrics.MetricName, String> metricPathMap;
    private Map<String, AllMetrics.MetricName> eventKeyToMetricNameMap;

    private MetricPropertiesConfig() {
        metricPathMap = new HashMap<>();
        metricPathMap.put(
                AllMetrics.MetricName.CACHE_CONFIG, PerformanceAnalyzerMetrics.sCacheConfigPath);
        metricPathMap.put(
                AllMetrics.MetricName.CIRCUIT_BREAKER,
                PerformanceAnalyzerMetrics.sCircuitBreakerPath);
        metricPathMap.put(AllMetrics.MetricName.HEAP_METRICS, PerformanceAnalyzerMetrics.sHeapPath);
        metricPathMap.put(
                AllMetrics.MetricName.DISK_METRICS, PerformanceAnalyzerMetrics.sDisksPath);
        metricPathMap.put(AllMetrics.MetricName.TCP_METRICS, PerformanceAnalyzerMetrics.sTCPPath);
        metricPathMap.put(AllMetrics.MetricName.IP_METRICS, PerformanceAnalyzerMetrics.sIPPath);
        metricPathMap.put(
                AllMetrics.MetricName.THREAD_POOL, PerformanceAnalyzerMetrics.sThreadPoolPath);
        metricPathMap.put(
                AllMetrics.MetricName.SHARD_STATS, PerformanceAnalyzerMetrics.sIndicesPath);
        metricPathMap.put(
                AllMetrics.MetricName.MASTER_PENDING, PerformanceAnalyzerMetrics.sPendingTasksPath);
        metricPathMap.put(
                AllMetrics.MetricName.MOUNTED_PARTITION_METRICS,
                PerformanceAnalyzerMetrics.sMountedPartitionMetricsPath);
        metricPathMap.put(
                AllMetrics.MetricName.CLUSTER_APPLIER_SERVICE,
                PerformanceAnalyzerMetrics.sClusterApplierService);
        metricPathMap.put(
                AllMetrics.MetricName.ADMISSION_CONTROL_METRICS,
                PerformanceAnalyzerMetrics.sAdmissionControlMetricsPath);
        metricPathMap.put(
                AllMetrics.MetricName.SHARD_INDEXING_PRESSURE,
                PerformanceAnalyzerMetrics.sShardIndexingPressurePath);

        eventKeyToMetricNameMap = new HashMap<>();
        eventKeyToMetricNameMap.put(
                PerformanceAnalyzerMetrics.sCacheConfigPath, AllMetrics.MetricName.CACHE_CONFIG);
        eventKeyToMetricNameMap.put(
                PerformanceAnalyzerMetrics.sCircuitBreakerPath,
                AllMetrics.MetricName.CIRCUIT_BREAKER);
        eventKeyToMetricNameMap.put(
                PerformanceAnalyzerMetrics.sHeapPath, AllMetrics.MetricName.HEAP_METRICS);
        eventKeyToMetricNameMap.put(
                PerformanceAnalyzerMetrics.sDisksPath, AllMetrics.MetricName.DISK_METRICS);
        eventKeyToMetricNameMap.put(
                PerformanceAnalyzerMetrics.sTCPPath, AllMetrics.MetricName.TCP_METRICS);
        eventKeyToMetricNameMap.put(
                PerformanceAnalyzerMetrics.sIPPath, AllMetrics.MetricName.IP_METRICS);
        eventKeyToMetricNameMap.put(
                PerformanceAnalyzerMetrics.sThreadPoolPath, AllMetrics.MetricName.THREAD_POOL);
        eventKeyToMetricNameMap.put(
                PerformanceAnalyzerMetrics.sIndicesPath, AllMetrics.MetricName.SHARD_STATS);
        eventKeyToMetricNameMap.put(
                PerformanceAnalyzerMetrics.sPendingTasksPath, AllMetrics.MetricName.MASTER_PENDING);
        eventKeyToMetricNameMap.put(
                PerformanceAnalyzerMetrics.sMountedPartitionMetricsPath,
                AllMetrics.MetricName.MOUNTED_PARTITION_METRICS);
        eventKeyToMetricNameMap.put(
                PerformanceAnalyzerMetrics.sClusterApplierService,
                AllMetrics.MetricName.CLUSTER_APPLIER_SERVICE);
        eventKeyToMetricNameMap.put(
                PerformanceAnalyzerMetrics.sAdmissionControlMetricsPath,
                AllMetrics.MetricName.ADMISSION_CONTROL_METRICS);
        eventKeyToMetricNameMap.put(
                PerformanceAnalyzerMetrics.sShardIndexingPressurePath,
                AllMetrics.MetricName.SHARD_INDEXING_PRESSURE);

        metricName2Property = new HashMap<>();

        metricName2Property.put(
                AllMetrics.MetricName.CACHE_CONFIG,
                new MetricProperties(
                        AllMetrics.CacheConfigDimension.values(),
                        AllMetrics.CacheConfigValue.values(),
                        createFileHandler(metricPathMap.get(AllMetrics.MetricName.CACHE_CONFIG))));
        metricName2Property.put(
                AllMetrics.MetricName.CIRCUIT_BREAKER,
                new MetricProperties(
                        AllMetrics.CircuitBreakerDimension.values(),
                        AllMetrics.CircuitBreakerValue.values(),
                        createFileHandler(
                                metricPathMap.get(AllMetrics.MetricName.CIRCUIT_BREAKER))));
        metricName2Property.put(
                AllMetrics.MetricName.HEAP_METRICS,
                new MetricProperties(
                        AllMetrics.HeapDimension.values(),
                        AllMetrics.HeapValue.values(),
                        createFileHandler(metricPathMap.get(AllMetrics.MetricName.HEAP_METRICS))));
        metricName2Property.put(
                AllMetrics.MetricName.DISK_METRICS,
                new MetricProperties(
                        AllMetrics.DiskDimension.values(),
                        AllMetrics.DiskValue.values(),
                        createFileHandler(metricPathMap.get(AllMetrics.MetricName.DISK_METRICS))));
        metricName2Property.put(
                AllMetrics.MetricName.TCP_METRICS,
                new MetricProperties(
                        AllMetrics.TCPDimension.values(),
                        AllMetrics.TCPValue.values(),
                        createFileHandler(metricPathMap.get(AllMetrics.MetricName.TCP_METRICS))));
        metricName2Property.put(
                AllMetrics.MetricName.IP_METRICS,
                new MetricProperties(
                        AllMetrics.IPDimension.values(),
                        AllMetrics.IPValue.values(),
                        createFileHandler(metricPathMap.get(AllMetrics.MetricName.IP_METRICS))));
        metricName2Property.put(
                AllMetrics.MetricName.THREAD_POOL,
                new MetricProperties(
                        AllMetrics.ThreadPoolDimension.values(),
                        AllMetrics.ThreadPoolValue.values(),
                        createFileHandler(metricPathMap.get(AllMetrics.MetricName.THREAD_POOL))));
        metricName2Property.put(
                AllMetrics.MetricName.SHARD_STATS,
                new MetricProperties(
                        AllMetrics.ShardStatsDerivedDimension.values(),
                        MetricProperties.EMPTY_DIMENSION,
                        AllMetrics.ShardStatsValue.values(),
                        new ShardStatFileHandler()));
        metricName2Property.put(
                AllMetrics.MetricName.MASTER_PENDING,
                new MetricProperties(
                        MetricProperties.EMPTY_DIMENSION,
                        AllMetrics.MasterPendingValue.values(),
                        createFileHandler(
                                metricPathMap.get(AllMetrics.MetricName.MASTER_PENDING),
                                PerformanceAnalyzerMetrics.MASTER_CURRENT,
                                PerformanceAnalyzerMetrics.MASTER_META_DATA)));
        metricName2Property.put(
                AllMetrics.MetricName.MOUNTED_PARTITION_METRICS,
                new MetricProperties(
                        AllMetrics.DevicePartitionDimension.values(),
                        AllMetrics.DevicePartitionValue.values(),
                        createFileHandler(
                                metricPathMap.get(
                                        AllMetrics.MetricName.MOUNTED_PARTITION_METRICS))));
        metricName2Property.put(
                AllMetrics.MetricName.CLUSTER_APPLIER_SERVICE,
                new MetricProperties(
                        MetricProperties.EMPTY_DIMENSION,
                        AllMetrics.ClusterApplierServiceStatsValue.values(),
                        createFileHandler(
                                metricPathMap.get(AllMetrics.MetricName.CLUSTER_APPLIER_SERVICE))));
        metricName2Property.put(
                AllMetrics.MetricName.ADMISSION_CONTROL_METRICS,
                new MetricProperties(
                        AllMetrics.AdmissionControlDimension.values(),
                        AllMetrics.AdmissionControlValue.values(),
                        createFileHandler(
                                metricPathMap.get(
                                        AllMetrics.MetricName.ADMISSION_CONTROL_METRICS))));
        metricName2Property.put(
                AllMetrics.MetricName.SHARD_INDEXING_PRESSURE,
                new MetricProperties(
                        AllMetrics.ShardIndexingPressureDimension.values(),
                        AllMetrics.ShardIndexingPressureValue.values(),
                        createFileHandler(
                                metricPathMap.get(AllMetrics.MetricName.SHARD_INDEXING_PRESSURE))));
    }

    public static MetricPropertiesConfig getInstance() {
        return INSTANCE;
    }

    public MetricProperties getProperty(AllMetrics.MetricName name) {
        return metricName2Property.get(name);
    }

    public Map<AllMetrics.MetricName, String> getMetricPathMap() {
        return metricPathMap;
    }

    Map<String, AllMetrics.MetricName> getEventKeyToMetricNameMap() {
        return eventKeyToMetricNameMap;
    }

    @VisibleForTesting
    Map<AllMetrics.MetricName, MetricProperties> getMetricName2Property() {
        return metricName2Property;
    }
}
