/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
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

package org.wso2.carbon.identity.application.mgt.bridge.model;

import org.apache.commons.lang.StringUtils;
import org.wso2.carbon.identity.application.common.model.ClaimConfig;
import org.wso2.carbon.identity.application.common.model.InboundAuthenticationConfig;
import org.wso2.carbon.identity.application.common.model.InboundProvisioningConfig;
import org.wso2.carbon.identity.application.common.model.LocalAndOutboundAuthenticationConfig;
import org.wso2.carbon.identity.application.common.model.OutboundProvisioningConfig;
import org.wso2.carbon.identity.application.common.model.PermissionsAndRoleConfig;
import org.wso2.carbon.identity.application.common.model.RequestPathAuthenticatorConfig;
import org.wso2.carbon.identity.application.common.model.ServiceProvider;
import org.wso2.carbon.identity.application.common.model.ServiceProviderProperty;
import org.wso2.carbon.identity.application.common.model.User;

import static org.wso2.carbon.identity.application.mgt.bridge.util.Constants.UNIQUE_ID_PREFIX;
import static org.wso2.carbon.identity.core.util.IdentityUtil.base58Encode;

/**
 * Extended service provider implementation for Application REST API
 */
public class ExtendedServiceProvider extends ServiceProvider {

    private ServiceProvider serviceProvider;

    private String uniqueId;

    public ExtendedServiceProvider(ServiceProvider serviceProvider) {

        this.serviceProvider = serviceProvider;
    }

    /**
     * Get the unique identifier of the service provider.
     *
     * @return unique identifier of the service provider
     */
    public String getUniqueId() {

        if (StringUtils.isBlank(uniqueId)) {
            if (getApplicationID() > 0) {
                uniqueId = base58Encode((UNIQUE_ID_PREFIX + getApplicationID()).getBytes());
            } else {
                //This is a file based SP
                uniqueId = base58Encode((UNIQUE_ID_PREFIX + getApplicationName()).getBytes());
            }
        }
        return uniqueId;
    }

    public int getApplicationID() {

        return serviceProvider.getApplicationID();
    }

    public InboundAuthenticationConfig getInboundAuthenticationConfig() {

        return serviceProvider.getInboundAuthenticationConfig();
    }

    public LocalAndOutboundAuthenticationConfig getLocalAndOutBoundAuthenticationConfig() {

        return serviceProvider.getLocalAndOutBoundAuthenticationConfig();
    }

    public RequestPathAuthenticatorConfig[] getRequestPathAuthenticatorConfigs() {

        return serviceProvider.getRequestPathAuthenticatorConfigs();
    }

    public InboundProvisioningConfig getInboundProvisioningConfig() {

        return serviceProvider.getInboundProvisioningConfig();
    }

    public OutboundProvisioningConfig getOutboundProvisioningConfig() {

        return serviceProvider.getOutboundProvisioningConfig();
    }

    public ClaimConfig getClaimConfig() {

        return serviceProvider.getClaimConfig();
    }

    public PermissionsAndRoleConfig getPermissionAndRoleConfig() {

        return serviceProvider.getPermissionAndRoleConfig();
    }

    public String getApplicationName() {

        return serviceProvider.getApplicationName();
    }

    public User getOwner() {

        return serviceProvider.getOwner();
    }

    public String getDescription() {

        return serviceProvider.getDescription();
    }

    public boolean isSaasApp() {

        return serviceProvider.isSaasApp();
    }

    public ServiceProviderProperty[] getSpProperties() {

        return serviceProvider.getSpProperties();
    }

    public String getCertificateContent() {

        return serviceProvider.getCertificateContent();
    }
}
