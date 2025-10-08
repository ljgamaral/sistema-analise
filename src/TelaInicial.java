import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class TelaInicial extends JFrame implements ActionListener {
    Vector<arquivo> arquivos = new Vector<>();
    JTable tabela;
    JButton bOrdenaBolha, bOrdenaInsercao;
    tabela modelo;
    
    public Vector<arquivo> getDados() throws SQLException {
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
        modelo = new tabela(arquivos);
        modelo.atualizarTabela(getDados());
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);

        bOrdenaBolha = new JButton("Ordenar Bolha");
        bOrdenaInsercao = new JButton("Ordenar Inserção");
        bOrdenaBolha.addActionListener(this);
        bOrdenaInsercao.addActionListener(this);

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
    
    public void ordenaBolha(String ordenarPor) {
        BubbleSort ordenar = new BubbleSort();
        arquivos = ordenar.ordenar(arquivos, ordenarPor);
    }

    public void ordenaInsercao(String ordenarPor) {
        InsertionSort ordenar = new InsertionSort();
        arquivos = ordenar.ordenar(arquivos, ordenarPor);
    }
    
    public static void main(String[] args) throws SQLException {
        TelaInicial janela = new TelaInicial();
        janela.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == bOrdenaBolha) {
            String resposta = JOptionPane.showInputDialog("Insira o nome da coluna a ser ordenada:");
            ordenaBolha(resposta);
            modelo.atualizarTabela(arquivos);
        } else if(e.getSource() == bOrdenaInsercao){
            String resposta = JOptionPane.showInputDialog("Insira o nome da coluna a ser ordenada:");
            ordenaInsercao(resposta);
            modelo.atualizarTabela(arquivos);            
        }
    }
}
