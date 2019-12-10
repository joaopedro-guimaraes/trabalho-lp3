package br.ifsp.modelo.Pessoa;

import java.util.ArrayList;
import java.util.List;

import br.ifsp.modelo.venda.Venda;

public class Vendedor extends Pessoa {

    private String login;
    private String senha;
    private boolean cargo;    
    public List<Venda> vendasRealizadas = new ArrayList<Venda>();
    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isCargo() {
        return cargo;
    }

    public void setCargo(boolean cargo) {
        this.cargo = cargo;
    }     

}
