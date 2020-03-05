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

@TableInfo(name = "auth_user")
public class AuthUser {

	@ColumnInfo(name = "id", primaryKey = true)
    private long id;

	@ColumnInfo(name = "user_id")
    private String userId;

	@ColumnInfo(name = "user_name")
    private String userName;

	@ColumnInfo(name = "enabled")
    private String enabled;

	@ColumnInfo(name = "seed")
    private String seed;

	@ColumnInfo(name = "mobile_no")
    private String mobileNo;

	@ColumnInfo(name = "email")
    private String email;

    public AuthUser() {
    	this.seed = "00000";
    }

    public AuthUser(AuthUser data) {
        this.id = data.id;
        this.userId = data.userId;
        this.userName = data.userName;
        this.enabled = data.enabled;
        this.seed = data.seed;
        this.mobileNo = data.mobileNo;
        this.email = data.email;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getEnabled() {
        return this.enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getSeed() {
        return this.seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
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

    public AuthUser copy() {
        return new AuthUser(this);
    }

    @Override
    public String toString() {
        return this.userId;
    }
}
