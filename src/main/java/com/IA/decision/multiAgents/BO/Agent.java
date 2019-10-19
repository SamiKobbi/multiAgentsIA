package com.IA.decision.multiAgents.BO;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Agent {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@OneToMany
	private List<LikingTowardsAgent> likingTowardAgent;
	
	public List<LikingTowardsAgent> getLikingTowardAgent() {
		return likingTowardAgent;
	}

	public void setLikingTowardAgent(List<LikingTowardsAgent> likingTowardAgent) {
		this.likingTowardAgent = likingTowardAgent;
	}

	public Long getId() {
		return id;
	}

	private String name;

	public Agent(String name) {
		this.name = name;
	}

	public Agent() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
