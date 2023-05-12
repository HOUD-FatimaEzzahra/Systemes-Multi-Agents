package ma.enset;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

public class SallerAgent1 extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
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
                            ACLMessage aclMessage1 = new ACLMessage(ACLMessage.CONFIRM);
                            aclMessage1.setContent("Accepted !");
                            aclMessage1.addReceiver(receivedMsg.getSender());
                            send(aclMessage1);
                            break;
                    }
                }
            }
        });

    }
}
