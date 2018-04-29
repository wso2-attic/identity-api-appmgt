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

package org.wso2.carbon.identity.integration.c4b7a.internal;

import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.natives.NativeElementRepository;
import org.wso2.carbon.identity.integration.c4b7a.BallerinaFunctionRegistration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Static instance to bridge the OSGI (c4) and SPI worlds.
 */
public class BallerinaFunctionRegistrationImpl implements BallerinaFunctionRegistration {

    private static BallerinaFunctionRegistrationImpl instance = new BallerinaFunctionRegistrationImpl();

    private List<NativeElementRepository.NativeFunctionDef> nativeFunctionDefList = new ArrayList<>();

    private BallerinaFunctionRegistrationImpl() {

    }

    public static BallerinaFunctionRegistrationImpl getInstance() {

        return instance;
    }

    public void register(String orgName, String pkgName, String callableName, TypeKind[] argTypes, TypeKind[] retTypes,
            String className) {

        nativeFunctionDefList
                .add((new NativeElementRepository.NativeFunctionDef(orgName, pkgName, callableName, argTypes, retTypes,
                        className)));
    }

    public List<NativeElementRepository.NativeFunctionDef> listRegistrations() {

        return Collections.unmodifiableList(nativeFunctionDefList);
    }
}
