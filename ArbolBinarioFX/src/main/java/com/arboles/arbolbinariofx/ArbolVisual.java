package com.arboles.arbolbinariofx;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ArbolVisual {
    private NodoVisual raiz;
    private Canvas canvas;
    private final double nodeRadius;
    private final double verticalSeparation;
    private final List<Integer> nodosPorNivel;
    private int hojas;

    public ArbolVisual(double nodeRadius, double verticalSeparation) {
        this.nodeRadius = nodeRadius;
        this.verticalSeparation = verticalSeparation;
        raiz = null;
        nodosPorNivel = new ArrayList<>();
        hojas = 0;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public void insertar(int dato) {
        if (raiz == null) {
            raiz = new NodoVisual(dato);
            return;
        }

        Queue<NodoVisual> cola = new LinkedList<>();
        cola.offer(raiz);

        while (!cola.isEmpty()) {
            NodoVisual temp = cola.poll();

            if (dato <= temp.getDato()) {
                if (temp.getIzquierda() == null) {
                    temp.setIzquierda(new NodoVisual(dato));
                    return;
                } else {
                    cola.offer(temp.getIzquierda());
                }
            } else {
                if (temp.getDerecha() == null) {
                    temp.setDerecha(new NodoVisual(dato));
                    return;
                } else {
                    cola.offer(temp.getDerecha());
                }
            }
        }
    }

    public void dibujarArbol(double sceneWidth) {
        if (canvas != null) {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            double scaleFactor = sceneWidth / canvas.getWidth();
            if (raiz != null) {
                calcularNodosPorNivel();
                dibujarNodo(raiz, canvas.getWidth() / 2, 50, canvas.getWidth() / 4 * scaleFactor, gc, scaleFactor);
                mostrarSumatoriaNodosPorNivel(gc);
                mostrarAltura(gc, raiz);
                mostrarHojas(gc, raiz);
            }
        }
    }

    private void calcularNodosPorNivel() {
        nodosPorNivel.clear();
        calcularNodosPorNivel(raiz, 0);
    }

    private void calcularNodosPorNivel(NodoVisual nodo, int nivel) {
        if (nodo != null) {
            if (nivel >= nodosPorNivel.size()) {
                nodosPorNivel.add(1);
            } else {
                nodosPorNivel.set(nivel, nodosPorNivel.get(nivel) + 1);
            }
            calcularNodosPorNivel(nodo.getIzquierda(), nivel + 1);
            calcularNodosPorNivel(nodo.getDerecha(), nivel + 1);
        }
    }

    private void dibujarNodo(NodoVisual nodo, double x, double y, double offsetX, GraphicsContext gc, double scaleFactor) {
        if (nodo != null) {
            if (nodo.getIzquierda() != null) {
                double nextX = x - offsetX;
                double nextY = y + verticalSeparation * scaleFactor;
                gc.setStroke(Color.BLACK);
                gc.strokeLine(x, y, nextX, nextY);
                dibujarNodo(nodo.getIzquierda(), nextX, nextY, offsetX / 2, gc, scaleFactor);
            }
            if (nodo.getDerecha() != null) {
                double nextX = x + offsetX;
                double nextY = y + verticalSeparation * scaleFactor;
                gc.setStroke(Color.BLACK);
                gc.strokeLine(x, y, nextX, nextY);
                dibujarNodo(nodo.getDerecha(), nextX, nextY, offsetX / 2, gc, scaleFactor);
            }
            double radius = nodeRadius * scaleFactor;
            gc.setFill(Color.WHITE);
            gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
            gc.setStroke(Color.BLACK);
            gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2);
            gc.setFill(Color.BLACK);
            gc.fillText(Integer.toString(nodo.getDato()), x - 5 * scaleFactor, y + 5 * scaleFactor);
        }
    }

    private void mostrarSumatoriaNodosPorNivel(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillText("Sumatoria de nodos por nivel:", 20, 20);
        int y = 40;
        for (int i = 0; i < nodosPorNivel.size(); i++) {
            gc.fillText("Nivel " + i + ": " + nodosPorNivel.get(i), 20, y);
            y += 20;
        }
    }

    private void mostrarAltura(GraphicsContext gc, NodoVisual nodo) {
        gc.setFill(Color.BLACK);
        gc.fillText("Altura del árbol: " + calcularAltura(nodo), 20, canvas.getHeight() - 40);
    }

    private int calcularAltura(NodoVisual nodo) {
        if (nodo == null)
            return 0;
        else {
            int alturaIzquierda = calcularAltura(nodo.getIzquierda());
            int alturaDerecha = calcularAltura(nodo.getDerecha());
            return Math.max(alturaIzquierda, alturaDerecha) + 1;
        }
    }

    private void mostrarHojas(GraphicsContext gc, NodoVisual nodo) {
        hojas = 0;
        calcularHojas(nodo);
        gc.setFill(Color.BLACK);
        gc.fillText("Número de hojas: " + hojas, 20, canvas.getHeight() - 20);
    }

    private void calcularHojas(NodoVisual nodo) {
        if (nodo == null)
            return;
        if (nodo.getIzquierda() == null && nodo.getDerecha() == null) {
            hojas++;
            return;
        }
        calcularHojas(nodo.getIzquierda());
        calcularHojas(nodo.getDerecha());
    }

    public List<Integer> getNodosPorNivel() {
        return nodosPorNivel;
    }
}
