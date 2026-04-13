package src.reto;
//Esta clase es controladora
public class GestorTablero {

 public void ejecutarCasilla(partida partida, jugador jugador, casilla c) {
        System.out.println("   [Casilla " + jugador.getPosicion()
                + ": " + c.getClass().getSimpleName() + "]");
        c.realizarAccion(jugador);
    }
 public boolean comprobarTurno(partida partida) {
        for (jugador j : partida.getTodosLosJugadores()) {
            if (j.getPosicion() >= partida.getTablero().getSize() - 1) {
                j.setPosicion(partida.getTablero().getSize() - 1);
                partida.setGanador(j);
                partida.setFinalizada(true);
                System.out.println("   FIN DE PARTIDA. Ganador: " + j.getNombre());
                return true;
            }
        }
        return false;
    }
}
