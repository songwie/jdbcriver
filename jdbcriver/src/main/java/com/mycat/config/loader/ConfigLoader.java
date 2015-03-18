/*
 * Copyright (c) 2013, OpenCloudDB/MyCAT and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software;Designed and Developed mainly by many Chinese
 * opensource volunteers. you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 2 only, as published by the
 * Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Any questions about this component can be directed to it's project Web address
 * https://code.google.com/p/opencloudb/.
 *
 */
package com.mycat.config.loader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.mycat.config.model.SystemConfig;
import com.mycat.config.model.UserConfig;

/**
 * @author mycat
 */
public class ConfigLoader {
	private static final Logger logger = Logger.getLogger(ConfigLoader.class);
	private static Properties prop = new Properties();
	private static void init(){
        try {
			prop.load(ConfigLoader.class.getResourceAsStream("/ServerConfig.properties"));
		} catch (FileNotFoundException e) {
			logger.error("ServerConfig.properties not found ");
		} catch (IOException e) {
			logger.error("load ServerConfig.properties error " + e.getMessage());
		}
	}
	public static SystemConfig getSystemConfig(){
		init();
        SystemConfig config = new SystemConfig();
        try {
            String port = prop.getProperty("mycat.port");
            String mycatHome = prop.getProperty("mycat.home");
            config.setMycatHome(mycatHome);
            if(port!=null&&!port.equals("")){
            	config.setPort(Integer.valueOf(port));
            }else {
            	config.setPort(SystemConfig.DEFAULT_PORT);
			}

		} catch (Exception e) {

		}
        return config;
	};
	public static UserConfig getUserConfig(){
		init();
		UserConfig user = new UserConfig();
		try {
            String username = prop.getProperty("mycat.user");
            String password = prop.getProperty("mycat.password");
            user.setName(username);
            user.setPassword(password);

		} catch (Exception e) {

		}
        return user;
	};
}