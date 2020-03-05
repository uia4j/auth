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

@ViewInfo(name = "view_auth_security", inherit = 1)
public class ViewAuthSecurity extends AuthSecurity {

	@ColumnInfo(name = "user_id")
    private String userId;

	@ColumnInfo(name = "enabled")
    private String enabled;

	@ColumnInfo(name = "seed")
    private String seed;

    public ViewAuthSecurity() {
    }	

    public ViewAuthSecurity(ViewAuthSecurity data) {
    	super(data);
        this.userId = data.userId;
        this.enabled = data.enabled;
        this.seed = data.seed;
    }	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getSeed() {
		return seed;
	}

	public void setSeed(String seed) {
		this.seed = seed;
	}

    @Override
    public ViewAuthSecurity copy() {
    	return new ViewAuthSecurity(this);
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", this.userId, getAuthUser());
    }
}
