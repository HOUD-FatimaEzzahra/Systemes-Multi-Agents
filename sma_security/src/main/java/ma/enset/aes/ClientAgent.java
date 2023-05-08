package ma.enset.aes;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

public class ClientAgent extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                String message="Hello server!";
                String password=(String) getArguments()[0];
                SecretKey secretKey=new SecretKeySpec(password.getBytes(),"AES");
                try {
                    Cipher cipher= Cipher.getInstance("AES");
                    cipher.init(Cipher.ENCRYPT_MODE,secretKey);
                    byte[] encryptedMessage=cipher.doFinal(message.getBytes());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
