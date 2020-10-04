package com.IA.decision.multiAgents.BO;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class EmotionTowardsAgent {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AGENT_ID")
	Agent agent;
	public Agent getAgent() {
		return agent;
	}
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	private double likingValue;

	private double influValue;
	private double trustValue;
	@OneToOne
	private Agent agentDest;
	public String toString() {
		return "likingValue : " + likingValue +", influValue : "+influValue+", trustValue : "+trustValue;
	}
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
	public double getInfluValue() {
		return influValue;
	}
	public double getTrustValue() {
		return trustValue;
	}
	public void setInfluValue(double influValue) {
		this.influValue = influValue;
	}
	public void setTrustValue(double trustValue) {
		this.trustValue = trustValue;
	}
}
