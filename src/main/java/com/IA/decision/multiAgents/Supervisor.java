package com.IA.decision.multiAgents;

import jade.core.Agent;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class Supervisor extends Agent {
	
	
	public static void go()
	{
		try {
		  // Récupération du conteneur (Main Container) en cours d'execution de Jade					
		  Runtime rt = Runtime.instance();
		  // Création du profil par défault
		  ProfileImpl p = new ProfileImpl(false);
		  AgentContainer container =rt.createAgentContainer(p);
		  // Agent controleur pour permettre la création des agents 
		  AgentController Agent=null;
		  //------
		  Agent = container.createNewAgent("Diffuseur", "learning.Diffuseur", null);
		  Agent.start();	
		  System.out.println("Diffuseur est lancé");
		  Agent = container.createNewAgent("Agent1", "learning.Agent1", null);
		  Agent.start();	
		  System.out.println("Agent1 est lancé");
		  //------
		  Agent = container.createNewAgent("Agent2", "learning.Agent2", null);
		  Agent.start();
		  System.out.println("Agent2 est lancé");
		  //------
		  //Agent = container.createNewAgent("Agent3", "learning.Agent3", null);
		  //Agent.start();
		  //jTextArea.append("<Agent3> est lancé "+"\n");
		  //------
		  
		  } catch (Exception any) {
		    any.printStackTrace();}
	}
	/**
	 * Cette méthode est appelé directement apèes la création de l'agent pour
	 * permettre l'initialisation et l'affectation des différents comportements à
	 * cet agent
	 */
	protected void setup() {

		System.out.println("Agent " + getLocalName() + " est lancé ");
		try {

			// Création de desciprion de l'agent [Portail]
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());

			// Enregistrement de la description de l'agent dans DF (Directory Facilitator)
			DFService.register(this, dfd);
			System.out.println("Agent " + getLocalName() + " est enregistré dans DF (Directory Facilitator) ");
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

	public static void main(String[] args) {
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
			Agent = container.createNewAgent("Supervisor", "learning.Supervisor", null);

			// Démarrage de l'agent Portail
			Agent.start();
		} catch (Exception any) {
			any.printStackTrace();
		}
	}
}
