package main.cmd;

import java.util.HashMap;
import java.util.Map;

public class CommandList {
	private Map<String[], CommandExecutor> list = new HashMap<String[], CommandExecutor>();
	
	public Map<String[], CommandExecutor> get() {
		
		list.put(new String[]{
				"dice", 
				"roll"
				}, new DiceCommand());
		
		list.put(new String[]{
				"money", 
				"wallet",
				"cash",
				"balance",
				"coins",
				"gp"
				}, new WalletCommand());
		
		list.put(new String[]{
				"slots"
		}, new SlotsCommand());
		
		list.put(new String[]{
				"bet"
		}, new BetCommand());
		
		list.put(new String[]{
				"clearbets"
		}, new ClearBetCommand());
		
		list.put(new String[]{
				"bets"
		}, new BetsCommand());
		
		list.put(new String[]{
				"roulette",
				"spin"
		}, new RouletteCommand());
		
		return list;
	}
}
