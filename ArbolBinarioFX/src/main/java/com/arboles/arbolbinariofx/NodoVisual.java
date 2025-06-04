package com.arboles.arbolbinariofx;

public class NodoVisual {
    private final int dato;
    private NodoVisual izquierda, derecha;

    public NodoVisual(int dato) {
        this.dato = dato;
        this.izquierda = null;
        this.derecha = null;
    }

    public int getDato() {
        return dato;
    }

    public NodoVisual getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(NodoVisual izquierda) {
        this.izquierda = izquierda;
    }

    public NodoVisual getDerecha() {
        return derecha;
    }

    public void setDerecha(NodoVisual derecha) {
        this.derecha = derecha;
    }

    public boolean esHoja() {
        return izquierda == null && derecha == null;
    }
}
