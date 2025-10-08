import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class TelaInicial extends JFrame {

    JTable tabela;
    JButton bOrdenaBolha, bOrdenaInsercao;
    
    public Vector<arquivo> getDados() throws SQLException {
        Vector<arquivo> arquivos = new Vector<>();
        conexao con = new conexao();
        ResultSet rs = null;
        Connection conn = con.getConnection();
        try  {
            Statement st = conn.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            rs = st.executeQuery("SELECT * from imagens");
        } catch (SQLException e) {
            System.out.println(e);
        }
        while (rs.next()) {
            int id = rs.getInt("id");
            String nome = rs.getString("nome");
            int tamanho = rs.getInt("tamanho");
            Timestamp data_criacao = rs.getTimestamp("data_criacao");
            byte[] arquivo = rs.getBytes("arquivo");
            arquivo novoArquivo = new arquivo(id, nome, data_criacao, tamanho, arquivo);
            arquivos.add(novoArquivo);
        }
        
        
        return arquivos; 
    }
    
    public TelaInicial() throws SQLException {
        String[] colunas = {"ID", "Nome", "Tamanho", "Data de criação"};
        Vector<arquivo> arquivos = getDados();
        Object[][] valores = new Object[arquivos.size()][4];
        for (int i = 0; i < arquivos.size(); i++) {
            valores[i][0] = arquivos.get(i).getId();
            valores[i][1] = arquivos.get(i).getNome();
            valores[i][2] = arquivos.get(i).getTamanho();
            valores[i][3] = arquivos.get(i).getDataCriacaoStringFormatado();
        }


        tabela = new JTable(valores, colunas);
        JScrollPane scroll = new JScrollPane(tabela);

        bOrdenaBolha = new JButton("Ordenar Bolha");
        bOrdenaInsercao = new JButton("Ordenar Inserção");

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(bOrdenaBolha);
        painelBotoes.add(bOrdenaInsercao);

        setLayout(new BorderLayout());
        add(scroll, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        setTitle("Tela Inicial");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws SQLException {
        TelaInicial janela = new TelaInicial();
        janela.setVisible(true);
    }
}
