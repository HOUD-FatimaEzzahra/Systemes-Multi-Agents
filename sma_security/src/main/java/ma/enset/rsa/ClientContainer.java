package ma.enset.rsa;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

public class ClientContainer {
    public static void main(String[] args) throws ControllerException {
        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST,"localhost");
        AgentContainer agentContainer=runtime.createAgentContainer(profile);
        String encodedPBK="MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIspL5RcZYKvjkA+zfvQdeCkcjLlKlmZuC+IHypO4C53PyjXtnM8XSlBfy/NNcMrTZKNyQ5KSEXOdrZUlq484vcCAwEAAQ==\n";
        AgentController clientAgent=agentContainer.createNewAgent("client", ClientAgent.class.getName(),new Object[]{encodedPBK});
        clientAgent.start();
    }
}
