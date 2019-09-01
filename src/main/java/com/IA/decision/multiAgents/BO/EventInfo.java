package com.IA.decision.multiAgents.BO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
@Entity
public class EventInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	public EventInfo(Double eventIntensityLevel) {
		super();
	
		this.eventIntensityLevel = eventIntensityLevel;
		
	}
	public EventInfo()
	{
		
	}
	
	private Double eventIntensityLevel;
	@OneToOne
	private Agent agent;
	@ManyToOne
	private EventName eventName;
	

	
	public Double getEventIntensityLevel() {
		return eventIntensityLevel;
	}
	public void setEventIntensityLevel(Double eventIntensityLevel) {
		this.eventIntensityLevel = eventIntensityLevel;
	}
	public Agent getAgent() {
		return agent;
	}
	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public EventName getEventName() {
		return eventName;
	}
	public void setEventName(EventName eventName) {
		this.eventName = eventName;
	}
}
