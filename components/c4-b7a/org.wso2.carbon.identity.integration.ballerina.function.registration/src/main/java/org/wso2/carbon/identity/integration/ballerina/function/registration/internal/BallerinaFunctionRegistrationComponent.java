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

package org.wso2.carbon.identity.integration.ballerina.function.registration.internal;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.wso2.carbon.identity.integration.c4b7a.BallerinaFunctionRegistration;
import org.wso2.carbon.identity.integration.c4b7a.internal.BallerinaFunctionRegistrationImpl;

/**
 * OSGI component hosting Ballerina runtime in Identity Server.
 */
@Component(name = "wso2identity.integration.ballerina.fn.reg.component", immediate = true)
public class BallerinaFunctionRegistrationComponent {

    private BallerinaFunctionRegistration ballerinaFunctionRegistration;

    @Activate
    protected void activate(ComponentContext componentContext) {

        ballerinaFunctionRegistration = BallerinaFunctionRegistrationImpl.getInstance();
        componentContext.getBundleContext()
                .registerService(BallerinaFunctionRegistration.class.getName(), ballerinaFunctionRegistration, null);
    }
}
