package ma.enset.sma;

import jade.core.AID;
import jade.core.Agent;

import jade.core.behaviours.*;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

public class AgentClient extends GuiAgent {
    private AgentClientGui clientGui;
    @Override
    protected void setup() {
        System.out.println("*** Client: la méthode setup *****");
        clientGui=(AgentClientGui) getArguments()[0];
       clientGui.setAgentClient(this);
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage receivedMSG = receive();
                if (receivedMSG!=null){
                    clientGui.showMessage("<<=="+receivedMSG.getContent());
                    System.out.println(receivedMSG.getContent());
                    System.out.println(receivedMSG.getSender().getName());
                }else {
                    block();
                }

            }}
        );
        /*ACLMessage message=new ACLMessage(ACLMessage.REQUEST);
        message.setContent("bonjour serveur ce message et pour demander un service");
        message.addReceiver(new AID("server",AID.ISLOCALNAME));
        send(message);*/
        //addBehaviour(new );
        //generic behaviour
        /*addBehaviour(new Behaviour() {
            private int conteur;
            @Override
            public void action() {
                System.out.println("**** Contneur = "+conteur+" *****");
                conteur++;
            }

            @Override
            public boolean done() {
                return conteur==10?true:false;
            }
        });*/
       /* addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                System.out.println("**** One Shot Behaviour ******");
            }
        });*/
        /*addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                System.out.println("**** Cyclic Behaviour *******");
            }
        });*/
      /*addBehaviour(new TickerBehaviour(this,5000) {
          @Override
          protected void onTick() {
              System.out.println("***** Ticker Behaviour *****");
          }
      });*/
       /* addBehaviour(new WakerBehaviour(this,5000) {
            @Override
            protected void onWake() {
                System.out.println("**** Waker Behaviour ****");
            }
        });*/
       /* ParallelBehaviour parallelBehaviour=new ParallelBehaviour();
        parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                System.out.println("***** Cyclic Behaviour 1 **** ");
            }
        });
        parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                System.out.println("***** Cyclic Behaviour 2 **** ");
            }
        });
        addBehaviour(parallelBehaviour);*/
    }

    @Override
    protected void beforeMove() {
        System.out.println("*** Client:  la méthode beforeMove *****");
    }

    @Override
    protected void afterMove() {
        System.out.println("*** Client: la méthode afterMove *****");
    }

    @Override
    protected void takeDown() {
        System.out.println("*** Client: la méthode takeDown *****");
    }

    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {
        String parameter =(String) guiEvent.getParameter(0);
        ACLMessage message=new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(new AID("server",AID.ISLOCALNAME));
        message.setContent(parameter);
        send(message);
    }
}
