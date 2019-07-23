package com.IA.decision.multiAgents.BO;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class GoalInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Double weight;

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public GoalInfo(Double weight) {
		super();
		this.weight = weight;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}


	@OneToOne
	private Agent agent;
	@ManyToOne
	private GoalName goalName;

	public GoalName getGoalName() {
		return goalName;
	}

	public void setGoalName(GoalName goalName) {
		this.goalName = goalName;
	}

	public GoalInfo() {

	}
}
