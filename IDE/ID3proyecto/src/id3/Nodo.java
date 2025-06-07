package id3;

import java.util.HashMap;
import java.util.Map;

public class Nodo {
    public String atributo;               // Atributo para dividir (null si es hoja)
    public Map<String, Nodo> hijos;      // Hijos: valor atributo -> nodo hijo
    public String clase;                  // Clase si es hoja (null si no)

    // Constructor para nodo hoja con clase
    public Nodo(String atributo, String clase) {
        this.atributo = atributo;
        this.clase = clase;
        this.hijos = new HashMap<>();
    }

    // Constructor para nodo no hoja (atributo y sin clase)
    public Nodo(String atributo) {
        this.atributo = atributo;
        this.clase = null;
        this.hijos = new HashMap<>();
    }

    // Constructor vacío (opcional)
    public Nodo() {
        this.atributo = null;
        this.clase = null;
        this.hijos = new HashMap<>();
    }

    public boolean esHoja() {
        return clase != null;
    }
}
