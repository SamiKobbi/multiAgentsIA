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
public class Action {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Boolean getActionDegree() {
		return actionDegree;
	}
	public void setActionDegree(Boolean actionDegree) {
		this.actionDegree = actionDegree;
	}
	public Boolean getRequestOrResponse() {
		return requestOrResponse;
	}
	public void setRequestOrResponse(Boolean requestOrResponse) {
		this.requestOrResponse = requestOrResponse;
	}
	public Double getApprovalDegreeLevel() {
		return approvalDegreeLevel;
	}
	public void setApprovalDegreeLevel(Double approvalDegreeLevel) {
		this.approvalDegreeLevel = approvalDegreeLevel;
	}

	public Agent getAgentSrc() {
		return agentSrc;
	}
	public void setAgentSrc(Agent agentSrc) {
		this.agentSrc = agentSrc;
	}
	public Agent getAgentDest() {
		return agentDest;
	}
	public void setAgentDest(Agent agentDest) {
		this.agentDest = agentDest;
	}
	
	public Action() {

	}
	public Action(String message, Boolean actionDegree, Boolean requestOrResponse, Double approvalDegreeLevel) {
		   this.message = message;
		   this.actionDegree = actionDegree;
		   this.requestOrResponse = requestOrResponse;
		   this.approvalDegreeLevel = approvalDegreeLevel;
	}
	private String message;
	private Boolean actionDegree;
	private Boolean requestOrResponse;
	private Double approvalDegreeLevel;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EVENT_REACTION_ID")
	private EventReaction eventReaction;

	public EventReaction getEventReaction() {
		return eventReaction;
	}
	public void setEventReaction(EventReaction eventReaction) {
		this.eventReaction = eventReaction;
	}
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "agent_src_id", referencedColumnName = "AGENT_ID")
	private Agent agentSrc;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "agent_dest_id", referencedColumnName = "AGENT_ID")
	private Agent agentDest;
	
}
