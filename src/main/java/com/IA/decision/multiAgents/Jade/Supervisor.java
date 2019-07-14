package com.IA.decision.multiAgents.Jade;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import jade.core.AID;
import jade.core.Agent;
	import jade.core.Runtime;
	import jade.core.ProfileImpl;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
	import jade.domain.DFService;
	import jade.domain.FIPAException;
	import jade.domain.FIPAAgentManagement.DFAgentDescription;
	import jade.lang.acl.ACLMessage;
	import jade.lang.acl.MessageTemplate;
	import jade.lang.acl.UnreadableException;
import jade.util.leap.Iterator;
import jade.wrapper.*;


import javax.swing.JFrame;
	import javax.swing.JPanel;
	import java.awt.BorderLayout;
	import java.awt.Dimension;
	import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JTextArea;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.IA.decision.multiAgents.MultiAgentsApplication;
import com.IA.decision.multiAgents.repositories.AgentRepository;
import com.IA.decision.multiAgents.services.impl.AgentServiceImpl;

import javax.swing.JButton;



	import java.awt.FlowLayout;
	import javax.swing.JScrollPane;
	import javax.swing.JLabel;
	
	public class Supervisor  extends Agent {
		String ch; 
				private JFrame jFrame = null;
				private JPanel jContentPane = null;
				private JPanel jPanel = null;
				private JPanel jPanel1 = null;
				private JPanel jPanel2 = null;
				private JTextArea jTextArea=null;
				private JButton jButton = null;
				private JScrollPane jScrollPane=null;
				private JLabel jLabel=null;
				@Autowired
				private AgentRepository agentRepo;
				
				
				/** Cette méthode est appelé directement apèes la création de l'agent pour permettre
				  * l'initialisation et l'affectation des différents comportements à cet agent 
				  * */
				protected void setup() {
		    	getJFrame().setVisible(true);
		    	jTextArea.append("Agent "+getLocalName()+" est lancé "+"\n");
		    	System.out.println("Agent "+getLocalName()+" est lancé ");
		    	try {
				
		    		// Création de desciprion de l'agent [Portail]
				DFAgentDescription dfd = new DFAgentDescription();
				dfd.setName(getAID());
				
				// Enregistrement de la description de l'agent dans DF (Directory Facilitator)
				DFService.register(this, dfd);
				jTextArea.append("Agent "+getLocalName()+" est enregistré dans DF (Directory Facilitator) "+"\n");
				System.out.println("Agent "+getLocalName()+" est enregistré dans DF (Directory Facilitator) ");
		    	} catch (FIPAException e) {
				e.printStackTrace();}
		    	
				
				//addBehaviour(new SupervisorBehaviour());
				//addBehaviour(new SupervisorBehaviour2());
	}
	//private class SupervisorBehaviour extends Behaviour{
		//private int i =0;
			//public void action() {
				
				//System.out.println("<Supervisor Agent: sending events to all the network>");
				//FileInputStream fichier;
				//try {
					//fichier = new FileInputStream(new File("C:\\Users\\USER\\Documents\\NETWORK.xlsx"));
					 //XSSFWorkbook wb = new XSSFWorkbook(fichier);
				       //XSSFSheet sheet = wb.getSheetAt(0);
				       //FormulaEvaluator formulaEvaluator = 
			             //        wb.getCreationHelper().createFormulaEvaluator();
				 //  int index = 1 ;
					     // int numColonne = 1 ;
					      //Row row = sheet.getRow(0) ;
					   //   while (row != null) {
					    	//  System.out.println(row.getCell(0)) ;
					    	 //ch= row.getCell(0).getStringCellValue();
					    	  
					    	 
				               // i++;
					      
			//	} catch (Exception e) {
				//	System.err.println("Erreur lors de l'accès au fichier !");
					//e.printStackTrace();
				//}
				                
				    			   
				    	   
				  //     }
			//@Override
			//public boolean done() {
				// TODO Auto-generated method stub
				//return i==2;
			//}
				       
				    	   
				    	   
				//       }
	
			
			
				protected void takeDown() {
						try {
						// Suppression de l'agent [Portail] depuis le DF
						DFService.deregister(this);
						jTextArea.append("Agent "+getLocalName()+" est terminé et supprimé depuis DF (Directory Facilitator) "+"\n");
						System.out.println("Agent "+getLocalName()+" est terminé et supprimé depuis DF (Directory Facilitator) ");
						} catch (FIPAException e) {
						e.printStackTrace();}
						}
							
				public JFrame getJFrame() {
					if (jFrame == null) {
						jFrame = new JFrame();
						jFrame.setSize(new java.awt.Dimension(500,350));
						Dimension tailleEcran =Toolkit.getDefaultToolkit().getScreenSize();
						int largeurEcran = tailleEcran.width;
						int hauteurEcran = tailleEcran.height;
						jFrame.setLocation((largeurEcran-500)/2,(hauteurEcran-350)/2);
						jFrame.setTitle("AGENT LAUNCH");
						jFrame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);					
						jFrame.setContentPane(getJContentPane());
					}
					return jFrame;
				}

				private JPanel getJContentPane() {
					if (jContentPane == null) {
						jContentPane = new JPanel();		
						jContentPane.setLayout(new BorderLayout());
						jContentPane.add(getJPanel2(), java.awt.BorderLayout.NORTH);
						jContentPane.add(getJPanel1(), java.awt.BorderLayout.CENTER);
						jContentPane.add(getJPanel(), java.awt.BorderLayout.SOUTH);
						}
					return jContentPane;
					}

				private JPanel getJPanel() {
					if (jPanel == null) {
						FlowLayout flowLayout = new FlowLayout();
						flowLayout.setAlignment(java.awt.FlowLayout.CENTER);
						jPanel = new JPanel();
						jPanel.setLayout(flowLayout);
						jPanel.add(getJButton(), null);
						}
					return jPanel;
					}

				private JPanel getJPanel1() {
					if (jPanel1 == null) {
						jPanel1 = new JPanel();
						jPanel1.setLayout(new BorderLayout());
						jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
						}
					return jPanel1;
					}

				private JTextArea getJTextArea() {
					if (jTextArea == null) {
						jTextArea = new JTextArea();
						}
					return jTextArea;
					}

				private JButton getJButton() {
					if (jButton == null) {
						jButton = new JButton();
						jButton.setText("Go agents");
						
						jButton.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent e) {
							try {
								
								System.out.println("sami");
								 AnnotationConfigApplicationContext context = 
								            new AnnotationConfigApplicationContext(MultiAgentsApplication.class);
								
							  // Récupération du conteneur (Main Container) en cours d'execution de Jade					
							  Runtime rt = Runtime.instance();
							  // Création du profil par défault
							  ProfileImpl p = new ProfileImpl(false);
							  AgentContainer container =rt.createAgentContainer(p);
							  // Agent controleur pour permettre la création des agents 
							  AgentController Agent=null;
							  //------
							 	  Agent = container.createNewAgent("Diffuseur", "com.IA.decision.multiAgents.Jade.Diffuseur", null);
							  Agent.start();
						
							  AgentRepository agentRepo = context.getBean(AgentRepository.class);
							
							for(com.IA.decision.multiAgents.BO.Agent agent :  agentRepo.findAll())
							{
							  Agent = container.createNewAgent(agent.getName(), "com.IA.decision.multiAgents.Jade.AgentTemplate", null);
							  Agent.start();
							}
							  jTextArea.append("<Agent2> est lancé "+"\n");
							  //------
							  //Agent = container.createNewAgent("Agent3", "learning.Agent3", null);
							  //Agent.start();
							  //jTextArea.append("<Agent3> est lancé "+"\n");
							  //------
							  
							  } catch (Exception any) {
							    any.printStackTrace();}
							  }
						     });
						    }
					        return jButton;
				}

				private JScrollPane getJScrollPane() {
					if (jScrollPane == null) {
						jScrollPane = new JScrollPane();
						jScrollPane.setViewportView(getJTextArea());
					}
					return jScrollPane;
					}

				private JPanel getJPanel2() {
					if (jPanel2 == null) {
						jLabel = new JLabel();
						jLabel.setText("Learning agents");
						jLabel.setFont(new java.awt.Font("Perpetua", java.awt.Font.BOLD, 18));
						jPanel2 = new JPanel();
						jPanel2.setBackground(java.awt.SystemColor.info);
						jPanel2.add(jLabel, null);
					}
					return jPanel2;
					}
			
				public static void main(String[] args)throws IOException {
					try {
						// Récupération du conteneur (Main Container) en cours d'execution de Jade					
						Runtime rt = Runtime.instance();
					
						// Création du profil par défault
						ProfileImpl p = new ProfileImpl(false);
						AgentContainer container = rt.createAgentContainer(p); // get a container controller for creating new agents
					
						// Agent controleur pour permettre la création des agents 
						AgentController Agent=null;		
					
						/* Création de l'agent Portail
						   cette commande est équivalente à la suivante: 
						   java jade.Boot Portail:JADE_exemple_personnel.Portail
						*/
						Agent = container.createNewAgent("Supervisors", "com.IA.decision.multiAgents.Jade.Supervisor", null);
					
						// Démarrage de l'agent Portail
						Agent.start();											
						} catch (Exception any) {
						any.printStackTrace();}				
					}
	}


