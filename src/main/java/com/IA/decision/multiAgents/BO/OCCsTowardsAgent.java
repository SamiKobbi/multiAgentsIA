package com.IA.decision.multiAgents.BO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class OCCsTowardsAgent {

	
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;

private double OCCValue;
@OneToOne
private Agent agentDest;

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
