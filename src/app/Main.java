package app;

import view.TelaJogoDaVelha;
import javax.swing.SwingUtilities;
//import model.TelaJogoDaVelha;

public class Main extends TelaJogoDaVelha {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaJogoDaVelha tela = new TelaJogoDaVelha();
            tela.setVisible(true);
        });
    }
    
}

