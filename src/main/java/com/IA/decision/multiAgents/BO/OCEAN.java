package com.IA.decision.multiAgents.BO;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
@Entity
public class OCEAN {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	

	private Double openness;
	private Double conscientiousness;
	private Double extraversion;
	private Double agreeableness;
	private Double neuroticism;
	@OneToOne(cascade=CascadeType.ALL,  fetch = FetchType.EAGER)
	@JoinColumn(name = "AGENT_ID", referencedColumnName = "AGENT_ID")
	private Agent agent;
	
	public OCEAN()
	{
		
	}
	public String toString() {
		return "Openenss : " + openness +", neuroticism : "+neuroticism;
	}
	public OCEAN(Double openness,Double conscientiousness ,Double extraversion,Double agreeableness,Double neuroticism)
	{
		this.openness = openness;
		this.conscientiousness = conscientiousness;
		this.extraversion = extraversion;
		this.agreeableness = agreeableness;
		this.neuroticism = neuroticism;
	}

	
	public Double getOpenness() {
		return openness;
	}
	public void setOpenness(Double openness) {
		this.openness = openness;
	}
	public Double getConscientiousness() {
		return conscientiousness;
	}
	public void setConscientiousness(Double conscientiousness) {
		this.conscientiousness = conscientiousness;
	}
	public Double getExtraversion() {
		return extraversion;
	}
	public void setExtraversion(Double extraversion) {
		this.extraversion = extraversion;
	}
	public Double getAgreeableness() {
		return agreeableness;
	}
	public void setAgreeableness(Double agreeableness) {
		this.agreeableness = agreeableness;
	}
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	public Agent getAgent() {
		return agent;
	}
	public Double getNeuroticism() {
		return neuroticism;
	}
	public void setNeuroticism(Double neuroticism) {
		this.neuroticism = neuroticism;
	}

}
