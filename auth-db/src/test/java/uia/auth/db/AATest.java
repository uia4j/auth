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

import java.sql.Connection;

import org.junit.Test;

import uia.auth.db.conf.AuthDB;
import uia.dao.DaoFactory;
import uia.dao.DaoFactoryTool;
import uia.dao.Database;
import uia.dao.pg.PostgreSQL;

public class AATest {

    @Test
    public void testTable() throws Exception {
        String sourceDir = "E:/fw/auth/auth-db/src/main/java/";

        String tableOrView = "auth_security";

        Database db = new PostgreSQL("localhost", "5432", "authdb", "auth", "auth");
        DaoFactoryTool tool = new DaoFactoryTool(db);
        tool.toDTO(sourceDir, "uia.auth.db", tableOrView);
    }

    @Test
    public void test() throws Exception {
    	AuthDB.config();
    	
        DaoFactory factory = new DaoFactory();
        factory.load("uia.auth.db");
        try(Connection conn = AuthDB.create()) {
            factory.test(conn);
        }
        
    }
}
