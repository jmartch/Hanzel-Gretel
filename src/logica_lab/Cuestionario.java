package logica_lab;

import java.util.ArrayList;

/**
 *
 * @author tuori
 */
public class Cuestionario {
  private final ArrayList<Pregunta> preguntas;
    private int aciertos;
    private int errores;// 


    public Cuestionario() {
        this.preguntas = new ArrayList<>();
    }

    public void agregarPregunta(Pregunta p) {
        this.preguntas.add(p);
    }

    public ArrayList<Pregunta> getPreguntas() {
        return preguntas;
    }
    
    public Pregunta getPregunta(String pregunta){
        for (Pregunta p : preguntas) {
            if (p.getPregunta().equalsIgnoreCase(pregunta)) {
                return p;
            }
        }
       return null;
    }
    
public boolean acierto() {
    int numero = 1;
    for (Pregunta p : this.preguntas) {
        boolean acierta = p.preguntar(numero);
        numero++;
        if (acierta) {
            System.out.println("Correcto");
            this.aciertos++;
        } else {
            System.out.println("Incorrecto");
            this.errores++;
            if (errores > 2) {                        
                System.out.println("Perdiste");
                return false;  // Retrocede en las hojdas
            }
        }
    }
    // Si se contestaron todas las preguntas sin superar el límite de errores
    return true;
}

}

//    public void imprimirResultadosFinales() {
//        int total = this.preguntas.size();
//        double porcentajeAciertos = (100 * (double) this.aciertos) / total;
//        double porcentajeErrores = (100 * (double) this.errores) / total;
//        System.out.printf("Total de preguntas: %d\nAciertos: %d (%.2f %%)\nErrores: %d (%.2f %%)", total, this.aciertos, porcentajeAciertos, this.errores, porcentajeErrores);
//    }  

