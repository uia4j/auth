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
import uia.dao.annotation.ViewInfo;

@ViewInfo(name = "view_auth_func_role_user")
public class ViewAuthFuncRoleUser extends AuthFuncRole {

	@ColumnInfo(name = "func_name")
    private String funcName;

	@ColumnInfo(name = "func_description")
    private String funcDescription;

	@ColumnInfo(name = "role_name")
    private String roleName;

	@ColumnInfo(name = "role_enabled")
    private String roleEnabled;

	@ColumnInfo(name = "auth_user")
    private long authUser;

	@ColumnInfo(name = "user_id")
    private String userId;

	@ColumnInfo(name = "user_name")
   private String userName;

	@ColumnInfo(name = "user_enabled")
    private String userEnabled;
    
	@ColumnInfo(name = "func_args")
    private String funcArgs;

    public ViewAuthFuncRoleUser() {
    }

    public ViewAuthFuncRoleUser(ViewAuthFuncRoleUser data) {
    	super(data);
        this.funcName = data.funcName;
        this.funcDescription = data.funcDescription;
        this.roleName = data.roleName;
        this.roleEnabled = data.roleEnabled;
        this.authUser = data.authUser;
        this.userId = data.userId;
        this.userName = data.userName;
        this.userEnabled = data.userEnabled;
        this.funcArgs = data.funcArgs;
    }

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getFuncDescription() {
		return funcDescription;
	}

	public void setFuncDescription(String funcDescription) {
		this.funcDescription = funcDescription;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleEnabled() {
		return roleEnabled;
	}

	public void setRoleEnabled(String roleEnabled) {
		this.roleEnabled = roleEnabled;
	}

	public long getAuthUser() {
		return authUser;
	}

	public void setAuthUser(long authUser) {
		this.authUser = authUser;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEnabled() {
		return userEnabled;
	}

	public void setUserEnabled(String userEnabled) {
		this.userEnabled = userEnabled;
	}

	public String getFuncArgs() {
		return funcArgs;
	}

	public void setFuncArgs(String funcArgs) {
		this.funcArgs = funcArgs;
	}
	
    @Override
	public ViewAuthFuncRoleUser copy() {
		return new ViewAuthFuncRoleUser(this);
	}
    
}
