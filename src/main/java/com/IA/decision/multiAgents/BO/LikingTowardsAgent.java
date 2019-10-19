package com.IA.decision.multiAgents.BO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class LikingTowardsAgent {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private double likingValue;
	@OneToOne
	private Agent agentDest;

	public Agent getAgentDest() {
		return agentDest;
	}
	public void setAgentDest(Agent agentDest) {
		this.agentDest = agentDest;
	}
	public double getLikingValue() {
		return likingValue;
	}
	public void setLikingValue(double likingValue) {
		this.likingValue = likingValue;
	}
	
}
