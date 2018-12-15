package gameUtils;

public class Paired {
	private Fruit fruit;
	private Packman packman;
	private double travelTime;
	
	public Paired(Fruit fruit ,Packman packman,double travelTime) {
		this.fruit=fruit;
		this.packman=packman;
		this.travelTime=travelTime;
	}

	public Fruit getFruit() {
		return fruit;
	}
	public Packman getPackman() {
		return packman;
	}
	public double getTravelTime() {
		return this.travelTime;
	}
}
