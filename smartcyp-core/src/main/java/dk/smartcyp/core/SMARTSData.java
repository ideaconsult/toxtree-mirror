package dk.smartcyp.core;

public class SMARTSData {

	protected double energy;
	protected String smirks;
	
	public SMARTSData(double energy, String smirks) {
		setEnergy(energy);
		setSmirks(smirks);
	}
	public double getEnergy() {
		return energy;
	}
	public void setEnergy(double energy) {
		this.energy = energy;
	}
	
	public String getSmirks() {
		return smirks;
	}
	public void setSmirks(String smirks) {
		this.smirks = smirks;
	}
}
