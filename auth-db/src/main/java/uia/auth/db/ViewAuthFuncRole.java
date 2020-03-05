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

@ViewInfo(name = "view_auth_func_role", inherit = 1)
public class ViewAuthFuncRole extends AuthFuncRole {

	@ColumnInfo(name = "func_name")
    private String funcName;

	@ColumnInfo(name = "func_description")
    private String funcDescription;

	@ColumnInfo(name = "role_name")
    private String roleName;

	@ColumnInfo(name = "role_enabled")
    private String roleEnabled;
    
	@ColumnInfo(name = "func_args")
    private String funcArgs;

    public ViewAuthFuncRole() {
    }

    public ViewAuthFuncRole(ViewAuthFuncRole data) {
        super(data); 
        this.funcName = data.funcName;
        this.funcDescription = data.funcDescription;
        this.roleName = data.roleName;
        this.roleEnabled = data.roleEnabled;
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

	public String getFuncArgs() {
		return funcArgs;
	}

	public void setFuncArgs(String funcArgs) {
		this.funcArgs = funcArgs;
	}

    @Override
    public ViewAuthFuncRole copy() {
        return new ViewAuthFuncRole(this);
    }

    @Override
    public String toString() {
        return String.format("%6s.%-30s %-20s %s", getAuthFunc(), this.funcName, this.roleName, this.getAccessType());
    }
}
