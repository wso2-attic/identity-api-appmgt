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

package org.wso2.carbon.identity.application.mgt.bridge.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ballerinalang.model.types.TypeKind;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.wso2.carbon.identity.application.mgt.ApplicationManagementService;
import org.wso2.carbon.identity.application.mgt.bridge.ApplicationManagementBridgeService;
import org.wso2.carbon.identity.integration.c4b7a.BallerinaFunctionRegistration;
import org.wso2.carbon.identity.sso.saml.SSOServiceProviderConfigManager;
import org.wso2.carbon.user.core.service.RealmService;

@Component(name = "identity.application.management.bridge.component",
           immediate = true)
public class ApplicationManagementBridgeServiceComponent {

    private static Log log = LogFactory.getLog(ApplicationManagementBridgeServiceComponent.class);

    @Activate
    protected void activate(ComponentContext context) {

        try {
            BundleContext bundleContext = context.getBundleContext();
            ApplicationManagementBridgeService applicationManagementBridgeService = new
                    ApplicationManagementBridgeService();
            ApplicationManagementBridgeServiceDataHolder.getInstance().setApplicationManagementBridgeService
                    (applicationManagementBridgeService);
            bundleContext
                    .registerService(ApplicationManagementBridgeService.class, applicationManagementBridgeService,
                            null);

            ApplicationManagementBridgeServiceDataHolder.getInstance().getBallerinaFunctionRegistration()
                    .register("ballerina", "appmgt", "getApplications", new TypeKind[] { TypeKind.INT, TypeKind.INT },
                            new TypeKind[] { TypeKind.ARRAY },
                            "org.wso2.carbon.identity.application.mgt.bridge.GetApplications");
            if (log.isDebugEnabled()) {
                log.debug("org.wso2.carbon.identity.application.mgt.bridge bundle is activated");
            }
        } catch (Throwable e) {
            log.error("Error while activating org.wso2.carbon.identity.application.mgt.bridge bundle", e);
        }
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {

        if (log.isDebugEnabled()) {
            log.debug("org.wso2.carbon.identity.application.mgt.bridge bundle is deactivated");
        }
    }

    @Reference(name = "RealmService",
               service = RealmService.class,
               cardinality = ReferenceCardinality.MANDATORY,
               policy = ReferencePolicy.DYNAMIC,
               unbind = "unsetRealmService")
    protected void setRealmService(RealmService realmService) {

        if (log.isDebugEnabled()) {
            log.debug("Setting the Realm Service.");
        }
        ApplicationManagementBridgeServiceDataHolder.getInstance().setRealmService(realmService);
    }

    protected void unsetRealmService(RealmService realmService) {

        if (log.isDebugEnabled()) {
            log.debug("Un-setting the Realm Service.");
        }
        ApplicationManagementBridgeServiceDataHolder.getInstance().setRealmService(null);
    }

    @Reference(name = "ApplicationManagementService",
               service = ApplicationManagementService.class,
               cardinality = ReferenceCardinality.MANDATORY,
               policy = ReferencePolicy.DYNAMIC,
               unbind = "unsetApplicationManagementService")
    protected void setApplicationManagementService(ApplicationManagementService applicationManagementService) {

        if (log.isDebugEnabled()) {
            log.debug("Setting the Application Management Service.");
        }
        ApplicationManagementBridgeServiceDataHolder.getInstance()
                .setApplicationManagementService(applicationManagementService);
    }

    protected void unsetApplicationManagementService(ApplicationManagementService applicationManagementService) {

        if (log.isDebugEnabled()) {
            log.debug("Un-setting the Application Management Service.");
        }
        ApplicationManagementBridgeServiceDataHolder.getInstance().setApplicationManagementService(null);
    }

    @Reference(name = "SSOServiceProviderConfigManager",
               service = SSOServiceProviderConfigManager.class,
               cardinality = ReferenceCardinality.MANDATORY,
               policy = ReferencePolicy.DYNAMIC,
               unbind = "unsetSSOServiceProviderConfigManager")
    protected void setSSOServiceProviderConfigManager(SSOServiceProviderConfigManager ssoServiceProviderConfigManager) {

        if (log.isDebugEnabled()) {
            log.debug("Setting the SSO Service Provider Config Manager.");
        }
        ApplicationManagementBridgeServiceDataHolder.getInstance()
                .setSsoServiceProviderConfigManager(ssoServiceProviderConfigManager);
    }

    protected void unsetSSOServiceProviderConfigManager(
            SSOServiceProviderConfigManager ssoServiceProviderConfigManager) {

        if (log.isDebugEnabled()) {
            log.debug("Un-setting the SSO Service Provider Config Manager.");
        }
        ApplicationManagementBridgeServiceDataHolder.getInstance().setSsoServiceProviderConfigManager(null);
    }

    @Reference(name = "BallerinaFunctionRegistration",
               service = BallerinaFunctionRegistration.class,
               cardinality = ReferenceCardinality.MANDATORY,
               policy = ReferencePolicy.DYNAMIC,
               unbind = "unsetBallerinaFunctionRegistration")
    public void setBallerinaFunctionRegistration(BallerinaFunctionRegistration ballerinaFunctionRegistration) {

        if (log.isDebugEnabled()) {
            log.debug("Setting the Ballerina Function Registration.");
        }
        ApplicationManagementBridgeServiceDataHolder.getInstance()
                .setBallerinaFunctionRegistration(ballerinaFunctionRegistration);
    }

    public void unsetBallerinaFunctionRegistration(BallerinaFunctionRegistration ballerinaFunctionRegistration) {

        if (log.isDebugEnabled()) {
            log.debug("Un-setting the Ballerina Function Registration.");
        }
        ApplicationManagementBridgeServiceDataHolder.getInstance().setBallerinaFunctionRegistration(null);
    }
}
