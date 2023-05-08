package ma.enset.rsa;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class ClientAgent extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                String message="Hello server!";
                String encodedPBK=(String) getArguments()[0];
                byte[] decodedPBK=Base64.getDecoder().decode(encodedPBK);
                try {
                    KeyFactory keyFactory=KeyFactory.getInstance("RSA");
                    PublicKey publicKey=keyFactory.generatePublic(new X509EncodedKeySpec(decodedPBK));
                    Cipher cipher= Cipher.getInstance("RSA");
                    cipher.init(Cipher.ENCRYPT_MODE,publicKey);
                    byte[] encryptedMessage=cipher.doFinal(message.getBytes());
                    String encryptedMessageAsString= Base64.getEncoder().encodeToString(encryptedMessage);
                    ACLMessage aclMessage=new ACLMessage(ACLMessage.INFORM);
                    aclMessage.setContent(encryptedMessageAsString);
                    aclMessage.addReceiver(new AID("server",AID.ISLOCALNAME));
                    send(aclMessage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
