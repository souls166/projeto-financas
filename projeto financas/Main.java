import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame {
    private Gestor gestor = new Gestor();
    private JTextArea areaTransacoes;
    private JLabel saldoLabel;
    private JLabel despesasLabel;
    private JTextField saldoInicialField = new JTextField(8);

    public Main() {
        setTitle("Gestor de Finanças");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTextField descricaoField = new JTextField(10);
        JTextField valorField = new JTextField(5);
        JComboBox<String> tipoBox = new JComboBox<>(new String[]{"Receita", "Despesa"});
        JButton adicionarBtn = new JButton("Adicionar");

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Saldo Inicial: R$"));
        inputPanel.add(saldoInicialField);
        inputPanel.add(new JLabel("Descrição:"));
        inputPanel.add(descricaoField);
        inputPanel.add(new JLabel("Valor:"));
        inputPanel.add(valorField);
        inputPanel.add(new JLabel("Tipo:"));
        inputPanel.add(tipoBox);
        inputPanel.add(adicionarBtn);

        areaTransacoes = new JTextArea();
        areaTransacoes.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaTransacoes);

        saldoLabel = new JLabel("Saldo atual: R$ 0.00");
        despesasLabel = new JLabel("Total de Despesas: R$ 0.00");

        JPanel southPanel = new JPanel(new GridLayout(2, 1));
        southPanel.add(despesasLabel);
        southPanel.add(saldoLabel);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        adicionarBtn.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        try {
            if (!saldoInicialField.getText().isEmpty()) {
                double saldoInicial = Double.parseDouble(saldoInicialField.getText());
                if (saldoInicial < 0) {
                    JOptionPane.showMessageDialog(null, "Saldo inicial não pode ser negativo!");
                    return;
                }
                gestor.setSaldoInicial(saldoInicial);
                saldoInicialField.setEditable(false);
            }

            String desc = descricaoField.getText();
            double valor = Double.parseDouble(valorField.getText());
            String tipo = (String) tipoBox.getSelectedItem();

            if (tipo.equals("Despesa") && valor > gestor.getSaldo()) {
                JOptionPane.showMessageDialog(null, "Saldo insuficiente para essa despesa!");
                return;
            }

            if (tipo.equals("Receita")) {
                gestor.adicionarTransacao(new Receita(desc, valor));
            } else {
                gestor.adicionarTransacao(new Despesa(desc, valor));
            }

            atualizarTela();
            descricaoField.setText("");
            valorField.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro: valor inválido!");
        }
    }
});
    }

    private void atualizarTela() {
        areaTransacoes.setText("");
        for (Transacao t : gestor.getTransacoes()) {
            areaTransacoes.append(t.getTipo() + " - " + t.getDescricao() + ": R$ " + String.format("%.2f", t.getValor()) + "\n");
        }
        despesasLabel.setText("Total de Despesas: R$ " + String.format("%.2f", gestor.getTotalDespesas()));
        saldoLabel.setText("Saldo atual: R$ " + String.format("%.2f", gestor.getSaldo()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}