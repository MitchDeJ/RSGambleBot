package roulette;

public enum RouletteBetType {
	RED("red"),
	BLACK("black"),
	EVEN("even"),
	ODD("odd"),
	ONE_TO_EIGHTEEN("1to18"),
	NINETEEN_TO_THIRTYSIX("19to36"),
	FIRST_12("1st12"),
	SECOND_12("2nd12"),
	THIRD_12("3rd12"),
	NUMBER("NOT_USED");
	
	private String name;

	private RouletteBetType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}