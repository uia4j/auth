/*******************************************************************************
 * Copyright 2018 UIA
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

/**
 *
 * @author Q. Foison Tech
 *
 */
public class Postgres {

    private static String CONN;

    private static String USER;

    private static String PWD;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (Exception ex) {

        }

        if (System.getProperties().get("pms.db.connection") == null) {
            CONN = "jdbc:postgresql://localhost:5432/authdb";
            USER = "auth";
            PWD = "auth";
        }
        else {
            CONN = "" + System.getProperties().get("auth.db.connection");
            USER = "" + System.getProperties().get("auth.db.user");
            PWD = "" + System.getProperties().get("auth.db.pwd");
        }
    }

    public static void config(String conn, String user, String pwd) {
        CONN = conn;
        USER = user;
        PWD = pwd;
    }

    public static Connection create() throws SQLException {
        return java.sql.DriverManager.getConnection(CONN, USER, PWD);
    }
}
