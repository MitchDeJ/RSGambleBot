package main.cmd;

import java.util.Random;

import org.javacord.api.entity.channel.TextChannel;
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
		
		if (!channels.contains(c)) {
			return;
		}
		
		Random rng = new Random();
		int bet = 0;
		
		if (args.length == 1) {
			c.sendMessage(user.getMention() + " syntax is: "+args[0]+" [amount]");
			return;
		}
		
		try {
		bet = Integer.parseInt(args[1]);
		} catch(NumberFormatException e) {
			c.sendMessage(user.getMention() + " '" + args[1] + "' is not a valid bet.");
			return;
		}
		
		if (bet > user.getCash()) {
			c.sendMessage(user.getMention() + " You do not have enough gp to bet that much.");
			return;
		}
		
		if (bet < Config.MIN_DICE_BET) {
			c.sendMessage(user.getMention() + " The minimum bet here is "+Config.MIN_DICE_BET + "gp.");
			return;
		}
		
		int num = rng.nextInt(99) + 1;
		
		if (num > 55) {//win
			c.sendMessage(user.getMention() +" rolled a "+num+" and won "+ bet + "gp.");
			user.setCash(user.getCash() + bet);
		} else {//lose
			c.sendMessage(user.getMention() +" rolled a "+num+" and lost "+ bet + "gp.");	
			user.setCash(user.getCash() - bet);
		}
		XMLDatabase.saveXML(user);
	}

}
