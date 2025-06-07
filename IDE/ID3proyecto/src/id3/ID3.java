package id3;

import java.util.*;

public class ID3 {

    public static Nodo construirArbol(List<Map<String, String>> ejemplos,
                                      List<String> atributos,
                                      String atributoObjetivo) {
        // Caso base: si todos los ejemplos tienen la misma clase
        if (todosMismoValor(ejemplos, atributoObjetivo)) {
            String clase = ejemplos.get(0).get(atributoObjetivo);
            return new Nodo(null, clase);
        }

        // Caso base: si no hay más atributos, devuelve el valor más común
        if (atributos.isEmpty()) {
            String clase = valorMasComun(ejemplos, atributoObjetivo);
            return new Nodo(null, clase);
        }

        // Seleccionar el mejor atributo (simplificado: el primero)
        String mejorAtributo = seleccionarMejorAtributo(ejemplos, atributos, atributoObjetivo);

        Nodo raiz = new Nodo(mejorAtributo);

        // Obtener valores únicos del atributo mejor
        Set<String> valores = obtenerValoresUnicos(ejemplos, mejorAtributo);

        for (String valor : valores) {
            // Filtrar ejemplos donde atributo=valor
            List<Map<String, String>> subset = filtrarPorValor(ejemplos, mejorAtributo, valor);

            if (subset.isEmpty()) {
                String clase = valorMasComun(ejemplos, atributoObjetivo);
                raiz.hijos.put(valor, new Nodo(null, clase));
            } else {
                List<String> nuevosAtributos = new ArrayList<>(atributos);
                nuevosAtributos.remove(mejorAtributo);
                Nodo hijo = construirArbol(subset, nuevosAtributos, atributoObjetivo);
                raiz.hijos.put(valor, hijo);
            }
        }
        return raiz;
    }

    // Métodos auxiliares (implementados simplificados):

    private static boolean todosMismoValor(List<Map<String, String>> ejemplos, String atributoObjetivo) {
        String primerValor = ejemplos.get(0).get(atributoObjetivo);
        for (Map<String, String> ej : ejemplos) {
            if (!ej.get(atributoObjetivo).equals(primerValor))
                return false;
        }
        return true;
    }

    private static String valorMasComun(List<Map<String, String>> ejemplos, String atributoObjetivo) {
        Map<String, Integer> conteo = new HashMap<>();
        for (Map<String, String> ej : ejemplos) {
            String val = ej.get(atributoObjetivo);
            conteo.put(val, conteo.getOrDefault(val, 0) + 1);
        }
        return Collections.max(conteo.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private static Set<String> obtenerValoresUnicos(List<Map<String, String>> ejemplos, String atributo) {
        Set<String> valores = new HashSet<>();
        for (Map<String, String> ej : ejemplos) {
            valores.add(ej.get(atributo));
        }
        return valores;
    }

    private static List<Map<String, String>> filtrarPorValor(List<Map<String, String>> ejemplos, String atributo, String valor) {
        List<Map<String, String>> subset = new ArrayList<>();
        for (Map<String, String> ej : ejemplos) {
            if (ej.get(atributo).equals(valor)) {
                subset.add(ej);
            }
        }
        return subset;
    }

    // Selección simple: elige el primer atributo (para mejorar, implementa cálculo de ganancia de información)
    private static String seleccionarMejorAtributo(List<Map<String, String>> ejemplos, List<String> atributos, String atributoObjetivo) {
        return atributos.get(0);
    }
}
