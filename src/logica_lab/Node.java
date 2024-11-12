package logica_lab;

import java.util.ArrayList;

/**
 *
 * @author tuori
 */
public class Node {
    Pregunta pregunta;
    public Node left;
    public Node right;

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }
    boolean Ganador;

    public boolean isGanador() {
        return Ganador;
    }

    public void setGanador(boolean Ganador) {
        this.Ganador = Ganador;
    }

    public Node(Pregunta pregunta) {
        this.left = null;
        this.right = null;
        this.pregunta = pregunta;
        this.Ganador = false;

    }

    public Pregunta getPregunta() {
        return pregunta;
    }



    

}
