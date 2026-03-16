package reto;
import javax.swing.SwingUtilities;

public class main {

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("consola")) {
            jugarPorConsola();
        } else {
            SwingUtilities.invokeLater(() -> new PantallaMenu().menu());
        }
    }

    public static void jugarPorConsola() {
        gestorpartida gestor = new gestorpartida();
        gestor.nuevaPartida();
        gestor.jugar();
    }
}
