package main;

import java.util.Map;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;

import main.cmd.CommandExecutor;
import main.cmd.CommandList;

public class MessageHandler {

	public void handle(BotUser user, MessageCreateEvent event) {
		String text = event.getMessageContent();
		TextChannel c = event.getChannel();
		String[] args = text.split(" ");
		String command = args[0].replace(Config.CMD_CHAR, "");
		
		Map<String[], CommandExecutor> cmdList = new CommandList().get();
		
		if (text.startsWith(Config.CMD_CHAR)) {
			for (Map.Entry<String[], CommandExecutor> entry : cmdList.entrySet()) {
			    for(int i=0;i<entry.getKey().length;i++) {
			    	if (command.equalsIgnoreCase(entry.getKey()[i]))
			    		entry.getValue().execute(user, event, args);
			    }
			}
		}
		
		
	}
	
}
