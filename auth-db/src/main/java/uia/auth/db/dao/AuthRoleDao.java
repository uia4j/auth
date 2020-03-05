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

import uia.auth.db.AuthRole;
import uia.auth.db.conf.AuthDB;
import uia.dao.DaoException;
import uia.dao.DaoMethod;
import uia.dao.TableDao;

public class AuthRoleDao extends TableDao<AuthRole>{

    public AuthRoleDao(Connection conn) {
    	super(conn, AuthDB.forTable(AuthRole.class));
    }

    @Override
    public int deleteByPK(Object... pks) throws SQLException {
        try (PreparedStatement ps = this.conn.prepareStatement("UPDATE auth_role SET enabled='N' WHERE id=?")) {
            ps.setLong(1, (long)pks[0]);
            return ps.executeUpdate();
        }
    }

    public AuthRole selectByName(String roleName) throws SQLException, DaoException {
    	DaoMethod<AuthRole> method = this.tableHelper.forSelect();
        try (PreparedStatement ps = this.conn.prepareStatement(method.getSql() + "WHERE role_name=?")) {
            ps.setString(1, roleName);
            try(ResultSet rs = ps.executeQuery()) {
            	return method.toOne(rs);
            }
        }
    }

    public List<AuthRole> selectByUser(long authUser) throws SQLException, DaoException {
    	DaoMethod<AuthRole> method = this.tableHelper.forSelect();
        try (PreparedStatement ps = this.conn.prepareStatement(method.getSql() + " INNER JOIN auth_role_user ru ON ru.auth_role=id WHERE ru.auth_user=? ORDER BY role_name")) {
            ps.setLong(1, authUser);
            try(ResultSet rs = ps.executeQuery()) {
            	return method.toList(rs);
            }
        }
    }
}
