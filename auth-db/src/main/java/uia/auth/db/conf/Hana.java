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

public class Hana {

    private static String hanaConn;

    private static String hanaUser;

    private static String hanaPwd;

    static {
        try {
            Class.forName("com.sap.db.jdbc.Driver");
        }
        catch (Exception e) {

        }

        if (System.getProperties().get("auth.db.connection") == null) {
        	hanaConn = "jdbc:sap://192.168.137.245:39015";
        	hanaUser= "WIP";
        	hanaPwd = "Sap12345";
        }
        else {
        	hanaConn = "" + System.getProperties().get("auth.db.connection");
        	hanaUser = "" + System.getProperties().get("auth.db.user");
        	hanaPwd = "" + System.getProperties().get("auth.db.pwd");
        }
    }

    private Hana() {
    }
    
    public static void config(String conn, String user, String pwd) {
    	hanaConn = conn;
    	hanaUser = user;
    	hanaPwd = pwd;
    }

    public static Connection create() throws SQLException {
        return java.sql.DriverManager.getConnection(hanaConn, hanaUser, hanaPwd);
    }
}
