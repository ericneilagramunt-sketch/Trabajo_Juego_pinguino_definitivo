package reto;
public class Trineo extends Casilla {

    private Tablero Tablero;

    public Trineo(int pos) {
        super(pos);
        this.tablero = null;
    }

    public Trineo(int pos, tablero tablero) {
        super(pos);
        this.tablero = tablero;
    }

    public void setTablero(tablero t) { this.tablero = t; }

    @Override
    public void realizarAccion(Jugador Jugador) {
        System.out.println("   Trineo! Avanzas al siguiente trineo.");
        if (tablero != null) {
            int siguiente = tablero.buscarProximoTrineo(jugador.getPosicion());
            if (siguiente != -1) {
                System.out.println("   Avanzas hasta casilla " + siguiente + ".");
                jugador.setPosicion(siguiente);
            } else {
                System.out.println("   No hay mas trineos. Avanzas 5 casillas.");
                jugador.mover(5);
            }
        } else {
            System.out.println("   Avanzas 5 posiciones.");
            jugador.mover(5);
        }
    }
}
