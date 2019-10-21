package com.IA.decision.multiAgents.Controllers;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.IA.decision.multiAgents.BO.*;
import com.IA.decision.multiAgents.repositories.*;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

@Service
public class MainController{
	private static final Logger logger = LogManager.getLogger(MainController.class);
	// agent
	@FXML
	public Button goButton;
	@FXML
	public Button generateAgents;
	@FXML
	public Button saveAgent;
	@FXML
	public Button updateAgent;
	@FXML
	public ComboBox<Agent> agentsComboBox;
	@FXML
	public TextField agentName;
	@FXML
	public TextField openness;
	@FXML
	public TextField conscientiousness;
	@FXML
	public TextField extraversion;
	@FXML
	public TextField agreeableness;
	@FXML
	public TextField neuroticism;
	// agent

	// goal
	@FXML
	public Button saveGoal;
	@FXML
	public Button updateGoal;
	@FXML
	public ComboBox<GoalName> goalNameComboBox;
	
	@FXML
	public ComboBox<GoalName> goalExecutionComboBox;
	
	@FXML
	public ComboBox<Agent> agentsGoalExecution;
	@FXML
	public TextField goalName;
	@FXML
	public TextField goalWeight;
	@FXML
	public Button generateGoal;
	@FXML
	public Button addGoalExecution;
	@FXML
	public Button updateGoalExecution;
	// goal

	// event
	@FXML
	public TextField eventName;

	@FXML
	public CheckBox confirmCheckBox;
	@FXML
	public CheckBox degreeCheckBox;
	

		//execution
	@FXML
	public ComboBox<Agent> agentNameExecutionEventComboBox;
	@FXML
	public ComboBox<EventName> eventNameExecutionComboBox;
	@FXML
	public TextField eventIntensity;
	@FXML
	public TextField eventReaction;
	   //execution
	@FXML
	public ComboBox<EventName> eventNameComboBox;
	@FXML
	public Button updateEventExecution;
	@FXML
	public Button updateEventName;
		
	@FXML
	public Button generateEventExecution;

	@FXML
	public Button addEventName;
	@FXML
	public Button addEventExecution;
	// event

	@FXML
	public Button generateGoalExecution;
	@FXML
	public ComboBox<Agent> agentSrc;
	@FXML
	public ComboBox<Agent> agentDest;
	@FXML
	public TextField actionMessage;
	@FXML
	public TextField actionApprDegLvl;
	@FXML
	public CheckBox reqCheckbox;

	@FXML
	public CheckBox actionDeg;
	@FXML
	public ComboBox<Action> actionComboBox;
	@FXML
	public Button updateAction;
	@FXML
	public Button addAction;
	@FXML
	public Button generateAction;
	// Action
	// reports
	@FXML
	LineChart emotionsChart;
	// reports
	@Autowired
	private EventNameRepository eventNameRepo;
	@Autowired
	private LikingTowardsAgentRepository likingTowardsAgentRepo;
	@Autowired
	private EventInfoRepository eventInfoRepo;
	@Autowired
	private EventReactionRepository eventReactionRepo;
	@Autowired
	private GoalNameRepository goalNameRepo;
	@Autowired
	private GoalInfoRepository goalInfoRepo;
	@Autowired
	private OCCRepository OCCRepo;
	@Autowired
	private ActionRepository actionRepo;
	@Autowired
	private AgentRepository agentRepo;
	@Autowired
	private OCEANRepository OCEANRepo;

	@FXML
	public void initialize() {

		agentsComboBox.setConverter(new AgentNameStringConverter());
		agentNameExecutionEventComboBox.setConverter(new AgentNameStringConverter());
		agentSrc.setConverter(new AgentNameStringConverter());
	
		agentDest.setConverter(new AgentNameStringConverter());
		agentsGoalExecution.setConverter(new AgentNameStringConverter());
		eventNameExecutionComboBox.setConverter(new EventNameStringConverter());
		goalNameComboBox.setConverter(new GoalNameStringConverter());
		goalExecutionComboBox.setConverter(new GoalNameStringConverter());
		eventNameComboBox.setConverter(new EventNameStringConverter());
		actionComboBox.setConverter(new ActionNameStringConverter());
		agentsComboBox.setItems(FXCollections.observableArrayList(agentRepo.findAll()));
		goalExecutionComboBox.setItems(FXCollections.observableArrayList(goalNameRepo.findAll()));
		agentNameExecutionEventComboBox.setItems(FXCollections.observableArrayList(agentRepo.findAll()));
		agentSrc.setItems(FXCollections.observableArrayList(agentRepo.findAll()));
		agentDest.setItems(FXCollections.observableArrayList(agentRepo.findAll()));
		agentsGoalExecution.setItems(FXCollections.observableArrayList(agentRepo.findAll()));
		agentsComboBox.getSelectionModel().selectFirst();
		goalNameComboBox.setItems(FXCollections.observableArrayList(goalNameRepo.findAll()));
		eventNameComboBox.setItems(FXCollections.observableArrayList(eventNameRepo.findAll()));
		eventNameExecutionComboBox.setItems(FXCollections.observableArrayList(eventNameRepo.findAll()));
		actionComboBox.setItems(FXCollections.observableArrayList(actionRepo.findAll()));
		goalNameComboBox.getSelectionModel().selectFirst();

		
		generateEventExecution.setOnAction(event -> {
			List<EventInfo> listEventInfo = new LinkedList<>();
			List<EventReaction> listEventReaction = new LinkedList<>();
			//if we implement the K factor EIL*C then we may use those two lists
			List<EventReaction> listPositiveReaction = new LinkedList<>();
			listPositiveReaction.add(new EventReaction("Asking for help"));
			listPositiveReaction.add(new EventReaction("High effort"));
			List<EventReaction> listNegativeReaction = new LinkedList<>();
			listNegativeReaction.add(new EventReaction("Asking for help"));
			listNegativeReaction.add(new EventReaction("Giving up"));
			for(Agent agent : agentRepo.findAll())
			{
				for(EventName eventName : eventNameRepo.findAll())
				{
					//EIL
				
					EventInfo eventInfo = new EventInfo();
					eventInfo.setAgent(agent);
					double randomValue = 0;
					if(OCEANRepo.findByAgent(agent).getNeuroticism()>=0.5)
					{
					 randomValue = Math.round(ThreadLocalRandom.current().nextDouble(0.5,1)* 100d) / 100d;
					}
					else
					{
						 randomValue = Math.round(ThreadLocalRandom.current().nextDouble(0.15,0.5)* 100d) / 100d;
					}
					eventInfo.setEventIntensityLevel(randomValue);
					
					eventInfo.setEventName(eventName);
					listEventInfo.add(eventInfo);
					EventReaction eventReaction = new EventReaction();
					if(eventName.getGoalName().getName().equals("Success the exam")||eventName.getGoalName().getName().equals("Success the year"))
					{
						
						if(eventName.getEventDegree())
						{
							eventReaction.setEventReaction("Keep working");
						}
						else
						{
							if(OCEANRepo.findByAgent(agent).getConscientiousness()>=0.5)
							{
								
									eventReaction.setEventReaction("High effort");
								
							}
							else
							{
								
								eventReaction.setEventReaction("Asking for help");
							}
						}
					}
					if(eventName.getGoalName().getName().equals("Appreciation"))
					{	
								if(eventName.getEventDegree())
								{
									eventReaction.setEventReaction("Finish the proposed activity ");
								}
								else 
								{ 
								
								if(OCEANRepo.findByAgent(agent).getConscientiousness()>=0.5)
									{
										
									eventReaction.setEventReaction("Improve level of competence");									
									}
									else
									{
										
										eventReaction.setEventReaction("Asking for help");
									}
								
								}

				}
//				if(eventName.getGoalName().getName().equals("Social growth"))
//				{		
//										
//												if(OCEANRepo.findByAgent(agent).getAgreeableness()>=0.5)
//												{
//													
//														eventReaction.setEventReaction("Accepting help");
//													
//												}
//												else
//												{
//													
//													eventReaction.setEventReaction("Rejecting help");
//												}
//											
//											}
						eventReaction.setEventInfo(eventInfo);
						listEventReaction.add(eventReaction);
					}

				
			}
			eventReactionRepo.deleteAll();
			eventInfoRepo.deleteAll();
			
			eventInfoRepo.saveAll(listEventInfo);
			eventReactionRepo.saveAll(listEventReaction);
			
		});
		generateAction.setOnAction(event -> {
			actionRepo.deleteAll();
			List<Action> actions = new LinkedList<>();
			List<EventReaction> eventReactions = eventReactionRepo.findAll();
			double approvalDegreeLevel=0;
			for(EventReaction eventReaction : eventReactions)
			{
				if(eventReaction.getReactionName().equals("Asking for help"))
				{
					Agent agentDest = eventReaction.getEventInfo().getAgent(); 
					for(Agent agentSrc: agentRepo.findAll())
					{
						if(agentSrc!=agentDest)
						{
						Action action = new Action();
						OCEAN oceanAgent = OCEANRepo.findByAgent(agentSrc); 
						if(oceanAgent.getAgreeableness()>=0.5)
						{
							approvalDegreeLevel = Math.round(ThreadLocalRandom.current().nextDouble(0.5 , 1)* 100d) / 100d;
							action.setMessage("Accepting help");
							action.setActionDegree(true);
						}
						else
						{
							approvalDegreeLevel = Math.round(ThreadLocalRandom.current().nextDouble(0 ,0.5 )* 100d) / 100d;
							action.setMessage("Rejecting help");
							action.setActionDegree(false);
						}
						action.setApprovalDegreeLevel(approvalDegreeLevel);
						action.setAgentSrc(agentSrc);
						action.setAgentDest(agentDest);
						actions.add(action);
						}
					}
				}
			}
			actionRepo.saveAll(actions);
		});
		generateGoalExecution.setOnAction(event -> {
			List<GoalInfo> goalInfos = new LinkedList<>();
			for(Agent agent: agentRepo.findAll())
			{
				for(GoalName goalName: goalNameRepo.findAll())
				{
				GoalInfo goalInfo = new GoalInfo();
				//goal weight
				double goalWeight = 0;

				if(OCEANRepo.findByAgent(agent).getConscientiousness() >=0.5)
				{
				goalWeight = Math.round(ThreadLocalRandom.current().nextDouble(0.5 , 1)* 100d) / 100d;
				}
				else {
				goalWeight = Math.round(ThreadLocalRandom.current().nextDouble(0.15,0.5)* 100d) / 100d; 
				}
				goalInfo.setWeight(goalWeight);
				goalInfo.setAgent(agent);
				goalInfo.setGoalName(goalName);
				goalInfos.add(goalInfo);
				}
			}
			goalInfoRepo.deleteAll();
			goalInfoRepo.saveAll(goalInfos);
		});
		
		generateGoal.setOnAction(event -> {
			eventNameRepo.deleteAll();
			goalNameRepo.deleteAll();
			GoalName goalName = new GoalName("Success the exam");
			goalNameRepo.save(goalName);

			List<EventName> eventNames = new LinkedList<>();				
			EventName  eventName = new EventName("Prospect bad mark", true,false, false);
			eventName.setLikelihood(1);
			eventName.setGoalName(goalName);
			eventNames.add(eventName);
			eventName = new EventName("Bad mark",false,true,false);
			eventName.setGoalName(goalName);
			eventName.setLikelihood(1);
			eventNames.add(eventName);
			eventName = new EventName("Prospect good mark",true, false, true);
			eventName.setLikelihood(1);
			eventName.setGoalName(goalName);
			eventNames.add(eventName);
			
			eventName = new EventName("Good mark", false, true, true);
			eventName.setLikelihood(1);
			eventName.setGoalName(goalName);
			eventNames.add(eventName);
		
			eventNameRepo.saveAll(eventNames);
			
			goalName = new GoalName("Success the year");
			goalNameRepo.save(goalName);
			eventNames.clear();		
			eventName = new EventName("Prospect failing the activities",true, false, false);
			eventName.setGoalName(goalName);
			eventName.setLikelihood(1);
			eventNames.add(eventName);
			eventName = new EventName("Failing the activities",false,false,false);
			eventName.setGoalName(goalName);
			eventNames.add(eventName);
		
			eventName = new EventName("Prospect success the activities", true, false, true);
			eventName.setGoalName(goalName);
			eventName.setLikelihood(1);
			eventNames.add(eventName);
			eventName = new EventName("Have success the activities",false, true, true);
			eventName.setGoalName(goalName);
			eventName.setLikelihood(1);
			eventNames.add(eventName);
		
			eventNameRepo.saveAll(eventNames);
			
			goalName = new GoalName("Appreciation");
			goalNameRepo.save(goalName);
			eventNames.clear();				
		
			eventName = new EventName("Negative feedback",false,true,false);
			eventName.setGoalName(goalName);
			eventName.setLikelihood(1);
			eventNames.add(eventName);
			eventName = new EventName("Positive feedback",false,true,false);
			eventName.setGoalName(goalName);
			eventName.setLikelihood(1);
			eventNames.add(eventName);
			eventNameRepo.saveAll(eventNames);
			
			goalName = new GoalName("Social growth");
			goalNameRepo.save(goalName);
			eventNames.clear();	
			eventName = new EventName("Asking for help (Other) ",false,true,false);
			eventName.setGoalName(goalName);
			eventName.setLikelihood(1);
			eventNames.add(eventName);
			eventNameRepo.saveAll(eventNames);
			

			goalNameComboBox.setItems(FXCollections.observableArrayList(goalNameRepo.findAll()));
			eventNameComboBox.setItems(FXCollections.observableArrayList(eventNames));
			goalExecutionComboBox.setItems(FXCollections.observableArrayList(goalNameRepo.findAll()));
			eventNameExecutionComboBox.setItems(FXCollections.observableArrayList(eventNames));
	
		
		});
		goButton.setOnAction(event -> {
			 
			
			    FileWriter fileWriter;
				try {
					Path currentDir = Paths.get("go");
					fileWriter = new FileWriter(currentDir.toAbsolutePath().toString()+"/start.txt");
					  fileWriter.write("go");
					    fileWriter.close();
					    Files.copy(Paths.get("C:\\folderOCC\\excelSheetOCC.xlsx"), Paths.get("C:\\folderOCC\\excelSheetOCCValue.xlsx"));
						 File file = new File("C:\\folderOCC\\excelSheetOCCValue.xlsx");
						 file.setLastModified(new Date().getTime());
				} catch (IOException e) {
					logger.error(e);
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		});

		addGoalExecution.setOnAction(event -> {
			GoalInfo goalWght = new GoalInfo(Double.parseDouble(goalWeight.getText()));
			Agent agent = agentsGoalExecution.getSelectionModel().getSelectedItem();
			goalWght.setAgent(agent);
			GoalName goalName = goalExecutionComboBox.getSelectionModel().getSelectedItem();
			goalWght.setGoalName(goalName);
			goalInfoRepo.save(goalWght);
		});
		updateGoalExecution.setOnAction(event -> {
			Agent agent = agentsGoalExecution.getSelectionModel().getSelectedItem();
			GoalName goalName = goalExecutionComboBox.getSelectionModel().getSelectedItem();
			GoalInfo goalWght = goalInfoRepo.findByGoalNameAndAgent(agent.getId(), goalName.getId());
			goalWght.setWeight(Double.parseDouble(goalWeight.getText()));
			goalInfoRepo.save(goalWght);
		});

		addAction.setOnAction(event -> {
			Action action = new Action(actionMessage.getText(), actionDeg.isSelected(), reqCheckbox.isSelected(), Double.parseDouble(actionApprDegLvl.getText()));
			action.setAgentSrc(agentSrc.getSelectionModel().getSelectedItem());
			action.setAgentDest(agentDest.getSelectionModel().getSelectedItem());
			//action.setEventName(eventNameComboBox.getSelectionModel().getSelectedItem());
			actionComboBox.getItems().add(action);
			actionRepo.saveAll(actionComboBox.getItems());
			clearSelectionAction();
		});
        updateAction.setOnAction(event -> {
        Action action = actionComboBox.getSelectionModel().getSelectedItem();
        action.setActionDegree(actionDeg.isSelected());
        action.setAgentDest(agentDest.getSelectionModel().getSelectedItem());
        action.setAgentSrc(agentSrc.getSelectionModel().getSelectedItem());
        action.setMessage(actionMessage.getText());
        action.setRequestOrResponse(reqCheckbox.isSelected());
        action.setApprovalDegreeLevel(Double.parseDouble(actionApprDegLvl.getText()));
        actionRepo.save(action);
        });
		saveGoal.setOnAction(event -> {
			
			GoalName name = new GoalName(goalName.getText());
			//GoalInfo goalInfo = new GoalInfo(Double.parseDouble(goalWeight.getText()));
			goalNameComboBox.getItems().add(name);
			goalNameRepo.saveAll(goalNameComboBox.getItems());
			goalExecutionComboBox.getItems().add(name);
			goalNameComboBox.setItems(FXCollections.observableArrayList(goalNameRepo.findAll()));
			//goalInfoRepo.save(goalInfo);
			clearSelectionGoal();

		});
		updateGoal.setOnAction(event -> {
			GoalName goal = goalNameComboBox.getSelectionModel().getSelectedItem();
			goal.setName(goalName.getText());
			goalNameRepo.save(goal);
			goalNameComboBox.setItems(FXCollections.observableArrayList(goalNameRepo.findAll()));
		});
		addEventName.setOnAction(event -> {
			EventName evName = new EventName(eventName.getText(), false,confirmCheckBox.isSelected(),degreeCheckBox.isSelected());
			evName.setGoalName(goalNameComboBox.getSelectionModel().getSelectedItem());
			eventNameComboBox.getItems().add(evName);
			eventNameExecutionComboBox.getItems().add(evName);
			eventNameRepo.saveAll(eventNameComboBox.getItems());
			clearSelectionEventName();
		});
		updateEventName.setOnAction(event -> {
			EventName evName = eventNameComboBox.getSelectionModel().getSelectedItem();
			evName.setName(eventName.getText());
			evName.setConfirmed(confirmCheckBox.isSelected());
			evName.setEventDegree(degreeCheckBox.isSelected());
			eventNameRepo.save(evName);
			eventNameComboBox.setItems(FXCollections.observableArrayList(eventNameRepo.findAll()));
			eventNameExecutionComboBox.setItems(FXCollections.observableArrayList(eventNameRepo.findAll()));
		});

		generateAgents.setOnAction(event -> {
			OCEANRepo.deleteAll();
			agentRepo.deleteAll();
			likingTowardsAgentRepo.deleteAll();
			
			List<Agent> agents = new LinkedList<> ();
			List<OCEAN> occeans = new LinkedList<>();
			//génération automatique de N(N=40)  agents 
			for(int i=0;i<40;i++)
			{
				//Agent + codeAsci à un charactere
				Agent agent = new Agent("Agent"+Character.toString((char)(i+65)));
				agents.add(agent);
				//initialisation de OCEAN
				OCEAN ocean = new OCEAN();
				ocean.setAgent(agent);
				
				Double agreeableness = Math.round(ThreadLocalRandom.current().nextDouble(0,1)* 100d) / 100d;
				ocean.setAgreeableness(agreeableness);
				Double conscientiousness = Math.round(ThreadLocalRandom.current().nextDouble(0,1)* 100d) / 100d;
				ocean.setConscientiousness(conscientiousness);
				Double openness = Math.round(ThreadLocalRandom.current().nextDouble(0,1)* 100d) / 100d;
				ocean.setOpenness(openness);
				Double extraversion = Math.round(ThreadLocalRandom.current().nextDouble(0,1)* 100d) / 100d;
				ocean.setExtraversion(extraversion);
				Double neuroticism = Math.round(ThreadLocalRandom.current().nextDouble(0,1)* 100d) / 100d;
				ocean.setNeuroticism(neuroticism);
				occeans.add(ocean);
				
			}
			OCEANRepo.saveAll(occeans);
			agentRepo.saveAll(agents);
		
			//insertion de nouvelles valeurs
			
			//Initialisation de Liking Towards Agent  
			LinkedList<LikingTowardsAgent> likingTowardsAgentList = new LinkedList<>();
			for(Agent agentSrc: agentRepo.findAll())
			{
				//récuperation de AgentSrc
				likingTowardsAgentList.clear();
				for(Agent agentDest: agentRepo.findAll())
				{
				//	récuperation de AgentDest
					if(!agentSrc.getName().equals(agentDest.getName()))
					{
						LikingTowardsAgent likingTowardsAgent = new LikingTowardsAgent();
						OCEAN ocean = occeans.stream().filter(oceanVect -> oceanVect.getAgent().getName().equals(agentSrc.getName()) ).findFirst().get();
						 double likingValue=0;
						if(ocean.getAgreeableness()>=0.5 || ocean.getExtraversion()>=0.5)
						{
						 likingValue = Math.round(ThreadLocalRandom.current().nextDouble(0.5,1)* 100d) / 100d;
						}
						else
						{
							 likingValue = Math.round(ThreadLocalRandom.current().nextDouble(0,0.5)* 100d) / 100d;
						}
						 likingTowardsAgent.setLikingValue(likingValue);
						likingTowardsAgent.setAgentDest(agentDest);
						likingTowardsAgentList.add(likingTowardsAgent);
					}
				}
				agentSrc.setLikingTowardAgent(likingTowardsAgentList);
				
				likingTowardsAgentRepo.saveAll(likingTowardsAgentList);
				
				agentRepo.save(agentSrc);
			}
			
			
//			//likingTowardsAgentRepo.saveAll(likingTowardsAgentList);
//			OCEANRepo.saveAll(occeans);
			//suppression de valeurs anciennes
	
		
			//mise à jour de la partie graphique
			//Agents
			agentsComboBox.setItems(FXCollections.observableArrayList(agents));
			//Action
			agentSrc.setItems(FXCollections.observableArrayList(agents));
			agentDest.setItems(FXCollections.observableArrayList(agents));
			//Event
			agentNameExecutionEventComboBox.setItems(FXCollections.observableArrayList(agents));
			//Goal
			agentsGoalExecution.setItems(FXCollections.observableArrayList(agents));			
			});
		
		addEventExecution.setOnAction(event -> {
	
			EventInfo evInfo = new EventInfo(Double.parseDouble(eventIntensity.getText()) );
			evInfo.setEventName(eventNameExecutionComboBox.getSelectionModel().getSelectedItem());
			evInfo.setAgent(agentNameExecutionEventComboBox.getSelectionModel().getSelectedItem());
			EventReaction evReaction = new EventReaction(eventReaction.getText());
			evReaction.setEventInfo(evInfo);
			eventInfoRepo.save(evInfo);
			eventReactionRepo.save(evReaction);
			clearSelectionEventExecution();
		});
		
		updateEventExecution.setOnAction(event -> {
			Agent agent = agentNameExecutionEventComboBox.getSelectionModel().getSelectedItem();
			EventName eventName = eventNameExecutionComboBox.getSelectionModel().getSelectedItem();
			EventInfo evInfo = eventInfoRepo.findByEventNameAndAgent(agent.getId(), eventName.getId()).get();
			EventReaction evReaction = eventReactionRepo.findByEventInfo(evInfo);
			evInfo.setEventIntensityLevel(Double.parseDouble(eventIntensity.getText()));
			eventInfoRepo.save(evInfo);
			evReaction.setEventReaction(eventReaction.getText());
			eventReactionRepo.save(evReaction);
		//	eventNameExecutionComboBox.setItems(FXCollections.observableArrayList(eventNameRepo.findAll()));
		});

		
		saveAgent.setOnAction(event -> {
			if (validateAgent()) {

				Agent agent = agentRepo.save(new Agent(agentName.getText()));
				agentsComboBox.getItems().add(agent);
				agentsGoalExecution.getItems().add(agent);
				// goalComboBox.getItems().stream().forEach(goalCombo->{goalCombo.setAgent(agent);});
				OCEAN ocean = new OCEAN(Double.parseDouble(openness.getText()),
						Double.parseDouble(conscientiousness.getText()), Double.parseDouble(extraversion.getText()),
						Double.parseDouble(agreeableness.getText()), Double.parseDouble(neuroticism.getText()));
				ocean.setAgent(agent);
				OCEANRepo.save(ocean);
				agentSrc.setItems(FXCollections.observableArrayList(agentRepo.findAll()));
				agentDest.setItems(FXCollections.observableArrayList(agentRepo.findAll()));
				agentNameExecutionEventComboBox.setItems(FXCollections.observableArrayList(agentRepo.findAll()));
				clearSelectionAgent();
			} else {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Validation Failed");
				alert.setHeaderText("Missing agent information");
				alert.setContentText("Please enter valid information!");
				alert.showAndWait();
			}

		});
		updateAgent.setOnAction(event -> {
			Agent agent = agentsComboBox.getSelectionModel().getSelectedItem();
			OCEAN ocean = OCEANRepo.findByAgent(agent);
			agent.setName(agentName.getText());
			ocean.setAgreeableness(Double.parseDouble(agreeableness.getText()));
			ocean.setConscientiousness(Double.parseDouble(conscientiousness.getText()));
			ocean.setExtraversion(Double.parseDouble(extraversion.getText()));
			ocean.setNeuroticism(Double.parseDouble(neuroticism.getText()));
			ocean.setOpenness(Double.parseDouble(openness.getText()));
			agentRepo.save(agent);
			OCEANRepo.save(ocean);
			agentsComboBox.setItems(FXCollections.observableArrayList(agentRepo.findAll()));
			agentSrc.setItems(FXCollections.observableArrayList(agentRepo.findAll()));
			agentDest.setItems(FXCollections.observableArrayList(agentRepo.findAll()));
			agentNameExecutionEventComboBox.setItems(FXCollections.observableArrayList(agentRepo.findAll()));
		});
		agentsComboBox.valueProperty().addListener((ChangeListener<Agent>) (ov, oldValue, newAgent) -> {

			if (newAgent != null) {
				OCEAN oceanByAgent = OCEANRepo.findByAgent(newAgent);
				Optional<OCC> occByAgentOpt = OCCRepo.findByAgent(newAgent);
				if (occByAgentOpt.isPresent())
				{
					OCC occByAgent = occByAgentOpt.get();
					
				}
				openness.setText(oceanByAgent.getOpenness().toString());
				conscientiousness.setText(oceanByAgent.getConscientiousness().toString());
				extraversion.setText(oceanByAgent.getExtraversion().toString());
				agreeableness.setText(oceanByAgent.getAgreeableness().toString());
				neuroticism.setText(oceanByAgent.getNeuroticism().toString());		
			}
		});
		actionComboBox.valueProperty().addListener((ChangeListener<Action>) (ov, oldValue, newAction) -> {
			if(newAction != null)
			{
				actionDeg.setSelected(newAction.getActionDegree());
				agentDest.getSelectionModel().select(newAction.getAgentDest());
				agentSrc.getSelectionModel().select(newAction.getAgentSrc());
				actionMessage.setText(newAction.getMessage());
				reqCheckbox.setSelected(newAction.getRequestOrResponse());
				actionApprDegLvl.setText(newAction.getApprovalDegreeLevel().toString());
			}
		});
		goalExecutionComboBox.valueProperty().addListener((ChangeListener<GoalName>) (ov, oldValue, newGoal) -> {
			if (newGoal != null) {
				Agent agent = agentsGoalExecution.getSelectionModel().getSelectedItem();
				if(agent != null)
				{
					GoalInfo goalInfo = goalInfoRepo.findByGoalNameAndAgent(agent.getId(), newGoal.getId());
					if(goalInfo != null)
					{
					goalWeight.setText(goalInfo.getWeight().toString());
					}
					else
					{
						goalWeight.setText("");
					}
				}
				
			}
			
		});
		agentsGoalExecution.valueProperty().addListener((ChangeListener<Agent>) (ov, oldValue, newAgent) -> {
			if (newAgent != null) {
				GoalName goalName = goalExecutionComboBox.getSelectionModel().getSelectedItem();
				if(goalName != null)
				{
					GoalInfo goalInfo = goalInfoRepo.findByGoalNameAndAgent(newAgent.getId(), goalName.getId());
					if(goalInfo != null)
					{
					goalWeight.setText(goalInfo.getWeight().toString());
					}
					else
					{
						goalWeight.setText("");
					}
				}
			}		
		});
		eventNameComboBox.valueProperty().addListener((ChangeListener<EventName>) (ov, oldValue, newEvent) -> {
			if (newEvent != null) {
			//	Agent agent = agentsComboBox.getSelectionModel().getSelectedItem();
				
				//EventInfo eventInfo = eventInfoRepo.findByEventNameAndAgent(agent.getId(), newEvent.getId());
				eventName.setText(newEvent.getName());
				//eventIntensity.setText(eventInfo.getEventIntensityLevel().toString());
				confirmCheckBox.setSelected(newEvent.getConfirmed());
				degreeCheckBox.setSelected(newEvent.getEventDegree());
				//eventReaction.setText(eventReactionRepo.findByEventInfo(eventInfo).getReactionName());
			}
			
		});
		agentNameExecutionEventComboBox.valueProperty().addListener((ChangeListener<Agent>) (ov, oldValueAgent, newValueAgent) -> {
			if (newValueAgent != null) {
				EventName eventNameExecution = eventNameExecutionComboBox.getSelectionModel().getSelectedItem();
				if(eventNameExecution != null)
				{
					EventInfo eventInfo = eventInfoRepo.findByEventNameAndAgent(newValueAgent.getId(), eventNameExecution.getId() ).get();
					if(eventInfo != null)
					{
					eventIntensity.setText(eventInfo.getEventIntensityLevel().toString());
					EventReaction evReaction = eventReactionRepo.findByEventInfo(eventInfo);
					eventReaction.setText(evReaction.getReactionName());
					}
					else
					{
						eventIntensity.setText("");
						eventReaction.setText("");
					}
				}
			}
		});
		eventNameExecutionComboBox.valueProperty().addListener((ChangeListener<EventName>) (ov, oldValueExecution, newEventNameExecution) -> {
			if (newEventNameExecution != null) {
				Agent agentExecution = agentNameExecutionEventComboBox.getSelectionModel().getSelectedItem();
				if(agentExecution != null)
				{
					EventInfo eventInfo = eventInfoRepo.findByEventNameAndAgent(agentExecution.getId(), newEventNameExecution.getId()).get();
					if(eventInfo != null)
					{
					eventIntensity.setText(eventInfo.getEventIntensityLevel().toString());
					EventReaction evReaction = eventReactionRepo.findByEventInfo(eventInfo);
					eventReaction.setText(evReaction.getReactionName());
					}
				}
			}
		});
		goalNameComboBox.valueProperty().addListener((ChangeListener<GoalName>) (ov, oldValue, newGoal) -> {

			if (newGoal != null) {		
				goalName.setText(newGoal.getName());
				}
			eventNameComboBox.setItems(FXCollections.observableArrayList(eventNameRepo.findByGoalName(newGoal)));
		});

	}
	
	private void clearSelectionEventName() {
		eventName.clear();
		eventNameComboBox.getSelectionModel().clearSelection();
		
		
	}
	private void clearSelectionEventExecution() {
		eventIntensity.clear();
		eventReaction.clear();
		agentNameExecutionEventComboBox.getSelectionModel().clearSelection();
		eventNameExecutionComboBox.getSelectionModel().clearSelection();
	}
	private void clearSelectionAction() {
		actionMessage.clear();
		actionApprDegLvl.clear();
		reqCheckbox.setSelected(false);
	
		actionDeg.setSelected(false);
		//agentNameEventComboBox.getSelectionModel().clearSelection();
		actionComboBox.getSelectionModel().clearSelection();
		agentSrc.getSelectionModel().clearSelection();
		agentDest.getSelectionModel().clearSelection();
	}
	private Boolean validateAgent() {
		return !agentName.getText().trim().equals("") && !openness.getText().trim().equals("")
				&& !conscientiousness.getText().trim().equals("") && !extraversion.getText().trim().equals("")
				&& !agreeableness.getText().trim().equals("") && !neuroticism.getText().trim().equals("");
	}

	private void clearSelectionAgent() {
		agentName.clear();
		openness.clear();
		conscientiousness.clear();
		extraversion.clear();
		agreeableness.clear();
		neuroticism.clear();

	}

	private void clearSelectionGoal() {
		goalNameComboBox.getSelectionModel().clearSelection();
		goalName.clear();
		goalWeight.clear();
	}

	



	private static class AgentNameStringConverter extends StringConverter<Agent> {
		@Override
		public String toString(Agent object) {
			return object.getName();
		}

		@Override
		public Agent fromString(String string) {
			return null;
		}
	}

	private static class ActionNameStringConverter extends StringConverter<Action> {
		@Override
		public String toString(Action object) {
			return object.getMessage();
		}

		@Override
		public Action fromString(String string) {
			return null;
		}
	}

	private static class EventNameStringConverter extends StringConverter<EventName> {
		@Override
		public String toString(EventName object) {
			return object.getName();
		}

		@Override
		public EventName fromString(String string) {
			return null;
		}
	}

	private static class GoalNameStringConverter extends StringConverter<GoalName> {
		@Override
		public String toString(GoalName object) {
			return object.getName();
		}

		@Override
		public GoalName fromString(String string) {
			return null;
		}
	}
//	private  void goAgents() {
//
//		try {
//		
//			AgentController Agent = null;
//			try {
//				Supervisor.getContainer().getAgent("Diffuseur");
//			} catch (jade.wrapper.ControllerException ex) {
//				Agent = Supervisor.getContainer().createNewAgent("Diffuseur", "com.IA.decision.multiAgents.Jade.Diffuseur", null);
//				Agent.start();
//
//			}
//
//			AgentRepository agentRepo = ApplicationContextProvider.getApplicationContext()
//					.getBean(AgentRepository.class);
//
//			for (com.IA.decision.multiAgents.BO.Agent agent : agentRepo.findAll()) {
//
//				try {
//					Supervisor.getContainer().getAgent(agent.getName());
//				} catch (jade.wrapper.ControllerException ex) {
//					Agent = Supervisor.getContainer().createNewAgent(agent.getName(), "com.IA.decision.multiAgents.Jade.AgentTemplate",
//							null);
//					Agent.start();
//				}
//
//			}
//
//		} catch (Exception any) {
//			any.printStackTrace();
//		}
//	}
}
