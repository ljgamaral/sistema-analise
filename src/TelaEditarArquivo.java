
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

public class TelaEditarArquivo extends JFrame implements ActionListener {

    private JLabel lNome, lArquivo;
    private JTextField tNome;
    private JFileChooser seletorArquivo;
    private JButton bSelecionarArquivo, bExcluirImagem, bSalvar, bCancelar;
    private JPanel painelBotoes, painelInformacoes, imagemLabel;
    private File arquivoSelecionado;
    private Arquivo arqNovo;
    private byte[] imagemBytes;
    private TelaInicial telaPai;

    public TelaEditarArquivo(TelaInicial telaPai, Arquivo arqNovo) {
        this.arqNovo = arqNovo;
        this.imagemBytes = arqNovo.getArquivo();
        this.telaPai = telaPai;

        lNome = new JLabel("Nome:");
        lArquivo = new JLabel("Arquivo:");
        tNome = new JTextField(20);
        tNome.setText(arqNovo.getNome());
        seletorArquivo = new JFileChooser();

        bSelecionarArquivo = new JButton("Selecionar...");
        bExcluirImagem = new JButton("Excluir imagem");
        bSalvar = new JButton("Salvar alterações");
        bCancelar = new JButton("Cancelar");

        imagemLabel = new JPanel(new BorderLayout());
        imagemLabel.setPreferredSize(new Dimension(150, 160));
        imagemLabel.setBorder(BorderFactory.createTitledBorder("Pré-visualização"));
        atualizarImagem();

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
        painelInformacoes.add(imagemLabel, gbc);

        painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        painelBotoes.add(bCancelar);
        painelBotoes.add(bSalvar);

        bSelecionarArquivo.addActionListener(this);
        bExcluirImagem.addActionListener(this);
        bSalvar.addActionListener(this);
        bCancelar.addActionListener(this);

        setLayout(new BorderLayout(10, 10));
        add(painelInformacoes, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
        setTitle("Editar arquivo");
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void atualizarImagem() {
        imagemLabel.removeAll();

        if (imagemBytes != null && imagemBytes.length > 0) {
            ImageIcon icon = new ImageIcon(imagemBytes);
            Image img = icon.getImage();

            int maxW = 100, maxH = 100;
            int w = img.getWidth(null), h = img.getHeight(null);
            if (w > 0 && h > 0) {
                double ratio = Math.min((double) maxW / w, (double) maxH / h);
                int newW = (int) (w * ratio);
                int newH = (int) (h * ratio);
                Image scaled = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
                icon = new ImageIcon(scaled);
            }

            JPanel painelInterno = new JPanel();
            painelInterno.setLayout(new BoxLayout(painelInterno, BoxLayout.Y_AXIS));

            JLabel imgLabel = new JLabel(icon);
            imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            painelInterno.add(imgLabel);

            painelInterno.add(Box.createRigidArea(new Dimension(0, 5)));

            bExcluirImagem.setPreferredSize(new Dimension(110, 25));
            bExcluirImagem.setMaximumSize(new Dimension(110, 25));
            bExcluirImagem.setAlignmentX(Component.CENTER_ALIGNMENT);
            painelInterno.add(bExcluirImagem);

            imagemLabel.add(painelInterno, BorderLayout.CENTER);

        } else {
            JPanel painelInterno = new JPanel(new FlowLayout(FlowLayout.CENTER));
            bSelecionarArquivo.setPreferredSize(new Dimension(120, 25));
            painelInterno.add(bSelecionarArquivo);
            imagemLabel.add(painelInterno, BorderLayout.CENTER);
        }

        imagemLabel.revalidate();
        imagemLabel.repaint();
    }

    public void mostrarJanela() {
        setVisible(true);
    }

    public Arquivo editarArquivo(int id, String nome, int tamanho, byte[] arq) throws SQLException {
        Conexao con = new Conexao();
        con.editarArquivo(id, nome, tamanho, arq);
        Timestamp agora = new Timestamp(System.currentTimeMillis());
        return new Arquivo(id, nome, agora, tamanho, arq);
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
                try {
                    imagemBytes = Files.readAllBytes(arquivoSelecionado.toPath());
                    atualizarImagem();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao carregar arquivo: " + ex.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == bExcluirImagem) {
            imagemBytes = null;
            arquivoSelecionado = null;
            atualizarImagem();
            imagemLabel.remove(bExcluirImagem);
            imagemLabel.add(bSelecionarArquivo);
        } else if (e.getSource() == bSalvar) {
            String nome = getNome();
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, informe um nome para o arquivo.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (imagemBytes == null || imagemBytes.length == 0) {
                JOptionPane.showMessageDialog(this, "Selecione uma imagem antes de salvar.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                int tamanho = imagemBytes.length;
                Arquivo novo = editarArquivo(arqNovo.getId(), nome, tamanho, imagemBytes);
                JOptionPane.showMessageDialog(this,
                        "Arquivo editado com sucesso!\nID: " + novo.getId());
                telaPai.modelo.atualizarArquivo(novo, telaPai);
                dispose();
            } catch (SQLException ex) {
                Logger.getLogger(TelaEditarArquivo.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this,
                        "Erro ao editar arquivo: " + ex.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == bCancelar) {
            dispose();
        }
    }
}
