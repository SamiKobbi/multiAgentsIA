package com.IA.decision.multiAgents.Controllers;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.IA.decision.multiAgents.BO.Action;
import com.IA.decision.multiAgents.BO.Agent;
import com.IA.decision.multiAgents.BO.EventInfo;
import com.IA.decision.multiAgents.BO.EventName;
import com.IA.decision.multiAgents.BO.EventReaction;
import com.IA.decision.multiAgents.BO.GoalName;
import com.IA.decision.multiAgents.BO.GoalInfo;
import com.IA.decision.multiAgents.BO.OCEAN;
import com.IA.decision.multiAgents.Jade.Diffuseur;
import com.IA.decision.multiAgents.Jade.Supervisor;
import com.IA.decision.multiAgents.config.ApplicationContextProvider;
import com.IA.decision.multiAgents.repositories.ActionRepository;
import com.IA.decision.multiAgents.repositories.AgentRepository;
import com.IA.decision.multiAgents.repositories.EventInfoRepository;
import com.IA.decision.multiAgents.repositories.EventReactionRepository;
import com.IA.decision.multiAgents.repositories.EventNameRepository;
import com.IA.decision.multiAgents.repositories.GoalInfoRepository;
import com.IA.decision.multiAgents.repositories.GoalNameRepository;
import com.IA.decision.multiAgents.repositories.OCCRepository;
import com.IA.decision.multiAgents.repositories.OCEANRepository;

import jade.wrapper.AgentController;
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
	public Button saveAgent;
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
	public ComboBox<GoalName> goalNameComboBox;
	@FXML
	public TextField goalName;
	@FXML
	public TextField goalWeight;
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
	public Button addEventName;
	@FXML
	public Button addEventExecution;
	// event

	// Action
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
	public CheckBox resCheckbox;
	@FXML
	public CheckBox actionDeg;
	
	@FXML
	public ComboBox<Action> actionComboBox;
	@FXML
	public Button addAction;
		// Action
	// reports
	@FXML
	LineChart emotionsChart;
	// reports
	@Autowired
	private EventNameRepository eventNameRepo;
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
		eventNameExecutionComboBox.setConverter(new EventNameStringConverter());
		goalNameComboBox.setConverter(new GoalNameStringConverter());
		eventNameComboBox.setConverter(new EventNameStringConverter());
		actionComboBox.setConverter(new ActionNameStringConverter());
		agentsComboBox.setItems(FXCollections.observableArrayList(agentRepo.findAll()));
		
		agentNameExecutionEventComboBox.setItems(FXCollections.observableArrayList(agentRepo.findAll()));
		agentSrc.setItems(FXCollections.observableArrayList(agentRepo.findAll()));
		agentDest.setItems(FXCollections.observableArrayList(agentRepo.findAll()));
		agentsComboBox.getSelectionModel().selectFirst();
		goalNameComboBox.setItems(FXCollections.observableArrayList(goalNameRepo.findAll()));
		eventNameComboBox.setItems(FXCollections.observableArrayList(eventNameRepo.findAll()));
		eventNameExecutionComboBox.setItems(FXCollections.observableArrayList(eventNameRepo.findAll()));
		actionComboBox.setItems(FXCollections.observableArrayList(actionRepo.findAll()));
		goalNameComboBox.getSelectionModel().selectFirst();

		goButton.setOnAction(event -> {
			 
			logger.info("go Agents");
			    FileWriter fileWriter;
				try {
					fileWriter = new FileWriter("C:\\multiAgents\\file.txt");
					  fileWriter.write("go");
					    fileWriter.close();
				} catch (IOException e) {
					logger.error(e);
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  
					//goAgents();
//	    				Agent agent = new Agent("sami");
//	    				
//	    				Goal goal = new Goal("succeed the exam", 0.5);
//	    				goal.setAgent(agent);
//	    				
//	    				EventName ev = new EventName("Exams will be difficult",false,false,0.3);
//
//	    				ev.setGoal(goal);
//	    				
//	    				agentRepo.save(agent);
//	    				goalRepo.save(goal);
//	    				eventRepo.save(ev);	  
		});

		addAction.setOnAction(event -> {
			Action action = new Action(actionMessage.getText(), actionDeg.isSelected(), reqCheckbox.isSelected(), Double.parseDouble(actionApprDegLvl.getText()));
			action.setAgentSrc(agentSrc.getSelectionModel().getSelectedItem());
			action.setAgentDest(agentDest.getSelectionModel().getSelectedItem());
			action.setGoal(goalNameComboBox.getSelectionModel().getSelectedItem());
			actionComboBox.getItems().add(action);
			actionRepo.saveAll(actionComboBox.getItems());
			clearSelectionAction();
		});

		saveGoal.setOnAction(event -> {
			
			GoalName name = new GoalName(goalName.getText());
			GoalInfo goalInfo = new GoalInfo(Double.parseDouble(goalWeight.getText()));
			Agent agent = agentsComboBox.getSelectionModel().getSelectedItem();
			goalInfo.setAgent(agent);
			goalInfo.setGoalName(name);
			goalNameComboBox.getItems().add(name);
			goalNameRepo.saveAll(goalNameComboBox.getItems());
			goalInfoRepo.save(goalInfo);
			clearSelectionGoal();

		});
		
		addEventName.setOnAction(event -> {
			EventName evName = new EventName(eventName.getText(), confirmCheckBox.isSelected(),degreeCheckBox.isSelected());
			evName.setGoalName(goalNameComboBox.getSelectionModel().getSelectedItem());
			eventNameComboBox.getItems().add(evName);
			eventNameExecutionComboBox.getItems().add(evName);
			eventNameRepo.saveAll(eventNameComboBox.getItems());
			clearSelectionEventName();
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
		}

		);

		saveAgent.setOnAction(event -> {
			if (validateAgent()) {

				Agent agent = agentRepo.save(new Agent(agentName.getText()));
				agentsComboBox.getItems().add(agent);
				
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

		agentsComboBox.valueProperty().addListener((ChangeListener<Agent>) (ov, oldValue, newAgent) -> {

			if (newAgent != null) {
				OCEAN oceanByAgent = OCEANRepo.findByAgent(newAgent);
				openness.setText(oceanByAgent.getOpenness().toString());
				conscientiousness.setText(oceanByAgent.getConscientiousness().toString());
				extraversion.setText(oceanByAgent.getExtraversion().toString());
				agreeableness.setText(oceanByAgent.getAgreeableness().toString());
				neuroticism.setText(oceanByAgent.getNeuroticism().toString());
				
			
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
		eventNameExecutionComboBox.valueProperty().addListener((ChangeListener<EventName>) (ov, oldValueExecution, newEventNameExecution) -> {
			if (newEventNameExecution != null) {
				Agent agentExecution = agentNameExecutionEventComboBox.getSelectionModel().getSelectedItem();
				if(agentExecution != null)
				{
					EventInfo eventInfo = eventInfoRepo.findByEventNameAndAgent(agentExecution.getId(), newEventNameExecution.getId());
					eventIntensity.setText(eventInfo.getEventIntensityLevel().toString());
					EventReaction evReaction = eventReactionRepo.findByEventInfo(eventInfo);
					eventReaction.setText(evReaction.getReactionName());
				}
			}
		});
		goalNameComboBox.valueProperty().addListener((ChangeListener<GoalName>) (ov, oldValue, newGoal) -> {

			if (newGoal != null) {
				
				goalName.setText(newGoal.getName());
				Agent agent = agentsComboBox.getSelectionModel().getSelectedItem();
				GoalInfo goalInfo = goalInfoRepo.findByGoalNameAndAgent(agent.getId(), newGoal.getId());
				goalWeight.setText(goalInfo.getWeight().toString());
				eventNameComboBox.setItems(FXCollections.observableArrayList(eventNameRepo.findByGoalName(newGoal)));

			}
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
		resCheckbox.setSelected(false);
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
