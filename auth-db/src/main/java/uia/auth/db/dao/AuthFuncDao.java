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
import java.util.ArrayList;
import java.util.List;

import uia.auth.db.AuthFunc;
import uia.auth.db.conf.AuthDB;
import uia.dao.DaoException;
import uia.dao.DaoMethod;
import uia.dao.TableDao;

public class AuthFuncDao extends TableDao<AuthFunc> {

	public AuthFuncDao(Connection conn) {
		super(conn, AuthDB.forTable(AuthFunc.class));
    }

    public AuthFunc selectByName(String funcName) throws SQLException, DaoException {
    	DaoMethod<AuthFunc> method = this.tableHelper.forSelect();
        try (PreparedStatement ps = this.conn.prepareStatement(method.getSql() + "WHERE func_name=?")) {
            ps.setString(1, funcName);
            try(ResultSet rs = ps.executeQuery()) {
            	return method.toOne(rs);
            }
        }
    }

    public List<AuthFunc> searchParents(long id) throws SQLException, DaoException {
    	ArrayList<AuthFunc> result = new ArrayList<>();
        AuthFunc f = selectByPK(id);
        while (f != null) {
            result.add(f);
            f = selectByPK(f.getParentFunc());
        }
        return result;
    }

    public List<AuthFunc> searchParents(String funcName) throws SQLException, DaoException {
    	ArrayList<AuthFunc> result = new ArrayList<>();
        AuthFunc f = selectByName(funcName);
        while (f != null) {
            result.add(f);
            f = selectByPK(f.getParentFunc());
        }
        return result;
    }

    public List<AuthFunc> searchNexts(long id) throws SQLException, DaoException {
    	DaoMethod<AuthFunc> method = this.tableHelper.forSelect();
        try (PreparedStatement ps = this.conn.prepareStatement(method.getSql() + "WHERE parent_func=? ORDER BY func_name")) {
            ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery()) {
            	return method.toList(rs);
            }
        }
    }
}
