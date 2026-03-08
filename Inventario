package reto;
import java.util.Random;
import java.util.ArrayList;
public class Inventario {
    private int peces;
    private int bolas;
    private ArrayList<Dado> dados;


    public Inventario() {
        peces = 0;
        bolas = 0;
        dados = new ArrayList<>();
        dados.add(new Dado(1, 6));
    }


    public int tirarDado() {
        return dados.get(0).tirar();
    }


    public void agregarDado(Dado d) {
        if (dados.size() < 3)
            dados.add(d);
        else
            System.out.println("Ya tienes el máximo de 3 dados.");
    }


    public int cantidadDados() {
        return dados.size();
    }


 public void agregarPez() {
        if (peces < 2)
            peces++;
        else
            System.out.println("Ya tienes el máximo de 2 peces.");
    }


    public boolean usarPez() {
        if (peces > 0) {
            peces--;
            return true;
        }
        return false;
    }


    public int getCantidadPeces() {
        return peces;
    }






    public void agregarBolas(int n) {
        bolas += n;
        if (bolas > 6) bolas = 6;
    }


    public boolean usarBola() {
        if (bolas > 0) {
            bolas--;
            return true;
        }
        return false;
    }


    public int getCantidadBolas() {
        return bolas;
    }


    public void perderTodasLasBolas() {
        bolas = 0;
    }




    public int totalObjetos() {
        return peces + bolas + dados.size();
    }


    public void perderMitadInventario() {
        peces = peces / 2;
        bolas = bolas / 2;
     
        while (dados.size() > Math.max(1, dados.size() / 2)) {
            dados.remove(dados.size() - 1);
        }
    }

public String perderObjetoAleatorio() {
        Random r = new Random();
        ArrayList<String> opciones = new ArrayList<>();


        if (peces > 0) opciones.add("pez");
        if (bolas > 0) opciones.add("bola");
        if (dados.size() > 1) opciones.add("dado");
        if (opciones.isEmpty()) return "nada";


        String elegido = opciones.get(r.nextInt(opciones.size()));
        switch (elegido) {
            case "pez":  peces--;  return "un pez";
            case "bola": bolas--;  return "una bola de nieve";
            case "dado":
                dados.remove(dados.size() - 1);
                return "un dado extra";
        }
        return "nada";
    }


    public void mostrar() {
        System.out.println(" Peces       : " + peces + " / 2");
        System.out.println(" Bolas nieve : " + bolas + " / 6");
        System.out.println(" Dados        : " + dados.size() + " / 3");
    }
}






