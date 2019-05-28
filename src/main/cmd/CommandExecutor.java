package main.cmd;

import org.javacord.api.event.message.MessageCreateEvent;

import main.BotUser;

public abstract class CommandExecutor {
		
	protected AllowedChannels channels;

	public abstract void execute(BotUser user, MessageCreateEvent event, String[] args);
	
}
