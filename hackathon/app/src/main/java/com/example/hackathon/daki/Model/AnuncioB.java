package com.example.hackathon.daki.Model;

/**
 * Created by Vaharamus on 22/03/2018.
 */

public class AnuncioB {

    private int id;
    private String titulo;
    private String tipo;
    private String endereco;
    private String data;
    private String urlImagem;
    private String contato;
    private String descricao;
    private Usuario usuario;
    private Boolean autorizacao;
    private int usuarioId;

    public AnuncioB(String titulo, String tipo, String endereco, String data, String urlImagem, String contato, String descricao, Boolean autorizacao, int usuarioId) {
        this.titulo = titulo;
        this.tipo = tipo;
        this.endereco = endereco;
        this.data = data;
        this.urlImagem = urlImagem;
        this.contato = contato;
        this.descricao = descricao;
        this.autorizacao = autorizacao;
        this.usuarioId = usuarioId;
    }

    public boolean isAutorizacao() {
        return autorizacao;
    }

    public void setAutorizacao(boolean autorizacao) {
        this.autorizacao = autorizacao;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "AnuncioB{" + "id=" + id + ", titulo='" + titulo + '\'' + ", tipo='" + tipo + '\'' + ", endereco='" + endereco + '\'' + ", data='" + data + '\'' + ", urlImagem='" + urlImagem + '\'' + ", contato='" + contato + '\'' + ", descricao='" + descricao + '\'' + ", usuario=" + usuario + ", autorizacao=" + autorizacao + ", usuarioId=" + usuarioId + '}';
    }
}