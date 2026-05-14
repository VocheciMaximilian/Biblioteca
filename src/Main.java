import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import ui.BibliotecaApp;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.out.println("Nu s-a putut seta tema sistemului: " + e.getMessage());
            }

            new BibliotecaApp().setVisible(true);
        });
    }
}
