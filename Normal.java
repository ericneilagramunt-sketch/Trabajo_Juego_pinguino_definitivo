package reto;
public class Normal extends casilla {
    public normal(int pos) {
        super(pos);
    }

    @Override
    public void realizarAccion(Jugador jugador) {
        System.out.println("Casilla normal. Sin efecto.");
    }
}
