package com.IA.decision.multiAgents.BO;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;



@Entity
public class Agent {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "AGENT_ID")
	private Long id;

	@Column(name = "AGENT_DRUG_STATUS")
	private String agentDrugStatus;
	public String getAgentDrugStatus() {
		return agentDrugStatus;
	}

	public void setAgentDrugStatus(String agentDrugStatus) {
		this.agentDrugStatus = agentDrugStatus;
	}

	//FetchType.EAGER load all children(likingtowardAgent) when loading Agent
	@OneToMany(mappedBy = "agent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<EmotionTowardsAgent> emotionTowardAgent;

	public List<EmotionTowardsAgent> getEmotionTowardAgent() {
		return emotionTowardAgent;
	}

	public void setEmotionTowardAgent(List<EmotionTowardsAgent> emotionTowardAgent) {
		this.emotionTowardAgent = emotionTowardAgent;
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
	
	public String toString() {
		return "agentName: " + this.name +" ,agentDrugStatus: "+ this.agentDrugStatus;
	}
    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Agent)) {
            return false;
        }

        Agent user = (Agent) o;

        return user.name.equals(name);
    }
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        return result;
    }

}
