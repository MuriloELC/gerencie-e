package model;

import java.util.UUID;

public class Usuario {
    private String id;
    private String email;
    private String senha;
    private String idEmpresa;
    private Integer ativo; // 0 = INATIVO, 1 = ATIVO

    public Usuario() {
        this.id = UUID.randomUUID().toString();
        this.ativo = 1; // Começa ativo por padrão
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(String idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Integer getAtivo() {
        return ativo;
    }

    public void setAtivo(Integer ativo) {
        this.ativo = ativo;
    }
}
