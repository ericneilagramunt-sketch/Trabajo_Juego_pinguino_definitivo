package reto;

public class Pez extends item {

    public Pez() {
        super("Pez", 1);
    }

    @Override
    public void usar(Jugador jugador, Gestor_partida gestor) {
        gestor.menuUsarPez(jugador);
    }

    @Override
    public String toString() {
        return "Pez";
    }
}
