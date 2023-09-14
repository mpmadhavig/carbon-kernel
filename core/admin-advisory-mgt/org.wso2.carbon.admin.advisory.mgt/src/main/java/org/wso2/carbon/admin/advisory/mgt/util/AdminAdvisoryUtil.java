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

package org.wso2.carbon.admin.advisory.mgt.util;

import org.wso2.carbon.admin.advisory.mgt.exception.AdminAdvisoryMgtException;
import org.wso2.carbon.admin.advisory.mgt.internal.AdminAdvisoryManagementDataHolder;
import org.wso2.carbon.user.api.Tenant;
import org.wso2.carbon.user.api.TenantManager;
import org.wso2.carbon.user.api.UserStoreException;
import org.wso2.carbon.user.core.service.RealmService;
import org.wso2.carbon.utils.multitenancy.MultitenantConstants;

/**
 * This class is used to persist and retrieve registry resources.
 */
public class AdminAdvisoryUtil {

    private static final RealmService realmService = AdminAdvisoryManagementDataHolder.getInstance().getRealmService();

    private AdminAdvisoryUtil() {
    }

    /**
     * This method is used to get the tenant UUID for the given tenant ID.
     *
     * @param tenantDomain Tenant domain
     * @return Tenant UUID
     * @throws AdminAdvisoryMgtException If an error occurs while getting the tenant UUID.
     */
    public static String getTenantUUID(String tenantDomain) throws AdminAdvisoryMgtException {

        try {
            int tenantId = realmService.getTenantManager().getTenantId(tenantDomain);

            // Super tenant does not have a tenant UUID. Therefore, set a hard coded value.
            if (tenantId == MultitenantConstants.SUPER_TENANT_ID) {
                // Set a hard length of 36 characters for super tenant ID.
                // This is to avoid the database column length constraint violation.
                return String.format("%1$-36d", tenantId);
            }

            if (tenantId != MultitenantConstants.INVALID_TENANT_ID) {
                TenantManager tenantManager = realmService.getTenantManager();
                Tenant tenant = tenantManager.getTenant(tenantId);
                return tenant.getTenantUniqueID();
            }
        } catch (UserStoreException e) {
                throw new AdminAdvisoryMgtException("Error while getting the tenant UUID for tenant domain: " +
                        tenantDomain, e);
        }

        throw new AdminAdvisoryMgtException("Invalid tenant domain: " + tenantDomain);
    }
}
