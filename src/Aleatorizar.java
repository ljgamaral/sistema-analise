import java.util.Collections;
import java.util.Random;
import java.util.Vector;

public class Aleatorizar {
    private Vector<Arquivo> arquivos;
    
    public Aleatorizar(Vector<Arquivo> arquivos, String nomeAtributoOrdenar) {
        QuickSort ordenar = new QuickSort();
        this.arquivos = ordenar.ordenar(arquivos, nomeAtributoOrdenar, 0, false);
    }
    
    public Vector<Arquivo> melhorCaso() {
        return arquivos;
    }
    
    public Vector<Arquivo> medioCaso() {
        Random gerar = new Random(arquivos.size());
        Collections.shuffle(arquivos, gerar);
        return arquivos;
    }
    
    public Vector<Arquivo> piorCaso() {
        Collections.reverse(arquivos);
        return arquivos;
    }
}
