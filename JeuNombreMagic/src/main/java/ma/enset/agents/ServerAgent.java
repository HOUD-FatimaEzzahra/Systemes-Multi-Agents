package ma.enset.agents;

import jdk.internal.agent.Agent;

public class ServerAgent extends Agent {
    private int secretNumber;
    private boolean gameStarted = false;

    protected void setup() {
        // Génère le nombre secret
        secretNumber = (int) (Math.random() * 100);
        System.out.println("Le nombre secret est : " + secretNumber);

        // Comportement de l'agent serveur pour recevoir les tentatives des joueurs
        addBehaviour(new Behaviour() {
            public void action() {
                // Ecoute les messages des joueurs
                MessageTemplate msgTemplate = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
                ACLMessage msg = myAgent.receive(msgTemplate);
                if (msg != null) {
                    int guess = Integer.parseInt(msg.getContent());
                    System.out.println("Le joueur " + msg.getSender().getLocalName() + " propose le nombre " + guess);

                    // Vérifie si le joueur a trouvé le nombre secret
                    if (guess == secretNumber) {
                        System.out.println("Le joueur " + msg.getSender().getLocalName() + " a gagné !");
                        ACLMessage winMsg = new ACLMessage(ACLMessage.INFORM);
                        winMsg.setContent("Le joueur " + msg.getSender().getLocalName() + " a gagné avec le nombre " + guess + " !");
                        for (int i = 0; i < 2; i++) {
                            winMsg.addReceiver(getAID("joueur" + (i + 1)));
                        }
                        send(winMsg);
                        doDelete();
                    } else {
                        String result = guess < secretNumber ? "dessus" : "dessous";
                        System.out.println("Le joueur " + msg.getSender().getLocalName() + " est " + result + " du nombre secret.");

                        // Envoie un message indiquant au joueur s'il est au-dessus ou en dessous du nombre secret
                        ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
                        reply.setContent(result);
                        reply.addReceiver(msg.getSender());
                        send(reply);
                    }
                } else {
                    block();
                }
            }

            public boolean done() {
                return gameStarted;
            }
        });
        System.out.println("L'agent serveur est prêt.");
    }
}
