package src.reto;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
//Esta clase es vista
public class Pantalla_Partida {
    private Gestor_partida gestorPartidas;
    private JFrame        frame;
    private JPanel    panelTablero;
    private JPanel    panelJugadores;
    private JPanel    panelInventario;
    private JTextArea areaLog;
    private JButton btnTirarDado;
    private JButton btnUsarObjeto;
    private JButton btnLanzarBola;
    private JButton btnGuardar;
 public void iniciarPartida() {
        frame = new JFrame("Pinguinos en el Hielo - Partida");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(5, 5));

        construirBarraBotones();
        construirTablero();
        construirPanelLateral();
        construirLog();

        frame.setVisible(true);
        refrescarPartida();
    }
private void construirBarraBotones() {
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 7));
        barra.setBackground(new Color(20, 80, 130));

        btnTirarDado  = crearBoton("Tirar Dado");
        btnUsarObjeto = crearBoton("Usar Objeto");
        btnLanzarBola = crearBoton("Lanzar Bola de Nieve");
        btnGuardar    = crearBoton("Guardar Partida");

        btnTirarDado.addActionListener(e  -> botonTirarDado());
        btnUsarObjeto.addActionListener(e -> botonUsarObjeto());
        btnLanzarBola.addActionListener(e -> botonLanzarBola());
        btnGuardar.addActionListener(e    -> guardarPartida());

        barra.add(btnTirarDado);
        barra.add(btnUsarObjeto);
        barra.add(btnLanzarBola);
        barra.add(btnGuardar);
        frame.add(barra, BorderLayout.NORTH);
    }

    private void construirTablero() {
        panelTablero = new JPanel(new GridLayout(5, 10, 2, 2));
        panelTablero.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Tablero (50 casillas)"));
        panelTablero.setPreferredSize(new Dimension(700, 380));
        frame.add(panelTablero, BorderLayout.CENTER);
    }

    private void construirPanelLateral() {
        JPanel lateral = new JPanel(new BorderLayout(0, 8));
        lateral.setPreferredSize(new Dimension(240, 0));
        lateral.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 8));

        panelJugadores = new JPanel();
        panelJugadores.setLayout(new BoxLayout(panelJugadores, BoxLayout.Y_AXIS));
        panelJugadores.setBorder(BorderFactory.createTitledBorder("Jugadores"));

        panelInventario = new JPanel();
        panelInventario.setLayout(new BoxLayout(panelInventario, BoxLayout.Y_AXIS));
        panelInventario.setBorder(BorderFactory.createTitledBorder("Inventario (turno actual)"));

        lateral.add(new JScrollPane(panelJugadores), BorderLayout.CENTER);
        lateral.add(panelInventario, BorderLayout.SOUTH);
        frame.add(lateral, BorderLayout.EAST);
    }

    private void construirLog() {
        areaLog = new JTextArea(5, 50);
        areaLog.setEditable(false);
        areaLog.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane scroll = new JScrollPane(areaLog);
        scroll.setBorder(BorderFactory.createTitledBorder("Registro de eventos"));
        frame.add(scroll, BorderLayout.SOUTH);
    }

    private void mostrarTablero() {
        panelTablero.removeAll();
        partida p = gestorPartidas.getPartida();

        for (int i = 0; i < 50; i++) {
            casilla c   = p.getTablero().getCasilla(i);
            JPanel celda = new JPanel(new BorderLayout());
            celda.setBackground(colorCasilla(c));
            celda.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
            celda.setToolTipText(c.getClass().getSimpleName() + " [" + i + "]");
 JLabel numLbl = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            numLbl.setFont(new Font("Arial", Font.PLAIN, 8));
            numLbl.setForeground(Color.DARK_GRAY);
            celda.add(numLbl, BorderLayout.SOUTH);
JLabel tipoLbl = new JLabel(simboloCasilla(c), SwingConstants.CENTER);
            tipoLbl.setFont(new Font("Arial", Font.BOLD, 10));
            celda.add(tipoLbl, BorderLayout.NORTH);
StringBuilder fichas = new StringBuilder();
            for (jugador j : p.getTodosLosJugadores()) {
                if (j.getPosicion() == i) {
                    fichas.append(j.getNombre().substring(0, 1));
                }
            }
            if (fichas.length() > 0) {
                JLabel fichaLbl = new JLabel(fichas.toString(), SwingConstants.CENTER);
                fichaLbl.setFont(new Font("Arial", Font.BOLD, 12));
                fichaLbl.setForeground(Color.BLACK);
                celda.add(fichaLbl, BorderLayout.CENTER);
            }

            panelTablero.add(celda);
        }
        panelTablero.revalidate();
        panelTablero.repaint();
    }
private Color colorCasilla(casilla c) {
        if (c instanceof oso)            return new Color(255, 180, 80);   // naranja
        if (c instanceof agujero)        return new Color(80,  80,  80);   // gris oscuro
        if (c instanceof trineo)         return new Color(100, 200, 255);  // azul claro
        if (c instanceof evento)         return new Color(255, 255, 100);  // amarillo
        if (c instanceof sueloquebradizo)return new Color(180, 120, 60);   // marrón
        return new Color(210, 240, 210);                                   // verde (normal)
    }

    private String simboloCasilla(casilla c) {
        if (c instanceof oso)             return "O";
        if (c instanceof agujero)         return "A";
        if (c instanceof trineo)          return "T";
        if (c instanceof evento)          return "?";
        if (c instanceof sueloquebradizo) return "S";
        return "·";
    }
private void mostrarEstadoJugadores() {
        panelJugadores.removeAll();
        partida p      = gestorPartidas.getPartida();
        jugador actual = p.getJugadorActual();

        for (jugador j : p.getTodosLosJugadores()) {
            boolean esTurno = (j == actual);
            String html = "<html>"
                    + (esTurno ? "<b>&#9654; " : "")
                    + j.getNombre() + "</b> [" + j.getColor() + "]<br>"
                    + "Casilla: " + j.getPosicion()
                    + (j.isPierdeTurno() ? " (pierde turno)" : "")
                    + (j instanceof foca && ((foca) j).estaBloqueada()
                        ? " (bloq. " + ((foca) j).getTurnosBloqueada() + ")" : "")
                    + "</html>";
            JLabel lbl = new JLabel(html);
            lbl.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
            if (esTurno) lbl.setForeground(new Color(0, 120, 0));
            panelJugadores.add(lbl);
            panelJugadores.add(new JSeparator());
        }
        panelJugadores.revalidate();
        panelJugadores.repaint();
    }

    private void mostrarInventario() {
        panelInventario.removeAll();
        jugador actual = gestorPartidas.getPartida().getJugadorActual();
        if (actual == null) return;
        inventario inv = actual.getInventario();

        panelInventario.add(new JLabel("Peces:  " + inv.getCantidadPeces()
                + " / " + inventario.MAX_PECES));
        panelInventario.add(new JLabel("Bolas:  " + inv.getCantidadBolas()
                + " / " + inventario.MAX_BOLAS));
        panelInventario.add(new JLabel("Dados:  " + inv.cantidadDados()
                + " / " + inventario.MAX_DADOS));
        for (dado d : inv.getDados()) {
            panelInventario.add(new JLabel("   -> " + d));
        }
        panelInventario.revalidate();
        panelInventario.repaint();
    }
 public void botonTirarDado() {
        partida p      = gestorPartidas.getPartida();
        jugador actual = p.getJugadorActual();
if (actual instanceof foca) {
            gestorPartidas.getFocaCPU().turnoIA(
                    p.getJugadoresHumanos(), p.getTablero());
            log("Turno de la FOCA (CPU) ejecutado automaticamente.");
            gestorPartidas.siguienteTurno();
            refrescarPartida();
            return;
        }

        if (actual.isPierdeTurno()) {
            actual.setPierdeTurno(false);
            log(actual.getNombre() + " pierde su turno.");
            gestorPartidas.siguienteTurno();
            refrescarPartida();
            return;
        }
int indiceDado = 0;
        ArrayList<dado> dados = actual.getInventario().getDados();
        if (dados.size() > 1) {
            String[] opts = new String[dados.size()];
            for (int i = 0; i < dados.size(); i++) opts[i] = dados.get(i).toString();
            String sel = (String) JOptionPane.showInputDialog(frame,
                    "Elige dado:", "Dados disponibles",
                    JOptionPane.QUESTION_MESSAGE, null, opts, opts[0]);
            if (sel != null) {
                for (int i = 0; i < opts.length; i++) {
                    if (opts[i].equals(sel)) { indiceDado = i; break; }
                }
            }
        }
        deshabilitarBotones(true);
        gestorPartidas.ejecutarTurnoCompleto(actual, indiceDado);
        log(actual.getNombre() + " tira el dado → casilla " + actual.getPosicion()
                + " (" + p.getTablero().getTipoCasilla(actual.getPosicion()) + ")");
f (actual.isPendienteOso()) {
            actual.setPendienteOso(false);
            int r = JOptionPane.showConfirmDialog(frame,
                    "Un oso te ataca! Tienes " + actual.getInventario().getCantidadPeces()
                    + " pez/peces. Quieres sobornarle?",
                    "Oso!", JOptionPane.YES_NO_OPTION);
            if (r == JOptionPane.YES_OPTION && actual.getInventario().usarPez()) {
                log("Has sobornado al oso con un pez.");
            } else {
                actual.setPosicion(0);
                log("El oso te devuelve al inicio!");
            }
        }
foca focaCPU = gestorPartidas.getFocaCPU();
        if (focaCPU != null && focaCPU.getPosicion() == actual.getPosicion()
                && actual instanceof pinguino) {
            gestionarFocaGUI(actual, focaCPU);
        }

        gestorPartidas.siguienteTurno();
        refrescarPartida();
        deshabilitarBotones(false);

        if (gestorPartidas.getPartida().isFinalizada()) mostrarVictoria();
    }

    public void botonUsarObjeto() {
        jugador actual = gestorPartidas.getPartida().getJugadorActual();
        ArrayList<item> items = actual.getInventario().getLista();
        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No tienes objetos en el inventario.");
            return;
        }
        String[] opts = items.stream().map(Object::toString).toArray(String[]::new);
        String sel = (String) JOptionPane.showInputDialog(frame,
                "Elige objeto:", "Inventario",
                JOptionPane.QUESTION_MESSAGE, null, opts, opts[0]);
        if (sel == null) return;
        for (item it : items) {
            if (it.toString().equals(sel)) {
                it.usar(actual, gestorPartidas);
                log(actual.getNombre() + " usa: " + it.getNombre());
                refrescarPartida();
                return;
            }
        }
    }

    public void botonFinalizarTurno() {
        gestorPartidas.siguienteTurno();
        refrescarPartida();
    }

    public void botonLanzarBola() {
        jugador actual = gestorPartidas.getPartida().getJugadorActual();
        if (actual.getInventario().getCantidadBolas() == 0) {
            JOptionPane.showMessageDialog(frame, "No tienes bolas de nieve.");
            return;
        }
 ArrayList<jugador> objetivos = new ArrayList<>();
        for (jugador j : gestorPartidas.getPartida().getTodosLosJugadores()) {
            if (j != actual) objetivos.add(j);
        }
        if (objetivos.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No hay jugadores a los que lanzar.");
            return;
        }
        String[] opts = objetivos.stream()
                .map(j -> j.getNombre() + " (casilla " + j.getPosicion() + ")")
                .toArray(String[]::new);
        String sel = (String) JOptionPane.showInputDialog(frame,
                "A quien lanzas la bola?", "Lanzar bola de nieve",
                JOptionPane.QUESTION_MESSAGE, null, opts, opts[0]);
        if (sel == null) return;
        for (int i = 0; i < opts.length; i++) {
            if (opts[i].equals(sel)) {
                jugador objetivo = objetivos.get(i);
                actual.getInventario().usarBola();
                objetivo.mover(-2);
                log(actual.getNombre() + " lanza una bola a " + objetivo.getNombre()
                        + " → casilla " + objetivo.getPosicion());
                refrescarPartida();
                return;
            }
        }
    }
 public void guardarPartida() {
        gestorPartidas.guardarPartida();
        JOptionPane.showMessageDialog(frame, "Partida guardada correctamente.");
        log("Partida guardada.");
    }

    public void cargarPartida() {
        String input = JOptionPane.showInputDialog(frame, "ID de la partida a cargar:");
        if (input == null || input.trim().isEmpty()) return;
        try {
            gestorPartidas.cargarPartida(Integer.parseInt(input.trim()));
            refrescarPartida();
            log("Partida cargada.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "ID invalido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 public void refrescarPartida() {
        mostrarTablero();
        mostrarEstadoJugadores();
        mostrarInventario();
    }

    public void mostrarVictoria() {
        deshabilitarBotones(true);
        JOptionPane.showMessageDialog(frame,
                "GANADOR: " + gestorPartidas.getPartida().getGanador().getNombre() + "!",
                "Fin de partida", JOptionPane.INFORMATION_MESSAGE);
        int op = JOptionPane.showConfirmDialog(frame,
                "Volver al menu principal?", "Fin", JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.YES_OPTION) {
            frame.dispose();
            new PantallaMenu().menu();
        }
    }
 private void gestionarFocaGUI(jugador actual, foca focaCPU) {
        if (actual.getInventario().getCantidadPeces() > 0) {
            int r = JOptionPane.showConfirmDialog(frame,
                    "La Foca esta en tu casilla!\nTienes "
                    + actual.getInventario().getCantidadPeces()
                    + " pez/peces. La alimentas para bloquearla 2 turnos?",
                    "Foca!", JOptionPane.YES_NO_OPTION);
            if (r == JOptionPane.YES_OPTION) {
                actual.getInventario().usarPez();
                focaCPU.bloquear();
                log(actual.getNombre() + " alimenta a la foca. Bloqueada 2 turnos.");
            } else {
                enviarAAgujero(actual, focaCPU);
            }
        } else {
            JOptionPane.showMessageDialog(frame,
                    "La Foca esta en tu casilla y no tienes peces. Te golpea!");
            enviarAAgujero(actual, focaCPU);
        }
    }

    private void enviarAAgujero(jugador actual, foca focaCPU) {
        int ag = gestorPartidas.getPartida().getTablero().buscarAgujeroAnterior(actual.getPosicion());
        actual.setPosicion(ag);
        log(actual.getNombre() + " golpeado por la foca → casilla " + ag);
    }

    private void log(String msg) {
        areaLog.append(msg + "\n");
        areaLog.setCaretPosition(areaLog.getDocument().getLength());
    }

    private void deshabilitarBotones(boolean deshabilitar) {
        btnTirarDado.setEnabled(!deshabilitar);
        btnUsarObjeto.setEnabled(!deshabilitar);
        btnLanzarBola.setEnabled(!deshabilitar);
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setBackground(new Color(0, 120, 200));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(7, 16, 7, 16));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
