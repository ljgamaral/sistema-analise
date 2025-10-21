
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.*;

public class TelaAleatorizar extends JFrame implements ItemListener, ActionListener {

    JLabel lAleatorizar;
    JRadioButton melhorCaso, medioCaso, piorCaso;
    ButtonGroup grupoEscolhas;
    JPanel painel, painelBotoes;
    JButton bContinuar, bCancelar;
    TelaInicial telaPai;
    Vector<Arquivo> arquivos;
    int escolha;

    public TelaAleatorizar(TelaInicial telaPai, Vector<Arquivo> arquivos, int escolha) {
        this.telaPai = telaPai;
        this.arquivos = arquivos;
        this.escolha = escolha;

        lAleatorizar = new JLabel("Aleatorizar com:");

        melhorCaso = new JRadioButton("Melhor caso");
        melhorCaso.setSelected(escolha == 0);
        melhorCaso.addItemListener(this);
        medioCaso = new JRadioButton("Médio caso");
        medioCaso.setSelected(escolha == 1 || escolha == 4);
        medioCaso.addItemListener(this);
        piorCaso = new JRadioButton("Pior caso");
        piorCaso.setSelected(escolha == 2);
        piorCaso.addItemListener(this);

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
        painel.add(melhorCaso);
        painel.add(medioCaso);
        painel.add(piorCaso);

        add(painel);
        add(painelBotoes);

        setLayout(new BorderLayout());
        add(painel, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
        setTitle("Aleatorizar");
        setSize(300, 120);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void mostrarJanela() {
        setVisible(true);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (melhorCaso.isSelected()) {
            escolha = 0;
        } else if (medioCaso.isSelected()) {
            escolha = 1;
        } else if (piorCaso.isSelected()) {
            escolha = 2;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Aleatorizar gerar = new Aleatorizar(arquivos, "Id");
        if (e.getSource() == bContinuar) {
            if (escolha == 0) {
                arquivos = gerar.melhorCaso();
            } else if (escolha == 1) {
                arquivos = gerar.medioCaso();
            } else if (escolha == 2) {
                arquivos = gerar.piorCaso();
            } else {
                dispose();
            }
            telaPai.modelo.atualizarTabela(arquivos);
            telaPai.escolhaCaso = escolha;
            dispose();
        } else if (e.getSource() == bCancelar) {
            dispose();
        }
    }
}
