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
        return arquivos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
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
        return colunas[column];
    }

    public void atualizarTabela(Vector<arquivo> novosArquivos) {
        this.arquivos = novosArquivos;
        fireTableDataChanged();
    }
}