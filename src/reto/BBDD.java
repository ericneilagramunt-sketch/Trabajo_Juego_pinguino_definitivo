package reto;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;
// Esta clase es controladora
public class Gestor_BBDD {
	public static Connection conectarBaseDatos(Scanner scan) {
		System.out.println("Intentando conectarse a la base de datos...");

		// 1) Elegir entorno
		String entorno = "";
		boolean valido = false;
		while (!valido) {
			System.out.println("Selecciona centro o fuera de centro (CENTRO/FUERA):");
			entorno = scan.nextLine().trim().toLowerCase();

			if (entorno.equalsIgnoreCase("centro") || entorno.equalsIgnoreCase("fuera")) {
				valido = true;
			} else {
				System.out.println("Entrada no válida. Escribe CENTRO o FUERA.");
			}
		}

		String url = entorno.equals("centro") 
				? "jdbc:oracle:thin:@//192.168.3.26:1521/XEPDB2"
				: "jdbc:oracle:thin:@//oracle.ilerna.com:1521/XEPDB2";

		
		String user = "DM1_2526_GRUP07";
		String pwd = "AGRUP07";

		System.out.println("Conectando automáticamente con el usuario: " + user);

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			Connection con = DriverManager.getConnection(url, user, pwd);

			if (con.isValid(5)) {
				System.out.println("Conectados a la base de datos (" + entorno.toUpperCase() + ").");
			} else {
				System.out.println("Conexión creada, pero no parece válida.");
			}

			return con;

		} catch (ClassNotFoundException e) {
			System.out.println("No se ha encontrado el driver de Oracle. Añade ojdbc.");
		} catch (SQLException e) {
			System.out.println("No se pudo conectar.");
			System.out.println("Detalle: " + e.getMessage());
		}

		return null;
	}

	public static void cerrar(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException ignored) {
			}
		}
	}

	public static int insert(Connection con, String sql) {
		return executeInsUpDel(con, sql, "Insert");
	}

	public static int update(Connection con, String sql) {
		return executeInsUpDel(con, sql, "Update");
	}

	public static int delete(Connection con, String sql) {
		return executeInsUpDel(con, sql, "Delete");
	}

	public static ArrayList<LinkedHashMap<String, String>> select(Connection con, String sql) {

		ArrayList<LinkedHashMap<String, String>> resultados = new ArrayList<>();

		if (con == null) {
			System.out.println("No hay conexión. Llama antes a conectarBaseDatos().");
			return resultados;
		}

		try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

			ResultSetMetaData meta = rs.getMetaData();
			int numColumnas = meta.getColumnCount();

			while (rs.next()) {
				LinkedHashMap<String, String> fila = new LinkedHashMap<>();

				for (int i = 1; i <= numColumnas; i++) {
					String columna = meta.getColumnLabel(i);
					String valor = rs.getString(i);
					fila.put(columna, valor);
				}

				resultados.add(fila);
			}

		} catch (SQLException e) {
			System.out.println("Error en SELECT: " + e.getMessage());
		}

		return resultados;
	}

	public static void print(Connection con, String sql, String[] listaElementosSeleccionados) {
		if (con == null) {
			System.out.println("No hay conexión. Llama antes a conectarBaseDatos().");
			return;
		}

		try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

			int fila = 0;
			boolean hayResultados = false;

			while (rs.next()) {
				hayResultados = true;
				fila++;
				System.out.println("---- Fila " + fila + " ----");
				for (String col : listaElementosSeleccionados) {
					System.out.println(col + ": " + rs.getString(col));
				}
			}

			if (!hayResultados) {
				System.out.println("No se ha encontrado nada");
			}

		} catch (SQLException e) {
			System.out.println("Error en SELECT: " + e.getMessage());
		}
	}

	public static int executeInsUpDel(Connection con, String sql, String etiqueta) {
		if (con == null) {
			System.out.println("No hay conexión. Llama antes a conectarBaseDatos().");
			return 0;
		}

		try (Statement st = con.createStatement()) {
			int filas = st.executeUpdate(sql);
			System.out.println(etiqueta + " hecho correctamente. Filas afectadas: " + filas);
			return filas;
		} catch (SQLException e) {
			System.out.println("Ha habido un error en " + etiqueta + ": " + e.getMessage());
			return 0;
		}
	}
}
