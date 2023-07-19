package org.wso2.carbon.keystore.dao;

import org.wso2.carbon.keystore.exception.KeyStoreException;
import org.wso2.carbon.keystore.model.KeyStoreModel;

import java.util.List;
import java.util.Optional;

public abstract class KeyStoreDAO {

    // TODO: check whether converting this to a protected variable is better.
    private final int tenantId;

    public KeyStoreDAO(int tenantId) {
        this.tenantId = tenantId;
    }

    public abstract void addKeyStore(KeyStoreModel keyStoreModel) throws KeyStoreException;

    // TODO: think whether we need a method to see existence of a key store.

    public abstract List<KeyStoreModel> getKeyStores() throws KeyStoreException;

    public abstract Optional<KeyStoreModel> getKeyStore(String fileName) throws KeyStoreException;

    public abstract void deleteKeyStore(String fileName) throws KeyStoreException;

    public abstract void updateKeyStore(KeyStoreModel keyStoreModel) throws KeyStoreException;

    public abstract void addPubCertIdToKeyStore(String fileName, String pubCertId) throws KeyStoreException;

    public abstract Optional<String> getPubCertIdFromKeyStore(String fileName) throws KeyStoreException;

    public int getTenantId() {

        return tenantId;
    }


}
