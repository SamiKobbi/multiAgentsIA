package com.IA.decision.multiAgents.Jade;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.IA.decision.multiAgents.MultiAgentsApplication;
import com.IA.decision.multiAgents.BO.EventName;
import com.IA.decision.multiAgents.config.ApplicationContextProvider;
import com.IA.decision.multiAgents.repositories.ActionRepository;
import com.IA.decision.multiAgents.repositories.AgentRepository;
import com.IA.decision.multiAgents.repositories.EventNameRepository;
import com.IA.decision.multiAgents.repositories.OCCRepository;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Diffuseur extends Agent {
	
	private static final Logger logger = LogManager.getLogger(Diffuseur.class);

	protected void setup() {
	
		logger.info(getLocalName() + " started");
		// ---------------
		try {
		

			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());
			// Enregistrement de la description de l'agent1 dans DF (Directory Facilitator)
			DFService.register(this, dfd);
			logger.info(getLocalName() + " is registered in the <DF>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		addBehaviour(new DiffBehaviour());
		addBehaviour(new DiffBehaviour2());
	}

	private class DiffBehaviour extends Behaviour {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int o = 0;
		 AgentRepository agentRepo = ApplicationContextProvider.getApplicationContext().getBean(AgentRepository.class);
		 EventNameRepository eventNameRepo = ApplicationContextProvider.getApplicationContext().getBean(EventNameRepository.class);
		 ActionRepository actionRepo = ApplicationContextProvider.getApplicationContext().getBean(ActionRepository.class);
		 OCCRepository occRepo = ApplicationContextProvider.getApplicationContext().getBean(OCCRepository.class);
				
		public void action() {
		
			 logger.info("<Diffuseur Agent: searching for events from the network>");
		
			try {
					occRepo.deleteAll();
					List<com.IA.decision.multiAgents.BO.Agent> agents = agentRepo.findAll();
					List<EventName> events = eventNameRepo.findAll();
					for(com.IA.decision.multiAgents.BO.EventName event:events)
					{
						logger.info("<Diffuseur Agent>: Diffusing message id:"+event.getId()+"___event:"+event.getName());
						ACLMessage msg3= new ACLMessage(ACLMessage.INFORM) ;
						msg3.setContent("event:"+event.getId().toString());
						for(com.IA.decision.multiAgents.BO.Agent agent : agents)
						{
						msg3.addReceiver(new AID(agent.getName(), AID.ISLOCALNAME));
						}
						send(msg3);
						o++;
					}

			} catch (Exception e) {
				logger.error("Erreur lors de l'accés au fichier !");
				e.printStackTrace();
			}

		}

		@Override
		public boolean done() {
			return o == eventNameRepo.findAll().size();
		}

	}

	private class DiffBehaviour2 extends Behaviour {
		int index;
		@Override
		public void action() {
			
			// TODO Auto-generated method stub
			ACLMessage msg2 = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
			if (msg2 != null) {
				
				index++;
				System.out.println("Diffuseur received " + msg2.getContent() + " from "+ msg2.getSender());

				
			
			}
		}

		@Override
		public boolean done() {
//			// TODO Auto-generated method stub
//
		
			return index==2;
		}

	}

	protected void takeDown() {

		// Suppression de l'agent [Acheteur] depuis le DF
		try {
			DFService.deregister(this);
			System.out.println(getLocalName() + " DEREGISTERED WITH THE DF");
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}

}
