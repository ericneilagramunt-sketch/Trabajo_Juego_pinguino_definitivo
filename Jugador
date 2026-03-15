package reto;

public abstract class Jugador {

    protected int posicion;
    protected String nombre;
    protected String color;
    protected Inventario inventario;
    protected boolean pierdeTurno;
    protected boolean pendienteOso; 

    public Jugador(String nombre, String color) {
        this.nombre       = nombre;
        this.color        = color;
        this.posicion     = 0;
        this.inventario   = new Inventario();
        this.pierdeTurno  = false;
        this.pendienteOso = false;
    }

    public void mover(int pasos) {
        posicion += pasos;
        if (posicion < 0) posicion = 0;
    }

    public void moverSesion(int p) {
        posicion = Math.max(0, p);
    }

    public int tirarDado() {
        return inventario.tirarDado();
    }

    public int    getPosicion()              
     { return posicion; }
    public void   setPosicion(int p)         
     { this.posicion = p; }
    public String getNombre()                
     { return nombre; }
    public void   setNombre(String n)        
     { this.nombre = n; }
    public String getColor()                  
    { return color; }
    public void   setColor(String c)         
     { this.color = c; }
    public Inventario getInventario()         
    { return inventario; }
    public void setInventario(Inventario inv) 
    { this.inventario = inv; }

    public boolean isPierdeTurno()            
     { return pierdeTurno; }
    public void    setPierdeTurno(boolean b)   
    { this.pierdeTurno = b; }
    public boolean isPendienteOso()            
    { return pendienteOso; }
    public void    setPendienteOso(boolean b) 
     { this.pendienteOso = b; }

    @Override
    public String toString() {
        return nombre + " [" + color + "] pos=" + posicion;
    }
}
