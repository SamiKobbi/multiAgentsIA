package com.IA.decision.multiAgents.Controllers;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

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
	public TextField eventIntensity;
	@FXML
	public CheckBox confirmCheckBox;
	@FXML
	public CheckBox degreeCheckBox;
	@FXML
	public ComboBox<EventName> eventNameComboBox;
	@FXML
	public TextField eventReaction;
	@FXML
	public Button addEvent;
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
	public ComboBox<Action> actionAgentComboBox;
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
		agentSrc.setConverter(new AgentNameStringConverter());
		agentDest.setConverter(new AgentNameStringConverter());
		goalNameComboBox.setConverter(new GoalNameStringConverter());
		eventNameComboBox.setConverter(new EventNameStringConverter());
		actionAgentComboBox.setConverter(new ActionNameStringConverter());
		agentsComboBox.setItems(FXCollections.observableArrayList(agentRepo.findAll()));
		agentSrc.setItems(FXCollections.observableArrayList(agentRepo.findAll()));
		agentDest.setItems(FXCollections.observableArrayList(agentRepo.findAll()));
		agentsComboBox.getSelectionModel().selectFirst();
		goalNameComboBox.getSelectionModel().selectFirst();

		goButton.setOnAction(event -> {
			 
		     
			    FileWriter fileWriter;
				try {
					fileWriter = new FileWriter("C:\\multiAgents\\file.txt");
					  fileWriter.write("go");
					    fileWriter.close();
				} catch (IOException e) {
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
			actionAgentComboBox.getItems().add(action);
			actionRepo.saveAll(actionAgentComboBox.getItems());
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
		
		addEvent.setOnAction(event -> {
			EventName evName = new EventName(eventName.getText(), confirmCheckBox.isSelected());
			evName.setGoalName(goalNameComboBox.getSelectionModel().getSelectedItem());
			EventInfo evInfo = new EventInfo(degreeCheckBox.isSelected(), Double.parseDouble(eventIntensity.getText()) );
			evInfo.setEventName(evName);
			evInfo.setAgent(agentsComboBox.getSelectionModel().getSelectedItem());
			EventReaction evReaction = new EventReaction(eventReaction.getText());
			Agent agent = agentsComboBox.getSelectionModel().getSelectedItem();

			
			
			
			//ev.setGoalInfo(goalInfo);
			//ev.setGoalInfo(goalNameComboBox.getSelectionModel().getSelectedItem());
			eventNameComboBox.getItems().add(evName);
			eventNameRepo.saveAll(eventNameComboBox.getItems());
			eventInfoRepo.save(evInfo);
			eventReactionRepo.save(evReaction);
			clearSelectionEvent();
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
				//List<GoalWeight> goalWeights = goalWeightRepo.findByAgent(agentsComboBox.getSelectionModel().getSelectedItem())
				//goalComboBox.setItems(FXCollections.observableArrayList(goalRepo.findByAgent(newAgent)));

			}
		});
		
		eventNameComboBox.valueProperty().addListener((ChangeListener<EventName>) (ov, oldValue, newEvent) -> {
			if (newEvent != null) {
				Agent agent = agentsComboBox.getSelectionModel().getSelectedItem();
				GoalName goalName = goalNameComboBox.getSelectionModel().getSelectedItem();
				EventInfo eventInfo = eventInfoRepo.findByEventNameAndAgent(agent.getId(), newEvent.getId());
				eventName.setText(newEvent.getName());
				eventIntensity.setText(eventInfo.getEventIntensityLevel().toString());
				confirmCheckBox.setSelected(newEvent.getConfirmed());
				degreeCheckBox.setSelected(eventInfo.getEventDegree());
				eventReaction.setText(eventReactionRepo.findByEventInfo(eventInfo).getReactionName());
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
	
	private void clearSelectionEvent() {
		eventName.clear();
		eventIntensity.clear();
		eventReaction.clear();
		eventNameComboBox.getSelectionModel().clearSelection();
	}
	private void clearSelectionAction() {
		actionMessage.clear();
		actionApprDegLvl.clear();
		actionAgentComboBox.getSelectionModel().clearSelection();
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
