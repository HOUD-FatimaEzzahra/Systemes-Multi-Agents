package gui;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.util.Arrays;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PlayerGUI extends BorderPane {
    private final AID serverAID;
    private final TextArea messagesTextArea;
    private final TextField numberTextField;
    private final Button sendButton;

    public PlayerGUI(AID serverAID) {
        this.serverAID = serverAID;

        // Créer une zone de texte pour afficher les messages du serveur
        messagesTextArea = new TextArea();
        messagesTextArea.setEditable(false);
        messagesTextArea.setWrapText(true);

        // Créer un champ texte pour saisir le numéro à deviner
        numberTextField = new TextField();
        numberTextField.setPromptText("Entrez un nombre entre 0 et 100");

        // Créer un bouton pour envoyer le numéro à deviner
        sendButton = new Button("Envoyer");
        sendButton.setDisable(true); // Le bouton est désactivé par défaut

        // Ajouter un écouteur d'événements pour activer ou désactiver le bouton en fonction de la saisie utilisateur
        numberTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d+") && Integer.parseInt(newValue) <= 100) {
                sendButton.setDisable(false);
            } else {
                sendButton.setDisable(true);
            }
        });

        // Créer une boîte horizontale pour le champ texte et le bouton
        HBox inputBox = new HBox(numberTextField, sendButton);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setSpacing(10);

        // Créer une boîte verticale pour afficher les messages du serveur et la boîte de saisie
        VBox centerBox = new VBox(messagesTextArea, inputBox);
        centerBox.setSpacing(10);
        centerBox.setPadding(new Insets(10));
        centerBox.setAlignment(Pos.CENTER);

        // Ajouter la boîte centrale à la zone centrale de la fenêtre
        setCenter(centerBox);
    }

    /**
     * Ajoute un message à la zone de texte des messages.
     *
     * @param message Le message à ajouter.
     */
    public void addMessage(String message) {
        Platform.runLater(() -> messagesTextArea.appendText(message + "\n"));
    }

    /**
     * Envoie un message au serveur contenant le numéro saisi.
     */
    public void sendNumber() {
        String number = numberTextField.getText();
        if (!number.isEmpty()) {
            // Créer un message ACL de type INFORM
            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
            message.addReceiver(serverAID);
            message.setContent(number);

            // Envoyer le message au serveur
            GuiAgent.send(message);

            // Effacer le champ texte et désactiver le bouton
            numberTextField.clear();
            sendButton.setDisable(true);
        }
    }

    /**
     * Ferme la fenêtre.
     */
    public void close() {
        Platform.runLater(() -> getScene().getWindow().hide());
    }
}
