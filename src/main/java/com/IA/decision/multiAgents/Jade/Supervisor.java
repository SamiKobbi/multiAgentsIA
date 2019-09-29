package com.IA.decision.multiAgents.Jade;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.omg.PortableServer.POAManagerPackage.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;


import com.IA.decision.multiAgents.MultiAgentsApplication;
import com.IA.decision.multiAgents.config.ApplicationContextProvider;
import com.IA.decision.multiAgents.repositories.AgentRepository;

@ComponentScan("com.IA.decision.multiAgents")
@SpringBootApplication
public class Supervisor extends Agent {
	private static final Logger logger = LogManager.getLogger(Supervisor.class);
	private static AnnotationConfigApplicationContext context;
	private static AgentContainer container ;
	private static Runtime rt ;
   
	protected void setup() {
		SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(Supervisor.class)
				.sources(Supervisor.class).properties(getProperties());
		springApplicationBuilder.run();
		System.out.println("Agent " + getLocalName() + " is started ");
		try {
			Scanner s = new Scanner(System.in);
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());
			DFService.register(this, dfd);

			rt = Runtime.instance();
			ProfileImpl p = new ProfileImpl(false);
		
			container = rt.createAgentContainer(p);
			File currentDirectory = new File(new File(".").getAbsolutePath());
			
			File parentDirectory = new File(currentDirectory.getParent());
			FileAlterationObserver observer = new FileAlterationObserver(new File(parentDirectory.getParent()).getParent() + "\\go");
			FileAlterationMonitor monitor = new FileAlterationMonitor(100);
			FileAlterationListener listener = new FileAlterationListenerAdaptor() {
				  @Override
				    public void onFileChange(File file) {
					  try {
						List<String> content = Files.readAllLines(Paths.get(file.getPath()));
						
						if(content.size()>0 && content.get(0).equals("go"))
						{
							logger.info("go Agents");
							goAgents();
						}
						PrintWriter writer = new PrintWriter(file);
						writer.print("");
						writer.close();

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    }
			 
			
			};
			observer.addListener(listener);
			monitor.addObserver(observer);
			monitor.start();
		} catch (FIPAException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// addBehaviour(new SupervisorBehaviour());
		// addBehaviour(new SupervisorBehaviour2());
	}
	private  void goAgents() {
		
		try {
			ProfileImpl p = new ProfileImpl(false);

			container.kill();
			container = rt.createAgentContainer(p);
			
			AgentController Agent = null;
			try {
				container.getAgent("Diffuseur");
			} catch (jade.wrapper.ControllerException ex) {
				Agent = container.createNewAgent("Diffuseur", "com.IA.decision.multiAgents.Jade.Diffuseur", null);
				Agent.start();

			}

			AgentRepository agentRepo = ApplicationContextProvider.getApplicationContext()
					.getBean(AgentRepository.class);

			for (com.IA.decision.multiAgents.BO.Agent agent : agentRepo.findAll()) {

				try {
					container.getAgent(agent.getName());
				} catch (jade.wrapper.ControllerException ex) {
					Agent = container.createNewAgent(agent.getName(), "com.IA.decision.multiAgents.Jade.AgentTemplate",
							null);
					Agent.start();
				}

			}

		} catch (Exception any) {
			logger.error(any);
			any.printStackTrace();
		}
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
