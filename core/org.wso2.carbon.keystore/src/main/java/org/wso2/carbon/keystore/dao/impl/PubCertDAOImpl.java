package org.wso2.carbon.keystore.dao.impl;

import org.wso2.carbon.database.utils.jdbc.NamedPreparedStatement;
import org.wso2.carbon.keystore.dao.constants.PubCertDAOConstants;
import org.wso2.carbon.keystore.dao.PubCertDAO;
import org.wso2.carbon.keystore.exception.KeyStoreException;
import org.wso2.carbon.keystore.model.PubCertModel;
import org.wso2.carbon.keystore.util.KeyStoreDatabaseUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import static org.wso2.carbon.keystore.dao.DAOUtils.getTenantUUID;

public class PubCertDAOImpl extends PubCertDAO {

    private final String tenantUUID;

    public PubCertDAOImpl(int tenantId) throws KeyStoreException {
        super(tenantId);
        this.tenantUUID = getTenantUUID(tenantId);
    }

    @Override
    public String addPubCert(PubCertModel pubCertModel) throws KeyStoreException {

        try (Connection connection = KeyStoreDatabaseUtil.getDBConnection(true)) {
            try {
                String uuid = processAddPubCert(connection, pubCertModel, tenantUUID);
                KeyStoreDatabaseUtil.commitTransaction(connection);
                return uuid;
            } catch (SQLException e) {
                KeyStoreDatabaseUtil.rollbackTransaction(connection);
                // TODO: Check whether this exception type is okay. Also see if we need to use a server exception type. i.e something like SecurityConfigServerException
                throw new KeyStoreException("Error while adding public certificate.", e);
            }
        } catch (SQLException e) {
            // TODO: Check whether this exception type is okay. Also see if we need to use a server exception type. i.e something like SecurityConfigServerException
            throw new KeyStoreException("Error while adding public certificate.", e);
        }
    }

    @Override
    public Optional<PubCertModel> getPubCert(String uuid) throws KeyStoreException {

        PubCertModel pubCertModel = null;

        try (Connection connection = KeyStoreDatabaseUtil.getDBConnection(false)) {
            try (NamedPreparedStatement statement = new NamedPreparedStatement(connection,
                    PubCertDAOConstants.SQLQueries.GET_PUB_CERT)) {
                statement.setString(PubCertDAOConstants.PubCertTableColumns.ID, uuid);
                statement.setString(PubCertDAOConstants.PubCertTableColumns.TENANT_UUID, tenantUUID);
                // T
                statement.setMaxRows(1);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        pubCertModel = new PubCertModel();
                        pubCertModel.setFileNameAppender(resultSet.getString(
                                PubCertDAOConstants.PubCertTableColumns.FILE_NAME_APPENDER));
                        pubCertModel.setContent(resultSet.getBytes(PubCertDAOConstants.PubCertTableColumns.CONTENT));
                    }
                }
            } catch (SQLException e) {
                throw new KeyStoreException("Error while retrieving notification template types.", e);
            }
        } catch (SQLException e) {
            throw new KeyStoreException("Error while retrieving notification template types.", e);
        }
        return Optional.ofNullable(pubCertModel);
    }

    private String processAddPubCert(Connection connection, PubCertModel pubCertModel, String tenantUUID)
            throws SQLException {

        String id = UUID.randomUUID().toString();

        try (NamedPreparedStatement statement = new NamedPreparedStatement(connection,
                PubCertDAOConstants.SQLQueries.ADD_PUB_CERT)) {
            statement.setString(PubCertDAOConstants.PubCertTableColumns.ID, id);
            statement.setString(
                    PubCertDAOConstants.PubCertTableColumns.FILE_NAME_APPENDER, pubCertModel.getFileNameAppender());
            statement.setString("TENANT_UUID", tenantUUID);
            statement.setBytes(4, pubCertModel.getContent());
            statement.executeUpdate();
        }
        return id;
    }
}
