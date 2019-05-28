package main.cmd;

import java.awt.Color;
import java.util.Random;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import main.BotUser;
import main.Config;
import xml.XMLDatabase;

public class DiceCommand extends CommandExecutor {
	
	protected AllowedChannels channels;

	@Override
	public void execute(BotUser user, MessageCreateEvent event, String[] args) {
		TextChannel c = event.getChannel();
		channels = new AllowedChannels(new String[]{"dicing"});
		EmbedBuilder embed = new EmbedBuilder()
			    .setAuthor("Dicing", "", "https://vignette.wikia.nocookie.net/2007scape/images/3/30/Coins_10000.png")
				.setColor(Color.RED);
		
		if (!channels.contains(c)) {
			return;
		}
		
		Random rng = new Random();
		int bet = 0;
		
		if (args.length == 1) {
			embed.setTitle(user.getName() + ", syntax is: "+args[0]+" [amount]");
			c.sendMessage(embed);
			return;
		}
		
		try {
		bet = Integer.parseInt(args[1]);
		} catch(NumberFormatException e) {
			embed.setTitle(user.getName() + ", '" + args[1] + "' is not a valid bet.");
			c.sendMessage(embed);
			return;
		}
		
		if (bet > user.getCash()) {
			embed.setTitle(user.getName() + ", You do not have enough gp to bet that much.");
			c.sendMessage(embed);
			return;
		}
		
		if (bet < Config.MIN_DICE_BET) {
			embed.setTitle(user.getName() + ", The minimum bet here is "+Config.MIN_DICE_BET + "gp.");
			c.sendMessage(embed);
			return;
		}
		
		int num = rng.nextInt(99) + 1;
		
		if (num > 55) {//win
			c.sendMessage(getResultEmbed(user, num, true, bet));
			user.setCash(user.getCash() + bet);
		} else {//lose
			c.sendMessage(getResultEmbed(user, num, false, bet));
			user.setCash(user.getCash() - bet);
		}
		XMLDatabase.saveXML(user);
	}
	
	public EmbedBuilder getResultEmbed(BotUser user, int num, boolean win, int bet) {
		EmbedBuilder embed = new EmbedBuilder()
			    .setAuthor("Dicing", "", "https://vignette.wikia.nocookie.net/2007scape/images/3/30/Coins_10000.png");
			    String title = user.getName()+" rolled a "+num;
		if (win) {
			    embed.setColor(Color.GREEN);
			    title += " and won "+bet+"gp";
		} else {
				embed.setColor(Color.RED);
			    title += " and lost "+bet+"gp";
		}
		embed.setTitle(title);
		return embed;
	}

}
