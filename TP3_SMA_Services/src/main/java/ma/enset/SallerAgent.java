package ma.enset;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

public class SallerAgent extends Agent {
    private String price;
    @Override
    protected void setup() {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                price=getArguments()[0].toString();
                DFAgentDescription dfAgentDescription = new DFAgentDescription();
                ServiceDescription serviceDescription = new ServiceDescription();
                serviceDescription.setType("pc");
                serviceDescription.setName("hp");
                dfAgentDescription.addServices(serviceDescription);
                try {
                    DFService.register(myAgent, dfAgentDescription);
                } catch (FIPAException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage receivedMsg=receive();
                if (receivedMsg!=null){
                    switch (receivedMsg.getPerformative()){
                        case ACLMessage.CFP:
                            ACLMessage aclMessage = new ACLMessage(ACLMessage.PROPOSE);
                            aclMessage.setContent("10000");
                            aclMessage.addReceiver(receivedMsg.getSender());
                            send(aclMessage);
                            break;
                        case ACLMessage.ACCEPT_PROPOSAL:
                            ACLMessage aclMessage1 = new ACLMessage(ACLMessage.AGREE);
                            aclMessage1.setContent("i can send it to you");
                            aclMessage1.addReceiver(receivedMsg.getSender());
                            send(aclMessage1);
                            break;
                        case ACLMessage.REQUEST:
                            ACLMessage aclMessage2 = new ACLMessage(ACLMessage.CONFIRM);
                            aclMessage2.setContent("i will send it to you");
                            aclMessage2.addReceiver(receivedMsg.getSender());
                            send(aclMessage2);
                            break;
                    }
                }
            }
        });
    }
    @Override
    protected void takeDown() {
    }
}
