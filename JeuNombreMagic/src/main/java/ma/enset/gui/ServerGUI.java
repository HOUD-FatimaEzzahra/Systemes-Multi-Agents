package gui;

import jade.core.AID;
import jade.gui.GuiEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerGUI extends JFrame implements ActionListener {

    private JButton btnStart, btnStop;
    private JTextArea taLog;
    private JScrollPane spLog;

    private ServerAgent myAgent;

    public ServerGUI(ServerAgent a) {
        super(a.getLocalName());
        myAgent = a;

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(1, 2));
        btnStart = new JButton("Démarrer");
        btnStart.addActionListener(this);
        p.add(btnStart);
        btnStop = new JButton("Arrêter");
        btnStop.addActionListener(this);
        btnStop.setEnabled(false);
        p.add(btnStop);
        getContentPane().add(p, BorderLayout.NORTH);

        taLog = new JTextArea(15, 30);
        spLog = new JScrollPane(taLog);
        getContentPane().add(spLog, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnStart) {
            myAgent.doWait();
            btnStart.setEnabled(false);
            btnStop.setEnabled(true);
            taLog.append("Démarrage du serveur\n");
            GuiEvent ev = new GuiEvent(this, ServerAgent.START_SERVER);
            myAgent.postGuiEvent(ev);
        }
        else {
            btnStart.setEnabled(true);
            btnStop.setEnabled(false);
            taLog.append("Arrêt du serveur\n");
            GuiEvent ev = new GuiEvent(this, ServerAgent.STOP_SERVER);
            myAgent.postGuiEvent(ev);
        }
    }

    public void logMessage(String msg) {
        taLog.append(msg + "\n");
    }

    public void alertWinner(AID winner, int secretNumber) {
        JOptionPane.showMessageDialog(this, "Joueur " + winner.getLocalName() + " a gagné avec le nombre " + secretNumber, "Fin de partie", JOptionPane.INFORMATION_MESSAGE);
    }

    public void alertGameStopped() {
        JOptionPane.showMessageDialog(this, "Fin de la partie", "Fin de partie", JOptionPane.INFORMATION_MESSAGE);
    }

}
