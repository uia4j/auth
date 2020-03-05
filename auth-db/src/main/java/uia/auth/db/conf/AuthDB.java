/*******************************************************************************
 * Copyright 2018, 2019 UIA
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
package uia.auth.db.conf;

import java.sql.Connection;
import java.sql.SQLException;

import uia.dao.DaoException;
import uia.dao.DaoFactory;
import uia.dao.TableDaoHelper;
import uia.dao.ViewDaoHelper;

public class AuthDB {

    private static DaoFactory factory = null;
    
    private AuthDB() {
    }

    public static synchronized void config() throws DaoException {
        initialDaoFactory(null);
    }

    public static synchronized void config(ClassLoader loader) throws DaoException {
        initialDaoFactory(loader);
    }

    public static void config(String conn, String user, String pwd) throws DaoException {
        initialDaoFactory(null);
        if ("HANA".equals(System.getProperty("auth.db.env"))) {
            Hana.config(conn, user, pwd);
        }
        else {
            Postgres.config(conn, user, pwd);
        }
    }

    public static Connection create() throws SQLException {
        if ("HANA".equals(System.getProperty("auth.db.env"))) {
            return Hana.create();
        }
        else {
            return Postgres.create();
        }
    }

    /**
     * Returns a DAO for a table.
     *
     * @param clz DTO class type.
     * @return DAO instance for the table.
     */
    public static synchronized <T> TableDaoHelper<T> forTable(Class<T> clz) {
        return factory.forTable(clz);
    }

    /**
     * Returns a DAO for a view.
     *
     * @param clz DTO class type.
     * @return DAO instance for the view.
     */
    public static synchronized <T> ViewDaoHelper<T> forView(Class<T> clz) {
        return factory.forView(clz);
    }

    private static synchronized void initialDaoFactory(ClassLoader loader) throws DaoException {
        if (factory == null) {
            factory = new DaoFactory();
            factory.load("uia.auth.db", loader);
        }
    }
}
