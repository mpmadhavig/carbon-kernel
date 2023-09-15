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

package org.wso2.carbon.core.shared.dao.impl;

import org.wso2.carbon.CarbonException;
import org.wso2.carbon.core.internal.CarbonCoreDataHolder;
import org.wso2.carbon.core.shared.dao.PropertyDAO;
import org.wso2.carbon.core.shared.dao.constants.PropertyDAOConstants;
import org.wso2.carbon.core.shared.dao.constants.PropertyDAOConstants.PropertyTableColumns;
import org.wso2.carbon.core.shared.dao.models.PropertyName;
import org.wso2.carbon.core.shared.dao.models.PropertyValue;
import org.wso2.carbon.database.utils.jdbc.NamedPreparedStatement;
import org.wso2.carbon.user.core.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import javax.sql.DataSource;

import static java.time.ZoneOffset.UTC;

/**
 * Implementation class of property DAO.
 */
public class PropertyDAOImpl implements PropertyDAO {

    private final DataSource propertyValueDataSource = CarbonCoreDataHolder.getInstance().getCommonPropertyDataSource();
    private final Calendar CALENDAR = Calendar.getInstance(TimeZone.getTimeZone(UTC));

    /**
     * {@inheritDoc}
     */
    @Override
    public void addProperty(PropertyValue propertyValue) throws CarbonException {

        try (Connection connection = DatabaseUtil.getDBConnection(propertyValueDataSource)) {
            try {
                persistProperty(connection, propertyValue);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new CarbonException(PropertyDAOConstants.ErrorMessages.ERROR_ADD_PROPERTY, e);
            }
        } catch (SQLException e) {
            throw new CarbonException(PropertyDAOConstants.ErrorMessages.ERROR_CANNOT_RETRIEVE_DB_CONN, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PropertyValue getProperty(String tenantUUID, PropertyName name) throws CarbonException {

        PropertyValue propertyValue = null;
        try (Connection connection = DatabaseUtil.getDBConnection(propertyValueDataSource)) {
            try {
                propertyValue = retrieveProperty(connection, tenantUUID, name);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new CarbonException(PropertyDAOConstants.ErrorMessages.ERROR_ADD_PROPERTY, e);
            }
        } catch (SQLException e) {
            throw new CarbonException(PropertyDAOConstants.ErrorMessages.ERROR_CANNOT_RETRIEVE_DB_CONN, e);
        }
        return propertyValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateProperty(PropertyValue propertyValue) throws CarbonException {

        try (Connection connection = DatabaseUtil.getDBConnection(propertyValueDataSource)) {
            try {
                persistUpdateProperty(connection, propertyValue);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new CarbonException(PropertyDAOConstants.ErrorMessages.ERROR_UPDATE_PROPERTY, e);
            }
        } catch (SQLException e) {
            throw new CarbonException(PropertyDAOConstants.ErrorMessages.ERROR_CANNOT_RETRIEVE_DB_CONN, e);
        }
    }

    private void persistProperty(Connection connection, PropertyValue propertyValue)
            throws SQLException {

        try (NamedPreparedStatement statement = new NamedPreparedStatement(connection,
                PropertyDAOConstants.SqlQueries.ADD_PROPERTY)) {
            statement.setString(PropertyTableColumns.ID, UUID.randomUUID().toString());
            statement.setString(PropertyTableColumns.TENANT_ID, propertyValue.getTenantUUID());
            statement.setString(PropertyTableColumns.NAME, propertyValue.getName().getId());
            statement.setString(PropertyTableColumns.VALUE, propertyValue.getValue());
            statement.setTimeStamp(PropertyTableColumns.LAST_UPDATED, new Timestamp(new Date().getTime()),
                    CALENDAR);
            statement.executeUpdate();
        }
    }

    private PropertyValue retrieveProperty(Connection connection, String tenantUUID, PropertyName name)
            throws SQLException {

        PropertyValue propertyValue = null;
        try (NamedPreparedStatement statement = new NamedPreparedStatement(connection,
                PropertyDAOConstants.SqlQueries.GET_PROPERTY)) {
            statement.setString(PropertyTableColumns.TENANT_ID, tenantUUID);
            statement.setString(PropertyTableColumns.NAME, name.getId());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    propertyValue = new PropertyValue(
                            resultSet.getString(PropertyTableColumns.ID),
                            resultSet.getString(PropertyTableColumns.TENANT_ID),
                            new PropertyName(resultSet.getString(PropertyTableColumns.NAME)),
                            resultSet.getString(PropertyTableColumns.VALUE),
                            resultSet.getTimestamp(PropertyTableColumns.LAST_UPDATED)
                    );
                }
            }
        }
        return propertyValue;
    }

    private void persistUpdateProperty(Connection connection, PropertyValue propertyValue) throws SQLException {

        try (NamedPreparedStatement statement = new NamedPreparedStatement(connection,
                PropertyDAOConstants.SqlQueries.UPDATE_PROPERTY)) {
            statement.setString(PropertyTableColumns.VALUE, propertyValue.getValue());
            statement.setTimeStamp(PropertyTableColumns.LAST_UPDATED, new Timestamp(new Date().getTime()),
                    CALENDAR);
            statement.setString(PropertyTableColumns.TENANT_ID, propertyValue.getTenantUUID());
            statement.setString(PropertyTableColumns.NAME, propertyValue.getName().getId());
            statement.executeUpdate();
        }
    }
}
