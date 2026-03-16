package reto;
//ESTA CLASE ES CONTROLADOR
public class Gestor_jugador {

	    // Jugador actual (para control de turnos)
	    private Jugador jugadorActual;

	    // Tablero del juego
	    private Tablero tablero;

	    // Constructor
	    public Gestor_jugador(Tablero tablero) {
	        this.tablero = tablero;
	    }

	    // Salto del jugador (ej: trineu u otro evento)
	    public void jugadorSaltaItem(String nombreItem) {

	        // Aquí puedes gestionar lógica del item
	        System.out.println("El jugador usa o activa el item: " + nombreItem);

	        // lógica futura
	    }

	    // Movimiento del jugador
	    public void jugadorSeMueve(Jugador jugador, int pasos, Tablero tablero) {

	        int posicionActual = jugador.getPosicion();
	        int nuevaPosicion = posicionActual + pasos;

	        jugador.setPosicion(nuevaPosicion);

	        System.out.println(jugador.getNombre() + " se mueve a la casilla " + nuevaPosicion);

	        // aquí luego puedes comprobar el tipo de casilla
	        // tablero.getCasilla(nuevaPosicion)
	    }

	    // Finalizar turno
	    public void jugadorFinalizaTurno(Jugador jugador) {

	        System.out.println("Turno finalizado de: " + jugador.getNombre());

	        // lógica de cambio de turno
	        // jugadorActual = siguienteJugador();

	    }

	    // Evento aleatorio de interrogante
	    public void pinguinoEvento(Pinguino pinguino) {

	        System.out.println("Evento activado para el pingüino");

	        // Aquí podrías generar eventos aleatorios:
	        // - pez
	        // - bolas de nieve
	        // - dado rápido
	        // - dado lento
	    }

	    // Guerra entre pingüinos (bolas de nieve)
	    public void pinguinoGuerra(Pinguino p1, Pinguino p2) {

	        System.out.println("Combate entre pingüinos");

	        // lógica futura
	        // comprobar inventario de bolas de nieve
	        // retroceder jugador

	    }

	    // Interacción con foca (o enemigo / obstáculo)
	    public void focaInteractua(Pinguino p, Foca f) {

	        System.out.println("El pingüino interactúa con una foca");

	        // lógica futura
	        // bloquear paso
	        // mover casilla
	    }

	}

