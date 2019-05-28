package main;
import java.util.Random;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import xml.XMLDatabase;

public class Main {
    public static void main(String[] args) {
        // Insert your bot's token here
        String token = Config.TOKEN;
        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        // Add a listener which answers with "Pong!" if someone writes "!ping"
        api.addMessageCreateListener(event -> {
        	
        		if (event.getMessageAuthor().isBotUser())
        			return;
        	
        		String name = event.getMessageAuthor().getDiscriminatedName();
        		String mention = event.getMessageAuthor().asUser().get().getMentionTag();
                
                if (!XMLDatabase.userExists(name)) {
                    BotUser u = new BotUser();
                    u.setName(name);
                    u.setCash(Config.STARTER_CASH);
                    u.setMention(mention);
                    XMLDatabase.saveXML(u);
                    event.getChannel().sendMessage("Created a save file for "+mention+".");
                }
                
					BotUser user = XMLDatabase.LoadUserXML(name);
					MessageHandler cmd = new MessageHandler();
					cmd.handle(user, event);
        });

        // Print the invite url of your bot
        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());
    }

}
