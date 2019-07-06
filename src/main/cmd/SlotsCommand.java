package main.cmd;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import main.BotUser;
import main.Config;
import xml.XMLDatabase;

public class SlotsCommand extends CommandExecutor {

	@Override
	public void execute(BotUser user, MessageCreateEvent event, String[] args) {
		TextChannel c = event.getChannel();
		
		channels = new AllowedChannels(new String[]{"slots"});
		
		if (!channels.contains(c)) {
			return;
		}
		
		int bet = 0;
		EmbedBuilder embed = new EmbedBuilder()
				.setAuthor("Slots", "", "https://vignette.wikia.nocookie.net/2007scape/images/3/30/Coins_10000.png")
				.setColor(Color.RED);
		
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
		
		List<String> slots = new ArrayList<String>();
		slots.add(0, ":boom:"); //just visual
		//actual slots
		slots.add(1, ":apple:");
		slots.add(2, ":tangerine:");
		slots.add(3, ":watermelon:");
		slots.add(4, ":cherries:");
		slots.add(5, ":grapes:");
		slots.add(6, ":boom:");
		//actual slots
		slots.add(7, ":apple:"); //just visual
		
		Random rng = new Random();
		
		int slot_1 = rng.nextInt(5) + 1;
		int slot_2 = rng.nextInt(5) + 1;
		int slot_3 = rng.nextInt(5) + 1;
		
		boolean win;
		int winMultiplier;
		
		if (slot_1 == slot_2 && slot_1 == slot_3) {//3 matches
			win = true;
			winMultiplier = 20;
			embed.setTitle(user.getName() + " won "+(bet * winMultiplier)+"gp.");
		} else { //lose
			win = false;
			winMultiplier = 0;
			embed.setTitle(user.getName() + " lost "+bet+"gp.");
		}
		
		Color color;
		if (win) {
			color = Color.GREEN;
			user.setCash(user.getCash() + (bet * winMultiplier));
		} else {
			user.setCash(user.getCash() - bet);
			color = Color.RED;			
		}
		
			embed
			.setColor(color)
			.setDescription(
					slots.get(slot_1 - 1)+" "+slots.get(slot_2 - 1)+" "+slots.get(slot_3 - 1) +
					"\n\n"+slots.get(slot_1)+" "+slots.get(slot_2)+" "+slots.get(slot_3) + 
					"\n\n"+slots.get(slot_1 + 1)+" "+slots.get(slot_2 + 1)+" "+slots.get(slot_3 + 1)
					);
		
		
		c.sendMessage(embed);
		XMLDatabase.saveXML(user);
	}

}
