/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica_lab;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author tuori
 */
public class Pregunta {
    
    private final String pregunta;
    private final List<Respuesta> respuestas;
    private int id ;

    // Nota: si crees que agregarás más respuestas, amplía las letras
    private static final String letras = "abcd";

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }
    public String getPregunta() {
        return pregunta;
    }

    public int getId() {
        return id;
    }
    
    public Pregunta(String pregunta, List<Respuesta> respuestas,int id) {
        this.pregunta = pregunta;
         this.id = id;
        this.respuestas = respuestas;
    }

    public Pregunta(String pregunta) {
        this.pregunta = pregunta;
        this.respuestas = new ArrayList<>();
       
    }

    public void agregarRespuesta(Respuesta r) {
        this.respuestas.add(r);
    }

    public boolean respuestaCorrecta(char respuesta) {
        int indice = letras.indexOf(respuesta);
        if (indice == -1) {
            return false;
        }
        return this.respuestas.get(indice).esCorrecta;
    }

    public boolean preguntar(int numero) {
        System.out.println(numero + ". " + this.pregunta);
        int indice = 0;
        for (Respuesta r : this.respuestas) {
            System.out.printf("%c) %s\n", letras.charAt(indice), r.respuesta);
            indice++;
        }
        System.out.println("Elige: ");
        Scanner sc = new Scanner(System.in);
        char respuesta = sc.nextLine().charAt(0);
        return this.respuestaCorrecta(respuesta);
    }
}
