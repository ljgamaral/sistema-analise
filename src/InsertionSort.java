public class InsertionSort {
    public arquivo[] ordenar(arquivo[] arquivos, Object ordenarPor, String nomeAtributoOrdenar) {
        String tipoDadoOrdenar = ordenarPor.getClass().getSimpleName();

        switch(tipoDadoOrdenar) {
            case "Integer":
                arquivos = ordenaInteger(arquivos, nomeAtributoOrdenar);
                break;
            case "String":
                arquivos = ordenaString(arquivos, nomeAtributoOrdenar);
                break;
            case "Timestamp":
                arquivos = ordenaLong(arquivos, nomeAtributoOrdenar);
                break;
            default:
                System.out.println("Erro: O tipo do atributo '" + tipoDadoOrdenar + "' não é aceito");
                return null;
        }
        
        return arquivos;
    }
    
    public arquivo[] ordenaInteger(arquivo[] arquivos, String nomeAtributoOrdenar) {
        final int n = arquivos.length;
        if(nomeAtributoOrdenar == "id") {
            for(int i = 1; i < n; i++) {
                arquivo chave = arquivos[i];
                int j = i - 1;

                while (j >= 0 && arquivos[j].getId() > chave.getId()) {
                    arquivos[j + 1] = arquivos[j];
                    j--;
                }

                arquivos[j + 1] = chave;
            }
        } else if (nomeAtributoOrdenar == "tamanho") {
            for (int i = 1; i < n; i++){
                arquivo chave = arquivos[i];
                int j = i - 1;

                while (j >= 0 && arquivos[j].getTamanho() > chave.getTamanho()) {
                    arquivos[j + 1] = arquivos[j];
                    j--;
                }

                arquivos[j + 1] = chave;
            }
        } else {
            System.out.println("Erro: Não há nenhum atributo chamado " + nomeAtributoOrdenar);
            return null;
        }
        
        return arquivos;
    }
    
    public arquivo[] ordenaString(arquivo[] arquivos, String nomeAtributoOrdenar) {
        final int n = arquivos.length;
        if(nomeAtributoOrdenar == "nome") {
            for (int i = 1; i < n; i++){
                arquivo chave = arquivos[i];
                int j = i - 1;

                while (j >= 0 && arquivos[j].getNome().compareToIgnoreCase(chave.getNome()) > 0) {
                    arquivos[j + 1].setId(arquivos[j].getId());
                    j--;
                }

                arquivos[j + 1] = chave;
            }
        } else {
            System.out.println("Erro: Não há nenhum atributo chamado " + nomeAtributoOrdenar);
            return null;
        }
        
        return arquivos;
    }
    
    public arquivo[] ordenaLong(arquivo[] arquivos, String nomeAtributoOrdenar) {
        final int n = arquivos.length;
        if(nomeAtributoOrdenar == "data_criacao") {
            for (int i = 1; i < n; i++){
                arquivo chave = arquivos[i];
                int j = i - 1;

                while (j >= 0 && arquivos[j].getDataCriacaoLong() > chave.getDataCriacaoLong()) {
                    arquivos[j + 1] = arquivos[j];
                    j--;
                }

                arquivos[j + 1] = chave;
            }
        } else {
            System.out.println("Erro: Não há nenhum atributo chamado " + nomeAtributoOrdenar);
            return null;
        }
        
        return arquivos;
    }
}
