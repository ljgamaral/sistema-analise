import java.util.Vector;

public class InsertSort {
    long tempoGasto = 0;
    public Vector<Arquivo> ordenar(Vector<Arquivo> arquivos, String nomeAtributoOrdenar, int casoUsado, String casoColuna, boolean salvar) {
        Cronometro tempo = new Cronometro();
        if ("ID".equals(nomeAtributoOrdenar) || "Tamanho".equals(nomeAtributoOrdenar)) {
            tempo.startar();
            arquivos = ordenaInteger(arquivos, nomeAtributoOrdenar);
            tempo.parar();
        } else if ("Nome".equals(nomeAtributoOrdenar)) {
            tempo.startar();
            arquivos = ordenaString(arquivos, nomeAtributoOrdenar);
            tempo.parar();
        } else if ("Data de criação".equals(nomeAtributoOrdenar)) {
            tempo.startar();
            arquivos = ordenaLong(arquivos, nomeAtributoOrdenar);
            tempo.parar();
        } else {
            System.out.println("Erro: não há nenhum atributo chamado '" + nomeAtributoOrdenar + "'");
        }
        
        tempoGasto = tempo.getTimerInt();
        if(salvar) {
            System.out.println("Tempo gasto: " + tempoGasto + " ms");
            Conexao con = new Conexao();
            con.registrarOrdenacao((int)tempoGasto, "Insert Sort", casoUsado, casoColuna, nomeAtributoOrdenar);
        }
        
        return arquivos;
    }
    
    public Vector<Arquivo> ordenaInteger(Vector<Arquivo> arquivos, String nomeAtributoOrdenar) {
        final int n = arquivos.size();
        if("ID".equals(nomeAtributoOrdenar)) {
            for(int i = 1; i < n; i++) {
                Arquivo chave = arquivos.get(i);
                int j = i - 1;

                while (j >= 0 && arquivos.get(j).getId() > chave.getId()) {
                    arquivos.set(j + 1, arquivos.get(j));
                    j--;
                }

                arquivos.set(j + 1, chave);
            }
        } else if ("Tamanho".equals(nomeAtributoOrdenar)) {
            for (int i = 1; i < n; i++){
                Arquivo chave = arquivos.get(i);
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
    
    public Vector<Arquivo> ordenaString(Vector<Arquivo> arquivos, String nomeAtributoOrdenar) {
        final int n = arquivos.size();
        if("Nome".equals(nomeAtributoOrdenar)) {
            for (int i = 1; i < n; i++){
                Arquivo chave = arquivos.get(i);
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
    
    public Vector<Arquivo> ordenaLong(Vector<Arquivo> arquivos, String nomeAtributoOrdenar) {
        final int n = arquivos.size();
        if("Data de criação".equals(nomeAtributoOrdenar)) {
            for (int i = 1; i < n; i++){
                Arquivo chave = arquivos.get(i);
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
