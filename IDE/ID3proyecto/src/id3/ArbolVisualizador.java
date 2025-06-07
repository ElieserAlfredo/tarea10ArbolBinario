package id3;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class ArbolVisualizador {

    private static int contadorNodos = 0;

    public static void generarArchivoDot(Nodo raiz, String archivoDot) {
        contadorNodos = 0; // reset contador para cada nuevo árbol
        try (FileWriter writer = new FileWriter(archivoDot)) {
            writer.write("digraph ID3 {\n");
            escribirNodo(writer, raiz);
            writer.write("}\n");
            System.out.println("Archivo .dot generado: " + archivoDot);
        } catch (IOException e) {
            System.err.println("Error al generar archivo .dot: " + e.getMessage());
        }
    }

    private static int escribirNodo(FileWriter writer, Nodo nodo) throws IOException {
        int idActual = contadorNodos++;
        if (nodo.esHoja()) {
            writer.write(String.format("n%d [label=\"Clase: %s\", shape=box];\n", idActual, nodo.clase));
        } else {
            writer.write(String.format("n%d [label=\"%s\"];\n", idActual, nodo.atributo));
            for (Map.Entry<String, Nodo> entrada : nodo.hijos.entrySet()) {
                int idHijo = escribirNodo(writer, entrada.getValue());
                writer.write(String.format("n%d -> n%d [label=\"%s\"];\n", idActual, idHijo, entrada.getKey()));
            }
        }
        return idActual;
    }
}
