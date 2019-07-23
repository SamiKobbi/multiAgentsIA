package com.IA.decision.multiAgents.Jade;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import jade.core.AID;
import jade.core.Agent;
import jade.core.Runtime;
import jade.core.ProfileImpl;

import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;

import jade.wrapper.*;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.IA.decision.multiAgents.MultiAgentsApplication;
import com.IA.decision.multiAgents.repositories.AgentRepository;

public class Supervisor extends Agent {
	@Autowired
	private AgentRepository agentRepo;
	private static AnnotationConfigApplicationContext context;

	protected void setup() {

		System.out.println("Agent " + getLocalName() + " est lancé ");
		try {
			Scanner s = new Scanner(System.in);
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());
			DFService.register(this, dfd);
			String action = s.next();
			if(action.equals("go"))
			{
				goAgents();
			}
		} catch (FIPAException e) {
			e.printStackTrace();
		}

		// addBehaviour(new SupervisorBehaviour());
		// addBehaviour(new SupervisorBehaviour2());
	}

	protected void takeDown() {
		try {
			// Suppression de l'agent [Portail] depuis le DF
			DFService.deregister(this);
			System.out
					.println("Agent " + getLocalName() + " est terminé et supprimé depuis DF (Directory Facilitator) ");
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}

	private static void goAgents() {

		try {

			context = new AnnotationConfigApplicationContext(
					MultiAgentsApplication.class);

			// Récupération du conteneur (Main Container) en cours d'execution de Jade
			Runtime rt = Runtime.instance();
			// Création du profil par défault
			ProfileImpl p = new ProfileImpl(false);
			AgentContainer container = rt.createAgentContainer(p);
			// Agent controleur pour permettre la création des agents
			AgentController Agent = null;
			// ------
			try {
				container.getAgent("Diffuseur");
			}
			catch(jade.wrapper.ControllerException ex)
			{
				Agent = container.createNewAgent("Diffuseur", "com.IA.decision.multiAgents.Jade.Diffuseur", null);
				Agent.start();
				
			}
		
			AgentRepository agentRepo = context.getBean(AgentRepository.class);

			for (com.IA.decision.multiAgents.BO.Agent agent : agentRepo.findAll()) {
				if((container.getAgent(agent.getName()) == null))
				{
					try {
						container.getAgent(agent.getName());
					}
					catch(jade.wrapper.ControllerException ex)
					{
						Agent = container.createNewAgent(agent.getName(), "com.IA.decision.multiAgents.Jade.AgentTemplate",
								null);
						Agent.start();
					}
			
				}
			}

			// Agent = container.createNewAgent("Agent3", "learning.Agent3", null);
			// Agent.start();
			// jTextArea.append("<Agent3> est lancé "+"\n");
			// ------

		} catch (Exception any) {
			any.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		try {
			// Récupération du conteneur (Main Container) en cours d'execution de Jade
			Runtime rt = Runtime.instance();

			// Création du profil par défault
			ProfileImpl p = new ProfileImpl(false);
			AgentContainer container = rt.createAgentContainer(p); // get a container controller for creating new agents

			// Agent controleur pour permettre la création des agents
			AgentController Agent = null;

			/*
			 * Création de l'agent Portail cette commande est équivalente à la suivante:
			 * java jade.Boot Portail:JADE_exemple_personnel.Portail
			 */
			Agent = container.createNewAgent("Supervisors", "com.IA.decision.multiAgents.Jade.Supervisor", null);

			// Démarrage de l'agent Portail
			Agent.start();
		
		} catch (Exception any) {
			any.printStackTrace();
		}
	}
}
