/*
 * Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.wso2.carbon.identity.application.mgt.bridge.internal;

import org.wso2.carbon.identity.application.mgt.ApplicationManagementService;
import org.wso2.carbon.identity.application.mgt.bridge.ApplicationManagementBridgeService;
import org.wso2.carbon.identity.integration.c4b7a.BallerinaFunctionRegistration;
import org.wso2.carbon.identity.sso.saml.SSOServiceProviderConfigManager;
import org.wso2.carbon.user.core.service.RealmService;

public class ApplicationManagementBridgeServiceDataHolder {

    private static ApplicationManagementBridgeServiceDataHolder instance =
            new ApplicationManagementBridgeServiceDataHolder();

    private RealmService realmService;

    private ApplicationManagementService applicationManagementService;

    private SSOServiceProviderConfigManager ssoServiceProviderConfigManager;

    private BallerinaFunctionRegistration ballerinaFunctionRegistration;

    private ApplicationManagementBridgeService applicationManagementBridgeService;

    public ApplicationManagementBridgeService getApplicationManagementBridgeService() {
        return applicationManagementBridgeService;
    }

    public void setApplicationManagementBridgeService(
            ApplicationManagementBridgeService applicationManagementBridgeService) {
        this.applicationManagementBridgeService = applicationManagementBridgeService;
    }

    private ApplicationManagementBridgeServiceDataHolder() {

    }

    public static ApplicationManagementBridgeServiceDataHolder getInstance() {

        return instance;
    }

    public RealmService getRealmService() {

        if (realmService == null) {
            throw new RuntimeException("RealmService is null. Bundle did not start correctly.");
        }
        return realmService;
    }

    public void setRealmService(RealmService realmService) {

        this.realmService = realmService;
    }

    public ApplicationManagementService getApplicationManagementService() {

        if (applicationManagementService == null) {
            throw new RuntimeException("ApplicationManagementService is null. Bundle did not start correctly.");
        }
        return applicationManagementService;
    }

    public void setApplicationManagementService(ApplicationManagementService applicationManagementService) {

        this.applicationManagementService = applicationManagementService;
    }

    public SSOServiceProviderConfigManager getSsoServiceProviderConfigManager() {

        if (ssoServiceProviderConfigManager == null) {
            throw new RuntimeException("SSOServiceProviderConfigManager is null. Bundle did not start correctly.");
        }
        return ssoServiceProviderConfigManager;
    }

    public void setSsoServiceProviderConfigManager(SSOServiceProviderConfigManager ssoServiceProviderConfigManager) {

        this.ssoServiceProviderConfigManager = ssoServiceProviderConfigManager;
    }

    public BallerinaFunctionRegistration getBallerinaFunctionRegistration() {
        return ballerinaFunctionRegistration;
    }

    public void setBallerinaFunctionRegistration(BallerinaFunctionRegistration ballerinaFunctionRegistration) {
        this.ballerinaFunctionRegistration = ballerinaFunctionRegistration;
    }
}
