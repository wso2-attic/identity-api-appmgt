/*
*   Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package org.wso2.carbon.identity.appmgt.nativeimpl;

import org.ballerinalang.bre.Context;
import org.ballerinalang.bre.bvm.BlockingNativeCallableUnit;
import org.ballerinalang.connector.api.BLangConnectorSPIUtil;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.model.values.BArray;
import org.ballerinalang.model.values.BRefValueArray;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.natives.annotations.Argument;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.natives.annotations.ReturnType;
import org.ballerinalang.util.codegen.ProgramFile;

/**
 * Native function wso2.appmgt:getApplications.
 *
 * @since 1.0.0
 */
@BallerinaFunction(
        orgName = "ballerina", packageName = "appmgt",
        functionName = "getApplications",
        args = {@Argument(name = "offset", type = TypeKind.INT), @Argument(name = "length", type = TypeKind.INT)},
        returnType = {@ReturnType(type = TypeKind.ARRAY)},
        isPublic = true
)
public class GetApplications extends BlockingNativeCallableUnit {

    public void execute(Context ctx) {

        ProgramFile programFile = ctx.getProgramFile();

        BStruct applicationBasicInfo = BLangConnectorSPIUtil.createBStruct(programFile, "ballerina.appmgt", "ApplicationBasicInfo");
        applicationBasicInfo.setStringField(0, "app-id");
        applicationBasicInfo.setStringField(1, "app-name");
        applicationBasicInfo.setStringField(2, "app-description");

        BRefValueArray applications = new BRefValueArray();
        applications.add(0, applicationBasicInfo);
        ctx.setReturnValues(applications);
    }
}
