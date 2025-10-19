
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Vector;

public class conexao {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sistema_analise?useSSL=false&serverTimezone=UTC",
                    "root",
                    ""
            );
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erro de conexão: " + e.getMessage());
        }
        return conn;
    }

    public Vector<arquivo> getDados(Vector<arquivo> arquivos) throws SQLException {
        String sql = "SELECT * FROM imagens";

        try (Connection conn = getConnection(); Statement st = conn.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                int tamanho = rs.getInt("tamanho");
                Timestamp data_criacao = rs.getTimestamp("data_criacao");
                byte[] arquivo = rs.getBytes("arquivo");

                arquivo novoArquivo = new arquivo(id, nome, data_criacao, tamanho, arquivo);
                arquivos.add(novoArquivo);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar dados: " + e.getMessage());
        }

        return arquivos;
    }

    public int criarArquivo(String nome, byte[] arquivo, int tamanho) throws SQLException {
        int id = -1;
        String sql = "INSERT INTO imagens (nome, data_criacao, tamanho, arquivo) VALUES (?, NOW(), ?, ?)";

        try (Connection conn = getConnection();      
        PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            st.setString(1, nome);
            st.setInt(2, tamanho);
            st.setBytes(3, arquivo);
            st.executeUpdate();

            try (ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao inserir imagem: " + e.getMessage());
        }

        return id;
    }

    public void editarArquivo(int id, arquivo arq) {
    String sql = "UPDATE imagens SET nome = ?, tamanho = ?, arquivo = ? WHERE id = ?";

    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, arq.getNome());
        ps.setLong(2, arq.getTamanho());
        ps.setBytes(3, arq.getArquivo());
        ps.setInt(4, id);

        ps.executeUpdate();
        System.out.println("Arquivo atualizado com sucesso!");

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}
