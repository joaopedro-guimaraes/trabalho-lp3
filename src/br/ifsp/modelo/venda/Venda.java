package br.ifsp.modelo.venda;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import br.ifsp.modelo.Pessoa.Cliente;
import br.ifsp.modelo.Pessoa.Vendedor;
import br.ifsp.modelo.infoprod.ProdutoVendido;

public class Venda {

    private int id;
    private Vendedor funcionario;
    private Cliente comprador;
    private Date data;
    private double total;
    private double desconto;
    public List<ProdutoVendido> itens = new ArrayList<ProdutoVendido>();
    public List<Pagamento> pagamentos = new ArrayList<Pagamento>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vendedor getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Vendedor funcionario) {
        this.funcionario = funcionario;
    }

    public Cliente getComprador() {
        return comprador;
    }

    public void setComprador(Cliente comprador) {
        this.comprador = comprador;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    
    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

       
}
