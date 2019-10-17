package com.IA.decision.multiAgents;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.IA.decision.multiAgents.BO.Agent;
import com.IA.decision.multiAgents.BO.OCC;
import com.IA.decision.multiAgents.BO.OCCsTowardsAgent;
import com.IA.decision.multiAgents.repositories.AgentRepository;
import com.IA.decision.multiAgents.repositories.OCCRepository;
import com.IA.decision.multiAgents.repositories.OCCsTowardsAgentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MultiAgentsApplicationTests {
@Autowired
private OCCsTowardsAgentRepository occsTowardsAgentRepository;
@Autowired
private AgentRepository agentRepository;
@Autowired
private OCCRepository occRepository;
	@Test
	public void contextLoads() {
	}
	 @Test
	    public void testHomeController() {
		 Agent agent = new Agent("Sami");
		 agentRepository.save(agent);
		 Agent agent1 = new Agent("Hiba");
		 agentRepository.save(agent1);
		 Agent agent2 = new Agent("Hiba2");
		 agentRepository.save(agent2);
		 OCC occ = new OCC();
		 occ.setAgent(agent);
		 List<OCCsTowardsAgent> occsTowardsAgentList = new LinkedList<>();
		 OCCsTowardsAgent occsTowardsAgent = new OCCsTowardsAgent();
		 occsTowardsAgent.setAgentDest(agent1);
		 occsTowardsAgent.setOCCValue(0.3);
		 occsTowardsAgentList.add(occsTowardsAgent);
		 occsTowardsAgent = new OCCsTowardsAgent();
		 occsTowardsAgent.setAgentDest(agent2);
		 occsTowardsAgent.setOCCValue(0.5);
		 occsTowardsAgentList.add(occsTowardsAgent);
		 occsTowardsAgentRepository.saveAll(occsTowardsAgentList);
		 occ.setHappyFor(occsTowardsAgentList);
		 occRepository.save(occ);
	    }

}
