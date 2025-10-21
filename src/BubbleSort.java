import java.util.Vector;

public class BubbleSort {

    public long tempoGasto = 0;

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
            con.registrarOrdenacao((int)tempoGasto, "Bubble Sort", casoUsado, casoColuna, nomeAtributoOrdenar);
        }
        
        return arquivos;
    }

    public long getTempoGasto() {
        return tempoGasto;
    }

    public Vector<Arquivo> ordenaInteger(Vector<Arquivo> arquivos, String nomeAtributoOrdenar) {
        final int n = arquivos.size();
        Arquivo aux;
        if ("ID".equals(nomeAtributoOrdenar)) {
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - 1 - i; j++) {
                    if (arquivos.get(j).getId() > arquivos.get(j + 1).getId()) {
                        aux = arquivos.get(j);
                        arquivos.set(j, arquivos.get(j + 1));
                        arquivos.set(j + 1, aux);
                    }
                }
            }
        } else if ("Tamanho".equals(nomeAtributoOrdenar)) {
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - 1 - i; j++) {
                    if (arquivos.get(j).getTamanho() > arquivos.get(j + 1).getTamanho()) {
                        aux = arquivos.get(j);
                        arquivos.set(j, arquivos.get(j + 1));
                        arquivos.set(j + 1, aux);
                    }
                }
            }
        } else {
            System.out.println("Erro: Não há nenhum atributo chamado " + nomeAtributoOrdenar);
            return null;
        }

        return arquivos;
    }

    public Vector<Arquivo> ordenaString(Vector<Arquivo> arquivos, String nomeAtributoOrdenar) {
        final int n = arquivos.size();
        Arquivo aux;
        if ("Nome".equals(nomeAtributoOrdenar)) {
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - 1 - i; j++) {
                    if (arquivos.get(j).getNome().compareToIgnoreCase(arquivos.get(j + 1).getNome()) > 0) {
                        aux = arquivos.get(j);
                        arquivos.set(j, arquivos.get(j + 1));
                        arquivos.set(j + 1, aux);
                    }
                }
            }
        } else {
            System.out.println("Erro: Não há nenhum atributo chamado " + nomeAtributoOrdenar);
            return null;
        }
        return arquivos;
    }

    public Vector<Arquivo> ordenaLong(Vector<Arquivo> arquivos, String nomeAtributoOrdenar) {
        final int n = arquivos.size();
        Arquivo aux;
        if ("Data de criação".equals(nomeAtributoOrdenar)) {
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - 1 - i; j++) {
                    if (arquivos.get(j).getDataCriacaoLong() > arquivos.get(j + 1).getDataCriacaoLong()) {
                        aux = arquivos.get(j);
                        arquivos.set(j, arquivos.get(j + 1));
                        arquivos.set(j + 1, aux);
                    }
                }
            }
        } else {
            System.out.println("Erro: Não há nenhum atributo chamado " + nomeAtributoOrdenar);
            return null;
        }

        return arquivos;
    }
}
