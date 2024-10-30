import ui.BibliothequeGUI;

import javax.swing.*;

/**
 * Classe principale pour lancer l'application de bibliothèque
 */
public class Main {
    public static void main(String[] args) {
        // Création et affichage de l'interface graphique
        SwingUtilities.invokeLater(() -> {
            BibliothequeGUI gui = new BibliothequeGUI();
            gui.setVisible(true);
        });
    }
} 