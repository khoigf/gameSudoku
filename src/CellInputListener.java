
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class CellInputListener implements ActionListener {
    int sum;

    @Override
    public void actionPerformed(ActionEvent e) {
        Cell sourceCell = (Cell) e.getSource();

        int numberIn = Integer.parseInt(sourceCell.getText());
        if (numberIn == sourceCell.number && sourceCell.ischecker == false) {
            sourceCell.status = CellStatus.CORRECT_GUESS;
            sourceCell.paint();
            sourceCell.counter = 1;
            sum += sourceCell.counter;
            sourceCell.ischecker = true;
            sourceCell.setEditable(false);
            JOptionPane.showMessageDialog(null, "It is correct!");
            System.out.println("You entered " + numberIn);
        } else if (numberIn != sourceCell.number) {
            sourceCell.status = CellStatus.WRONG_GUESS;
            sourceCell.paint();
            JOptionPane.showMessageDialog(null, "You redo it again!");
            System.out.println("You entered " + numberIn);
        }
        if (sum == 45) {
            JOptionPane.showMessageDialog(null, "Congratulation!\nYou win the game");
            sourceCell.status = CellStatus.WIN;

        }
    }

}
