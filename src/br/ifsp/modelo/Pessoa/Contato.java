package br.ifsp.modelo.Pessoa;

public class Contato {
	private String numeroCelular;
        private String numeroResidencial;
	private String email;
        private Object dono;

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public String getNumeroResidencial() {
        return numeroResidencial;
    }

    public void setNumeroResidencial(String numeroResidencial) {
        this.numeroResidencial = numeroResidencial;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getDono() {
        return dono;
    }

    public void setDono(Object dono) {
        this.dono = dono;
    }
	
        
}
