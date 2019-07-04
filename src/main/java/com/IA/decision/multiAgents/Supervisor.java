package com.IA.decision.multiAgents;





import jade.core.Agent;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class Supervisor extends Agent {
	public static void main(String[] args) {
		try {
			// Récupération du conteneur (Main Container) en cours d'execution de Jade					
			Runtime rt = Runtime.instance();
		
			// Création du profil par défault
			ProfileImpl p = new ProfileImpl(false);
			AgentContainer container =rt.createAgentContainer(p); // get a container controller for creating new agents
		
			// Agent controleur pour permettre la création des agents 
			AgentController Agent=null;		
		
			/* Création de l'agent Portail
			   cette commande est équivalente à la suivante: 
			   java jade.Boot Portail:JADE_exemple_personnel.Portail
			*/
			Agent = container.createNewAgent("Supervisor", "learning.Supervisor", null);
		
			// Démarrage de l'agent Portail
			Agent.start();											
			} catch (Exception any) {
			any.printStackTrace();}				
		}
}
