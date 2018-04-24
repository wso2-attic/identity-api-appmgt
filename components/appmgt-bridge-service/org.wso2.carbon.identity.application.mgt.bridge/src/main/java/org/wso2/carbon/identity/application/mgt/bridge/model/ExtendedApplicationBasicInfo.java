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
import org.wso2.carbon.identity.application.common.model.ApplicationBasicInfo;

import static org.wso2.carbon.identity.application.mgt.bridge.util.Constants.UNIQUE_ID_PREFIX;
import static org.wso2.carbon.identity.core.util.IdentityUtil.base58Encode;

/**
 * Extended application basic info implementation for Application REST API
 */
public class ExtendedApplicationBasicInfo extends ApplicationBasicInfo {

    private ApplicationBasicInfo applicationBasicInfo;

    private String uniqueId;

    public ExtendedApplicationBasicInfo(ApplicationBasicInfo applicationBasicInfo) {

        this.applicationBasicInfo = applicationBasicInfo;
    }

    /**
     * Get the unique identifier of the service provider.
     *
     * @return unique identifier of the service provider
     */
    public String getUniqueId() {

        if (StringUtils.isBlank(uniqueId)) {
            if (getApplicationId() > 0) {
                uniqueId = base58Encode((UNIQUE_ID_PREFIX + getApplicationId()).getBytes());
            } else {
                //This is a file based SP
                uniqueId = base58Encode((UNIQUE_ID_PREFIX + getApplicationName()).getBytes());
            }
        }
        return uniqueId;
    }

    public int getApplicationId() {
        return applicationBasicInfo.getApplicationId();
    }

    public String getApplicationName() {
        return applicationBasicInfo.getApplicationName();
    }

    public String getDescription() {
        return applicationBasicInfo.getDescription();
    }
}
