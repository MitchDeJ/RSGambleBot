package roulette;

import main.BotUser;

public class RouletteBet {
	
	private RouletteBetType type;
	private int num;
	private int bet;
	private BotUser user;
	
	public RouletteBet(BotUser user, RouletteBetType type, int bet) {
		this.user = user;
		this.type = type;
		this.bet = bet;
	}
	
	public RouletteBet(BotUser user, RouletteBetType type, int bet, int num) {
		this.user = user;
		this.type = type;
		this.bet = bet;
		this.num = num;
	}
	
	public RouletteBetType getType() {
		return type;
	}
		
	public int getNum() {
		return num;
	}

	public int getBet() {
		return bet;
	}
	
	public BotUser getUser() {
		return this.user;
	}
}
