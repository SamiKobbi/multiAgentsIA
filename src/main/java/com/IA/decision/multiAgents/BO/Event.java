package com.IA.decision.multiAgents.BO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}

	public Boolean getEventDegree() {
		return eventDegree;
	}

	public void setEventDegree(Boolean eventDegree) {
		this.eventDegree = eventDegree;
	}

	public Double getEventIntensityLevel() {
		return eventIntensityLevel;
	}

	public void setEventIntensityLevel(Double eventIntensityLevel) {
		this.eventIntensityLevel = eventIntensityLevel;
	}




	public Event(String name, Boolean confirmed, Boolean eventDegree, Double eventIntensityLevel) {
		this.name = name;
		this.confirmed = confirmed;
		this.eventDegree = eventDegree;
		this.eventIntensityLevel = eventIntensityLevel;
	}

	private String name;
	private Boolean confirmed;
	private Boolean eventDegree;
	private Double eventIntensityLevel;
	
	@ManyToOne
	private GoalName goalName;

	@ManyToOne
	private Agent agent;
	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public GoalName getGoalName() {
		return goalName;
	}

	public void setGoalName(GoalName goalName) {
		this.goalName = goalName;
	}

	public Event()
	{
		
	}
	
}
