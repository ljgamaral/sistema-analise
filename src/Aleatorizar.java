import java.util.Collections;
import java.util.Random;
import java.util.Vector;

public class Aleatorizar {
    private Vector<arquivo> arquivos;
    
    public Aleatorizar(Vector<arquivo> arquivos, String nomeAtributoOrdenar) {
        QuickSort ordenar = new QuickSort();
        this.arquivos = ordenar.ordenar(arquivos, nomeAtributoOrdenar, 0, false);
    }
    
    public Vector<arquivo> melhorCaso() {
        return arquivos;
    }
    
    public Vector<arquivo> medioCaso() {
        Random gerar = new Random(arquivos.size());
        Collections.shuffle(arquivos, gerar);
        return arquivos;
    }
    
    public Vector<arquivo> piorCaso() {
        Collections.reverse(arquivos);
        return arquivos;
    }
}
