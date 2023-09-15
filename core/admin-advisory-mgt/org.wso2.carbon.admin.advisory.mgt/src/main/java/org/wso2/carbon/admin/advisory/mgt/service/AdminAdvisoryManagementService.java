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

package org.wso2.carbon.admin.advisory.mgt.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.CarbonException;
import org.wso2.carbon.admin.advisory.mgt.constants.AdminAdvisoryManagementConstants;
import org.wso2.carbon.admin.advisory.mgt.dto.AdminAdvisoryBannerDTO;
import org.wso2.carbon.admin.advisory.mgt.exception.AdminAdvisoryMgtException;
import org.wso2.carbon.admin.advisory.mgt.util.AdminAdvisoryUtil;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.core.shared.dao.impl.PropertyDAOImpl;
import org.wso2.carbon.core.shared.dao.models.PropertyName;
import org.wso2.carbon.core.shared.dao.models.PropertyValue;

/**
 * This service is to configure the Admin Advisory Management functionality.
 */
public class AdminAdvisoryManagementService {

    protected static final Log LOG = LogFactory.getLog(AdminAdvisoryManagementService.class);
    private final PropertyDAOImpl propertyDAO = new PropertyDAOImpl();

    /**
     * This method is used to save the Admin advisory banner configurations which is specific to tenant.
     *
     * @param adminAdvisoryBanner Admin advisory banner to be saved.
     */
    public void saveAdminAdvisoryConfig(AdminAdvisoryBannerDTO adminAdvisoryBanner) throws AdminAdvisoryMgtException {

        String tenantDomain = PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantDomain();
        String tenantUUID = AdminAdvisoryUtil.getTenantUUID(tenantDomain);
        PropertyValue enableBannerProperty = new PropertyValue(
                tenantUUID, new PropertyName(AdminAdvisoryManagementConstants.ENABLE_BANNER),
                    String.valueOf(adminAdvisoryBanner.getEnableBanner()), null);
        PropertyValue contentProperty = new PropertyValue(
                tenantUUID, new PropertyName(AdminAdvisoryManagementConstants.BANNER_CONTENT),
                    adminAdvisoryBanner.getBannerContent(), null);
        try {
            propertyDAO.addProperty(enableBannerProperty);
            propertyDAO.addProperty(contentProperty);
        } catch (CarbonException e) {
            throw new AdminAdvisoryMgtException("Error while saving the property value in database.", e);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Admin advisory banner configuration saved successfully for tenant: " + tenantDomain);
        }
    }

    /**
     * This method is used to load the tenant specific Admin advisory banner configurations.
     *
     * @return AdminAdvisoryBannerDTO object.
     */
    public AdminAdvisoryBannerDTO getAdminAdvisoryConfig() throws AdminAdvisoryMgtException {

        AdminAdvisoryBannerDTO adminAdvisoryBanner = new AdminAdvisoryBannerDTO();
        String tenantDomain = PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantDomain();
        String tenantUUID = AdminAdvisoryUtil.getTenantUUID(tenantDomain);
        try {
            PropertyValue enableBannerProperty = propertyDAO.getProperty(tenantUUID, new PropertyName(
                    AdminAdvisoryManagementConstants.ENABLE_BANNER));
            PropertyValue contentProperty = propertyDAO.getProperty(tenantUUID, new PropertyName(
                    AdminAdvisoryManagementConstants.BANNER_CONTENT));

            adminAdvisoryBanner.setEnableBanner(Boolean.parseBoolean(enableBannerProperty.getValue()));
            adminAdvisoryBanner.setBannerContent(contentProperty.getValue());
        } catch (CarbonException e) {
            throw new AdminAdvisoryMgtException("Error while retrieving the property value from database.", e);
        }

        return adminAdvisoryBanner;
    }
}
