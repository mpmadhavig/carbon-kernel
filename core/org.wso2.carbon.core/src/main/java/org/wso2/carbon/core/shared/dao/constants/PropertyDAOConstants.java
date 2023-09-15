/*
 * Copyright (c) 2023, WSO2 LLC. (https://www.wso2.com).
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

package org.wso2.carbon.core.shared.dao.constants;

/**
 * Constants related to the PropertyDAO.
 */
public class PropertyDAOConstants {

    public static final String PROPERTY_VALUE_DATASOURCE = "jdbc/SHARED_DB";

    private PropertyDAOConstants() {

    }

    public static class PropertyTableColumns {

        private PropertyTableColumns() {

        }

        public static final String ID = "ID";
        public static final String TENANT_ID = "TENANT_UUID";
        public static final String NAME = "PROPERTY_NAME_ID";
        public static final String VALUE = "PROPERTY_VALUE";
        public static final String LAST_UPDATED = "LAST_UPDATED";
    }

    public static class SqlQueries {

        private SqlQueries() {

        }

        public static final String ADD_PROPERTY = "INSERT INTO SHRD_PROPERTY_VALUE " +
                "(ID, TENANT_UUID, PROPERTY_NAME_ID, PROPERTY_VALUE, LAST_UPDATED) " +
                " VALUES (:ID;, :TENANT_UUID;, :PROPERTY_NAME_ID;, :PROPERTY_VALUE;, :LAST_UPDATED;)";

        public static final String UPDATE_PROPERTY = "UPDATE SHRD_PROPERTY_VALUE SET " +
                "PROPERTY_VALUE = :PROPERTY_VALUE;, LAST_UPDATED = :LAST_UPDATED; " +
                "WHERE TENANT_UUID = :TENANT_UUID; AND PROPERTY_NAME_ID = :PROPERTY_NAME_ID;";

        public static final String GET_PROPERTY = "SELECT * FROM SHRD_PROPERTY_VALUE " +
                "WHERE TENANT_UUID = :TENANT_UUID; AND PROPERTY_NAME_ID = :PROPERTY_NAME_ID;";

    }

    public static class ErrorMessages {

        private ErrorMessages() {

        }

        public static final String ERROR_ADD_PROPERTY = "Error while adding property.";
        public static final String ERROR_GET_PROPERTY = "Error while retrieving the property.";
        public static final String ERROR_UPDATE_PROPERTY = "Error while updating property.";
        public static final String ERROR_CANNOT_RETRIEVE_DB_CONN = "Error while retrieving database connection.";
    }
}
