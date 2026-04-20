package clases_modelo;

import src.reto.Casilla;
import src.reto.Jugador;
import src.reto.Tablero;

public class Agujero extends Casilla {

    private Tablero tablero;

    public Agujero(int pos) {
        super(pos);
        this.Tablero = null;
    }

    public Agujero(int pos, Tablero tablero) {
        super(pos);
        this.Tablero = Tablero;
    }

    public void setTablero(Tablero t) {
        this.Tablero = t;
    }

    @Override
    public void realizarAccion(Jugador jugador) {
        System.out.println("¡Caes en un agujero en el hielo!");

        if (tablero != null) {
            int agujeroAnterior = Tablero.buscarAgujeroAnterior(jugador.getPosicion());
            if (agujeroAnterior < jugador.getPosicion() && agujeroAnterior != 0) {
                System.out.println("   Te hundes hasta el agujero anterior en casilla " + agujeroAnterior + ".");
                jugador.setPosicion(agujeroAnterior);
            } else {
                System.out.println("   No hay agujero anterior. Retrocedes 3 posiciones.");
                jugador.mover(-3);
            }
        } else {
            System.out.println("   Retrocedes 3 posiciones.");
            jugador.mover(-3);
        }
    }
}

