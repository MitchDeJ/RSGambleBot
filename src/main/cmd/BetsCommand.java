package main.cmd;

import java.awt.Color;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import main.BotUser;
import main.Config;
import roulette.Roulette;
import roulette.RouletteBet;
import roulette.RouletteBetType;

public class BetsCommand extends CommandExecutor {
	
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
		String title = "";
		
		for (RouletteBet b : Roulette.getUserBets(user)) {
			if (b.getType() != RouletteBetType.NUMBER) {
				title += b.getType() + ": "+b.getBet() + "gp, ";
			} else {
				title += b.getNum() + ": "+b.getBet() + "gp, ";				
			}
		}
		
		if (title == "") {
			title = user.getName() + ", you have no active roulette bets.";
		} else {
			title = user.getName() + ", your bets: " + title;
		}
		
		embed.setTitle(title);
		c.sendMessage(embed);
	}
}
