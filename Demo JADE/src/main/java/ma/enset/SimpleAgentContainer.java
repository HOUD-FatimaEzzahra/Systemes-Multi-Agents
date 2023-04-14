package ma.enset;
import jade.core.Agent;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;


public class SimpleAgentContainer {
    public static void main(String[] args) throws Exception{

        Runtime runtime = Runtime.instance();
        ProfileImpl profileImpl = new ProfileImpl();
        profileImpl.setParameter("host", "localhost");
        AgentContainer agentContainer = runtime.createAgentContainer(profileImpl);
        AgentController agentController = agentContainer.createNewAgent("Client", AgentClient.class.getName(), new Object[]{});
        agentController.start();
    }
}
