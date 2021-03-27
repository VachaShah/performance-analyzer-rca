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

package com.amazon.opendistro.opensearch.performanceanalyzer.rca.store.rca.hotshard;

public class NodeShardKey {
    private final String nodeId;
    private final String shardId;

    public NodeShardKey(String nodeId, String shardId) {
        this.nodeId = nodeId;
        this.shardId = shardId;
    }

    public String getNodeId() {
        return this.nodeId;
    }

    public String getShardId() {
        return this.shardId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof NodeShardKey) {
            NodeShardKey key = (NodeShardKey) obj;
            return nodeId.equals(key.nodeId) && shardId.equals(key.shardId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return nodeId.hashCode() * 31 + shardId.hashCode();
    }

    @Override
    public String toString() {
        return String.join(" ", new String[] {this.nodeId, this.shardId});
    }
}
