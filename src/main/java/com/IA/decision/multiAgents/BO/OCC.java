package com.IA.decision.multiAgents.BO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
public class OCC {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long agentId;

	public Long getAgentId() {
		return agentId;
	}
	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}
	@OneToOne
	@PrimaryKeyJoinColumn
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
	private double shame;
	public double getShame() {
		return shame;
	}
	public void setShame(double shame) {
		this.shame = shame;
	}
	private double proud;

	public double getProud() {
		return proud;
	}
	public void setProud(double proud) {
		this.proud = proud;
	}
}
