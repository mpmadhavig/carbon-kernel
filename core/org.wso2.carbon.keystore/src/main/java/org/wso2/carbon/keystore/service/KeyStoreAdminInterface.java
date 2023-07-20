/*
 * Copyright (c) 2023, WSO2 LLC. (https://www.wso2.com) All Rights Reserved.
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.keystore.service;


import org.wso2.carbon.keystore.exception.KeyStoreException;
import org.wso2.carbon.keystore.model.KeyStoreData;

public interface KeyStoreAdminInterface {

    KeyStoreData[] getKeyStores() throws KeyStoreException;

    void addKeyStore(String file, String filename,
                     String password, String provider, String type, String pvtkeyPass) throws KeyStoreException;

    void addTrustStore(String file, String filename,
                       String password, String provider, String type) throws KeyStoreException;

    void deleteStore(String keyStoreName) throws KeyStoreException;

    void importCertToStore(String fileName, String fileData, String keyStoreName) throws KeyStoreException;

    String[] getStoreEntries(String keyStoreName) throws KeyStoreException;

    KeyStoreData getKeystoreInfo(String keyStoreName) throws KeyStoreException;

    void removeCertFromStore(String alias, String keyStoreName) throws KeyStoreException;

}
