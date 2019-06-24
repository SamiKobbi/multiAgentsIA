package com.IA.decision.multiAgents.BO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
@Entity
public class Goal {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private String name;
	private Double weight;
	@OneToOne
	private Agent agent;
	
	public Goal()
	{
		
	}
	public Goal(String goalName, double goalWeight) {
	   this.name = goalName;
	   this.weight = goalWeight;
	}
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	public Agent getAgent() {
		return agent;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
}
