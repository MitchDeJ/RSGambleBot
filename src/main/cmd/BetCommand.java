package main.cmd;

import java.awt.Color;
import java.util.Random;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import main.BotUser;
import main.Config;
import roulette.Roulette;
import roulette.RouletteBetType;
import xml.XMLDatabase;

public class BetCommand extends CommandExecutor {
	
	protected AllowedChannels channels;

	@Override
	public void execute(BotUser user, MessageCreateEvent event, String[] args) {
		TextChannel c = event.getChannel();
		channels = new AllowedChannels(new String[]{"roulette"});
		EmbedBuilder embed = new EmbedBuilder()
			    .setAuthor("Roulette", "", "https://vignette.wikia.nocookie.net/2007scape/images/3/30/Coins_10000.png")
				.setColor(Color.RED);
		
		if (!channels.contains(c)) {
			return;
		}
		
		int bet = 0;
		
		if (args.length != 3) {
			embed.setTitle(user.getName() + ", syntax is: "+args[0]+" [amount] [option]");
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
		
		
		//determine bet type
		RouletteBetType type = null;
		int num = -1;
		
		for (RouletteBetType t : RouletteBetType.values()) {
			  if (args[2].equalsIgnoreCase(t.getName())) {
				  type = t;
			  }
		}
		
		if (args[2].chars().allMatch( Character::isDigit )) {
			num = Integer.parseInt(args[2]);
			if (num >= 0 && num <= 36)
				type = RouletteBetType.NUMBER;
		}
		
		if (type == null) {
			embed.setTitle(user.getName() + ", '"+args[2]+ "' is not a valid option. Options: 0-36, red, black, even, odd, 1st12, 2nd12, 3rd12, 1to18, 19to36");
			c.sendMessage(embed);
			return;
		}
		
		//place bet
		user.setCash(user.getCash() - bet);
		embed.setColor(Color.BLUE);		
		
		if (type != RouletteBetType.NUMBER) {
			Roulette.addBet(user, type, bet);
			embed.setTitle( user.getName()+ " placed a bet of "+bet+"gp on "+type.getName()+".");
		} else {
			Roulette.addBet(user, type, bet, num);
			embed.setTitle( user.getName()+ " placed a bet of "+bet+"gp on "+num+".");
		}
		c.sendMessage(embed);
		XMLDatabase.saveXML(user);
	}
	
}
