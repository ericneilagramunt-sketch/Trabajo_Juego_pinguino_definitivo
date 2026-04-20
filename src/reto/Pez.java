package src.reto;

import src.vista.Gestor_partida;

public class Pez extends Item {

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
