import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TelaCriarArquivo extends JFrame implements ActionListener {
    private JLabel lNome, lArquivo;
    private JTextField tNome;
    private JFileChooser seletorArquivo;
    private JButton bSelecionarArquivo, bCriar, bCancelar;
    private JPanel painelBotoes, painelInformacoes;
    private File arquivoSelecionado;
    private TelaInicial telaPai;

    public TelaCriarArquivo(TelaInicial telaPai) {
        this.telaPai = telaPai;
        lNome = new JLabel("Nome:");
        lArquivo = new JLabel("Arquivo:");
        tNome = new JTextField(20);
        seletorArquivo = new JFileChooser();
        bSelecionarArquivo = new JButton("Selecionar...");
        bCriar = new JButton("Criar");
        bCancelar = new JButton("Cancelar");

        painelInformacoes = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        painelInformacoes.add(lNome, gbc);

        gbc.gridx = 1;
        tNome.setPreferredSize(new Dimension(200, 25));
        painelInformacoes.add(tNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        painelInformacoes.add(lArquivo, gbc);

        gbc.gridx = 1;
        bSelecionarArquivo.setPreferredSize(new Dimension(150, 25));
        painelInformacoes.add(bSelecionarArquivo, gbc);

        painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        painelBotoes.add(bCriar);
        painelBotoes.add(bCancelar);

        bSelecionarArquivo.addActionListener(this);
        bCriar.addActionListener(this);
        bCancelar.addActionListener(this);

        setLayout(new BorderLayout(10, 10));
        add(painelInformacoes, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        setTitle("Criar arquivo");
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    public void mostrarJanela() {
        setVisible(true);
    }

    public arquivo criarArquivo(String nome, byte[] conteudo, int tamanho) throws SQLException {
        conexao con = new conexao();
        int id = con.criarArquivo(nome, conteudo, tamanho);
        Timestamp agora = new Timestamp(System.currentTimeMillis());
        return new arquivo(id, nome, agora, tamanho, conteudo);
    }

    public String getNome() {
        return tNome.getText();
    }

    public byte[] getArquivoByte() throws IOException {
        return Files.readAllBytes(arquivoSelecionado.toPath());
    }

    public int getTamanho() {
        return (int) arquivoSelecionado.length();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bSelecionarArquivo) {
            int resposta = seletorArquivo.showOpenDialog(this);
            if (resposta == JFileChooser.APPROVE_OPTION) {
                arquivoSelecionado = seletorArquivo.getSelectedFile();
                JOptionPane.showMessageDialog(this,
                        "Arquivo selecionado:\n" + arquivoSelecionado.getName());
            }
        } else if (e.getSource() == bCriar) {
            if (arquivoSelecionado == null) {
                JOptionPane.showMessageDialog(this,
                        "Por favor, selecione um arquivo antes de criar.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String nome = getNome();
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Por favor, informe um nome para o arquivo.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                byte[] arq = getArquivoByte();
                int tamanho = getTamanho();
                arquivo novo = criarArquivo(nome, arq, tamanho);
                JOptionPane.showMessageDialog(this,
                        "Arquivo criado com sucesso!\nID: " + novo.getId());
                telaPai.adicionarArquivo(novo);
                dispose();
            } catch (IOException | SQLException ex) {
                Logger.getLogger(TelaCriarArquivo.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this,
                        "Erro ao criar arquivo: " + ex.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == bCancelar) {
            dispose();
        }
    }
}