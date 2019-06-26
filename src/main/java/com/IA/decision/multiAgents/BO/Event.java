package com.IA.decision.multiAgents.BO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Goal getGoal() {
		return goal;
	}

	public void setGoal(Goal goal) {
		this.goal = goal;
	}

	private String name;
	private String type;
	private Boolean eventDegree;
	private Double eventIntensityLevel;
	
	@ManyToOne
	private Goal goal;

	public Event()
	{
		
	}
	
}
