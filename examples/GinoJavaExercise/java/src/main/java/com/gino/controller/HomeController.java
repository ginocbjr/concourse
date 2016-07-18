package com.gino.controller;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gino.model.GameStats;

/**
 * Created by gino on 18/07/2016.
 */
@Controller
public class HomeController {
	
	@RequestMapping(value = "/")
	public String index(ModelMap map) {
		//Initialize a game stat...
		//Generate a session ID..
		String sessionId = UUID.randomUUID().toString();
		
		//For now player ID is also a random UUID
		String playerId = UUID.randomUUID().toString();
		
		//As well as game ID
		String gameId = UUID.randomUUID().toString();
		
		GameStats gameStats = new GameStats(sessionId, playerId, gameId);
		gameStats.initGame();
		
		map.put("gameStats", gameStats);
		
		return "index";
	}
	
	@RequestMapping(value = "/game-stats/{id}")
	public @ResponseBody GameStats getById(@PathVariable Long id) {
		return GameStats.get(id);
	}
	
	@RequestMapping(value = "/game-stats/start/{id}")
	public void startGame(@PathVariable Long id) {
		GameStats.startGame(id);
	}
	
	@RequestMapping(value = "/game-stats/end/{id}")
	public void endGame(@PathVariable Long id) {
		GameStats.endGame(id);
	}

}
