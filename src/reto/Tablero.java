package src.reto;

import java.util.ArrayList;
import java.util.Random;


public class Tablero {

    private ArrayList<Casilla> casillas;

    public Tablero(int size) {
        casillas = new ArrayList<>();
        Random r = new Random();

        casillas.add(new normal(0));

        for (int i = 1; i < size - 1; i++) {
            int tipo = r.nextInt(6);
            switch (tipo) {
                case 0:  casillas.add(new Oso(i));               break;
                case 1:  casillas.add(new Agujero(i));           break;
                case 2:  casillas.add(new Trineo(i));            break;
                case 3:  casillas.add(new Evento(i));            break;
                case 4:  casillas.add(new SueloQuebradizo(i));   break;
                default: casillas.add(new Normal(i));            break;
            }
        }

        casillas.add(new Normal(size - 1));

        for (Casilla c : casillas) {
            if (c instanceof Trineo)       ((Trineo)       c).setTablero(this);
            if (c instanceof Agujero)      ((Agujero)      c).setTablero(this);
            if (c instanceof Evento)       ((Evento)       c).setTablero(this);
        }
    }


    public void activarCasilla(Jugador j) {
        int pos = j.getPosicion();
        if (pos >= 0 && pos < casillas.size()) {
            casillas.get(pos).realizarAccion(j);
        }
    }


    public int getSize() { return casillas.size(); }

    public casilla getCasilla(int pos) {
        if (pos >= 0 && pos < casillas.size()) return casillas.get(pos);
        return null;
    }

    public String getTipoCasilla(int pos) {
        if (pos < 0 || pos >= casillas.size()) return "fuera";
        return casillas.get(pos).getClass().getSimpleName();
    }

    public int buscarProximoTrineo(int posActual) {
        for (int i = posActual + 1; i < casillas.size(); i++) {
            if (casillas.get(i) instanceof trineo) return i;
        }
        return -1;
    }

    public int buscarAgujeroAnterior(int posActual) {
        for (int i = posActual - 1; i >= 0; i--) {
            if (casillas.get(i) instanceof agujero) return i;
        }
        return 0;
    }

    
    public ArrayList<Integer> posicionesIntermedias(int desde, int hasta) {
        ArrayList<Integer> pos = new ArrayList<>();
        int ini = Math.min(desde, hasta);
        int fin = Math.max(desde, hasta);
        for (int i = ini + 1; i <= fin; i++) pos.add(i);
        return pos;
    }

    public void mostrarTablero(ArrayList<jugador> jugadores) {
        System.out.println("\n=== TABLERO (50 casillas) ===");
        System.out.println("N=Normal  O=Oso  A=Agujero  T=Trineo  E=Evento  S=Suelo");
        for (int i = 0; i < casillas.size(); i++) {
            char simbolo = obtenerSimbolo(casillas.get(i).getClass().getSimpleName());
            StringBuilder fichas = new StringBuilder();
            for (jugador j : jugadores) {
                if (j.getPosicion() == i)
                    fichas.append("[").append(j.getNombre().charAt(0)).append("]");
            }
            System.out.printf("%2d:%c%s ", i, simbolo, fichas.toString());
            if ((i + 1) % 10 == 0) System.out.println();
        }
        System.out.println("\n============================");
    }

    private char obtenerSimbolo(String tipo) {
        switch (tipo) {
            case "oso":            return 'O';
            case "agujero":        return 'A';
            case "trineo":         return 'T';
            case "evento":         return 'E';
            case "sueloquebradizo":return 'S';
            default:               return 'N';
        }
    }
}
