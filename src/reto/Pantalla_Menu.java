package src.reto;
//Esta clase es vista
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class Pantalla_Menu {

    public void menu() {
        System.out.println("=== MENU DEL JUEGO ===");
        System.out.println("1. Nueva Partida");
        System.out.println("2. Cargar Partida");
        System.out.println("3. Salir");
    }

   
    public void botonNuevaPartida() {
        System.out.println("Iniciando una nueva partida...");
        
            }

   
    public void botonCargarPartida(int id) {
        System.out.println("Cargando partida con ID: " + id);

        
    }
    public void botonSalir() {
        System.out.println("Saliendo del juego...");
        System.exit(0);
    }
}
