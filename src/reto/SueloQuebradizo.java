package src.reto;
public class Suelo_quebradizo extends Casilla {

    public Suelo_quebradizo(int pos) {
        super(pos);
    }

    @Override
    public void realizarAccion(Jugador jugador) {
        int total = jugador.getInventario().totalObjetos();
        System.out.println("¡Pisas suelo quebradizo! (Tienes " + total + " objeto(s) en inventario)");

        if (total > 5) {
            System.out.println("¡Demasiado peso! El suelo cede... Vuelves al inicio.");
            jugador.setPosicion(0);
        } else if (total > 0) {
            System.out.println("El suelo cruje... pierdes tu turno.");
            jugador.setPierdeTurno(true);
        } else {
            System.out.println("Sin peso extra, el suelo aguanta. Pasas sin penalización.");
        }
    }
}


