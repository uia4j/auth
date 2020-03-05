/*******************************************************************************
 * Copyright 2019 UIA
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package uia.auth.db;

import uia.dao.annotation.ColumnInfo;
import uia.dao.annotation.TableInfo;

@TableInfo(name = "auth_func_role")
public class AuthFuncRole {

	@ColumnInfo(name = "auth_func", primaryKey = true)
	private long authFunc;
 
	@ColumnInfo(name = "auth_role", primaryKey = true)
   private long authRole;

	@ColumnInfo(name = "access_type")
    private String accessType;
    
	@ColumnInfo(name = "func_role_args")
    private String funcRoleArgs;

    public AuthFuncRole() {
    }

    public AuthFuncRole(AuthFuncRole data) {
        this.authFunc = data.authFunc;
        this.authRole = data.authRole;
        this.accessType = data.accessType;
        this.funcRoleArgs = data.funcRoleArgs;
    }

    public AuthFuncRole(long authFunc, long authRole, String accessType, String funcRoleArgs) {
    	this.authFunc = authFunc;
    	this.authRole = authRole;
    	this.accessType = accessType;
    	this.funcRoleArgs = funcRoleArgs;
    }

    public long getAuthFunc() {
        return this.authFunc;
    }

    public void setAuthFunc(long authFunc) {
        this.authFunc = authFunc;
    }

    public long getAuthRole() {
        return this.authRole;
    }

    public void setAuthRole(long authRole) {
        this.authRole = authRole;
    }

    public String getAccessType() {
        return this.accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getFuncRoleArgs() {
		return funcRoleArgs;
	}

	public void setFuncRoleArgs(String funcRoleArgs) {
		this.funcRoleArgs = funcRoleArgs;
	}

    public AuthFuncRole copy() {
        return new AuthFuncRole(this);
    }

    @Override
    public String toString() {
        return this.authFunc + ", " + this.authRole;
    }
}
