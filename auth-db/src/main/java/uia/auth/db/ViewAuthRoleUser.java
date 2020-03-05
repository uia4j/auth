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

@ViewInfo(name = "view_auth_role_user")
public class ViewAuthRoleUser {

	@ColumnInfo(name = "auth_user")
    private Long authUser;

	@ColumnInfo(name = "auth_role")
    private Long authRole;

	@ColumnInfo(name = "role_name")
    private String roleName;

	@ColumnInfo(name = "role_enabled")
    private String roleEnabled;

	@ColumnInfo(name = "user_id")
    private String userId;

	@ColumnInfo(name = "user_name")
    private String userName;

	@ColumnInfo(name = "user_enabled")
    private String userEnabled;

	@ColumnInfo(name = "mobile_no")
    private String mobileNo;

	@ColumnInfo(name = "email")
    private String email;

    public ViewAuthRoleUser() {
    }

    public ViewAuthRoleUser(ViewAuthRoleUser data) {
        this.authUser = data.authUser;
        this.authRole = data.authRole;
        this.roleName = data.roleName;
        this.roleEnabled = data.roleEnabled;
        this.userId = data.userId;
        this.userName = data.userName;
        this.userEnabled = data.userEnabled;
        this.mobileNo = data.mobileNo;
        this.email = data.email;
    }

    public Long getAuthUser() {
        return this.authUser;
    }

    public void setAuthUser(Long authUser) {
        this.authUser = authUser;
    }

    public Long getAuthRole() {
        return this.authRole;
    }

    public void setAuthRole(Long authRole) {
        this.authRole = authRole;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleEnabled() {
        return this.roleEnabled;
    }

    public void setRoleEnabled(String roleEnabled) {
        this.roleEnabled = roleEnabled;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEnabled() {
        return this.userEnabled;
    }

    public void setUserEnabled(String userEnabled) {
        this.userEnabled = userEnabled;
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ViewAuthRoleUser copy() {
        return new ViewAuthRoleUser(this);
    }

    @Override
    public String toString() {
        return "" + this.authUser;
    }
}
