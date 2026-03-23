package reto;
public class Pinguino extends Jugador {
    public Pinguino(String nombre, String color) {
        super(nombre, color);
    }
    public void getPersonaSatelite(pinguino p) {
        System.out.println("Estado de " + p.getNombre()
                + " [" + p.getColor() + "] → casilla " + p.getPosicion());
        p.getInventario().mostrar();
    }
    public void anadirItem(item i) {
        inventario.anadirItem(i);
    }
    public void usarItem(item i, gestorpartida gestor) {
        i.usar(this, gestor);
    }
    public void quitarItem(item i) {
        inventario.quitarItem(i);
    }
}

