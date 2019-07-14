package com.IA.decision.multiAgents.BO;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToOne;

@Entity
public class GoalWeight {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Double weight;

	public GoalWeight(Double weight) {
		super();
		this.weight = weight;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Goal getGoal() {
		return goal;
	}

	public void setGoal(Goal goal) {
		this.goal = goal;
	}

	@OneToOne
	private Agent agent;
	@OneToOne
	private Goal goal;

	public GoalWeight() {

	}
}
