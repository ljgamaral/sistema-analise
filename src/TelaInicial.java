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
    int idSelecionado = -1;
    int escolhaCaso = 0;

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
            TelaCriarArquivo janela = new TelaCriarArquivo(this);
            janela.mostrarJanela();
        });
        JButton bEditar = new JButton("Editar arquivo");
        bEditar.setEnabled(false);
        bEditar.addActionListener(e -> {
            if (idSelecionado != -1) {
                TelaEditarArquivo janela = new TelaEditarArquivo(this, arquivos.elementAt(idSelecionado));
                janela.mostrarJanela();
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma linha para editar");
            }
        });
        JButton bExcluir = new JButton("Excluir arquivo");
        bExcluir.setEnabled(false);
        bExcluir.addActionListener(e -> {
            if (idSelecionado != -1) {
                int confirmar = JOptionPane.showConfirmDialog(
                        this,
                        "Tem certeza que deseja excluir esse arquivo?",
                        "Confirmar exclusão",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirmar == JOptionPane.YES_OPTION) {
                    arquivo arqExcluir = arquivos.get(idSelecionado);

                    ExcluirArquivo excluir = new ExcluirArquivo();
                    excluir.excluir(arquivos, arqExcluir);

                    deletarArquivo(arqExcluir);

                    JOptionPane.showMessageDialog(this, "Arquivo excluído com sucesso!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma linha para excluir");
            }
        });
        JButton bAleatorizar = new JButton("Aleatorizar ordem");
        bAleatorizar.addActionListener(e -> {
            TelaAleatorizar janela = new TelaAleatorizar(this, arquivos, escolhaCaso);
            janela.mostrarJanela();
        });

        painelBotoes = new JPanel();
        painelBotoes.add(bCriar);
        painelBotoes.add(bEditar);
        painelBotoes.add(bExcluir);
        painelBotoes.add(bAleatorizar);        

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
                        idSelecionado = selectedRow;
                        bEditar.setEnabled(true);
                        bExcluir.setEnabled(true);
                        mostrarDetalhes(arquivos.get(selectedRow));
                    } else {
                        idSelecionado = -1;
                        bEditar.setEnabled(false);
                        bExcluir.setEnabled(false);
                    }
                }
            }
        });

        tabela.getTableHeader()
                .addMouseListener(new MouseAdapter() {
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
        JMenuItem itemQuick = new JMenuItem("Ordenar por Quick Sort");

        itemBolha.addActionListener(ev -> {
            ordenaBolha(nomeColuna);
            modelo.atualizarTabela(arquivos);
        });

        itemInsercao.addActionListener(ev -> {
            ordenaInsercao(nomeColuna);
            modelo.atualizarTabela(arquivos);
        });
        
        itemQuick.addActionListener(ev -> {
            ordenaQuick(nomeColuna);
            modelo.atualizarTabela(arquivos);
        });

        menu.add(itemBolha);
        menu.add(itemInsercao);
        menu.add(itemQuick);

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
    
    public void ordenaQuick(String ordenarPor) {
        QuickSort ordenar = new QuickSort();
        arquivos = ordenar.ordenar(arquivos, ordenarPor);
    }

    public void adicionarArquivo(arquivo novo) {
        arquivos.add(novo);
        modelo.fireTableDataChanged();
    }

    public void deletarArquivo(arquivo deletado) {
        modelo.fireTableDataChanged();
        painelDados.removeAll();
        painelDados.revalidate();
        painelDados.repaint();
    }

    public void atualizarArquivo(arquivo editado) {
        for (int i = 0; i < arquivos.size(); i++) {
            if (arquivos.get(i).getId() == editado.getId()) {
                arquivos.set(i, editado);
                modelo.fireTableRowsUpdated(i, i);
                break;
            }
        }
        mostrarDetalhes(editado);
    }

    public static void main(String[] args) throws SQLException {
        TelaInicial janela = new TelaInicial();
        janela.setVisible(true);
    }
}
