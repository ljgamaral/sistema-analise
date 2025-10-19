import java.util.Vector;

public class BubbleSort {

    public long tempoGasto = 0;

    public Vector<arquivo> ordenar(Vector<arquivo> arquivos, String nomeAtributoOrdenar) {
        Cronometro tempo = new Cronometro();
        if ("ID".equals(nomeAtributoOrdenar) || "Tamanho".equals(nomeAtributoOrdenar)) {
            tempo.startar();
            arquivos = ordenaInteger(arquivos, nomeAtributoOrdenar);
            tempo.parar();
            tempoGasto = tempo.getTimerInt();
        } else if ("Nome".equals(nomeAtributoOrdenar)) {
            tempo.startar();
            arquivos = ordenaString(arquivos, nomeAtributoOrdenar);
            tempo.parar();
            tempoGasto = tempo.getTimerInt();
        } else if ("Data de criação".equals(nomeAtributoOrdenar)) {
            tempo.startar();
            arquivos = ordenaLong(arquivos, nomeAtributoOrdenar);
            tempo.parar();
            tempoGasto = tempo.getTimerInt();
        } else {
            System.out.println("Erro: não há nenhum atributo chamado '" + nomeAtributoOrdenar + "'");
        }
        System.out.println(tempoGasto);
        return arquivos;
    }

    public long getTempoGasto() {
        return tempoGasto;
    }

    public Vector<arquivo> ordenaInteger(Vector<arquivo> arquivos, String nomeAtributoOrdenar) {
        final int n = arquivos.size();
        arquivo aux;
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

    public Vector<arquivo> ordenaString(Vector<arquivo> arquivos, String nomeAtributoOrdenar) {
        final int n = arquivos.size();
        arquivo aux;
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

    public Vector<arquivo> ordenaLong(Vector<arquivo> arquivos, String nomeAtributoOrdenar) {
        final int n = arquivos.size();
        arquivo aux;
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
