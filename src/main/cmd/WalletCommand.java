package main.cmd;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;

import main.BotUser;

public class WalletCommand extends CommandExecutor {

	@Override
	public void execute(BotUser user, MessageCreateEvent event, String args[]) {
		TextChannel c = event.getChannel();
		c.sendMessage(user.getMention() + " has "+ user.getCash() + "gp in his wallet.");
	}

}
