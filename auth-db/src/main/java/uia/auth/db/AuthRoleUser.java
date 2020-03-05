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

@TableInfo(name = "auth_role_user")
public class AuthRoleUser {

	@ColumnInfo(name = "auth_user", primaryKey = true)
    private long authUser;

	@ColumnInfo(name = "auth_role", primaryKey = true)
    private long authRole;

    public AuthRoleUser() {
    }

    public AuthRoleUser(AuthRoleUser data) {
        this.authUser = data.authUser;
        this.authRole = data.authRole;
    }

    public long getAuthUser() {
        return this.authUser;
    }

    public void setAuthUser(long authUser) {
        this.authUser = authUser;
    }

    public long getAuthRole() {
        return this.authRole;
    }

    public void setAuthRole(long authRole) {
        this.authRole = authRole;
    }

    public AuthRoleUser copy() {
        return new AuthRoleUser(this);
    }

    @Override
    public String toString() {
        return this.authUser + ", " + this.authRole;
    }
}
