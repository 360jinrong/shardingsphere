/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.infra.route;

import org.apache.shardingsphere.infra.route.context.RouteContext;
import org.apache.shardingsphere.infra.route.context.RouteMapper;
import org.apache.shardingsphere.infra.route.context.RouteUnit;
import org.apache.shardingsphere.infra.sql.LogicSQL;
import org.apache.shardingsphere.sql.parser.sql.common.statement.SQLStatement;
import org.apache.shardingsphere.sql.parser.sql.dialect.statement.mysql.dal.MySQLShowTablesStatement;

import java.util.Collections;

/**
 * Unconfigured schema SQL router.
 */
public final class UnconfiguredSchemaSQLRouter {
    
    /**
     * Decorate route context.
     *
     * @param routeContext route context
     * @param logicSQL logic SQL
     */
    public void decorate(final RouteContext routeContext, final LogicSQL logicSQL) {
        if (isNeedUnconfiguredSchema(logicSQL.getSqlStatementContext().getSqlStatement())) {
            for (String each : logicSQL.getSchema().getMetaData().getRuleSchemaMetaData().getUnconfiguredSchemaMetaDataMap().keySet()) {
                routeContext.getRouteUnits().add(new RouteUnit(new RouteMapper(each, each), Collections.emptyList()));
            }
        }
    }
    
    // TODO use dynamic config to judge UnconfiguredSchema
    private boolean isNeedUnconfiguredSchema(final SQLStatement sqlStatement) {
        return sqlStatement instanceof MySQLShowTablesStatement;
    }
}
