package src.reto;
public class Normal extends Casilla {
    public Normal(int pos) {
        super(pos);
    }

    @Override
    public void realizarAccion(Jugador jugador) {
        System.out.println("Casilla normal. Sin efecto.");
    }
}
