/*
 * Copyright (c) 2023, WSO2 LLC. (http://www.wso2.com).
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.core.common.dao.models;

import java.util.Date;

/**
 * DAO class for property value.
 */
public class PropertyValue {

    private String id;
    private final String tenantUUID;
    private final PropertyName name;
    private final String value;
    private final Date lastUpdated;

    public PropertyValue(String id, String tenantUUID, PropertyName name, String value, Date lastUpdated) {

        this.id = id;
        this.tenantUUID = tenantUUID;
        this.name = name;
        this.value = value;
        this.lastUpdated = lastUpdated;
    }

    public PropertyValue(String tenantUUID, PropertyName name, String value, Date lastUpdated) {

        this.tenantUUID = tenantUUID;
        this.name = name;
        this.value = value;
        this.lastUpdated = lastUpdated;
    }

    /**
     * Get the property value id.
     *
     * @return Property value id.
     */
    public String getId() {

        return id;
    }

    /**
     * Get the tenant UUID.
     *
     * @return Tenant UUID.
     */
    public String getTenantUUID() {

        return tenantUUID;
    }

    /**
     * Get the property name.
     *
     * @return Property name.
     */
    public PropertyName getName() {

        return name;
    }

    /**
     * Get the property value.
     *
     * @return Property value.
     */
    public String getValue() {

        return value;
    }

    /**
     * Get the property last updated time.
     *
     * @return Property last updated time.
     */
    public Date getLastUpdated() {

        return lastUpdated;
    }
}
