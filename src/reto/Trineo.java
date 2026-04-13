package src.reto;
public class Trineo extends Casilla {

    private Tablero Tablero;

    public Trineo(int pos) {
        super(pos);
        this.Tablero = null;
    }

    public Trineo(int pos, Tablero tablero) {
        super(pos);
        this.Tablero = tablero;
    }

    public void setTablero(Tablero t) { this.Tablero = t; }

    @Override
    public void realizarAccion(Jugador Jugador) {
        System.out.println("   Trineo! Avanzas al siguiente trineo.");
        if (Tablero != null) {
            int siguiente = Tablero.buscarProximoTrineo(Jugador.getPosicion());
            if (siguiente != -1) {
                System.out.println("   Avanzas hasta casilla " + siguiente + ".");
                Jugador.setPosicion(siguiente);
            } else {
                System.out.println("   No hay mas trineos. Avanzas 5 casillas.");
                Jugador.mover(5);
            }
        } else {
            System.out.println("   Avanzas 5 posiciones.");
            Jugador.mover(5);
        }
    }
}
