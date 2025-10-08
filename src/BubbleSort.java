public class BubbleSort {
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
        int aux;
        if(nomeAtributoOrdenar == "id") {
            for (int i = 0; i < n-1; i++){
                for (int j = 0; j < n-1-i; j++){
                    if (arquivos[j].getId() > arquivos[j+1].getId()){
                        aux = arquivos[j].getId();
                        arquivos[j].setId(arquivos[j+1].getId());
                        arquivos[j+1].setId(aux);
                    }
                }
            }
        } else if (nomeAtributoOrdenar == "tamanho") {
            for (int i = 0; i < n-1; i++){
                for (int j = 0; j < n-1-i; j++){
                    if (arquivos[j].getTamanho() > arquivos[j+1].getTamanho()){
                        aux = arquivos[j].getTamanho();
                        arquivos[j].setTamanho(arquivos[j+1].getTamanho());
                        arquivos[j+1].setTamanho(aux);
                    }
                }
            }
        } else {
            System.out.println("Erro: Não há nenhum atributo chamado " + nomeAtributoOrdenar);
            return null;
        }
        
        return arquivos;
    }
    
    public arquivo[] ordenaString(arquivo[] arquivos, String nomeAtributoOrdenar) {
        final int n = arquivos.length;
        String aux;
        if(nomeAtributoOrdenar == "nome") {
            for (int i = 0; i < n-1; i++){
                for (int j = 0; j < n-1-i; j++){
                    if (arquivos[j].getNome().compareToIgnoreCase(arquivos[j + 1].getNome()) > 0) {
                        aux = arquivos[j].getNome();
                        arquivos[j].setNome(arquivos[j + 1].getNome());
                        arquivos[j + 1].setNome(aux);
                    }
                }
            }
        } else {
            System.out.println("Erro: Não há nenhum atributo chamado " + nomeAtributoOrdenar);
            return null;
        }
        
        return arquivos;
    }
    
    public arquivo[] ordenaLong(arquivo[] arquivos, String nomeAtributoOrdenar) {
        final int n = arquivos.length;
        long aux;
        if(nomeAtributoOrdenar == "data_criacao") {
            for (int i = 0; i < n-1; i++){
                for (int j = 0; j < n-1-i; j++){
                    if (arquivos[j].getDataCriacaoLong() > arquivos[j+1].getDataCriacaoLong()){
                        aux = arquivos[j].getDataCriacaoLong();
                        arquivos[j].setDataCriacaoLong(arquivos[j+1].getDataCriacaoLong());
                        arquivos[j+1].setDataCriacaoLong(aux);
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
