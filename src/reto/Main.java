package reto;
import javax.swing.SwingUtilities;

public class Main {

    public static void Main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("consola")) {
            jugarPorConsola();
        } else {
            SwingUtilities.invokeLater(() -> new PantallaMenu().menu());
        }
    }

    public static void jugarPorConsola() {
        Gestor_partida gestor = new Gestor_partida();
        gestor.nuevaPartida();
        gestor.jugar();
    }
}
