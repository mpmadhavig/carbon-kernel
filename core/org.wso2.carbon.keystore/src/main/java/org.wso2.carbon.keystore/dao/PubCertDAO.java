package org.wso2.carbon.keystore.dao;

import org.wso2.carbon.keystore.exception.KeyStoreException;
import org.wso2.carbon.keystore.model.PubCertModel;

import java.util.Optional;

public abstract class PubCertDAO {

    private final int tenantId;

    public PubCertDAO(int tenantId) {
        this.tenantId = tenantId;
    }

    public abstract String addPubCert(PubCertModel pubCertModel) throws KeyStoreException;

    public abstract Optional<PubCertModel> getPubCert(String uuid) throws KeyStoreException;

    public int getTenantId() {

        return tenantId;
    }
}
