package com.IA.decision.multiAgents.BO;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class EventReaction {
	@Id
	@Column(name = "EVENT_REACTION_ID")
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

	@OneToMany(mappedBy = "eventReaction", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Action> actions = new HashSet<>();

	private String reactionName;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "event_info_id", referencedColumnName = "id")
	private EventInfo eventInfo;

	public EventInfo getEventInfo() {
		return eventInfo;
	}
	public void setEventInfo(EventInfo eventInfo) {
		this.eventInfo = eventInfo;
	}

}
