package id3;

import java.util.*;

public class ID3Utils {

    public static double calcularEntropia(List<List<String>> datos) {
        Map<String, Integer> frecuencia = new HashMap<>();
        for (List<String> fila : datos) {
            String clase = fila.get(fila.size() - 1);
            frecuencia.put(clase, frecuencia.getOrDefault(clase, 0) + 1);
        }

        double entropia = 0.0;
        int total = datos.size();
        for (int f : frecuencia.values()) {
            double p = (double) f / total;
            entropia -= p * (Math.log(p) / Math.log(2));
        }
        return entropia;
    }

    public static double ganancia(List<List<String>> datos, int indiceAtributo) {
        double entropiaTotal = calcularEntropia(datos);
        Map<String, List<List<String>>> particiones = new HashMap<>();

        for (List<String> fila : datos) {
            String valor = fila.get(indiceAtributo);
            particiones.computeIfAbsent(valor, k -> new ArrayList<>()).add(fila);
        }

        double entropiaCondicional = 0.0;
        for (List<List<String>> particion : particiones.values()) {
            double peso = (double) particion.size() / datos.size();
            entropiaCondicional += peso * calcularEntropia(particion);
        }

        return entropiaTotal - entropiaCondicional;
    }
}
