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

package org.wso2.carbon.core.shared.dao.models;

import java.util.Date;

/**
 * DAO class for property name.
 */
public class PropertyName {

    private String id;
    private final String name;
    private Date lastUpdated;

    public PropertyName(String id, String name, Date lastUpdated) {

        this.id = id;
        this.name = name;
        this.lastUpdated = lastUpdated;
    }

    public PropertyName(String name) {

        this.name = name;
    }

    /**
     * Get the property name.
     *
     * @return Property name.
     */
    public String getId() {

        return id;
    }

    /**
     * Get the property name.
     *
     * @return Property name.
     */
    public String getName() {

        return name;
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
