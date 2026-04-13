package src.reto;
import java.util.Random;
public class Dado {
  private int min;
  private int max;
  public Dado(int min, int max) {
      this.min = min;
      this.max = max;
  }
  public int tirar() {
      Random r = new Random();
      return r.nextInt(max - min + 1) + min;
  }
}





