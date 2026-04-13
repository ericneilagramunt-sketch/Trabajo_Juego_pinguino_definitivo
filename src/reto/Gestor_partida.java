package src.reto;
//esta clase es controladora
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.sql.Connection;
import java.util.LinkedHashMap;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

public class Gestor_partida {

    private Partida partida;
    private Random random;
    private Connection conn;

    public Gestor_partida(Connection conn) {
        this.random = new Random();
        this.conn = conn;
    }

    // Crear nueva partida
    public void nuevaPartida(Tablero tablero, ArrayList<Jugador> jugadores) {
        partida = new Partida(tablero, jugadores);
        System.out.println("Nueva partida creada.");
    }

    // Tirar dado
    public int tirarDado(Jugador j) {
        int resultado = random.nextInt(6) + 1;
        System.out.println(j.getNombre() + " ha sacado: " + resultado);
        return resultado;
    }

    // Ejecutar turno completo
    public void ejecutarTurnoCompleto() {
        if (partida == null || partida.isFinalizada()) return;

        Jugador actual = partida.getJugadorActual();
        System.out.println("Turno de: " + actual.getNombre());

        // Mover jugador
        int tirada = tirarDado(actual);
        actual.mover(tirada);

        // Activar casilla
        partida.getTablero().activarCasilla(actual);

        // Comprobar ganador
        if (actual.getPosicion() >= partida.getTablero().getSize() - 1) {
            partida.setGanador(actual);
            partida.setFinalizada(true);
            System.out.println("Ganador: " + actual.getNombre());
            return;
        }

        // Siguiente turno
        partida.siguienteTurno();
    }

    // Obtener partida
    public Partida getPartida() {
        return partida;
    }

 // Guardar partida
    public void guardarPartida() {
        if (conn == null) { System.out.println("Sin conexión."); return; }
        String query = "INSERT INTO partidas (estado) VALUES ('EN_CURSO')";
        Gestor_BBDD.insertRow(conn, query);
        System.out.println("Partida guardada.");
    }

    // Cargar partida
    public void cargarPartida(int id) {
        if (conn == null) { System.out.println("Sin conexión."); return; }
        String query = "SELECT * FROM partidas WHERE id = " + id;
        ArrayList<LinkedHashMap<String, String>> rows = Gestor_BBDD.fetchRows(conn, query);
        if (!rows.isEmpty()) {
            System.out.println("Partida encontrada con id: " + id);
        } else {
            System.out.println("No se encontró la partida con id: " + id);
        }
    }
}

