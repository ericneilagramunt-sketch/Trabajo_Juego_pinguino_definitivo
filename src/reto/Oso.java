public class Oso extends Casilla {

    public Oso(int posicion) {
        super(posicion);
    }
  @Override
    public void realizarAccion(Partida partida, Jugador jugador) {
        System.out.println("¡Un oso ataca!");

        int nuevaPos = jugador.getPosicion() - 1;
        if (nuevaPos < 0) nuevaPos = 0;

        jugador.setPosicion(nuevaPos);

        System.out.println("El jugador retrocede a la posición " + nuevaPos);
    }
}
