import javax.swing.JButton;

public class TicTacToeButton extends JButton {
    private String state;  // "X", "O", or ""

    public TicTacToeButton() {
        state = "";  // Initially empty
        this.setText("");  // Empty text
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
        this.setText(state);  // Update button text
    }
}