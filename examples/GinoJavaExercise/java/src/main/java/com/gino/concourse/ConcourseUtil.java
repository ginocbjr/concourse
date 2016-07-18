package com.gino.concourse;

import com.cinchapi.concourse.ConnectionPool;

/**
 * Utility classes containing methods for interacting to a Concourse instance
 * @author gino
 *
 */
public class ConcourseUtil {
	
	public static ConnectionPool CONCOURSE_CONNECTIONS = ConnectionPool.newCachedConnectionPool();
	
	public static final String CLASS_KEY_NAME = "_class";

}
