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

@TableInfo(name = "auth_func_user")
public class AuthFuncUser {

	@ColumnInfo(name = "auth_func", primaryKey = true)
    private long authFunc;

	@ColumnInfo(name = "auth_user", primaryKey = true)
    private long authUser;

	@ColumnInfo(name = "access_type")
    private String accessType;
    
	@ColumnInfo(name = "func_user_args")
    private String funcUserArgs;

    public AuthFuncUser() {
    }

    public AuthFuncUser(AuthFuncUser data) {
        this.authFunc = data.authFunc;
        this.authUser = data.authUser;
        this.accessType = data.accessType;
        this.funcUserArgs = data.funcUserArgs;
    }

    public AuthFuncUser(long authFunc, long authUser, String accessType, String funcUserArgs) {
    	this.authFunc = authFunc;
    	this.authUser = authUser;
    	this.accessType = accessType;
    	this.funcUserArgs = funcUserArgs;
    }

    public long getAuthFunc() {
        return this.authFunc;
    }

    public void setAuthFunc(long authFunc) {
        this.authFunc = authFunc;
    }

    public long getAuthUser() {
        return this.authUser;
    }

    public void setAuthUser(long authUser) {
        this.authUser = authUser;
    }

    public String getAccessType() {
        return this.accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getFuncUserArgs() {
		return funcUserArgs;
	}

	public void setFuncUserArgs(String funcUserArgs) {
		this.funcUserArgs = funcUserArgs;
	}

    public AuthFuncUser copy() {
        return new AuthFuncUser(this);
    }

    @Override
    public String toString() {
        return this.authFunc + ", " + this.authUser;
    }
}
