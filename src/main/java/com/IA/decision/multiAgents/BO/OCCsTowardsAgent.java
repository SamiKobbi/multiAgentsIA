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
private Agent agent;

public double getOCCValue() {
	return OCCValue;
}
public void setOCCValue(double OCCValue) {
	this.OCCValue = OCCValue;
}
public Agent getAgent() {
	return agent;
}
public void setAgent(Agent agent) {
	this.agent = agent;
}


}
