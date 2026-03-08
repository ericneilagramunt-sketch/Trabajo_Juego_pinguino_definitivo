package reto;
import java.util.*;
import java.util.Random;
import java.util.ArrayList;
public class Tablero {
  private ArrayList<Casilla> casillas;
  public Tablero(int size) {
      casillas = new ArrayList<>();
      Random r = new Random();
      for (int i = 0; i < size; i++) {
          int tipo = r.nextInt(5);
          switch (tipo) {
              case 0: casillas.add(new oso(i)); break;
              case 1: casillas.add(new agujero(i)); break;
              case 2: casillas.add(new trineo(i)); break;
              case 3: casillas.add(new evento(i)); break;
              default: casillas.add(new suelo_quebradizo(i)); break;
          }
      }
  }
  public void activarCasilla(jugador j) {
      casillas.get(j.getPosicion()).realizarAccion(j);
  }
  public int getSize() {
      return casillas.size();
  }
}

