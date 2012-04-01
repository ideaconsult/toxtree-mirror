package dk.smartcyp.core;

import dk.smartcyp.smirks.SMARTCYPReaction;

public class SMARTSData {

	protected double energy;
	protected SMARTCYPReaction reaction;
	
	public SMARTCYPReaction getReaction() {
		return reaction;
	}
	public void setReaction(SMARTCYPReaction reaction) {
		this.reaction = reaction;
	}
	public SMARTSData(double energy, SMARTCYPReaction reaction) {
		setEnergy(energy);
		setReaction(reaction);
	}
	public double getEnergy() {
		return energy;
	}
	public void setEnergy(double energy) {
		this.energy = energy;
	}
	@Override
	public String toString() {
		return String.format("Energy %f %s %s",energy,reaction.toString(),reaction.getSMIRKS());
	}
	

}
