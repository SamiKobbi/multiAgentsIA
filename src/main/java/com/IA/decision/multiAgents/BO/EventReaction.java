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
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public EventReaction(String reactionName) {
		this.reactionName = reactionName;
	}
	public EventReaction()
	{
		
	}
	public String getReactionName() {
		return reactionName;
	}

	public void setEventReaction(String reactionName) {
		this.reactionName = reactionName;
	}



	private String reactionName;
	
	@OneToOne
	private EventInfo eventInfo;

	public EventInfo getEventInfo() {
		return eventInfo;
	}
	public void setEventInfo(EventInfo eventInfo) {
		this.eventInfo = eventInfo;
	}

}
