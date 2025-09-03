public class Conta {
    private int numero;
    private Cliente titular;
    private double saldo;

    public Conta(int numero, Cliente titular) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = 0.0;
    }

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
            System.out.println("Depósito realizado com sucesso.");
        } else {
            System.out.println("Valor inválido.");
        }
    }

    public void sacar(double valor) {
        if (valor > 0 && valor <= saldo) {
            saldo -= valor;
            System.out.println("Saque realizado com sucesso.");
        } else {
            System.out.println("Saldo insuficiente ou valor inválido.");
        }
    }

    public void exibirExtrato() {
        System.out.println("=== Extrato da Conta ===");
        System.out.println("Titular: " + titular.getNome());
        System.out.println("CPF: " + titular.getCpf());
        System.out.println("Número da conta: " + numero);
        System.out.println("Saldo atual: R$ " + saldo);
        System.out.println("========================");
    }

    public int getNumero() {
        return numero;
    }

    // Getters adicionais para o Swing
    public Cliente getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }
}
