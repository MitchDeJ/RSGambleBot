package roulette;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import main.BotUser;
import xml.XMLDatabase;


public class Roulette {

	static public List<RouletteBet> bets = new ArrayList<RouletteBet>();
	
	public static int[] green = { 0 };
	public static int[] red = { 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35 };
	public static int[] black = { 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36 };
	
	public static void addBet(BotUser user, RouletteBetType type, int amount) {
		bets.add(new RouletteBet(user, type, amount));
	}
	
	public static void addBet(BotUser user, RouletteBetType type, int amount, int num) {
		bets.add(new RouletteBet(user, type, amount, num));
	}
	
	public static void removeUserBets(BotUser user, boolean refund) {
		List<RouletteBet> toRemove = new ArrayList<RouletteBet>();
		for (RouletteBet b : bets) {
			if (b.getUser().getMention().equals(user.getMention())) {
				toRemove.add(b);
			}
		}
		for (RouletteBet b : toRemove) {
			if (refund) {
				user.setCash(user.getCash() + b.getBet());
				XMLDatabase.saveXML(user);
			}
			bets.remove(b);
		}		
	}
	
	public static List<RouletteBet> getUserBets(BotUser user) {
		List<RouletteBet> userBets = new ArrayList<RouletteBet>();
		for (RouletteBet b : bets) {
			if (b.getUser().getMention().equals(user.getMention())) {
				userBets.add(b);
			}
		}

		return userBets;
	}
	
	public static boolean win(RouletteBet bet, int num) {
		switch (bet.getType()) {
		case BLACK:
			if (IntStream.of(black).anyMatch(x -> x == num))
				return true;
			return false;
		case EVEN:
			if ((num % 2) == 0)
				return true;
			return false;
		case FIRST_12:
			if (num >= 1 && num <= 12)
				return true;
			return false;
		case NINETEEN_TO_THIRTYSIX:
			if (num >= 19 && num <= 36)
				return true;
			return false;
		case NUMBER:
			if (num == bet.getNum())
				return true;
			return false;
		case ODD:
			if ((num % 2) != 0)
				return true;
			return false;
		case ONE_TO_EIGHTEEN:
			if (num >= 1 && num <= 18)
				return true;
			return false;
		case RED:
			if (IntStream.of(red).anyMatch(x -> x == num))
				return true;
			return false;
		case SECOND_12:
			if (num >= 13 && num <= 24)
				return true;
			return false;
		case THIRD_12:
			if (num >= 25 && num <= 36)
				return true;
			return false;
		}
		return false;
	}

	static public int getWinMultiplier(RouletteBet bet) {
		switch (bet.getType()) {
		case BLACK:
			return 2;
		case EVEN:
			return 2;
		case FIRST_12:
			return 3;
		case NINETEEN_TO_THIRTYSIX:
			return 2;
		case NUMBER:
			return 36;
		case ODD:
			return 2;
		case ONE_TO_EIGHTEEN:
			return 2;
		case RED:
			return 2;
		case SECOND_12:
			return 3;
		case THIRD_12:
			return 3;

		}
		return 1;
	}
}
