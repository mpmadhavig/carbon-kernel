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

package org.wso2.carbon.core.common.dao;

import org.wso2.carbon.CarbonException;
import org.wso2.carbon.core.common.dao.models.PropertyName;
import org.wso2.carbon.core.common.dao.models.PropertyValue;

public interface PropertyDAO {

    /**
     * Get the property value.
     *
     * @param propertyValue property value model containing all the values related to a property.
     * @throws CarbonException If an error occurs while retrieving the property value.
     */
    void addProperty(PropertyValue propertyValue) throws CarbonException;

    /**
     * Get the property value.
     *
     * @param tenantUUID Tenant UUID.
     * @param name       Property name.
     * @return Property value.
     * @throws CarbonException If an error occurs while retrieving the property value.
     */
    PropertyValue getProperty(String tenantUUID, PropertyName name) throws CarbonException;


    /**
     * Update the property value.
     *
     * @param propertyValue property value model containing all the values related to a property.
     * @throws CarbonException If an error occurs while updating the property value.
     */
    void updateProperty(PropertyValue propertyValue) throws CarbonException;

}
