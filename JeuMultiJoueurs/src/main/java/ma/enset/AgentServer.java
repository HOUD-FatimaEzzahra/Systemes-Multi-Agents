package ma.enset;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

import java.util.Random;

public class AgentServer extends GuiAgent {
    private AgentServerGui agentServerGui;
    private int magicNumber;
    private int attemptsLeft;

    @Override
    protected void setup() {
        System.out.println("*** la méthode setup *****");
        agentServerGui = (AgentServerGui) getArguments()[0];
        agentServerGui.setAgentServer(this);

        // Générer un nombre aléatoire entre 0 et 100
        Random rand = new Random();
        magicNumber = rand.nextInt(101);
        System.out.println("Le nombre magique est : " + magicNumber);
        agentServerGui.showMessage("Le nombre magique est : " + magicNumber);

        // Ajouter un comportement cyclique pour gérer les messages reçus
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage receivedMSG = receive();
                if (receivedMSG != null) {
                    agentServerGui.showMessage("<<==" + receivedMSG.getContent());

                    // Vérifier si le message contient un nombre
                    int guess = 0;
                    try {
                        guess = Integer.parseInt(receivedMSG.getContent());
                    } catch (NumberFormatException e) {
                        // Le message ne contient pas un nombre valide
                        System.out.println("Le message ne contient pas un nombre valide.");
                        agentServerGui.showMessage("Le message ne contient pas un nombre valide.");
                        return;
                    }

                    // Vérifier si le nombre est correct
                    attemptsLeft--;
                    if (guess == magicNumber) {
                        // Le nombre est correct, envoyer un message de succès
                        ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
                        reply.addReceiver(new AID("client", AID.ISLOCALNAME));
                        reply.setContent("Bravo, vous avez gagné !");
                        send(reply);
                        System.out.println("Le client a gagné !");
                        agentServerGui.showMessage("Le client a gagné !");
                        agentServerGui.showMessage("Nombre de tentatives restantes : " + attemptsLeft);

                        // Générer un nouveau nombre magique
                        magicNumber = rand.nextInt(101);
                        System.out.println("Le nouveau nombre magique est : " + magicNumber);
                        agentServerGui.showMessage("Le nouveau nombre magique est : " + magicNumber);

                        // Réinitialiser le nombre de tentatives restantes
                        attemptsLeft = 5;
                    } else if (guess < magicNumber && attemptsLeft > 0) {
                        // Le nombre est trop petit, envoyer un message d'erreur
                        ACLMessage reply = new ACLMessage(ACLMessage.FAILURE);
                        reply.addReceiver(new AID("client", AID.ISLOCALNAME));
                        reply.setContent("Le nombre est trop petit !");
                        send(reply);
                        System.out.println("Le client a entré un nombre trop petit.");
                        agentServerGui.showMessage("Le client a entré un nombre trop petit.");
                        agentServerGui.showMessage("Nombre de tentatives restantes : " + attemptsLeft);
                    } else if (guess > magicNumber && attemptsLeft > 0) {
                        // Le nombre est trop grand, envoyer un message d'erreur
                        ACLMessage reply = new ACLMessage(ACLMessage.FAILURE);
                        reply.addReceiver(new AID("client", AID.ISLOCALNAME));
                        reply.setContent("Le nombre est trop grand !");
                        send(reply);
                        System.out.println("Le client a entré un nombre trop grand.");
                        agentServerGui.showMessage("Le client a entré un nombre trop grand.");
                        agentServerGui.showMessage("Nombre de tentatives restantes : " + attemptsLeft);
                    } else {
                        // Le client a perdu, envoyer un message de défaite
                        ACLMessage reply = new ACLMessage(ACLMessage.FAILURE);
                        reply.addReceiver(new AID("client", AID.ISLOCALNAME));
                        reply.setContent("Vous avez perdu !");
                        send(reply);
                        System.out.println("Le client a perdu !");
                        agentServerGui.showMessage("Le client a perdu !");
                        agentServerGui.showMessage("Le nombre magique était : " + magicNumber);

                        // Générer un nouveau nombre magique
                        magicNumber = rand.nextInt(101);
                        System.out.println("Le nouveau nombre magique est : " + magicNumber);
                        agentServerGui.showMessage("Le nouveau nombre magique est : " + magicNumber);

                        // Réinitialiser le nombre de tentatives restantes
                        attemptsLeft = 5;
                    }
                } else {
                    // Attente de messages
                    block();
                }
            }
        });
    }

    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {
        System.out.println("*** la méthode onGuiEvent *****");
        if (guiEvent.getType() == 1) {
            // L'utilisateur a cliqué sur le bouton "Démarrer"
            // Envoyer un message de démarrage au client
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.addReceiver(new AID("client", AID.ISLOCALNAME));
            msg.setContent("Démarrer");
            send(msg);
            System.out.println("Démarrer");
            agentServerGui.showMessage("Démarrer");
            agentServerGui.showMessage("Nombre de tentatives restantes : " + attemptsLeft);
        }
    }
}
