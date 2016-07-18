package com.gino.model;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.cinchapi.concourse.Concourse;
import com.gino.concourse.ConcourseUtil;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * Entity representing a game statistics. This holds all important information for a played game.
 * 
 * @author gino
 *
 */
public class GameStats {
	
	private static final List<String> KEYS = Arrays.asList(new String[]{
			"sessionId", "playerId", "gameId", "startDate", "endDate"
	}); 
	
	private long id;
	
	/**
	 * Unique session ID of the user
	 */
	private String sessionId;
	
	/**
	 * Id of the player
	 */
	private String playerId;
	
	/**
	 * The ID of the game played by the player
	 */
	private String gameId;
	
	/**
	 * The date game was started in epoch format
	 */
	private long startDate;
	
	/**
	 * The date game ended in epoch format
	 */
	private long endDate;
	
	public GameStats(long id) {
		this.id = id;
	}
	
	public GameStats(String sessionId, String playerId, String gameId) {
		super();
		this.sessionId = sessionId;
		this.playerId = playerId;
		this.gameId = gameId;
	}


	/**
	 * Initialize a game stat
	 * 
	 * @param sessionId
	 * @param playerId
	 * @param gameId
	 */
	public void initGame() {
		Concourse concourse = ConcourseUtil.CONCOURSE_CONNECTIONS.request();
		try {
			Multimap<String, Object> data = HashMultimap.create();
			data.put(ConcourseUtil.CLASS_KEY_NAME, GameStats.class.getName());
			data.put("sessionId", sessionId);
			data.put("playerId", playerId);
			data.put("gameId", gameId);
			this.id = concourse.insert(data);
		} finally {
			ConcourseUtil.CONCOURSE_CONNECTIONS.release(concourse);
		}
	}
	
	
	/**
	 * Call this method to start a game
	 */
	public static void startGame(long id) {
		Concourse concourse = ConcourseUtil.CONCOURSE_CONNECTIONS.request();
		try {
			Multimap<String, Object> data = HashMultimap.create();
			data.put("startDate", new Date().getTime());
			concourse.insert(data, id);
		} finally {
			ConcourseUtil.CONCOURSE_CONNECTIONS.release(concourse);
		}
	}
	
	/**
	 * Call this method to end a game
	 * @param id
	 */
	public static void endGame(long id) {
		Concourse concourse = ConcourseUtil.CONCOURSE_CONNECTIONS.request();
		try {
			Multimap<String, Object> data = HashMultimap.create();
			data.put("endDate", new Date().getTime());
			concourse.insert(data, id);
		} finally {
			ConcourseUtil.CONCOURSE_CONNECTIONS.release(concourse);
		}
	}
	
	public static GameStats get(long id) {
		Concourse concourse = ConcourseUtil.CONCOURSE_CONNECTIONS.request();
		try {
			GameStats gameStats = new GameStats(id);
			Map<String, Object> result = concourse.get(KEYS, id);
			BeanUtils.populate(gameStats, result);
			return gameStats;
		} catch (IllegalAccessException | InvocationTargetException e) {
			return null;
		} finally {
			ConcourseUtil.CONCOURSE_CONNECTIONS.release(concourse);
		}
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getSessionId() {
		return sessionId;
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public Long getStartDate() {
		return startDate;
	}

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}

}
