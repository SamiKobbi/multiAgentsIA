package com.IA.decision.multiAgents.BO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class OCC {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@OneToOne
	private Agent agent;
	
	public Agent getAgent() {
		return agent;
	}
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	public double getJoy() {
		return joy;
	}
	public void setJoy(double joy) {
		this.joy = joy;
	}
	public double getDistress() {
		return distress;
	}
	public void setDistress(double distress) {
		this.distress = distress;
	}
	public OCC()
	{
		
	}
	private double joy;
	private double distress;
}
