package com.IA.decision.multiAgents.Controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.IA.decision.multiAgents.BO.*;
import com.IA.decision.multiAgents.repositories.*;

import javafx.animation.FillTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import javafx.util.StringConverter;

@Service
public class MainController {
	private static final Logger logger = LogManager.getLogger(MainController.class);

	private static final int X = 600;
	private static final int Y = 330;
	private static final double LAMBDA_VALUE = 0.5 ;
	private static final int NUMBER_OF_AGENT = 5;
	private static final int  NUMBER_ITERATION = 20;
	private static final int  NUMBER_ITERATION_LEARNING = 20;
	private static final int REWARD_MAX = 130;
	private static SequentialTransition seq ;
	private static SequentialTransition seqLearn ;
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

	// execution
	@FXML
	public ComboBox<Agent> agentNameExecutionEventComboBox;
	@FXML
	public ComboBox<EventName> eventNameExecutionComboBox;
	@FXML
	public TextField eventIntensity;
	@FXML
	public TextField eventReaction;
	// execution
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
	// Drug Spreading
	@FXML
	public Button playSpreadDrug;
	@FXML
	public Button generateDrugSpread;
	@FXML
	public AnchorPane drugSpreadingPane;
	// Drug Spreading
	
	//Drug Learning
	@FXML
	public Button generateDrugLearn;
	@FXML
	public Button playDrugLearn;
	
	@FXML
	public AnchorPane drugLearningPane;
	//Drug Learning
	// reports
	@FXML
	LineChart emotionsChart;
	
	
	// reports
	@Autowired
	private EventNameRepository eventNameRepo;
	@Autowired
	private EmotionTowardsAgentRepository emotionTowardsAgentRepo;
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
	private DrugSpreadRepository drugSpreadRepo;
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
			// if we implement the K factor EIL*C then we may use those two lists
			List<EventReaction> listPositiveReaction = new LinkedList<>();
			listPositiveReaction.add(new EventReaction("Asking for help"));
			listPositiveReaction.add(new EventReaction("High effort"));
			List<EventReaction> listNegativeReaction = new LinkedList<>();
			listNegativeReaction.add(new EventReaction("Asking for help"));
			listNegativeReaction.add(new EventReaction("Giving up"));
			for (Agent agent : agentRepo.findAll()) {
				for (EventName eventName : eventNameRepo.findAll()) {
					// EIL

					EventInfo eventInfo = new EventInfo();
					eventInfo.setAgent(agent);
					double randomValue = 0;
					if (OCEANRepo.findByAgent(agent).getNeuroticism() >= 0.5) {
						randomValue = generateRandomNumber(0.5, 1.0);
					} else {
						randomValue = generateRandomNumber(0.15, 0.5);
					}
					eventInfo.setEventIntensityLevel(randomValue);

					eventInfo.setEventName(eventName);
					listEventInfo.add(eventInfo);
					EventReaction eventReaction = new EventReaction();
					if (eventName.getGoalName().getName().equals("Success the exam")
							|| eventName.getGoalName().getName().equals("Success the year")) {

						if (eventName.getEventDegree()) {
							eventReaction.setEventReaction("Keep working");
						} else {
							if (OCEANRepo.findByAgent(agent).getConscientiousness() >= 0.5) {

								eventReaction.setEventReaction("High effort");

							} else {

								eventReaction.setEventReaction("Asking for help");
							}
						}
					}
					if (eventName.getGoalName().getName().equals("Appreciation")) {
						if (eventName.getEventDegree()) {
							eventReaction.setEventReaction("Finish the proposed activity ");
						} else {

							if (OCEANRepo.findByAgent(agent).getConscientiousness() >= 0.5) {

								eventReaction.setEventReaction("Improve level of competence");
							} else {

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
			double approvalDegreeLevel = 0;
			for (EventReaction eventReaction : eventReactions) {
				if (eventReaction.getReactionName().equals("Asking for help")) {
					Agent agentDest = eventReaction.getEventInfo().getAgent();
					for (Agent agentSrc : agentRepo.findAll()) {
						if (agentSrc != agentDest) {
							Action action = new Action();
							OCEAN oceanAgent = OCEANRepo.findByAgent(agentSrc);
							if (oceanAgent.getAgreeableness() >= 0.5) {
								approvalDegreeLevel = generateRandomNumber(0.5, 1.0);
								action.setMessage("Accepting help");
								action.setActionDegree(true);
							} else {
								approvalDegreeLevel = generateRandomNumber(0.0, 0.5);
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
			for (Agent agent : agentRepo.findAll()) {
				for (GoalName goalName : goalNameRepo.findAll()) {
					GoalInfo goalInfo = new GoalInfo();
					// goal weight
					double goalWeight = 0;

					if (OCEANRepo.findByAgent(agent).getConscientiousness() >= 0.5) {
						goalWeight = generateRandomNumber(0.5, 1.0);
					} else {
						goalWeight = generateRandomNumber(0.15, 0.5);					}
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
			EventName eventName = new EventName("Prospect bad mark", true, false, false);
			eventName.setLikelihood(1);
			eventName.setGoalName(goalName);
			eventNames.add(eventName);
			eventName = new EventName("Bad mark", false, true, false);
			eventName.setGoalName(goalName);
			eventName.setLikelihood(1);
			eventNames.add(eventName);
			eventName = new EventName("Prospect good mark", true, false, true);
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
			eventName = new EventName("Prospect failing the activities", true, false, false);
			eventName.setGoalName(goalName);
			eventName.setLikelihood(1);
			eventNames.add(eventName);
			eventName = new EventName("Failing the activities", false, false, false);
			eventName.setGoalName(goalName);
			eventNames.add(eventName);

			eventName = new EventName("Prospect success the activities", true, false, true);
			eventName.setGoalName(goalName);
			eventName.setLikelihood(1);
			eventNames.add(eventName);
			eventName = new EventName("Have success the activities", false, true, true);
			eventName.setGoalName(goalName);
			eventName.setLikelihood(1);
			eventNames.add(eventName);

			eventNameRepo.saveAll(eventNames);

			goalName = new GoalName("Appreciation");
			goalNameRepo.save(goalName);
			eventNames.clear();

			eventName = new EventName("Negative feedback", false, true, false);
			eventName.setGoalName(goalName);
			eventName.setLikelihood(1);
			eventNames.add(eventName);
			eventName = new EventName("Positive feedback", false, true, false);
			eventName.setGoalName(goalName);
			eventName.setLikelihood(1);
			eventNames.add(eventName);
			eventNameRepo.saveAll(eventNames);

			goalName = new GoalName("Social growth");
			goalNameRepo.save(goalName);
			eventNames.clear();
			eventName = new EventName("Asking for help (Other) ", false, true, false);
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
				fileWriter = new FileWriter(currentDir.toAbsolutePath().toString() + "/start.txt");
				fileWriter.write("go");
				fileWriter.close();
				Files.copy(Paths.get("C:\\folderOCC\\excelSheetOCC.xlsx"),
						Paths.get("C:\\folderOCC\\excelSheetOCCValue.xlsx"));
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
			Action action = new Action(actionMessage.getText(), actionDeg.isSelected(), reqCheckbox.isSelected(),
					Double.parseDouble(actionApprDegLvl.getText()));
			action.setAgentSrc(agentSrc.getSelectionModel().getSelectedItem());
			action.setAgentDest(agentDest.getSelectionModel().getSelectedItem());
			// action.setEventName(eventNameComboBox.getSelectionModel().getSelectedItem());
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
			// GoalInfo goalInfo = new GoalInfo(Double.parseDouble(goalWeight.getText()));
			goalNameComboBox.getItems().add(name);
			goalNameRepo.saveAll(goalNameComboBox.getItems());
			goalExecutionComboBox.getItems().add(name);
			goalNameComboBox.setItems(FXCollections.observableArrayList(goalNameRepo.findAll()));
			// goalInfoRepo.save(goalInfo);
			clearSelectionGoal();

		});
		updateGoal.setOnAction(event -> {
			GoalName goal = goalNameComboBox.getSelectionModel().getSelectedItem();
			goal.setName(goalName.getText());
			goalNameRepo.save(goal);
			goalNameComboBox.setItems(FXCollections.observableArrayList(goalNameRepo.findAll()));
		});
		addEventName.setOnAction(event -> {
			EventName evName = new EventName(eventName.getText(), false, confirmCheckBox.isSelected(),
					degreeCheckBox.isSelected());
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

		playSpreadDrug.setOnAction(event -> {
			
			seq.play();
		});
		playDrugLearn.setOnAction(event -> {
			
			seqLearn.play();
		});
		generateDrugSpread.setOnAction(event -> {
			List<Agent> agentList = agentRepo.findAll();
			seq = new SequentialTransition();
			LocalDateTime  currentTime = LocalDateTime.now();
			Map<Agent, Circle> agentMap = createAgentPoint(agentList);
			
			for (Agent agent : agentMap.keySet()) {
				drugSpreadingPane.getChildren().add(agentMap.get(agent));
			}
			Agent agentSellerInit = agentList.get(generateRandomInt(0,agentList.size()-1));
			agentMap.get(agentSellerInit).setFill(Color.RED);
			Set<Agent> agentSellers  =  new HashSet<Agent>();
			agentSellers.add(agentSellerInit);
			List<Agent> agentBuyers = new ArrayList<>(agentList);
			Map<Agent, Integer> agentDrugOccurence = initAgentDrugOccurence(agentList);
			List<EmotionTowardsAgent> emotionTowardsAgentList = emotionTowardsAgentRepo.findAll();
			List<OCEAN> oceans = OCEANRepo.findAll();
			List<OCC> allOCC = OCCRepo.findAll();
			for(int i=0; i<NUMBER_ITERATION ; i++) {
				logger.log(Level.INFO, "current time {}", currentTime);
				Set<Agent> agentSellersNew = new HashSet<Agent>(agentSellers);
				seq = moveAllAgent(seq,agentMap);
				logger.log(Level.INFO, "agent sellers list {}", agentSellers);
				logger.log(Level.INFO, "agent buyers list {}", agentBuyers);
				for(Agent agentSeller : agentSellers) {
				//	logger.log(Level.INFO, "start selling agent seller {}", agentSeller);
					//logger.log(Level.INFO, "--------------------------------");
					for(Agent agentBuyer: agentBuyers) {
						//logger.log(Level.INFO, "agent possible buyer {}", agentBuyer);
						if (!agentBuyer.getName().equals(agentSeller.getName())) {
							OCC occ;
							Optional<OCC> occOpt = allOCC.stream().filter(occAgent -> occAgent.getAgent().equals(agentBuyer)).findAny();
							if(occOpt.isPresent()) {
								occ = occOpt.get();
							}
							else {
								occ = new OCC();
							}
							OCEAN ocean = oceans.stream().filter(oceanA->oceanA.getAgent().equals(agentBuyer)).findAny().get();
							//logger.log(Level.INFO, "agent OCEAN {}", ocean);
							EmotionTowardsAgent emotionTowardsAgent = emotionTowardsAgentList.stream().filter(emotion -> emotion.getAgent().equals(agentBuyer) && emotion.getAgentDest().equals(agentSeller)).findAny().get();
							//logger.log(Level.INFO, "agent emotion Towards Agent {}", emotionTowardsAgent);
							boolean emotionTowardsAgentBool =  (emotionTowardsAgent.getLikingValue() > 0.5)  && 
															((emotionTowardsAgent.getInfluValue() > 0.5) ||
															(emotionTowardsAgent.getTrustValue() > 0.5) );
							if ((ocean.getNeuroticism() > 0.5 && emotionTowardsAgentBool) || ocean.getOpenness() > 0.5){
								double appealingness = generateRandomNumber(0.5, 1.0);
								double familiarity = agentDrugOccurence.get(agentBuyer) * 0.2;
								double liking = appealingness * familiarity ;
								occ.setLiking(liking);
								occ.setJoy(liking);
								occ.setRelief(liking);
								occ.setSatisfaction(liking);
							
								if(!agentBuyer.getAgentDrugStatus().equals("C")) {
								//	logger.log(Level.INFO,"setting C");
									agentBuyer.setAgentDrugStatus("C");
									agentSellersNew.add(agentBuyer);
									FillTransition fillTransition = createColorTransition(agentMap.get(agentBuyer), Color.RED);
									seq.getChildren().add(fillTransition);
								}
							
								//CURRENT_DRUG_USER
							} else {
								double appealingness = generateRandomNumber(0.0, 1.0);
								double familiarity = agentDrugOccurence.get(agentBuyer) * 0.2;
								double disliking = appealingness * familiarity ;
								occ.setDisliking(disliking);
							    //NON_DRUG_USER
								if(agentBuyer.getAgentDrugStatus().equals("C")) {
									logger.log(Level.INFO,"setting NC");
									agentBuyer.setAgentDrugStatus("NC");
									FillTransition fillTransition = createColorTransition(agentMap.get(agentBuyer), Color.BLUE);
									seq.getChildren().add(fillTransition);
								}
								else if(agentBuyer.getAgentDrugStatus().equals("S")) {
									logger.log(Level.INFO,"setting N");
									agentBuyer.setAgentDrugStatus("N");
									FillTransition fillTransition = createColorTransition(agentMap.get(agentBuyer), Color.GOLD);
									seq.getChildren().add(fillTransition);
								}
							
								//NON_CURRENT_DRUG_USER
							}
						}
					}
				}
				agentSellers = agentSellersNew;
				currentTime = currentTime.plusHours(1);
			}
		
		});
		generateDrugLearn.setOnAction(event -> {
			//init agents Qmax Matrix the structure will be A 3D Matrix [agent][state(joy or distress)][action(accept A1 or refuse A2)]
			List<Agent> agentList = agentRepo.findAll();
			seqLearn = new SequentialTransition();
			LocalDateTime  currentTime = LocalDateTime.now();
			Map<Agent, Circle> agentMap = createAgentPoint(agentList);
			Map<Agent, int[][]> qMaxMatrix = initQmaxAgent(agentList);
			Map<Agent, Integer> agentRewards = generateAgentRewards(agentList);
			for (Agent agent : agentMap.keySet()) {
				drugLearningPane.getChildren().add(agentMap.get(agent));
			}
			List<Agent> agentConsumers = new ArrayList<>(agentList);
			List<OCEAN> oceans = OCEANRepo.findAll();
			List<OCC> allOCC = OCCRepo.findAll();
			for(int i=0; i<NUMBER_ITERATION_LEARNING ; i++) {
				seqLearn = moveAllAgent(seqLearn,agentMap);
				logger.log(Level.INFO, "current time {}", currentTime);
						//logger.log(Level.INFO, "agent possible buyer {}", agentBuyer);
							Random rand = new Random();
							Agent agentConsumer =  agentConsumers.get(rand.nextInt(agentConsumers.size()));
							int[][] qMaxAgent = qMaxMatrix.get(agentConsumer);
							OCC occ;
							Optional<OCC> occOpt = allOCC.stream().filter(occAgent -> occAgent.getAgent().equals(agentConsumer)).findAny();
							if(occOpt.isPresent()) {
								occ = occOpt.get();
							}
							else {
								occ = new OCC();
							}
							OCEAN ocean = oceans.stream().filter(oceanA->oceanA.getAgent().equals(agentConsumer)).findAny().get();
							int reward = agentRewards.get(agentConsumer);
							if ((ocean.getNeuroticism() > 0.5) || ocean.getOpenness() > 0.5){
								double liking = reward/REWARD_MAX ;
								occ.setLiking(liking);
								occ.setJoy(liking);
								occ.setRelief(liking);
								occ.setSatisfaction(liking);
							
								if(!agentConsumer.getAgentDrugStatus().equals("C")) {
								//	logger.log(Level.INFO,"setting C");
									agentConsumer.setAgentDrugStatus("C");
									
									FillTransition fillTransition = createColorTransition(agentMap.get(agentConsumer), Color.RED);
									seqLearn.getChildren().add(fillTransition);
									qMaxAgent[0][0] += LAMBDA_VALUE*reward; 
								}
								else {
									qMaxAgent[0][1] += LAMBDA_VALUE*reward; 
								}
								reward = reward - 5;
								if(reward > 0) {
									agentRewards.replace(agentConsumer , reward);
								}
								//CURRENT_DRUG_USER
							} else {
								double disliking = reward/REWARD_MAX ;
								occ.setDisliking(disliking);
							    //NON_DRUG_USER
								if(agentConsumer.getAgentDrugStatus().equals("C")) {
									logger.log(Level.INFO,"setting NC");
									agentConsumer.setAgentDrugStatus("NC");
									FillTransition fillTransition = createColorTransition(agentMap.get(agentConsumer), Color.BLUE);
									seqLearn.getChildren().add(fillTransition);
								}
								else if(agentConsumer.getAgentDrugStatus().equals("S")) {
									logger.log(Level.INFO,"setting N");
									agentConsumer.setAgentDrugStatus("N");
									FillTransition fillTransition = createColorTransition(agentMap.get(agentConsumer), Color.GOLD);
									seqLearn.getChildren().add(fillTransition);
								}
								currentTime = currentTime.plusHours(1);
								//NON_CURRENT_DRUG_USER
							}
						}
			
			printQmaxMatrixAgent( qMaxMatrix, agentList);
			//Start sending drug consuming signals using for loop
			//A1 so state will be joy. update the [joy][accept] with the formula r + Qmax. compute like and update joy, satisfaction and relief with the like value.
			//A2 so state will be distress. update the [distress][refuse] 
			//end loop
			
		
		});
		generateAgents.setOnAction(event -> {
			OCEANRepo.deleteAll();
			emotionTowardsAgentRepo.deleteAll();
			agentRepo.deleteAll();
			List<Agent> agents = new LinkedList<>();
			List<OCEAN> oceans = new LinkedList<>();
			for (int i = 0; i < NUMBER_OF_AGENT; i++) {
				Agent agent = new Agent("Agent" + Character.toString((char) (i + 65)));
				agent.setAgentDrugStatus("S");
				agents.add(agent);
					OCEAN ocean = new OCEAN();
				ocean.setAgent(agent);

				Double agreeableness = generateRandomNumber(0.0, 1.0);
				ocean.setAgreeableness(agreeableness);
				Double conscientiousness = generateRandomNumber(0.0, 1.0);
				ocean.setConscientiousness(conscientiousness);
				Double openness = generateRandomNumber(0.0, 1.0);
				ocean.setOpenness(openness);
				Double extraversion = generateRandomNumber(0.0, 1.0);
				ocean.setExtraversion(extraversion);
				Double neuroticism = generateRandomNumber(0.0, 1.0);
				ocean.setNeuroticism(neuroticism);
				oceans.add(ocean);
			}
			OCEANRepo.saveAll(oceans);
			agentRepo.saveAll(agents);

			// insertion de nouvelles valeurs

			// Initialisation de Liking Towards Agent
			LinkedList<EmotionTowardsAgent> emotionTowardsAgentList = new LinkedList<>();
			for (Agent agentSrc : agentRepo.findAll()) {
				emotionTowardsAgentList.clear();
				for (Agent agentDest : agentRepo.findAll()) {
					if (!agentSrc.getName().equals(agentDest.getName())) {
						EmotionTowardsAgent emotionTowardsAgent = new EmotionTowardsAgent();
						OCEAN ocean = oceans.stream()
								.filter(oceanVect -> oceanVect.getAgent().getName().equals(agentSrc.getName()))
								.findFirst().get();
						double likingValue = 0;
						if (ocean.getAgreeableness() >= 0.5 || ocean.getExtraversion() >= 0.5) {
							likingValue = generateRandomNumber(0.5, 1.0);
						} else {
				            likingValue = generateRandomNumber(0.0, 0.5);
						}
						double influValue = generateRandomNumber(0.0, 1.0);
						emotionTowardsAgent.setInfluValue(influValue);
						emotionTowardsAgent.setLikingValue(likingValue);
						double trustValue = generateRandomNumber(0.0, 1.0);
						emotionTowardsAgent.setTrustValue(trustValue);
						emotionTowardsAgent.setAgentDest(agentDest);
						emotionTowardsAgent.setAgent(agentSrc);
						emotionTowardsAgentList.add(emotionTowardsAgent);
					}
				}
				agentSrc.setEmotionTowardAgent(emotionTowardsAgentList);

				emotionTowardsAgentRepo.saveAll(emotionTowardsAgentList);

				agentRepo.save(agentSrc);
			}

//			//likingTowardsAgentRepo.saveAll(likingTowardsAgentList);
//			OCEANRepo.saveAll(occeans);
			// suppression de valeurs anciennes

			// mise à jour de la partie graphique
			// Agents
			agentsComboBox.setItems(FXCollections.observableArrayList(agents));
			// Action
			agentSrc.setItems(FXCollections.observableArrayList(agents));
			agentDest.setItems(FXCollections.observableArrayList(agents));
			// Event
			agentNameExecutionEventComboBox.setItems(FXCollections.observableArrayList(agents));
			// Goal
			agentsGoalExecution.setItems(FXCollections.observableArrayList(agents));
		});

		addEventExecution.setOnAction(event -> {

			EventInfo evInfo = new EventInfo(Double.parseDouble(eventIntensity.getText()));
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
			// eventNameExecutionComboBox.setItems(FXCollections.observableArrayList(eventNameRepo.findAll()));
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
				if (occByAgentOpt.isPresent()) {
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
			if (newAction != null) {
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
				if (agent != null) {
					GoalInfo goalInfo = goalInfoRepo.findByGoalNameAndAgent(agent.getId(), newGoal.getId());
					if (goalInfo != null) {
						goalWeight.setText(goalInfo.getWeight().toString());
					} else {
						goalWeight.setText("");
					}
				}

			}

		});
		agentsGoalExecution.valueProperty().addListener((ChangeListener<Agent>) (ov, oldValue, newAgent) -> {
			if (newAgent != null) {
				GoalName goalName = goalExecutionComboBox.getSelectionModel().getSelectedItem();
				if (goalName != null) {
					GoalInfo goalInfo = goalInfoRepo.findByGoalNameAndAgent(newAgent.getId(), goalName.getId());
					if (goalInfo != null) {
						goalWeight.setText(goalInfo.getWeight().toString());
					} else {
						goalWeight.setText("");
					}
				}
			}
		});
		eventNameComboBox.valueProperty().addListener((ChangeListener<EventName>) (ov, oldValue, newEvent) -> {
			if (newEvent != null) {
				// Agent agent = agentsComboBox.getSelectionModel().getSelectedItem();

				// EventInfo eventInfo = eventInfoRepo.findByEventNameAndAgent(agent.getId(),
				// newEvent.getId());
				eventName.setText(newEvent.getName());
				// eventIntensity.setText(eventInfo.getEventIntensityLevel().toString());
				confirmCheckBox.setSelected(newEvent.getConfirmed());
				degreeCheckBox.setSelected(newEvent.getEventDegree());
				// eventReaction.setText(eventReactionRepo.findByEventInfo(eventInfo).getReactionName());
			}
		});
		agentNameExecutionEventComboBox.valueProperty()
				.addListener((ChangeListener<Agent>) (ov, oldValueAgent, newValueAgent) -> {
					if (newValueAgent != null) {
						EventName eventNameExecution = eventNameExecutionComboBox.getSelectionModel().getSelectedItem();
						if (eventNameExecution != null) {
							EventInfo eventInfo = eventInfoRepo
									.findByEventNameAndAgent(newValueAgent.getId(), eventNameExecution.getId()).get();
							if (eventInfo != null) {
								eventIntensity.setText(eventInfo.getEventIntensityLevel().toString());
								EventReaction evReaction = eventReactionRepo.findByEventInfo(eventInfo);
								eventReaction.setText(evReaction.getReactionName());
							} else {
								eventIntensity.setText("");
								eventReaction.setText("");
							}
						}
					}
				});
		eventNameExecutionComboBox.valueProperty()
				.addListener((ChangeListener<EventName>) (ov, oldValueExecution, newEventNameExecution) -> {
					if (newEventNameExecution != null) {
						Agent agentExecution = agentNameExecutionEventComboBox.getSelectionModel().getSelectedItem();
						if (agentExecution != null) {
							EventInfo eventInfo = eventInfoRepo
									.findByEventNameAndAgent(agentExecution.getId(), newEventNameExecution.getId())
									.get();
							if (eventInfo != null) {
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
	public SequentialTransition moveAllAgent(SequentialTransition seq, Map<Agent, Circle> agentList) {
		for (Circle agentCircle: agentList.values()) {
			TranslateTransition translateTransition = createMoveTransition(Math.random() * X - agentCircle.getCenterX(), Math.random() * Y - agentCircle.getCenterY(), agentCircle);
			seq.getChildren().add(translateTransition);
		}
		return seq;
	}

	public static int generateRandomInt(int min, int max) {
	    Random r = new Random();
	    return r.nextInt((max - min) + 1) + min;
	}
	public Map<Agent, Integer> initAgentDrugOccurence(List<Agent> setAgent) {
		Map<Agent, Integer> agentDrugOccurence = new HashMap<Agent, Integer>();
		for (Agent agent : setAgent) {
			agentDrugOccurence.put(agent, 0);
		}
		return agentDrugOccurence;

	}
	public Map<Agent, int[][]> initQmaxAgent(List<Agent> listAgent) {
		Map<Agent, int[][]> qMaxAgent = new HashMap<Agent, int[][]>();
		for (Agent agent : listAgent) {
			int[][] qMaxAgentMatrix = new int [2][2];
			qMaxAgentMatrix [0][0] = 0;
			qMaxAgentMatrix [0][1] = 0;
			qMaxAgentMatrix [0][0] = 0;
			qMaxAgentMatrix [1][0] = 0;
			qMaxAgent.put(agent, qMaxAgentMatrix);
		}
		return qMaxAgent;
		
	}
	public int getQmaxAgent(Agent agent, Map<Agent, int[][]> qMaxAgent) {
		int[][] qMaxMatrix = qMaxAgent.get(agent);
		int qMax = 0;
		for(int i=0; i<qMaxMatrix.length; i++) {
			int[] matrixLine = qMaxMatrix[i];
			for(int j=0; j < matrixLine.length; j++) {
				if(qMax < matrixLine[j]) {
					qMax = matrixLine[j];
				}
			}
		}
		return qMax;
	}
	public void printQmaxMatrixAgent(Map<Agent, int[][]> qMaxMatrixAgent, List<Agent> agents) {
		for(Agent agent: agents) {
			logger.log(Level.INFO, "Agent {} Qmax {}", agent, getQmaxAgent(agent, qMaxMatrixAgent) );
		}
	}
	public Double generateRandomNumber(Double min, Double max) {
		return  Math.round(ThreadLocalRandom.current().nextDouble(min, max) * 100d) / 100d;
		
	}
	
	public Map<Agent, Circle> createAgentPoint(List<Agent> setAgent) {
		Map<Agent, Circle> agentPoints = new HashMap<Agent, Circle>();
		for (Agent agent : setAgent) {
			Circle circle = new Circle();
			circle.setCenterX(Math.random() * X);
			circle.setCenterY(Math.random() * Y);
			circle.setRadius(4f);
			circle.setFill(Color.GREEN);
			agentPoints.put(agent, circle);
		}
		return agentPoints;
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
		// agentNameEventComboBox.getSelectionModel().clearSelection();
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

	private TranslateTransition createMoveTransition(double x, double y, Circle circle) {
		TranslateTransition trans = new TranslateTransition();
		trans.setToX(x);
		trans.setToY(y);
		trans.setDuration(Duration.millis(200));
		trans.setCycleCount(1);
		trans.setNode(circle);
		return trans;
	}

	private FillTransition createColorTransition(Circle circle, Color color) {
		FillTransition fltr = new FillTransition(Duration.millis(200), circle, color, color);
		fltr.setCycleCount(1);
		fltr.setAutoReverse(true);
		return fltr;
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
	private Map<Agent, Integer> generateAgentRewards (List<Agent> agents) {
		Map<Agent, Integer> agentRewards = new HashMap<Agent, Integer>();
		for(Agent agent: agents) {

			agentRewards.put(agent, generateRandomInt(50, REWARD_MAX));
		}
		return agentRewards;
		
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
