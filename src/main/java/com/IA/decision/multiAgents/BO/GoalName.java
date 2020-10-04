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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class GoalName {
	@Id
	@Column(name = "GOAL_NAME_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	public Long getId() {
		return id;
	}
	 @OneToMany(mappedBy = "goalName", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<GoalInfo> goalInfo = new HashSet<>();
	public void setId(Long id) {
		this.id = id;
	}

	private String name;

	public GoalName() {

	}

	public GoalName(String goalName) {
		this.name = goalName;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
