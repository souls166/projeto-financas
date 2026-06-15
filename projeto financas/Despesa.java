public class Despesa extends Transacao {
    public Despesa(String descricao, double valor) {
        super(descricao, valor);
    }

    @Override
    public String getTipo() { return "Despesa"; }
}