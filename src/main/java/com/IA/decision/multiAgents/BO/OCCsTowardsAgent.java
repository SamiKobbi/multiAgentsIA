package com.IA.decision.multiAgents.BO;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class OCCsTowardsAgent {

	
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;

private double OCCValue;
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "agent_dest_id", referencedColumnName = "AGENT_ID")
private Agent agentDest;




@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "OCC_ID", insertable = false, updatable = false)
private OCC  agentAdmiration;
	

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "OCC_ID", insertable = false, updatable = false)
private OCC  agentReproach;	

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "OCC_ID", insertable = false, updatable = false)
private OCC agentHappyFor;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "OCC_ID", insertable = false, updatable = false)
private OCC  agentSorryFor;	

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "OCC_ID", insertable = false, updatable = false)
private OCC  agentGloating;		

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "OCC_ID", insertable = false, updatable = false)
private OCC  agentPity;	

public Agent getAgentDest() {
	return agentDest;
}
public void setAgentDest(Agent agentDest) {
	this.agentDest = agentDest;
}
public double getOCCValue() {
	return OCCValue;
}
public void setOCCValue(double OCCValue) {
	this.OCCValue = OCCValue;
}



}
