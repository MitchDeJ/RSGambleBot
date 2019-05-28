package main.cmd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.javacord.api.entity.channel.TextChannel;

public class AllowedChannels {

	private List<String> list = new ArrayList<String>();
	public AllowedChannels(String[] channels) {
		list.addAll(Arrays.asList(channels));
	}
	
	public boolean contains(String channel) {
		return list.contains(channel);
	}
	
	public boolean contains(TextChannel channel) {
		return list.contains(channel.asServerTextChannel().get().getName());
	}

}
