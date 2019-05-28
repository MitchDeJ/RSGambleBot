package main.cmd;

import java.awt.Color;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import main.BotUser;

public class WalletCommand extends CommandExecutor {

	@Override
	public void execute(BotUser user, MessageCreateEvent event, String args[]) {
		TextChannel c = event.getChannel();
		EmbedBuilder embed = new EmbedBuilder()
			.setAuthor("Wallet", "", "https://vignette.wikia.nocookie.net/2007scape/images/3/30/Coins_10000.png")
			.setTitle(user.getName() + " has "+ user.getCash() + "gp in his wallet.")
			.setColor(Color.BLUE);
			
		c.sendMessage(embed);
	}

}
