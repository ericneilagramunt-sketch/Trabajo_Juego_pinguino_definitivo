package reto;
public class Oso extends Casilla {
    public Oso(int pos) {
        super(pos);
    }

    @Override
    public void realizarAccion(Jugador jugador) {
        System.out.println("¡Un oso te ataca!");

        int peces = jugador.getInventario().getCantidadPeces();

        if (peces > 0) {
            jugador.setPendienteOso(true);
        } else {
            System.out.println("   No tienes peces. ¡El oso te devuelve al inicio! ");
            jugador.setPosicion(0);
        }
    }
}

