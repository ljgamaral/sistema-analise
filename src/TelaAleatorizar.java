
import java.awt.BorderLayout;
import static java.awt.BorderLayout.CENTER;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.*;

public class TelaAleatorizar extends JFrame implements ItemListener, ActionListener {

    JLabel lAleatorizar, lColuna;
    JRadioButton melhorCaso, medioCaso, piorCaso;
    ButtonGroup grupoEscolhas;
    JPanel painel, painelBotoes;
    JButton bContinuar, bCancelar;
    JComboBox<String> selecao;
    String[] colunas = {"ID", "Nome", "Tamanho", "Data de criação"};
    TelaInicial telaPai;
    Vector<Arquivo> arquivos;
    int escolhaCaso;
    String escolhaColuna;

    public TelaAleatorizar(TelaInicial telaPai, Vector<Arquivo> arquivos, int escolhaCaso, String escolhaColuna) {
        this.telaPai = telaPai;
        this.arquivos = arquivos;
        this.escolhaCaso = escolhaCaso;
        this.escolhaColuna = escolhaColuna;
        
        lAleatorizar = new JLabel("Aleatorizar com:");

        melhorCaso = new JRadioButton("Melhor caso");
        melhorCaso.setSelected(escolhaCaso == 0);
        melhorCaso.addItemListener(this);
        medioCaso = new JRadioButton("Médio caso");
        medioCaso.setSelected(escolhaCaso == 1 || escolhaCaso == 4);
        medioCaso.addItemListener(this);
        piorCaso = new JRadioButton("Pior caso");
        piorCaso.setSelected(escolhaCaso == 2);
        piorCaso.addItemListener(this);
        
        lColuna = new JLabel("Com a coluna:");
        
        selecao = new JComboBox<>(colunas);
        selecao.setSelectedItem(escolhaColuna);

        selecao.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                escolhaColuna = (String) selecao.getSelectedItem();
            }
        });

        bContinuar = new JButton("Continuar");
        bContinuar.addActionListener(this);

        bCancelar = new JButton("Cancelar");
        bCancelar.addActionListener(this);

        grupoEscolhas = new ButtonGroup();
        grupoEscolhas.add(melhorCaso);
        grupoEscolhas.add(medioCaso);
        grupoEscolhas.add(piorCaso);

        painelBotoes = new JPanel();
        painelBotoes.add(bCancelar);
        painelBotoes.add(bContinuar);

        painel = new JPanel();
        painel.add(lAleatorizar);
        painel.add(melhorCaso);
        painel.add(medioCaso);
        painel.add(piorCaso);
        painel.add(lColuna);
        painel.add(selecao);
        painel.setLayout(new FlowLayout(1));

        add(painel);
        add(painelBotoes);

        setLayout(new BorderLayout());
        add(painel, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
        setTitle("Aleatorizar");
        setSize(400, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void mostrarJanela() {
        setVisible(true);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (melhorCaso.isSelected()) {
            escolhaCaso = 0;
        } else if (medioCaso.isSelected()) {
            escolhaCaso = 1;
        } else if (piorCaso.isSelected()) {
            escolhaCaso = 2;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Aleatorizar gerar = new Aleatorizar(arquivos, escolhaColuna);
        if (e.getSource() == bContinuar) {
            if (escolhaCaso == 0) {
                arquivos = gerar.melhorCaso();
            } else if (escolhaCaso == 1) {
                arquivos = gerar.medioCaso();
            } else if (escolhaCaso == 2) {
                arquivos = gerar.piorCaso();
            } else {
                dispose();
            }
            telaPai.modelo.atualizarTabela(arquivos);
            telaPai.escolhaCaso = escolhaCaso;
            telaPai.escolhaColuna = escolhaColuna;
            dispose();
        } else if (e.getSource() == bCancelar) {
            dispose();
        }
    }
}
