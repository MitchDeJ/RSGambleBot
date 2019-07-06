package main.cmd;

import java.awt.Color;
import java.util.Arrays;
import java.util.Random;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import main.BotUser;
import main.Config;
import roulette.Roulette;
import roulette.RouletteBet;
import roulette.RouletteBetType;
import xml.XMLDatabase;

public class RouletteCommand extends CommandExecutor {
	
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
			embed.setTitle(user.getName() + ", please place a bet using !bet first.");
			c.sendMessage(embed);
			return;
		}

		Random rng = new Random();
		int num = rng.nextInt(36);
		int win = 0;
		int result = 0;
		
		for (RouletteBet b : Roulette.getUserBets(user)) {	
			if (Roulette.win(b, num)) {
				win += (b.getBet() * Roulette.getWinMultiplier(b));
				result += (b.getBet() * Roulette.getWinMultiplier(b)) - b.getBet();
			} else {
				result -= b.getBet();
			}
		}
		
		if (result > 0) {
			embed.setColor(Color.GREEN);
			embed.setTitle(user.getName() + ", the ball lands on "+num+", you won "+result+"gp!");
		} else if (result == 0) {
			embed.setColor(Color.BLUE);
			embed.setTitle(user.getName() + ", the ball lands on "+num+", you broke even.");
		} else if (result < 0) {
			embed.setColor(Color.RED);
			embed.setTitle(user.getName() + ", the ball lands on "+num+", you lost "+(result * -1)+"gp.");
		}
		
		Roulette.removeUserBets(user, false);
		user.setCash(user.getCash() + win);
		XMLDatabase.saveXML(user);
		
		//show color
		if (Arrays.stream(Roulette.black).anyMatch(i -> i == num)) {
			embed.setAuthor("Roulette", "", "https://i.imgur.com/JItLBDH.png");
		}
		if (Arrays.stream(Roulette.red).anyMatch(i -> i == num)) {
			embed.setAuthor("Roulette", "", "https://i.imgur.com/SQwfobm.png");			
		}
		if (num == 0) {
			embed.setAuthor("Roulette", "", "https://i.imgur.com/cOgUKMw.png");			
		}
		
		c.sendMessage(embed);
	}
}
