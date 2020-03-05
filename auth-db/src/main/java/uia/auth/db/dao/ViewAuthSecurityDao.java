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
package uia.auth.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import uia.auth.db.ViewAuthSecurity;
import uia.auth.db.conf.AuthDB;
import uia.dao.DaoException;
import uia.dao.DaoMethod;
import uia.dao.ViewDao;

public class ViewAuthSecurityDao extends ViewDao<ViewAuthSecurity> {

    public ViewAuthSecurityDao(Connection conn) {
    	super(conn, AuthDB.forView(ViewAuthSecurity.class));
    }

    public ViewAuthSecurity selectByUserId(long authUser) throws SQLException, DaoException {
    	DaoMethod<ViewAuthSecurity> method = this.viewHelper.forSelect();
        try (PreparedStatement ps = this.conn.prepareStatement(method.getSql() + "WHERE auth_user=?")) {
        	ps.setLong(1, authUser);
            try(ResultSet rs = ps.executeQuery()) {
            	return method.toOne(rs);
            }
        }
    }    

    public ViewAuthSecurity selectByUserId(String userId) throws SQLException, DaoException {
    	DaoMethod<ViewAuthSecurity> method = this.viewHelper.forSelect();
        try (PreparedStatement ps = this.conn.prepareStatement(method.getSql() + "WHERE user_id=?")) {
        	ps.setString(1, userId);
            try(ResultSet rs = ps.executeQuery()) {
            	return method.toOne(rs);
            }
        }
    }    

    public ViewAuthSecurity selectBySession(String session) throws SQLException, DaoException {
    	DaoMethod<ViewAuthSecurity> method = this.viewHelper.forSelect();
        try (PreparedStatement ps = this.conn.prepareStatement(method.getSql() + "WHERE token=?")) {
        	ps.setString(1, session);
            try(ResultSet rs = ps.executeQuery()) {
            	return method.toOne(rs);
            }
        }
    }    
}
