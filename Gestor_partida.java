package reto;
import java.util.Scanner;
import java.util.ArrayList;
public class Gestorpartida {

    private Partida partida;
    private Scanner sc = new Scanner(System.in);
    private Foca focaCPU;

    public void nuevaPartida() {
        System.out.println("BIENVENIDO AL JUEGO DEL PINGUINO");
       

        System.out.println("\nCuantos jugadores humanos? (2-4):");
        int numJugadores = leerEnteroEntre(2, 4);

        ArrayList <Jugador> jugadores = new ArrayList<>();
        String[] colores = {"Azul", "Rojo", "Verde", "Amarillo"};

        for (int i = 1; i <= numJugadores; i++) {
            System.out.println("Nombre jugador " + i + ":");
            String nombre = sc.nextLine().trim();
            jugadores.add(new Pinguino(nombre, colores[i - 1]));
        }
        focaCPU = new Foca();
        Jugadores.add(focaCPU);
        System.out.println("\n>> La Foca (CPU) se une como rival!");

        Tablero tablero = new Tablero(50);
        Partida = new Partida(Tablero, Jugadores);

        System.out.println("\n Partida creada con " + numJugadores + " jugadores + Foca CPU.");
        System.out.println("   El primero en llegar a la casilla 50 gana.\n");
    }

    public void jugar() {
        while (!partida.isFinalizada()) {

            Jugador actual = partida.getJugadorActual();
            if (actual instanceof Foca) {
                focaCPU.turnoIA(partida.getJugadoresHumanos(), partida.getTablero());
                comprobarVictoria(focaCPU);
                partida.siguienteTurno();
                continue;
            }

            System.out.println("\n════════════════════════════════════════");
            System.out.println("JUGADOR: su Turno de: " + actual.getNombre() + " (" + actual.getColor() + ")");
            System.out.println(" Posicion actual: " + actual.getPosicion() + " / 49");
            System.out.println("════════════════════════════════════════");

            if (actual.isPierdeTurno()) {
                System.out.println("TURNO PERDIDO " + actual.getNombre() + " pierde este turno por penalizacion anterior.");
                actual.setPierdeTurno(false);
                partida.siguienteTurno();
                continue;
            }

            System.out.println("\n Inventario de " + actual.getNombre() + ":");
            actual.getInventario().mostrar();

            boolean turnoFinalizado = false;
            while (!turnoFinalizado) {
                System.out.println("\n¿Que quieres hacer?");
                System.out.println("  1) Tirar dado");
                System.out.println("  2) Usar item del inventario");
                System.out.println("  3) Guardar partida");
                System.out.println("  4) Ver tablero");
                System.out.print("Opcion: ");

                int opcion = leerEnteroEntre(1, 4);

                switch (opcion) {
                    case 1:
                        turnoFinalizado = realizarTirada(actual);
                        break;
                    case 2:
                        menuUsarItem(actual);
                        break;
                    case 3:
                        System.out.println("\nPartida guardada con exito. ¡Hasta pronto!");
                        turnoFinalizado = true;
                        break;
                    case 4:
                        partida.getTablero().mostrarTablero(partida.getTodosLosJugadores());
                        break;
                }
            }

            if (!partida.isFinalizada()) comprobarVictoria(actual);

            if (!partida.isFinalizada()) comprobarGuerra(actual);

            if (!partida.isFinalizada()) comprobarInteraccionFoca(actual);

            partida.siguienteTurno();
        }

        System.out.println("\n FELICIDADES HAS GANADO");
        System.out.println(" FIN DE PARTIDA");
        System.out.println("  GANADOR: " + partida.getGanador().getNombre());
    }

    private boolean realizarTirada(Jugador actual) {
        int tirada = actual.tirarDado();
        System.out.println("\n DADO " + actual.getNombre() + " tira el dado... ¡" + tirada + "!");
        actual.mover(tirada);

        int pos = actual.getPosicion();
        System.out.println(" Nueva posicion: " + pos);

        if (pos < partida.getTablero().getSize()) {
            System.out.println("   [Casilla " + pos + ": " + partida.getTablero().getTipoCasilla(pos) + "]");
            partida.getTablero().activarCasilla(actual);
        }

        return true;
    }

    private void menuUsarItem(Jugador actual) {
        Inventario inv = actual.getInventario();
        ArrayList<String> opciones = new ArrayList<>();

        if (inv.getCantidadPeces() > 0)
            opciones.add("PEZ");
        if (inv.getCantidadBolas() > 0)
            opciones.add("BOLA");
        if (inv.cantidadDados() > 1)
            opciones.add("DADO_EXTRA");

        if (opciones.isEmpty()) {
            System.out.println("   No tienes items utilizables ahora mismo.");
            return;
        }

        System.out.println("\n Items disponibles:");
        int idx = 1;
        for (String op : opciones) {
            switch (op) {
                case "PEZ":
                    System.out.println("  " + idx + ")  Pez (" + inv.getCantidadPeces() + ") "
                            + "- Sobornar al oso / Bloquear foca 2 turnos");
                    break;
                case "BOLA":
                    System.out.println("  " + idx + ")   Bola de nieve (" + inv.getCantidadBolas() + ") "
                            + "- Lanzar a otro jugador (retrocede 2 casillas)");
                    break;
                case "DADO_EXTRA":
                    System.out.println("  " + idx + ")  Dado extra (x" + (inv.cantidadDados() - 1) + ") "
                            + "- Ya equipado, se suma automaticamente al tirar");
                    break;
            }
            idx++;
        }
        System.out.println("  " + idx + ")  Cancelar");
        System.out.print("Opcion: ");

        int op = leerEnteroEntre(1, idx);
        if (op == idx) {
            System.out.println("   Cancelado.");
            return;
        }

        String elegido = opciones.get(op - 1);
        switch (elegido) {
            case "PEZ":        menuUsarPez(actual);      break;
            case "BOLA":       menuUsarBola(actual);     break;
            case "DADO_EXTRA": menuVerDados(actual);     break;
        }
    }

    private void menuUsarPez(Jugador actual) {
        System.out.println("\n ¿Para que quieres usar el pez?");
        System.out.println("  1) Reservar para sobornar al oso (automatico cuando caigas en oso)");
        System.out.println("  2) Alimentar a la foca para bloquearla 2 turnos");
        System.out.print("Opcion: ");
        int op = leerEnteroEntre(1, 2);

        if (op == 2) {
            if (focaCPU.estaBloqueada()) {
                System.out.println("   La foca ya esta bloqueada (" + focaCPU.getTurnosBloqueada() + " turnos). Ahorra el pez.");
            } else {
                actual.getInventario().usarPez();
                focaCPU.bloquear();
                System.out.println("Has alimentado a la foca. ¡Queda bloqueada 2 turnos!");
            }
        } else {
            System.out.println("Pez guardado. Se usara automaticamente si caes en un oso.");
        }
    }

    private void menuUsarBola(Jugador actual) {
        ArrayList<Jugador> humanos   = partida.getJugadoresHumanos();
        ArrayList<Jugador> objetivos = new ArrayList<>();

        for (Jugador j : humanos) {
            if (!j.equals(actual)) objetivos.add(j);
        }
        objetivos.add(focaCPU); 

        if (objetivos.isEmpty()) {
            System.out.println(" No hay jugadores a los que lanzar.");
            return;
        }

        System.out.println("\n  ¿A quien lanzas la bola de nieve?");
        for (int i = 0; i < objetivos.size(); i++) {
            Jugador obj = objetivos.get(i);
            System.out.println("  " + (i + 1) + ") " + obj.getNombre()
                    + " (casilla " + obj.getPosicion() + ")");
        }
        System.out.println("  " + (objetivos.size() + 1) + ") Cancelar");
        System.out.print("Opcion: ");
        int op = leerEnteroEntre(1, objetivos.size() + 1);

        if (op == objetivos.size() + 1) {
            System.out.println("   Cancelado.");
            return;
        }

        Jugador objetivo = objetivos.get(op - 1);
        if (actual.getInventario().usarBola()) {
            objetivo.mover(-2);
            if (objetivo.getPosicion() < 0) objetivo.setPosicion(0);
            System.out.println("Bola de nieve lanzada! "
                    + objetivo.getNombre() + " retrocede 2 casillas → casilla "
                    + objetivo.getPosicion());
        } else {
            System.out.println("   No te quedan bolas de nieve.");
        }
    }

    private void menuVerDados(Jugador actual) {
        int total = actual.getInventario().cantidadDados();
        System.out.println("   Tienes " + total + " dado(s). Al tirar se usara el dado con mayor rango disponible.");
        System.out.println("   (El sistema elige automaticamente el dado al tirar)");
    }

    private void comprobarGuerra(Jugador actual) {
        for (Jugador otro : partida.getJugadoresHumanos()) {
            if (otro.equals(actual)) continue;
            if (otro.getPosicion() != actual.getPosicion()) continue;

            System.out.println("\n ATAQUES");
            System.out.println("  ¡GUERRA DE PINGUINOS! ");
            System.out.println("   " + actual.getNombre() + " y " + otro.getNombre()
                    + " estan en la casilla " + actual.getPosicion() + "!");

            int bolasA = actual.getInventario().getCantidadBolas();
            int bolasB = otro.getInventario().getCantidadBolas();

            System.out.println("   " + actual.getNombre() + " tiene " + bolasA + " bola(s)");
            System.out.println("   " + otro.getNombre()   + " tiene " + bolasB + " bola(s)");

        
            actual.getInventario().perderTodasLasBolas();
            otro.getInventario().perderTodasLasBolas();
            System.out.println("   Ambos pierden todas sus bolas de nieve.");

            if (bolasA > bolasB) {
                int diff = bolasA - bolasB;
                actual.mover(diff);
                System.out.println("  Gana " + actual.getNombre() + "! Avanza " + diff
                        + " casillas → casilla " + actual.getPosicion());
            } else if (bolasB > bolasA) {
                int diff = bolasB - bolasA;
                otro.mover(diff);
                System.out.println(" Gana " + otro.getNombre() + "! Avanza " + diff
                        + " casillas → casilla " + otro.getPosicion());
            } else {
                System.out.println(" Empate. Nadie avanza.");
            }
        }
    }

    private void comprobarInteraccionFoca(Jugador actual) {
        if (focaCPU.getPosicion() != actual.getPosicion()) return;

        System.out.println("\n🦭 ¡La FOCA esta en tu misma casilla!");

        if (actual.getInventario().getCantidadPeces() > 0) {
            System.out.println("   Tienes " + actual.getInventario().getCantidadPeces()
                    + " pez/peces. ¿Quieres darle uno para bloquearla 2 turnos? (S/N):");
            String resp = sc.nextLine().trim().toUpperCase();

            if (resp.equals("S")) {
                actual.getInventario().usarPez();
                focaCPU.bloquear();
                System.out.println(" La foca queda bloqueada 2 turnos. ¡Buena jugada!");
            } else {
                ataqueDelaFoca(actual);
            }
        } else {
            System.out.println("   No tienes peces.");
            ataqueDelaFoca(actual);
        }
    }

    private void ataqueDelaFoca(Jugador actual) {
        System.out.println("   🦭 ¡La foca te golpea con la cola! Te envia al agujero anterior.");
        int agujero = partida.getTablero().buscarAgujeroAnterior(actual.getPosicion());
        actual.setPosicion(agujero);
        System.out.println("   Ahora estas en la casilla: " + agujero);
    }

    private void comprobarVictoria(Jugador j) {
        if (j.getPosicion() >= partida.getTablero().getSize()) {
            j.setPosicion(partida.getTablero().getSize() - 1); 
            partida.setGanador(j);
            partida.setFinalizada(true);
        }
    }


    private int leerEnteroEntre(int min, int max) {
        while (true) {
            try {
                String linea = sc.nextLine().trim();
                int val = Integer.parseInt(linea);
                if (val >= min && val <= max) return val;
                System.out.print("Introduce un numero entre " + min + " y " + max + ": ");
            } catch (NumberFormatException e) {
                System.out.print("Opcion no valida. Introduce un numero: ");
            }
        }
    }
}

