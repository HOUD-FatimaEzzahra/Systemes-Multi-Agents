package ma.enset.behaviours;

public class Behaviour extends OneShotBehaviour {

    private int guess;
    private PlayerGUI gui;

    public GuessingBehaviour(PlayerGUI gui, int guess) {
        this.gui = gui;
        this.guess = guess;
    }

    @Override
    public void action() {
        gui.addMessage("Tentative de guess : " + guess);
        gui.disableGuessing(); // disable the guessing button while waiting for server response
    }

    public void setGuess(int guess) {
        this.guess = guess;
    }
}
