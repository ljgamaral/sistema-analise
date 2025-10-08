import java.util.Vector;
import javax.swing.table.AbstractTableModel;

public class tabela extends AbstractTableModel {
    private final String[] colunas = {"ID", "Nome", "Tamanho", "Data de criação"};
    private Vector<arquivo> arquivos;

    public tabela(Vector<arquivo> dados) {
        this.arquivos = dados;
    }

    @Override
    public int getRowCount() {
        // Retorna a quantidade de linhas (número de arquivos)
        return arquivos.size();
    }

    @Override
    public int getColumnCount() {
        // Retorna a quantidade de colunas
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // Retorna o valor específico (célula) que será mostrado na tabela
        arquivo a = arquivos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return a.getId();
            case 1:
                return a.getNome();
            case 2:
                return a.getTamanho();
            case 3:
                return a.getDataCriacaoStringFormatado();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        // Retorna o nome da coluna
        return colunas[column];
    }

    // Atualiza o conteúdo da tabela (quando o vetor for modificado)
    public void atualizarTabela(Vector<arquivo> novosArquivos) {
        this.arquivos = novosArquivos;
        fireTableDataChanged(); // notifica a JTable que os dados mudaram
    }
}