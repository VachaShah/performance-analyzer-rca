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

package com.amazon.opendistro.opensearch.performanceanalyzer.model;


import com.amazon.opendistro.opensearch.performanceanalyzer.metrics.AllMetrics;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MetricsModel {

    public static final Map<String, MetricAttributes> ALL_METRICS;

    static {
        Map<String, MetricAttributes> allMetricsInitializer = new HashMap<>();
        // OS Metrics
        allMetricsInitializer.put(
                AllMetrics.OSMetrics.CPU_UTILIZATION.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.CORES.toString(),
                        AllMetrics.AggregatedOSDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.OSMetrics.PAGING_MAJ_FLT_RATE.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT_PER_SEC.toString(),
                        AllMetrics.AggregatedOSDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.OSMetrics.PAGING_MIN_FLT_RATE.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT_PER_SEC.toString(),
                        AllMetrics.AggregatedOSDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.OSMetrics.PAGING_RSS.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.PAGES.toString(),
                        AllMetrics.AggregatedOSDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.OSMetrics.SCHED_RUNTIME.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.SEC_PER_CONTEXT_SWITCH.toString(),
                        AllMetrics.AggregatedOSDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.OSMetrics.SCHED_WAITTIME.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.SEC_PER_CONTEXT_SWITCH.toString(),
                        AllMetrics.AggregatedOSDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.OSMetrics.SCHED_CTX_RATE.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT_PER_SEC.toString(),
                        AllMetrics.AggregatedOSDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.OSMetrics.HEAP_ALLOC_RATE.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.BYTE_PER_SEC.toString(),
                        AllMetrics.AggregatedOSDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.OSMetrics.IO_READ_THROUGHPUT.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.BYTE_PER_SEC.toString(),
                        AllMetrics.AggregatedOSDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.OSMetrics.IO_WRITE_THROUGHPUT.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.BYTE_PER_SEC.toString(),
                        AllMetrics.AggregatedOSDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.OSMetrics.IO_TOT_THROUGHPUT.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.BYTE_PER_SEC.toString(),
                        AllMetrics.AggregatedOSDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.OSMetrics.IO_READ_SYSCALL_RATE.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT_PER_SEC.toString(),
                        AllMetrics.AggregatedOSDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.OSMetrics.IO_WRITE_SYSCALL_RATE.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT_PER_SEC.toString(),
                        AllMetrics.AggregatedOSDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.OSMetrics.IO_TOTAL_SYSCALL_RATE.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT_PER_SEC.toString(),
                        AllMetrics.AggregatedOSDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.OSMetrics.THREAD_BLOCKED_TIME.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.SEC_PER_EVENT.toString(),
                        AllMetrics.AggregatedOSDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.OSMetrics.THREAD_BLOCKED_EVENT.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.AggregatedOSDimension.values()));

        // Latency Metric
        allMetricsInitializer.put(
                AllMetrics.CommonMetric.LATENCY.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.MILLISECOND.toString(),
                        AllMetrics.LatencyDimension.values()));

        allMetricsInitializer.put(
                AllMetrics.ShardOperationMetric.SHARD_OP_COUNT.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.AggregatedOSDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardBulkMetric.DOC_COUNT.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.AggregatedOSDimension.values()));

        // HTTP Metrics
        allMetricsInitializer.put(
                AllMetrics.HttpMetric.HTTP_REQUEST_DOCS.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.HttpOnlyDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.HttpMetric.HTTP_TOTAL_REQUESTS.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.HttpOnlyDimension.values()));

        // Cache Max Size Metrics
        allMetricsInitializer.put(
                AllMetrics.CacheConfigValue.CACHE_MAX_SIZE.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.BYTE.toString(),
                        AllMetrics.CacheConfigDimension.values()));

        // Circuit Breaker Metrics
        allMetricsInitializer.put(
                AllMetrics.CircuitBreakerValue.CB_ESTIMATED_SIZE.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.BYTE.toString(),
                        AllMetrics.CircuitBreakerDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.CircuitBreakerValue.CB_CONFIGURED_SIZE.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.BYTE.toString(),
                        AllMetrics.CircuitBreakerDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.CircuitBreakerValue.CB_TRIPPED_EVENTS.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.CircuitBreakerDimension.values()));

        // Heap Metrics
        allMetricsInitializer.put(
                AllMetrics.HeapValue.GC_COLLECTION_EVENT.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.HeapDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.HeapValue.GC_COLLECTION_TIME.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.MILLISECOND.toString(),
                        AllMetrics.HeapDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.HeapValue.HEAP_COMMITTED.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.BYTE.toString(), AllMetrics.HeapDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.HeapValue.HEAP_INIT.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.BYTE.toString(), AllMetrics.HeapDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.HeapValue.HEAP_MAX.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.BYTE.toString(), AllMetrics.HeapDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.HeapValue.HEAP_USED.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.BYTE.toString(), AllMetrics.HeapDimension.values()));

        // Disk Metrics
        allMetricsInitializer.put(
                AllMetrics.DiskValue.DISK_UTILIZATION.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.PERCENT.toString(),
                        AllMetrics.DiskDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.DiskValue.DISK_WAITTIME.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.MILLISECOND.toString(),
                        AllMetrics.DiskDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.DiskValue.DISK_SERVICE_RATE.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.MEGABYTE_PER_SEC.toString(),
                        AllMetrics.DiskDimension.values()));

        // TCP Metrics
        allMetricsInitializer.put(
                AllMetrics.TCPValue.Net_TCP_NUM_FLOWS.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(), AllMetrics.TCPDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.TCPValue.Net_TCP_TXQ.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.SEGMENT_PER_FLOW.toString(),
                        AllMetrics.TCPDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.TCPValue.Net_TCP_RXQ.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.SEGMENT_PER_FLOW.toString(),
                        AllMetrics.TCPDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.TCPValue.Net_TCP_LOST.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.SEGMENT_PER_FLOW.toString(),
                        AllMetrics.TCPDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.TCPValue.Net_TCP_SEND_CWND.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.BYTE_PER_FLOW.toString(),
                        AllMetrics.TCPDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.TCPValue.Net_TCP_SSTHRESH.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.BYTE_PER_FLOW.toString(),
                        AllMetrics.TCPDimension.values()));

        // IP Metrics
        allMetricsInitializer.put(
                AllMetrics.IPValue.NET_PACKET_RATE4.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.PACKET_PER_SEC.toString(),
                        AllMetrics.IPDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.IPValue.NET_PACKET_DROP_RATE4.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.PACKET_PER_SEC.toString(),
                        AllMetrics.IPDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.IPValue.NET_PACKET_RATE6.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.PACKET_PER_SEC.toString(),
                        AllMetrics.IPDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.IPValue.NET_PACKET_DROP_RATE6.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.PACKET_PER_SEC.toString(),
                        AllMetrics.IPDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.IPValue.NET_THROUGHPUT.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.BYTE_PER_SEC.toString(),
                        AllMetrics.IPDimension.values()));

        // Thread Pool Metrics
        allMetricsInitializer.put(
                AllMetrics.ThreadPoolValue.THREADPOOL_QUEUE_SIZE.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.ThreadPoolDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ThreadPoolValue.THREADPOOL_REJECTED_REQS.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.ThreadPoolDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ThreadPoolValue.THREADPOOL_TOTAL_THREADS.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.ThreadPoolDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ThreadPoolValue.THREADPOOL_ACTIVE_THREADS.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.ThreadPoolDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ThreadPoolValue.THREADPOOL_QUEUE_LATENCY.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.ThreadPoolDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ThreadPoolValue.THREADPOOL_QUEUE_CAPACITY.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.ThreadPoolDimension.values()));

        // Shard Stats Metrics
        allMetricsInitializer.put(
                AllMetrics.ShardStatsValue.INDEXING_THROTTLE_TIME.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.MILLISECOND.toString(),
                        AllMetrics.ShardStatsDerivedDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardStatsValue.CACHE_QUERY_HIT.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.ShardStatsDerivedDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardStatsValue.CACHE_QUERY_MISS.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.ShardStatsDerivedDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardStatsValue.CACHE_QUERY_SIZE.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.BYTE.toString(),
                        AllMetrics.ShardStatsDerivedDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardStatsValue.CACHE_FIELDDATA_EVICTION.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.ShardStatsDerivedDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardStatsValue.CACHE_FIELDDATA_SIZE.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.BYTE.toString(),
                        AllMetrics.ShardStatsDerivedDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardStatsValue.CACHE_REQUEST_HIT.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.ShardStatsDerivedDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardStatsValue.CACHE_REQUEST_MISS.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.ShardStatsDerivedDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardStatsValue.CACHE_REQUEST_EVICTION.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.ShardStatsDerivedDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardStatsValue.CACHE_REQUEST_SIZE.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.BYTE.toString(),
                        AllMetrics.ShardStatsDerivedDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardStatsValue.REFRESH_EVENT.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.ShardStatsDerivedDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardStatsValue.REFRESH_TIME.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.MILLISECOND.toString(),
                        AllMetrics.ShardStatsDerivedDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardStatsValue.FLUSH_EVENT.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.ShardStatsDerivedDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardStatsValue.FLUSH_TIME.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.MILLISECOND.toString(),
                        AllMetrics.ShardStatsDerivedDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardStatsValue.MERGE_EVENT.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.ShardStatsDerivedDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardStatsValue.MERGE_TIME.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.MILLISECOND.toString(),
                        AllMetrics.ShardStatsDerivedDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardStatsValue.MERGE_CURRENT_EVENT.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.ShardStatsDerivedDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardStatsValue.INDEXING_BUFFER.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.BYTE.toString(),
                        AllMetrics.ShardStatsDerivedDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardStatsValue.SEGMENTS_TOTAL.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.ShardStatsDerivedDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardStatsValue.SEGMENTS_MEMORY.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.BYTE.toString(),
                        AllMetrics.ShardStatsDerivedDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardStatsValue.TERMS_MEMORY.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.BYTE.toString(),
                        AllMetrics.ShardStatsDerivedDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardStatsValue.STORED_FIELDS_MEMORY.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.BYTE.toString(),
                        AllMetrics.ShardStatsDerivedDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardStatsValue.TERM_VECTOR_MEMORY.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.BYTE.toString(),
                        AllMetrics.ShardStatsDerivedDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardStatsValue.NORMS_MEMORY.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.BYTE.toString(),
                        AllMetrics.ShardStatsDerivedDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardStatsValue.POINTS_MEMORY.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.BYTE.toString(),
                        AllMetrics.ShardStatsDerivedDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardStatsValue.DOC_VALUES_MEMORY.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.BYTE.toString(),
                        AllMetrics.ShardStatsDerivedDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardStatsValue.INDEX_WRITER_MEMORY.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.BYTE.toString(),
                        AllMetrics.ShardStatsDerivedDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardStatsValue.VERSION_MAP_MEMORY.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.BYTE.toString(),
                        AllMetrics.ShardStatsDerivedDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardStatsValue.BITSET_MEMORY.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.BYTE.toString(),
                        AllMetrics.ShardStatsDerivedDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardStatsValue.SHARD_SIZE_IN_BYTES.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.BYTE.toString(),
                        AllMetrics.ShardStatsDerivedDimension.values()));

        // Master Metrics
        allMetricsInitializer.put(
                AllMetrics.MasterPendingValue.MASTER_PENDING_QUEUE_SIZE.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.EmptyDimension.values()));

        allMetricsInitializer.put(
                AllMetrics.MasterMetricValues.MASTER_TASK_QUEUE_TIME.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.MILLISECOND.toString(),
                        AllMetrics.MasterMetricDimensions.values()));

        allMetricsInitializer.put(
                AllMetrics.MasterMetricValues.MASTER_TASK_RUN_TIME.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.MILLISECOND.toString(),
                        AllMetrics.MasterMetricDimensions.values()));

        allMetricsInitializer.put(
                AllMetrics.FaultDetectionMetric.FOLLOWER_CHECK_LATENCY.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.MILLISECOND.toString(),
                        AllMetrics.FaultDetectionDimension.values()));

        allMetricsInitializer.put(
                AllMetrics.FaultDetectionMetric.LEADER_CHECK_LATENCY.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.MILLISECOND.toString(),
                        AllMetrics.FaultDetectionDimension.values()));

        allMetricsInitializer.put(
                AllMetrics.FaultDetectionMetric.FOLLOWER_CHECK_FAILURE.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.FaultDetectionDimension.values()));

        allMetricsInitializer.put(
                AllMetrics.FaultDetectionMetric.LEADER_CHECK_FAILURE.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.FaultDetectionDimension.values()));

        allMetricsInitializer.put(
                AllMetrics.MasterThrottlingValue.MASTER_THROTTLED_PENDING_TASK_COUNT.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.EmptyDimension.values()));

        allMetricsInitializer.put(
                AllMetrics.MasterThrottlingValue.DATA_RETRYING_TASK_COUNT.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.EmptyDimension.values()));

        allMetricsInitializer.put(
                AllMetrics.ShardStateValue.SHARD_STATE.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.ShardStateDimension.values()));

        allMetricsInitializer.put(
                AllMetrics.ClusterApplierServiceStatsValue.CLUSTER_APPLIER_SERVICE_LATENCY
                        .toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.MILLISECOND.toString(),
                        AllMetrics.EmptyDimension.values()));

        allMetricsInitializer.put(
                AllMetrics.ClusterApplierServiceStatsValue.CLUSTER_APPLIER_SERVICE_FAILURE
                        .toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.EmptyDimension.values()));

        allMetricsInitializer.put(
                AllMetrics.AdmissionControlValue.REJECTION_COUNT.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.AdmissionControlDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.AdmissionControlValue.THRESHOLD_VALUE.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.AdmissionControlDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.AdmissionControlValue.CURRENT_VALUE.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.AdmissionControlDimension.values()));

        // Shard Indexing Pressure Metrics
        allMetricsInitializer.put(
                AllMetrics.ShardIndexingPressureValue.REJECTION_COUNT.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT.toString(),
                        AllMetrics.ShardIndexingPressureDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardIndexingPressureValue.CURRENT_BYTES.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.BYTE.toString(),
                        AllMetrics.ShardIndexingPressureDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardIndexingPressureValue.CURRENT_LIMITS.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.BYTE.toString(),
                        AllMetrics.ShardIndexingPressureDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardIndexingPressureValue.AVERAGE_WINDOW_THROUGHPUT.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.COUNT_PER_SEC.toString(),
                        AllMetrics.ShardIndexingPressureDimension.values()));
        allMetricsInitializer.put(
                AllMetrics.ShardIndexingPressureValue.LAST_SUCCESSFUL_TIMESTAMP.toString(),
                new MetricAttributes(
                        AllMetrics.MetricUnits.MILLISECOND.toString(),
                        AllMetrics.ShardIndexingPressureDimension.values()));

        ALL_METRICS = Collections.unmodifiableMap(allMetricsInitializer);
    }
}
