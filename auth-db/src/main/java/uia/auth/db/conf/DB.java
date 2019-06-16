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
 * @author fw
 *
 */
public class DB {

    public static void config(String host, String port, String pwd) {
        if ("HANA".equals(System.getProperty("auth.db.env"))) {
            Hana.config(host, port, pwd);
        }
        else {
            Postgres.config(host, port, pwd);
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
}
