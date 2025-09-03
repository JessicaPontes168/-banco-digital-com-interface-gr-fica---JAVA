import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BancoAppSwing {
    private JFrame frame;
    private JTextField nomeField, cpfField, valorField;
    private JTextArea textoArea;
    private Conta conta;

    public BancoAppSwing() {
        frame = new JFrame("Banco App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 350);
        frame.setLayout(new BorderLayout());

        // Painel topo: cadastro cliente
        JPanel painelCadastro = new JPanel(new GridLayout(3, 2));
        painelCadastro.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        painelCadastro.add(nomeField);
        painelCadastro.add(new JLabel("CPF:"));
        cpfField = new JTextField();
        painelCadastro.add(cpfField);

        JButton criarContaBtn = new JButton("Criar Conta");
        painelCadastro.add(criarContaBtn);

        frame.add(painelCadastro, BorderLayout.NORTH);

        // Painel meio: área de texto para extrato e mensagens
        textoArea = new JTextArea();
        textoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textoArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Painel rodapé: operações
        JPanel painelOperacoes = new JPanel();
        painelOperacoes.add(new JLabel("Valor:"));
        valorField = new JTextField(8);
        valorField.setEnabled(false);
        painelOperacoes.add(valorField);

        JButton depositarBtn = new JButton("Depositar");
        depositarBtn.setEnabled(false);
        JButton sacarBtn = new JButton("Sacar");
        sacarBtn.setEnabled(false);
        JButton extratoBtn = new JButton("Ver Extrato");
        extratoBtn.setEnabled(false);

        painelOperacoes.add(depositarBtn);
        painelOperacoes.add(sacarBtn);
        painelOperacoes.add(extratoBtn);

        frame.add(painelOperacoes, BorderLayout.SOUTH);

        // Ações dos botões
        criarContaBtn.addActionListener(e -> {
            String nome = nomeField.getText().trim();
            String cpf = cpfField.getText().trim();

            if (nome.isEmpty() || cpf.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Por favor, preencha nome e CPF.");
                return;
            }

            Cliente cliente = new Cliente(nome, cpf);
            conta = new Conta(12345, cliente);

            textoArea.setText("Conta criada com sucesso!\nBem-vindo(a), " + nome + "!\n");
            valorField.setEnabled(true);
            depositarBtn.setEnabled(true);
            sacarBtn.setEnabled(true);
            extratoBtn.setEnabled(true);

            nomeField.setEnabled(false);
            cpfField.setEnabled(false);
            criarContaBtn.setEnabled(false);
        });

        depositarBtn.addActionListener(e -> {
            if (conta == null)
                return;

            try {
                double valor = Double.parseDouble(valorField.getText());
                conta.depositar(valor);
                textoArea.append("Depósito de R$ " + valor + " realizado com sucesso.\n");
                valorField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Digite um valor numérico válido.");
            }
        });

        sacarBtn.addActionListener(e -> {
            if (conta == null)
                return;

            try {
                double valor = Double.parseDouble(valorField.getText());
                if (valor <= 0)
                    throw new NumberFormatException();
                if (valor > conta.getSaldo()) {
                    textoArea.append("Saldo insuficiente para saque de R$ " + valor + ".\n");
                } else {
                    conta.sacar(valor);
                    textoArea.append("Saque de R$ " + valor + " realizado com sucesso.\n");
                }
                valorField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Digite um valor numérico válido e positivo.");
            }
        });

        extratoBtn.addActionListener(e -> {
            if (conta == null)
                return;
            textoArea.append("=== Extrato da Conta ===\n");
            textoArea.append("Titular: " + conta.getTitular().getNome() + "\n");
            textoArea.append("CPF: " + conta.getTitular().getCpf() + "\n");
            textoArea.append("Número da conta: " + conta.getNumero() + "\n");
            textoArea.append(String.format("Saldo atual: R$ %.2f\n", conta.getSaldo()));
            textoArea.append("========================\n");
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BancoAppSwing());
    }
}
