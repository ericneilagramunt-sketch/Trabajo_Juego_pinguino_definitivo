package reto;
import java.util.ArrayList;
public class partida {
    private Tablero             tablero;
    private ArrayList<jugador>  jugadores;
    private int                 turnos;
    private int                 jugadorActual; 
    private boolean             finalizada;
    private Jugador             ganador;

    public partida(tablero tablero, ArrayList<jugador> jugadores) {
        this.tablero        = tablero;
        this.jugadores      = jugadores;
        this.turnos         = 0;
        this.jugadorActual  = 0;
        this.finalizada     = false;
        this.ganador        = null;
    }

    public Jugador getJugadorActual() {
        return jugadores.get(jugadorActual);
    }

    public void siguienteTurno() {
        turnos++;
        jugadorActual++;
        if (jugadorActual >= jugadores.size()) jugadorActual = 0;
    }

    public Tablero getTablero()               
    { return tablero; }
    public void  setTablero(tablero t)     
     { this.tablero = t; }

    public ArrayList<jugador> getJugadores()            
     { return jugadores; }
    public void setJugadores(ArrayList<jugador> j)      
     { this.jugadores = j; }

    public int  getTurnos()                             
     { return turnos; }
    public void setTurnos(int t)                         
    { this.turnos = t; }

    public int  getIdxJugadorActual()                   
     { return jugadorActual; }
    public void setIdxJugadorActual(int i)              
     { this.jugadorActual = i; }

    public boolean isFinalizada()                       
     { return finalizada; }
    public void    setFinalizada(boolean f)             
     { this.finalizada = f; }

    public jugador getGanador()                         
     { return ganador; }
    public void    setGanador(jugador j)                 
    { this.ganador = j; }


    public ArrayList<Jugador> getJugadoresHumanos() {
        ArrayList<Jugador> humanos = new ArrayList<>();
        for (Jugador j : jugadores) {
            if (!(j instanceof foca)) humanos.add(j);
        }
        return humanos;
    }

    public ArrayList<Jugador> getTodosLosJugadores() {
        return jugadores;
    }
}

    
