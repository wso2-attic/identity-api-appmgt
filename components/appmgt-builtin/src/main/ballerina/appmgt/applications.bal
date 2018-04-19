// Copyright (c) 2018 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
//
// WSO2 Inc. licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except
// in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package ballerina.appmgt;

@Description {value:"Returns basic info of applications"}
@Param {value:"offset: The offset value"}
@Param {value:"limit: The number of search results"}
@Return { value:"The size of an ulp of the argument"}
public native function getApplications (int offset, int length) returns (ApplicationBasicInfo[]);
