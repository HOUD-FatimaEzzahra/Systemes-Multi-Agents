package ma.enset.rsa;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

public class ServerContainer {
    public static void main(String[] args) throws ControllerException {
        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST,"localhost");
        AgentContainer agentContainer=runtime.createAgentContainer(profile);
        String encodedPRK="MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAiykvlFxlgq+OQD7N+9B14KRyMuUqWZm4L4gfKk7gLnc/KNe2czxdKUF/L801wytNko3JDkpIRc52tlSWrjzi9wIDAQABAkAML+2yTQs40D4hgz6P5JhGNbCoo2g7k5pNU1sDXggFGZxJuXqp97qmRxfE5ucq7kia8n9zH7qGQrXvXvWcjbK1AiEAskGkGuHvWDwEuyvgWd5HYg65lKZeF+EzjjtAGRT3RA0CIQDH2oW6JTtrBeoXciHMwjxd0pfzTVp8cSDMVSCfm6quEwIhAJXK0zxD3/0lclRW6pCaWSHtfcWMiEVI2SoMYDCzjZWBAiAPBY/TpATJUJJ93KhJubfL3Y5qTUAl5mLuuN9Q3+R+XQIgSD515NaP2GLFKUi7xvj1bSjmvtQ6BZASdk0nxqy0wxg=";
        AgentController serverAgent=agentContainer.createNewAgent("server", ServerAgent.class.getName(),new Object[]{encodedPRK});
        serverAgent.start();
    }
}
