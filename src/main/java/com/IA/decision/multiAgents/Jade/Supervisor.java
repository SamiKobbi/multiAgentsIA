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
import jade.util.leap.Properties;
import jade.wrapper.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;

import com.IA.decision.multiAgents.MultiAgentsApplication;
import com.IA.decision.multiAgents.config.ApplicationContextProvider;
import com.IA.decision.multiAgents.repositories.AgentRepository;

@ComponentScan("com.IA.decision.multiAgents")
@SpringBootApplication
public class Supervisor extends Agent {
	private	static AgentContainer container ;
	public static AgentContainer getContainer() {
		return container;
	}

	private static AnnotationConfigApplicationContext context;
	protected void setup() {
		SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(Supervisor.class)
				.sources(Supervisor.class).properties(getProperties());
		springApplicationBuilder.run();
		System.out.println("Agent " + getLocalName() + " est lancé ");
		try {
			Scanner s = new Scanner(System.in);
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());
			DFService.register(this, dfd);

			Runtime rt = Runtime.instance();
			ProfileImpl p = new ProfileImpl(false);
			
			container = rt.createAgentContainer(p);
		} catch (FIPAException e) {
			e.printStackTrace();
		}

		// addBehaviour(new SupervisorBehaviour());
		// addBehaviour(new SupervisorBehaviour2());
	}

	static Properties getProperties() {
		Properties props = new Properties();
		props.put("spring.config.location", "classpath:jade/");
		return props;
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
	@JmsListener(destination = "inbound.queue")
	  public void receiveMessage(String message) {
		if(message.equals("goAgent"))
		{
		//	goAgents();
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
