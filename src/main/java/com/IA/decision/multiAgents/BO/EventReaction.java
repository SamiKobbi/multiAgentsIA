package com.IA.decision.multiAgents.BO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class EventReaction {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	public EventReaction(String eventReaction) {
		this.eventReaction = eventReaction;
	}
	public EventReaction()
	{
		
	}
	public String getEventReaction() {
		return eventReaction;
	}

	public void setEventReaction(String eventReaction) {
		this.eventReaction = eventReaction;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	private String eventReaction;
	
	@OneToOne
	private Event event;
}
