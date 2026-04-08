package reto;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class GestorBBDD {

    private Connection con;

    
    public GestorBBDD(Connection con) {
        this.con = con;
    }

   
    public void guardarBBDD(Partida p) {

        String sql = "INSERT INTO PARTIDAS (ID, NOMBRE, PUNTUACION) VALUES (" +
                p.getId() + ", '" +
                p.getNombre() + "', " +
                p.getPuntuacion() + ")";

        Gestor_BBDD.insert(con, sql);
    }

    
    public Partida cargarBBDD(int id) {

        String sql = "SELECT * FROM PARTIDAS WHERE ID = " + id;

        ArrayList<LinkedHashMap<String, String>> resultados =
                Gestor_BBDD.select(con, sql);

        if (resultados.isEmpty()) {
            System.out.println("No se ha encontrado la partida.");
            return null;
        }

        LinkedHashMap<String, String> fila = resultados.get(0);

        int idPartida = Integer.parseInt(fila.get("ID"));
        String nombre = fila.get("NOMBRE");
        int puntuacion = Integer.parseInt(fila.get("PUNTUACION"));

        return new Partida(idPartida, nombre, puntuacion);
    }
}
