package reto;

public class BolaDeNieve extends Item {

    public BolaDeNieve(String nombre, int cantidad) {
        super(nombre, cantidad);
    }
 public void usar(Jugador objetivo) {
        if (getCantidad() > 0) {
            System.out.println("¡Bola de nieve lanzada a " + objetivo.getNombre() + "!");

            // Ejemplo de efecto: el jugador pierde turno
            objetivo.setPierdeTurno(true);

            // Reducimos la cantidad
            setCantidad(getCantidad() - 1);
        } else {
            System.out.println("No quedan bolas de nieve.");
        }
    }
}
