import java.util.Vector;
import java.lang.reflect.Method;

public class QuickSort {
    public long tempoGasto = 0;

    public Vector<arquivo> ordenar(Vector<arquivo> arquivos, String nomeAtributoOrdenar) {
        Cronometro tempo = new Cronometro();

        if ("ID".equalsIgnoreCase(nomeAtributoOrdenar) || "Tamanho".equalsIgnoreCase(nomeAtributoOrdenar)) {
            tempo.startar();
            quickSortNumerico(arquivos, 0, arquivos.size() - 1, nomeAtributoOrdenar.equalsIgnoreCase("ID") ? "Id" : "Tamanho");
            tempo.parar();

        } else if ("Nome".equalsIgnoreCase(nomeAtributoOrdenar)) {
            tempo.startar();
            quickSortString(arquivos, 0, arquivos.size() - 1, "Nome");
            tempo.parar();

        } else if ("Data de criação".equalsIgnoreCase(nomeAtributoOrdenar)) {
            tempo.startar();
            quickSortNumerico(arquivos, 0, arquivos.size() - 1, "DataCriacaoLong");
            tempo.parar();

        } else {
            System.out.println("Erro: não há atributo chamado '" + nomeAtributoOrdenar + "'");
        }

        tempoGasto = tempo.getTimerInt();
        System.out.println("Tempo gasto: " + tempoGasto + " ms");

        return arquivos;
    }

    public long getTempoGasto() {
        return tempoGasto;
    }

    public static void quickSortNumerico(Vector<arquivo> arquivos, int esquerda, int direita, String nomeAtributoOrdenar) {
        try {
            if (arquivos == null || arquivos.isEmpty()) return;

            String metodoNome = "get" + nomeAtributoOrdenar.substring(0, 1).toUpperCase() + nomeAtributoOrdenar.substring(1);
            Method metodo = arquivo.class.getMethod(metodoNome);

            int esq = esquerda;
            int dir = direita;

            long pivo = ((Number) metodo.invoke(arquivos.get((esq + dir) / 2))).longValue();

            while (esq <= dir) {
                while (((Number) metodo.invoke(arquivos.get(esq))).longValue() < pivo) esq++;
                while (((Number) metodo.invoke(arquivos.get(dir))).longValue() > pivo) dir--;

                if (esq <= dir) {
                    arquivo temp = arquivos.get(esq);
                    arquivos.set(esq, arquivos.get(dir));
                    arquivos.set(dir, temp);
                    esq++;
                    dir--;
                }
            }

            if (dir > esquerda)
                quickSortNumerico(arquivos, esquerda, dir, nomeAtributoOrdenar);
            if (esq < direita)
                quickSortNumerico(arquivos, esq, direita, nomeAtributoOrdenar);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void quickSortString(Vector<arquivo> arquivos, int esquerda, int direita, String nomeAtributoOrdenar) {
        try {
            if (arquivos == null || arquivos.isEmpty()) return;

            String metodoNome = "get" + nomeAtributoOrdenar.substring(0, 1).toUpperCase() + nomeAtributoOrdenar.substring(1);
            Method metodo = arquivo.class.getMethod(metodoNome);

            int esq = esquerda;
            int dir = direita;

            String pivo = (String) metodo.invoke(arquivos.get((esq + dir) / 2));

            while (esq <= dir) {
                while (((String) metodo.invoke(arquivos.get(esq))).compareToIgnoreCase(pivo) < 0) esq++;
                while (((String) metodo.invoke(arquivos.get(dir))).compareToIgnoreCase(pivo) > 0) dir--;

                if (esq <= dir) {
                    arquivo temp = arquivos.get(esq);
                    arquivos.set(esq, arquivos.get(dir));
                    arquivos.set(dir, temp);
                    esq++;
                    dir--;
                }
            }

            if (dir > esquerda)
                quickSortString(arquivos, esquerda, dir, nomeAtributoOrdenar);
            if (esq < direita)
                quickSortString(arquivos, esq, direita, nomeAtributoOrdenar);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
