package logica_lab;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author tuori
 */
public class Tree {

    //Atributos
    public Node raiz;
    public Node nodoGanador;
    public Random r = new Random();

    // Agregar un nodo
    public void addNode(Pregunta pregunta) {
        Node nuevoNodo = new Node(pregunta);
        if (this.raiz == null) {
            this.raiz = nuevoNodo;
        } else {
            this.buscarYAgregar(this.raiz, nuevoNodo);
        }
    }
    public ArrayList<Integer> buscarHojas(Node nodo) {
        ArrayList<Integer> hojas = new ArrayList<>();
        encontrarHojas(nodo, hojas);
        return hojas;
    }
    private void encontrarHojas(Node nodo, ArrayList<Integer> hojas) {
        if (nodo == null) {
            return;
        }
        if (nodo.left == null && nodo.right == null) {
            hojas.add(nodo.pregunta.getId());
        }
        // Llamada recursiva para el hijo izquierdo y derecho
        encontrarHojas(nodo.left, hojas);
        encontrarHojas(nodo.right, hojas);
    }
    
    public void addNodeGanador(Node node) {
        if (node!= null) {
            if (node.left != null & node.right!= null) {
                int rr = r.nextInt(2);
                if (rr == 0)addNodeGanador(node.left);
                else addNodeGanador(node.right);
            }
            else{
                if (node.left==null & node.right==null){
                    node.Ganador=true;
                    this.nodoGanador = node;
                }
                else if (node.left!= null)addNodeGanador(node.left);
                else addNodeGanador(node.right);
            }
        }
    }

// Metodo para encontrar y agregar un nodo en el árbol ordenado por longitud de la pregunta
    public void buscarYAgregar(Node nodoActual, Node nodoNuevo) {
        if (nodoActual.getPregunta().getId() > nodoNuevo.getPregunta().getId()) {
            if (nodoActual.left == null) {
                nodoActual.left = nodoNuevo;
            } else {
                buscarYAgregar(nodoActual.left, nodoNuevo);
            }
        } else {
            if (nodoActual.right == null) {
                nodoActual.right = nodoNuevo;
            } else {
                buscarYAgregar(nodoActual.right, nodoNuevo);
            }
        }
    }

    public Node mover(Node nodoActual, String direccion) {
        if (nodoActual == null) {
            System.out.println("Nodo actual es nulo.");
            return null;
        }
        switch (direccion) {
            case "izquierda":
                return nodoActual.left;
            case "derecha":
                return nodoActual.right;
            default:
                System.out.println("Dirección no válida. Use 'izquierda' o 'derecha'.");
                return null;
        }
    }
    
    // Metodo para calcular la columna (Clase)
    public static int calcularColumna(int altura) {
        if (altura == 1) {
            return 1;
        }
        return calcularColumna(altura - 1) + calcularColumna(altura - 1) + 1;
    }

    // Metodo para imprimir el árbol(Clase)
    public static void PrinterTree(int[][] matriz, Node raiz, int col, int fila, int altura) {
        if (raiz == null) {
            return;
        }
        matriz[fila][col] = raiz.getPregunta().getId();
        PrinterTree(matriz, raiz.left, col - (int) Math.pow(2, altura - 2), fila + 1, altura - 1);
        PrinterTree(matriz, raiz.right, col + (int) Math.pow(2, altura - 2), fila + 1, altura - 1);
    }

    // Metodo para imprimir el árbol en la consola
    public void TreePrinter() {
        int altura = alturaArbol(this.raiz);
        int columnas = calcularColumna(altura);
        int[][] matriz = new int[altura][columnas];
        PrinterTree(matriz, this.raiz, columnas / 2, 0, altura);
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < columnas; j++) {
                if (matriz[i][j] == 0) {
                    System.out.print("  ");
                } else {
                    System.out.print(matriz[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    // Metodo para calcular la altura de un árbol(Clase)
    public static int alturaArbol(Node nodo) {
        if (nodo == null) {
            return 0;
        }
        return Math.max(alturaArbol(nodo.left), alturaArbol(nodo.right)) + 1;
    }

    public  int BuscarNivelNodo(Node nodo, Node buscar, int nivel) {
        System.out.println(nodo.getPregunta().getId());
        if (nodo.getPregunta().getId() == buscar.getPregunta().getId()) {
            return nivel;
        } else {
            if (nodo.getPregunta().getId() < buscar.getPregunta().getId()) {
                return BuscarNivelNodo(nodo.right, buscar, nivel + 1);
            } else {
                return BuscarNivelNodo(nodo.left, buscar, nivel + 1);
            }
        }
    }

    public boolean cercania(Node nodoActual) {
        Node padreDelGanador = findParent(this.raiz, this.nodoGanador);

        if (nodoActual == padreDelGanador) {
            return true; // El nodo actual es el padre del nodo ganador
        }
        return false;
    }

    public boolean lejania(Node nodoActual) {
        // Busca el abuelo del nodo ganador
        Node padreDelGanador = findParent(this.raiz, this.nodoGanador);

        // Verifica si el nodo ganador tiene padre
        if (padreDelGanador == null) {
            return false; // No hay abuelo
        }

        // Ahora busca el abuelo
        Node abueloDelGanador = findParent(this.raiz, padreDelGanador);

        if (nodoActual == abueloDelGanador) {
            return true; // El nodo actual es el abuelo del nodo ganador
        }

        return false;
    }

    // Método auxiliar para encontrar el padre de un nodo
    public Node findParent(Node raiz, Node node) {
        if (raiz == null || node == null || raiz.left == node || raiz.right == node) {
            return raiz;
        }
        // Buscar recursivamente en el subárbol izquierdo
        Node left = findParent(raiz.left, node);
        if (left != null) {
            return left;
        }
        // Si no se encuentra en el izquierdo, buscar en el subárbol derecho
        return findParent(raiz.right, node);
    }
}
