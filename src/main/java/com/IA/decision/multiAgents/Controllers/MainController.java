package com.IA.decision.multiAgents.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.IA.decision.multiAgents.BO.Action;
import com.IA.decision.multiAgents.BO.Agent;
import com.IA.decision.multiAgents.BO.Event;
import com.IA.decision.multiAgents.BO.GoalName;
import com.IA.decision.multiAgents.BO.GoalInfo;
import com.IA.decision.multiAgents.BO.OCEAN;
import com.IA.decision.multiAgents.repositories.ActionRepository;
import com.IA.decision.multiAgents.repositories.AgentRepository;
import com.IA.decision.multiAgents.repositories.EventRepository;
import com.IA.decision.multiAgents.repositories.GoalNameRepository;
import com.IA.decision.multiAgents.repositories.OCCRepository;
import com.IA.decision.multiAgents.repositories.OCEANRepository;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

@Service
public class MainController {
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
	public ComboBox<Event> eventComboBox;
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
	private EventRepository eventRepo;
	@Autowired
	private GoalNameRepository goalNameRepo;
	@Autowired
	private OCCRepository OCCRepo;
	@Autowired
	private ActionRepository actionRepo;
	@Autowired
	private AgentRepository agentRepo;
	@Autowired
	private OCEANRepository OCEANRepo;
	@Autowired
	private GoalNameRepository goalRepo;

	@FXML
	public void initialize() {

		agentsComboBox.setConverter(new AgentNameStringConverter());
		agentSrc.setConverter(new AgentNameStringConverter());
		agentDest.setConverter(new AgentNameStringConverter());
		goalNameComboBox.setConverter(new GoalNameStringConverter());
		eventComboBox.setConverter(new EventNameStringConverter());
		actionAgentComboBox.setConverter(new ActionNameStringConverter());
		agentsComboBox.setItems(FXCollections.observableArrayList(agentRepo.findAll()));
		agentSrc.setItems(FXCollections.observableArrayList(agentRepo.findAll()));
		agentDest.setItems(FXCollections.observableArrayList(agentRepo.findAll()));
		agentsComboBox.getSelectionModel().selectFirst();
		goalNameComboBox.getSelectionModel().selectFirst();

		goButton.setOnAction(event -> {

//	    				Agent agent = new Agent("sami");
//	    				
//	    				Goal goal = new Goal("succeed the exam", 0.5);
//	    				goal.setAgent(agent);
//	    				
//	    				Event ev = new Event("Exams will be difficult",false,false,0.3);
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
			goalRepo.saveAll(goalNameComboBox.getItems());
			clearSelectionGoal();

		});
		
		addEvent.setOnAction(event -> {
			Event ev = new Event(eventName.getText(), confirmCheckBox.isSelected(), degreeCheckBox.isSelected(),
					Double.parseDouble(eventIntensity.getText()));
			
			//ev.setGoalInfo(goalInfo);
			//ev.setGoalInfo(goalNameComboBox.getSelectionModel().getSelectedItem());
			eventComboBox.getItems().add(ev);
			eventRepo.saveAll(eventComboBox.getItems());
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
		
		goalNameComboBox.valueProperty().addListener((ChangeListener<GoalName>) (ov, oldValue, newGoal) -> {

			if (newGoal != null) {
				
				eventComboBox.setItems(FXCollections.observableArrayList(eventRepo.findByGoalName(newGoal)));

			}
		});

	}
	
	private void clearSelectionEvent() {
		eventName.clear();
		eventIntensity.clear();
		eventComboBox.getSelectionModel().clearSelection();
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

	private static class EventNameStringConverter extends StringConverter<Event> {
		@Override
		public String toString(Event object) {
			return object.getName();
		}

		@Override
		public Event fromString(String string) {
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
}
