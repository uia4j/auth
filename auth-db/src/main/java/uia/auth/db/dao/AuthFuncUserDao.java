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
import java.util.List;

import uia.auth.db.AuthFuncUser;
import uia.auth.db.conf.AuthDB;
import uia.dao.DaoException;
import uia.dao.DaoMethod;
import uia.dao.TableDao;

public class AuthFuncUserDao extends TableDao<AuthFuncUser> {

    public AuthFuncUserDao(Connection conn) {
    	super(conn, AuthDB.forTable(AuthFuncUser.class));
    }

    public int delete(long authFunc) throws SQLException {
    	DaoMethod<AuthFuncUser> method = this.tableHelper.forDelete();
        try (PreparedStatement ps = this.conn.prepareStatement(method.getSql() + "WHERE auth_func=? ")) {
            ps.setLong(1, authFunc);
            return ps.executeUpdate();
        }
    }

    public List<AuthFuncUser> selectByUser(long authUser) throws SQLException, DaoException {
    	DaoMethod<AuthFuncUser> method = this.tableHelper.forSelect();
        try (PreparedStatement ps = this.conn.prepareStatement(method.getSql() + "WHERE auth_user=? ORDER BY auth_func")) {
            ps.setLong(1, authUser);
            try(ResultSet rs = ps.executeQuery()) {
            	return method.toList(rs);
            }
        }
    }

    public List<AuthFuncUser> selectByFunc(long authFunc) throws SQLException, DaoException {
    	DaoMethod<AuthFuncUser> method = this.tableHelper.forSelect();
        try (PreparedStatement ps = this.conn.prepareStatement(method.getSql() + "WHERE auth_func=? ORDER BY auth_user")) {
            ps.setLong(1, authFunc);
            try(ResultSet rs = ps.executeQuery()) {
            	return method.toList(rs);
            }
        }
    }
}
