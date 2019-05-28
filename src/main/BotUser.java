package main;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="BotUser")
public class BotUser {
	
	private String name;
	private String mention;
	private int cash;
	
	public BotUser() {
		//
	}
	
	public String getName() {
		return this.name;
	}
	
	@XmlElement(name = "name")
	public void setName(String name) {
		this.name = name;
	}
	
	public int getCash() {
		return this.cash;
	}
	
	@XmlElement(name = "cash")
	public void setCash(int cash) {
		this.cash = cash;
	}
	
	public String getMention() {
		return this.mention;
	}
	
	@XmlElement(name = "mention")
	public void setMention(String mention) {
		this.mention = mention;
	}
}
