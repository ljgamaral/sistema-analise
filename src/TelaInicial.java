import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Vector;

public class TelaInicial extends JFrame {
    Vector<arquivo> arquivos = new Vector<>();
    JTable tabela;
    tabela modelo;
    JPanel painelDados, painelBotoes;

    conexao con = new conexao();

    public TelaInicial() throws SQLException {
        modelo = new tabela(arquivos);
        modelo.atualizarTabela(con.getDados(arquivos));
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);

        painelDados = new JPanel(new BorderLayout());
        painelDados.setBorder(BorderFactory.createTitledBorder("Detalhes do Arquivo"));

        JButton bCriar = new JButton("Criar arquivo");
        bCriar.addActionListener(e -> {
            TelaCriarArquivo janela = new TelaCriarArquivo();
            janela.mostrarJanela();
        });

        painelBotoes = new JPanel();
        painelBotoes.add(bCriar);

        setLayout(new BorderLayout());
        add(painelDados, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        setTitle("Tela Inicial");
        setSize(600, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = tabela.getSelectedRow();
                    if (selectedRow >= 0 && selectedRow < arquivos.size()) {
                        mostrarDetalhes(arquivos.get(selectedRow));
                    }
                }
            }
        });

        tabela.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int coluna = tabela.columnAtPoint(e.getPoint());
                String nomeColuna = tabela.getColumnName(coluna);
                mostrarMenuOrdenacao(e, nomeColuna);
            }
        });
    }

    private void mostrarMenuOrdenacao(MouseEvent e, String nomeColuna) {
        JPopupMenu menu = new JPopupMenu("Ordenar");

        JMenuItem itemBolha = new JMenuItem("Ordenar por Bolha");
        JMenuItem itemInsercao = new JMenuItem("Ordenar por Inserção");

        itemBolha.addActionListener(ev -> {
            ordenaBolha(nomeColuna);
            modelo.atualizarTabela(arquivos);
        });

        itemInsercao.addActionListener(ev -> {
            ordenaInsercao(nomeColuna);
            modelo.atualizarTabela(arquivos);
        });

        menu.add(itemBolha);
        menu.add(itemInsercao);

        menu.show(e.getComponent(), e.getX(), e.getY());
    }

    private void mostrarDetalhes(arquivo arq) {
        painelDados.removeAll();

        JPanel painelTexto = new JPanel(new GridLayout(4, 1));
        painelTexto.add(new JLabel("ID: " + arq.getId()));
        painelTexto.add(new JLabel("Nome: " + arq.getNome()));
        painelTexto.add(new JLabel("Data Criação: " + arq.getDataCriacaoStringFormatado()));
        painelTexto.add(new JLabel("Tamanho: " + arq.getTamanho() + " bytes"));

        JPanel painelImagem = new JPanel();
        painelImagem.setPreferredSize(new Dimension(100, 100));
        painelImagem.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        byte[] dadosImagem = arq.getArquivo();
        if (dadosImagem != null && dadosImagem.length > 0) {
            try {
                ImageIcon icon = new ImageIcon(dadosImagem);
                Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                painelImagem.add(new JLabel(new ImageIcon(img)));
            } catch (Exception ex) {
                painelImagem.add(new JLabel("Imagem inválida"));
            }
        } else {
            painelImagem.add(new JLabel("Sem imagem"));
        }

        painelDados.add(painelTexto, BorderLayout.CENTER);
        painelDados.add(painelImagem, BorderLayout.EAST);
        painelDados.revalidate();
        painelDados.repaint();
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
}