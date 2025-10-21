import java.util.Vector;
import javax.swing.table.AbstractTableModel;

public class Tabela extends AbstractTableModel {
    private final String[] colunas = {"ID", "Nome", "Tamanho", "Data de criação"};
    private Vector<Arquivo> arquivos;

    public Tabela(Vector<Arquivo> dados) {
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
        Arquivo a = arquivos.get(rowIndex);
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

    public void atualizarTabela(Vector<Arquivo> novosArquivos) {
        this.arquivos = novosArquivos;
        fireTableDataChanged();
    }
    
    public void adicionarArquivo(Arquivo novo, TelaInicial telaPai) {
        arquivos.add(novo);
        telaPai.modelo.fireTableDataChanged();
    }

    public void deletarArquivo(Arquivo deletado, TelaInicial telaPai) {
        telaPai.modelo.fireTableDataChanged();
        telaPai.painelDados.removeAll();
        telaPai.painelDados.revalidate();
        telaPai.painelDados.repaint();
    }

    public void atualizarArquivo(Arquivo editado, TelaInicial telaPai) {
        for (int i = 0; i < arquivos.size(); i++) {
            if (arquivos.get(i).getId() == editado.getId()) {
                arquivos.set(i, editado);
                telaPai.modelo.fireTableRowsUpdated(i, i);
                break;
            }
        }
        telaPai.mostrarDetalhes(editado);
    }
}