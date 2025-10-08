import java.util.Vector;

public class InsertionSort {
    int tempoGasto = 0;
    public Vector<arquivo> ordenar(Vector<arquivo> arquivos, String nomeAtributoOrdenar) {
        Cronometro tempo = new Cronometro();
        if ("ID".equals(nomeAtributoOrdenar) || "Tamanho".equals(nomeAtributoOrdenar)) {
            tempo.start();
            arquivos = ordenaInteger(arquivos, nomeAtributoOrdenar);
            tempo.parar();
            tempoGasto = tempo.getTimerInt();
        } else if ("Nome".equals(nomeAtributoOrdenar)) {
            tempo.start();
            arquivos = ordenaString(arquivos, nomeAtributoOrdenar);
            tempo.parar();
            tempoGasto = tempo.getTimerInt();
        } else if ("Data de criação".equals(nomeAtributoOrdenar)) {
            tempo.start();
            arquivos = ordenaLong(arquivos, nomeAtributoOrdenar);
            tempo.parar();
            tempoGasto = tempo.getTimerInt();
        } else {
            System.out.println("Erro: não há nenhum atributo chamado '" + nomeAtributoOrdenar + "'");
        }
        System.out.println(tempoGasto);
        return arquivos;
    }
    
    public Vector<arquivo> ordenaInteger(Vector<arquivo> arquivos, String nomeAtributoOrdenar) {
        final int n = arquivos.size();
        if("ID".equals(nomeAtributoOrdenar)) {
            for(int i = 1; i < n; i++) {
                arquivo chave = arquivos.get(i);
                int j = i - 1;

                while (j >= 0 && arquivos.get(j).getId() > chave.getId()) {
                    arquivos.set(j + 1, arquivos.get(j));
                    j--;
                }

                arquivos.set(j + 1, chave);
            }
        } else if ("Tamanho".equals(nomeAtributoOrdenar)) {
            for (int i = 1; i < n; i++){
                arquivo chave = arquivos.get(i);
                int j = i - 1;

                while (j >= 0 && arquivos.get(j).getTamanho() > chave.getTamanho()) {
                    arquivos.set(j + 1, arquivos.get(j));
                    j--;
                }

                arquivos.set(j + 1, chave);
            }
        } else {
            System.out.println("Erro: Não há nenhum atributo chamado " + nomeAtributoOrdenar);
            return null;
        }
        
        return arquivos;
    }
    
    public Vector<arquivo> ordenaString(Vector<arquivo> arquivos, String nomeAtributoOrdenar) {
        final int n = arquivos.size();
        if("Nome".equals(nomeAtributoOrdenar)) {
            for (int i = 1; i < n; i++){
                arquivo chave = arquivos.get(i);
                int j = i - 1;

                while (j >= 0 && arquivos.get(j).getNome().compareToIgnoreCase(chave.getNome()) > 0) {
                    arquivos.set(j + 1, arquivos.get(j));
                    j--;
                }

                arquivos.set(j + 1, chave);
            }
        } else {
            System.out.println("Erro: Não há nenhum atributo chamado " + nomeAtributoOrdenar);
            return null;
        }
        
        return arquivos;
    }
    
    public Vector<arquivo> ordenaLong(Vector<arquivo> arquivos, String nomeAtributoOrdenar) {
        final int n = arquivos.size();
        if("Data de criação".equals(nomeAtributoOrdenar)) {
            for (int i = 1; i < n; i++){
                arquivo chave = arquivos.get(i);
                int j = i - 1;

                while (j >= 0 && arquivos.get(j).getDataCriacaoLong() > chave.getDataCriacaoLong()) {
                    arquivos.set(j + 1, arquivos.get(j));
                    j--;
                }

                arquivos.set(j + 1 , chave);
            }
        } else {
            System.out.println("Erro: Não há nenhum atributo chamado " + nomeAtributoOrdenar);
            return null;
        }
        
        return arquivos;
    }
}
