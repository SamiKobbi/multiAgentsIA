package com.IA.decision.multiAgents.Jade;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.IA.decision.multiAgents.MultiAgentsApplication;
import com.IA.decision.multiAgents.config.ApplicationContextProvider;
import com.IA.decision.multiAgents.repositories.ActionRepository;
import com.IA.decision.multiAgents.repositories.AgentRepository;
import com.IA.decision.multiAgents.repositories.EventRepository;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Diffuseur extends Agent {


	protected void setup() {
	
		System.out.println(getLocalName() + " démarré");
		// ---------------
		try {
		

			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());
			// Enregistrement de la description de l'agent1 dans DF (Directory Facilitator)
			DFService.register(this, dfd);
			System.out.println(getLocalName() + " enregistré par le <DF>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		addBehaviour(new DiffBehaviour());
		addBehaviour(new DiffBehaviour2());
	}

	private class DiffBehaviour extends Behaviour {
		private int i = 0, o = 0;

		public void action() {
			 AgentRepository agentRepo = ApplicationContextProvider.getApplicationContext().getBean(AgentRepository.class);
			 EventRepository eventRepo = ApplicationContextProvider.getApplicationContext().getBean(EventRepository.class);
			 ActionRepository actionRepo = ApplicationContextProvider.getApplicationContext().getBean(ActionRepository.class);
				
			 System.out.println("<Diffuseur Agent: searching for events from the network>");
		
			try {
				List<com.IA.decision.multiAgents.BO.Agent> listAgents = agentRepo.findAll();
				// System.out.println("<Diffuseur Agent: sending events to all the network>");
				for(com.IA.decision.multiAgents.BO.Agent agent:listAgents)
				{
					List<com.IA.decision.multiAgents.BO.Event> events = eventRepo.findByAgent(agent);
					for(com.IA.decision.multiAgents.BO.Event event:events)
					{
						ACLMessage msg3 = new ACLMessage(ACLMessage.INFORM);
						msg3.setContent("event:"+event.getId().toString());
						msg3.addReceiver(new AID(agent.getName(), AID.ISLOCALNAME));
			
						// msg3.addReceiver(new AID("Agent3", AID.ISLOCALNAME));
						// Envoyer le message � l'agent Vendeur
						send(msg3);
						o++;
					}
					List<com.IA.decision.multiAgents.BO.Action> actions = actionRepo.findByAgentDest(agent);
					for(com.IA.decision.multiAgents.BO.Action action : actions)
					{
						ACLMessage msg3 = new ACLMessage(ACLMessage.INFORM);
						msg3.setContent("action:" + action.getId().toString());
						msg3.addReceiver(new AID(agent.getName(), AID.ISLOCALNAME));
						msg3.setSender(new AID(action.getAgentSrc().getName(), AID.ISLOCALNAME));
						
						// msg3.addReceiver(new AID("Agent3", AID.ISLOCALNAME));
						// Envoyer le message � l'agent Vendeur
						send(msg3);
						o++;
					}
	
				// o++;
				
				}
				//msg3.setContent(content);

				// Pr�ciser les agents destinataires du message qui est l'agent Vendeur dans ce
				// cas
				
			} catch (Exception e) {
				System.err.println("Erreur lors de l'accés au fichier !");
				e.printStackTrace();
			}

		}

		@Override
		public boolean done() {
			return o==3;
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
				System.out.println("Diffuseur received" + msg2.getContent() + msg2.getSender());

				
			
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
