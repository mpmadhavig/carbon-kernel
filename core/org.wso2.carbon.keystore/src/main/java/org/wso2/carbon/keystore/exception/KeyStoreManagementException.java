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

package org.wso2.carbon.keystore.exception;

/**
 * Handles the Keystore Management errors.
 */
public class KeyStoreManagementException extends Exception {

    private String errorCode = null;

    public KeyStoreManagementException(String message) {
        super(message);
    }

    public KeyStoreManagementException(String errorCode, String message) {

        super(message);
        this.errorCode = errorCode;
    }

    public KeyStoreManagementException(String message, Throwable cause) {
        super(message, cause);
    }

    public KeyStoreManagementException(String errorCode, String message, Throwable cause) {

        super(message, cause);
        this.errorCode = errorCode;
    }
}