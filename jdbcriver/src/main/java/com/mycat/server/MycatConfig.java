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
package com.mycat.server;

import java.util.concurrent.locks.ReentrantLock;

import com.mycat.config.loader.ConfigLoader;
import com.mycat.config.model.SystemConfig;
import com.mycat.config.model.UserConfig;

/**
 * @author mycat
 */
public class MycatConfig {
	private volatile SystemConfig system;
	private volatile UserConfig users;
	private long reloadTime;
	private long rollbackTime;
	private int status;
	private final ReentrantLock lock;

	private MycatConfig() {
	    this.system = ConfigLoader.getSystemConfig();
		this.users = ConfigLoader.getUserConfig();
		this.lock = new ReentrantLock();
	}

	public static MycatConfig instance(){
		return new MycatConfig();
	}

	public SystemConfig getSystem() {
		return system;
	}
	public ReentrantLock getLock() {
		return lock;
	}

	public long getReloadTime() {
		return reloadTime;
	}

	public long getRollbackTime() {
		return rollbackTime;
	}

	public UserConfig getUsers() {
		return users;
	}

	public void setUsers(UserConfig users) {
		this.users = users;
	}

	public void setSystem(SystemConfig system) {
		this.system = system;
	}

}
