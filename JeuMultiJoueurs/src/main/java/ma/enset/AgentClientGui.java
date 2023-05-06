package ma.enset;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AgentClientGui extends Application {
    private AgentClient agentClient;
    ObservableList<String> data = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        startContainer();
        primaryStage.setTitle("Agent Client");

        BorderPane root = new BorderPane();
        Label labelMsg = new Label("Nombre :");
        TextField textFieldMsg = new TextField();
        Button buttonSend = new Button("Envoyer");
        Button buttonStart = new Button("Commencer le jeu");
        HBox hBox = new HBox(buttonStart, labelMsg, textFieldMsg, buttonSend);

        ListView<String> listView = new ListView<>(data);
        root.setBottom(hBox);
        root.setCenter(listView);

        Scene scene = new Scene(root, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();

        buttonStart.setOnAction(event -> {
            GuiEvent guiEvent = new GuiEvent(this, 2);
            agentClient.onGuiEvent(guiEvent);
        });

        buttonSend.setOnAction(event -> {
            GuiEvent guiEvent = new GuiEvent(this, 1);
            guiEvent.addParameter(textFieldMsg.getText());
            synchronized (data) {
                data.add("==>>" + textFieldMsg.getText());
            }
            textFieldMsg.setText("");
            agentClient.onGuiEvent(guiEvent);
        });
    }

    private void startContainer() throws StaleProxyException {
        ProfileImpl profile = new ProfileImpl();
        profile.setParameter(ProfileImpl.GUI, "true"); // ajout de cette ligne pour activer l'interface graphique
        profile.setParameter(ProfileImpl.MAIN_HOST, "localhost");
        AgentContainer container = Runtime.instance().createAgentContainer(profile);
        AgentController agentController = container.createNewAgent("client", "ma.enset.AgentClient", new Object[]{this});
        agentController.start();
    }

    public void setAgentClient(AgentClient agentClient) {
        this.agentClient = agentClient;
    }

    public void showMessage(String message) {
        synchronized (data) { // ajout d'un bloc synchronized pour rendre l'accès à la liste de messages thread-safe
            Platform.runLater(() -> {
                data.add(message);
            });
        }
    }
}
