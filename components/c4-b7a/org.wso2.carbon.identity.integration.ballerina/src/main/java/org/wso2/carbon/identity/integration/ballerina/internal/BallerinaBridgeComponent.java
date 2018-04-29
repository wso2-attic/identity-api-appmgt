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

package org.wso2.carbon.identity.integration.ballerina.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;
import org.wso2.carbon.identity.integration.ballerina.rt.BallerinaServiceHost;
import org.wso2.carbon.identity.integration.ballerina.tc.B7aBridgeServlet;

import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.ServletException;

/**
 * OSGI component hosting Ballerina runtime in Identity Server.
 */
@Component(name = "wso2identity.integration.ballerina.component",
           immediate = true)
public class BallerinaBridgeComponent {

    private static final Log log = LogFactory.getLog(BallerinaBridgeComponent.class);
    private static final String BALLERINA_SERVICE_DIR = "repository/deployment/server/ballerina";
    private static final String SERVICE_ROOT = "/IS";
    private BallerinaServiceHost host;
    private HttpService httpService;
    private B7aBridgeServlet bridgeServlet = new B7aBridgeServlet();

    @Activate
    protected void activate(ComponentContext componentContext) {

        String carbonHome = System.getProperty("carbon.home");
        activateB7a(carbonHome);
        activateServletTransport(componentContext);
        log.info("Started ballerina ");
    }

    private void activateB7a(String carbonHome) {

        Path ballerinaFilePath = Paths.get(carbonHome, BALLERINA_SERVICE_DIR);
        host = new BallerinaServiceHost(ballerinaFilePath);

        log.info("Activating ballerina on path: " + ballerinaFilePath);
        try {
            host.start();
        } catch (Exception e) {
            log.error("Failed to enable activate Ballerina.", e);
        }
    }

    private void activateServletTransport(ComponentContext componentContext) {

        try {
            bridgeServlet.init();
        } catch (ServletException e) {
            log.error("Error occurred while initializing the bridge servlet.", e);
        }

        HttpContext defaultHttpContext = httpService.createDefaultHttpContext();
        try {
            httpService.registerServlet(SERVICE_ROOT, bridgeServlet, null, defaultHttpContext);
            log.info("MSF4J - Servlet bridge activated Successfully.");
        } catch (Exception e) {
            log.error("Error in registering the MSF4J servlet", e);
        }
    }

    @Deactivate
    protected void deactivate(ComponentContext componentContext) {

        if (host != null) {
            host.stop();
        }
    }

    @Reference(name = "http.service",
               service = HttpService.class,
               cardinality = ReferenceCardinality.MANDATORY,
               policy = ReferencePolicy.DYNAMIC,
               unbind = "unsetHttpService")
    public void setHttpService(HttpService httpService) {

        this.httpService = httpService;
    }

    public void unsetHttpService(HttpService httpService) {

        this.httpService = null;
    }
}
