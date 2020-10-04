package com.IA.decision.multiAgents.BO;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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


	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "AGENT_ID", referencedColumnName = "AGENT_ID")
	private Agent agent;
	 @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "GOAL_NAME_ID")
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
