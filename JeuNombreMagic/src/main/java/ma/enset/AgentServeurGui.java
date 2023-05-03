package ma.enset;

import jade.gui.GuiEvent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AgentServeurGui extends Application {
    private AgentServer myAgent;
    private TextField magicNumberField;
    private ListView<String> messagesList;
    private Button startButton;
    private Button stopButton;

    public static final int START_GAME = 1;
    public static final int STOP_GAME = 2;

    public void setAgent(AgentServer agent) {
        myAgent = agent;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Top panel - magic number input
        HBox magicNumberBox = new HBox();
        magicNumberBox.setAlignment(Pos.CENTER_LEFT);
        magicNumberBox.setSpacing(10);
        Label magicNumberLabel = new Label("Magic number:");
        magicNumberField = new TextField();
        magicNumberField.setPrefWidth(50);
        magicNumberBox.getChildren().addAll(magicNumberLabel, magicNumberField);
        root.setTop(magicNumberBox);

        // Center panel - messages list
        messagesList = new ListView<String>();
        root.setCenter(messagesList);

        // Bottom panel - start and stop buttons
        HBox buttonsBox = new HBox();
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setSpacing(10);
        startButton = new Button("Start");
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int magicNumber = Integer.parseInt(magicNumberField.getText());
                GuiEvent guiEvent = new GuiEvent(this, START_GAME);
                guiEvent.addParameter(magicNumber);
                myAgent.postGuiEvent(guiEvent);
            }
        });
        stopButton = new Button("Stop");
        stopButton.setDisable(true);
        stopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GuiEvent guiEvent = new GuiEvent(this, STOP_GAME);
                myAgent.postGuiEvent(guiEvent);
            }
        });
        buttonsBox.getChildren().addAll(startButton, stopButton);
        root.setBottom(buttonsBox);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Agent Server");
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();
    }

    public void notifyGameStarted() {
        Platform.runLater(() -> {
            startButton.setDisable(true);
            stopButton.setDisable(false);
        });
    }

    public void notifyGameStopped() {
        Platform.runLater(() -> {
            startButton.setDisable(false);
            stopButton.setDisable(true);
        });
    }

    public void updateMessages(String message) {
        Platform.runLater(() -> messagesList.getItems().add(message));
    }
}
