package ma.enset.rsa;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class ServerAgent extends Agent {
    @Override
    protected void setup() {
        try {
            // Get the private key from the arguments and decode it
            String encodedPRK = (String) getArguments()[0];
            byte[] decodedPRK = Base64.getDecoder().decode(encodedPRK);

            // Set up a cyclic behaviour to receive messages
            addBehaviour(new CyclicBehaviour() {
                @Override
                public void action() {
                    // Receive a message
                    ACLMessage receivedMessage = receive();
                    if (receivedMessage != null) {
                        try {
                            // Decode the encrypted message from Base64
                            String encryptedMessageAsString = receivedMessage.getContent();
                            byte[] encryptedMessage = Base64.getDecoder().decode(encryptedMessageAsString);

                            // Initialize the cipher with the private key and decrypt the message
                            Cipher cipher = Cipher.getInstance("RSA");
                            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(decodedPRK);
                            PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(privateKeySpec);
                            cipher.init(Cipher.DECRYPT_MODE, privateKey);
                            byte[] decryptedMessage = cipher.doFinal(encryptedMessage);
                            // Print the decrypted message
                            System.out.println(new String(decryptedMessage));
                        } catch (Exception e) {
                            // Print the error message and stack trace
                            e.printStackTrace();
                        }
                    } else {
                        // If no message is received, block the behaviour
                        block();
                    }
                }
            });
        } catch (Exception e) {
            // Print the error message and stack trace
            e.printStackTrace();
        }
    }
}
