package com.IA.decision.multiAgents;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.IA.decision.multiAgents.BO.Agent;
import com.IA.decision.multiAgents.Controllers.MainController;
import com.IA.decision.multiAgents.repositories.AgentRepository;

import jade.core.ProfileImpl;

import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

@SpringBootApplication
public class MultiAgentsApplication extends Application {

	private ConfigurableApplicationContext context;
	private Parent rootNode;


	@Override
	public void init() throws Exception {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(MultiAgentsApplication.class);
		context = builder.run(getParameters().getRaw().toArray(new String[0]));

		FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
		loader.setControllerFactory(context::getBean);
		rootNode = loader.load();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
		double width = visualBounds.getWidth();
		double height = visualBounds.getHeight();

		primaryStage.setScene(new Scene(rootNode, width, height));
		primaryStage.centerOnScreen();
		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		context.close();
	}
	public static void supervisor()
	{
		String line = "java -classpath C:/jade/jade/lib/jade.jar jade.Boot -gui";
    	CommandLine cmdLine = CommandLine.parse(line);
    	DefaultExecutor executor = new DefaultExecutor();
    	int exitValue = executor.execute(cmdLine);
    	
	}
	public static void main(String[] args) {
		supervisor();
		launch(args);
	}
	
}
