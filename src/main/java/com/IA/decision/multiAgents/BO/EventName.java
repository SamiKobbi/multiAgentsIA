package com.IA.decision.multiAgents.BO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class EventName {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	public Boolean prospected;
	public Boolean getProspected() {
		return prospected;
	}

	public void setProspected(Boolean prospected) {
		this.prospected = prospected;
	}
	private Boolean eventDegree;
	public Boolean getEventDegree() {
		return eventDegree;
	}
	private int likelihood;
	public void setEventDegree(Boolean eventDegree) {
		this.eventDegree = eventDegree;
	}

	public EventName(String name, Boolean prospected, Boolean confirmed, Boolean eventDegree) {
		super();
		this.name = name;
		this.confirmed = confirmed;
		this.eventDegree = eventDegree;
		this.prospected = prospected;
	}
	private Boolean confirmed;

	public Boolean getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}
	private String name;
	@ManyToOne
	private GoalName goalName;
	
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


	public GoalName getGoalName() {
		return goalName;
	}

	public void setGoalName(GoalName goalName) {
		this.goalName = goalName;
	}

	public EventName()
	{
		
	}

	public int getLikelihood() {
		return likelihood;
	}

	public void setLikelihood(int likelihood) {
		this.likelihood = likelihood;
	}
	
}
