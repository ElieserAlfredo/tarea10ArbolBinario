package id3;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("¿Cuántos atributos?: ");
        int nAtributos = Integer.parseInt(scanner.nextLine());

        List<String> atributos = new ArrayList<>();
        for (int i = 0; i < nAtributos; i++) {
            System.out.print("Nombre del atributo " + (i + 1) + ": ");
            atributos.add(scanner.nextLine());
        }

        System.out.print("¿Cuántos ejemplos?: ");
        int nEjemplos = Integer.parseInt(scanner.nextLine());

        List<Map<String, String>> ejemplos = new ArrayList<>();

        for (int i = 0; i < nEjemplos; i++) {
            System.out.println("Ejemplo " + (i + 1) + ":");
            Map<String, String> ejemplo = new HashMap<>();
            for (String attr : atributos) {
                System.out.print(attr + ": ");
                ejemplo.put(attr, scanner.nextLine());
            }
            System.out.print("Clase: ");
            ejemplo.put("Clase", scanner.nextLine());
            ejemplos.add(ejemplo);
        }

        // Construir árbol
        Nodo arbol = ID3.construirArbol(ejemplos, atributos, "Clase");

        // Mostrar el árbol generado como archivo .dot
        ArbolVisualizador.generarArchivoDot(arbol, "arbol.dot");

        System.out.println("¡Árbol generado y archivo 'arbol.dot' creado!");
        System.out.println("Usa Graphviz para generar la imagen: dot -Tpng arbol.dot -o arbol.png");
    }
}
