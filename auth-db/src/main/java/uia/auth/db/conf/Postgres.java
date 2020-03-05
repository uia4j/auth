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
package uia.auth.db.conf;

import java.sql.Connection;
import java.sql.SQLException;

public class Postgres {

    private static String pgConn;

    private static String pgUser;

    private static String pgPwd;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (Exception ex) {

        }

        if (System.getProperties().get("auth.db.connection") == null) {
            pgConn = "jdbc:postgresql://localhost:5432/authdb";
            pgUser = "auth";
            pgPwd = "auth";
        }
        else {
            pgConn = "" + System.getProperties().get("auth.db.connection");
            pgUser = "" + System.getProperties().get("auth.db.user");
            pgPwd = "" + System.getProperties().get("auth.db.pwd");
        }
    }

    private Postgres() {
    }

    public static void config(String conn, String user, String pwd) {
        pgConn = conn;
        pgUser = user;
        pgPwd = pwd;
    }

    public static Connection create() throws SQLException {
        return java.sql.DriverManager.getConnection(pgConn, pgUser, pgPwd);
    }
}
