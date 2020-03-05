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

import java.util.Date;
import java.util.UUID;

import uia.dao.annotation.ColumnInfo;
import uia.dao.annotation.TableInfo;

@TableInfo(name = "auth_security")
public class AuthSecurity {

	@ColumnInfo(name = "auth_user", primaryKey = true)
    private long authUser;

	@ColumnInfo(name = "password")
	private String password;

	@ColumnInfo(name = "token")
    private String token;

	@ColumnInfo(name = "token_expired")
    private Date tokenExpired;

	@ColumnInfo(name = "token_expired_short")
    private Date tokenExpiredShort;


    public AuthSecurity() { 
    	this.password = "692692acdf9c710fcc5eb9a60c1af429";
    	this.token = UUID.randomUUID().toString();
    	this.tokenExpired = new Date();
    	this.tokenExpiredShort = this.tokenExpired;
    }	

    public AuthSecurity(AuthSecurity data) {
        this.authUser = data.authUser;
        this.password = data.password;
        this.token = data.token;
        this.tokenExpired = data.tokenExpired;
        this.tokenExpiredShort = data.tokenExpiredShort;
    }	

    public long getAuthUser() {
		return authUser;
	}

	public void setAuthUser(long authUser) {
		this.authUser = authUser;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getTokenExpired() {
		return tokenExpired;
	}

	public void setTokenExpired(Date tokenExpired) {
		this.tokenExpired = tokenExpired;
	}

	public Date getTokenExpiredShort() {
		return tokenExpiredShort;
	}

	public void setTokenExpiredShort(Date tokenExpiredShort) {
		this.tokenExpiredShort = tokenExpiredShort;
	}

    public AuthSecurity copy() {
    	return new AuthSecurity(this);
    }

    @Override
    public String toString() {
        return "" + this.authUser;
    }
}
