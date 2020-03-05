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
import java.sql.Timestamp;

import uia.auth.db.AuthSecurity;
import uia.auth.db.conf.AuthDB;
import uia.dao.DaoException;
import uia.dao.DaoMethod;
import uia.dao.TableDao;

public class AuthSecurityDao extends TableDao<AuthSecurity> {

    public AuthSecurityDao(Connection conn) {
    	super(conn, AuthDB.forTable(AuthSecurity.class));
    }

    public int updatePassword(long authUser, String password) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement("UPDATE auth_security SET pwd=?,token_expired=? WHERE auth_user=?")) {
            ps.setString(1, password);
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            ps.setLong(3, authUser);

            return ps.executeUpdate();
        }
    }

    public AuthSecurity selectBySession(String session) throws SQLException, DaoException {
    	DaoMethod<AuthSecurity> method = this.tableHelper.forSelect();
    	try (PreparedStatement ps = this.conn.prepareStatement(method.getSql() + "WHERE token=?")) {
            ps.setString(1, session);
            try(ResultSet rs = ps.executeQuery()) {
            	return method.toOne(rs);
            }
        }
    }    
}
