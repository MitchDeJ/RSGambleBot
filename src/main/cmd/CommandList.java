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
		
		return list;
	}
}
