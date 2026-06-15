import java.util.ArrayList;

public class Gestor {
    private ArrayList<Transacao> transacoes = new ArrayList<>();
    private double saldo = 0;

    public void setSaldoInicial(double valor) {
        if (saldo == 0) {
            saldo = valor;
        }
    }

    public void adicionarTransacao(Transacao t) {
        transacoes.add(t);
        if (t.getTipo().equals("Receita")) {
            saldo += t.getValor();
        } else {
            saldo -= t.getValor();
        }
    }

    public double getTotalDespesas() {
        double total = 0;
        for (Transacao t : transacoes) {
            if (t.getTipo().equals("Despesa")) {
                total += t.getValor();
            }
        }
        return total;
    }

    public double getSaldo() { return saldo; }
    public ArrayList<Transacao> getTransacoes() { return transacoes; }
}