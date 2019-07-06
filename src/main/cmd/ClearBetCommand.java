package main.cmd;

import java.awt.Color;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import main.BotUser;
import main.Config;
import roulette.Roulette;

public class ClearBetCommand extends CommandExecutor {
	
	protected AllowedChannels channels;
	
	public void execute(BotUser user, MessageCreateEvent event, String args[]) {
		TextChannel c = event.getChannel();
		channels = new AllowedChannels(new String[]{"roulette"});
		EmbedBuilder embed = new EmbedBuilder()
			    .setAuthor("Roulette", "", "https://vignette.wikia.nocookie.net/2007scape/images/3/30/Coins_10000.png")
				.setColor(Color.BLUE);
			
		if (!channels.contains(c)) {
			return;
		}
		
		if (Roulette.getUserBets(user).size() == 0) {
			embed.setColor(Color.RED);
			embed.setTitle(user.getName() + ", you have no active bets to clear.");
		} else {
			Roulette.removeUserBets(user, true);
			embed.setTitle(user.getName() + ", your bets have been cleared.");
		}
		c.sendMessage(embed);
	}
}
