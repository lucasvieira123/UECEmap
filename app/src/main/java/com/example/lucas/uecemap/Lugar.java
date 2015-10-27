package com.example.lucas.uecemap;

import android.provider.ContactsContract;

import java.io.Serializable;

/**
 * Created by stack on 20/10/15.
 */
public class Lugar implements Serializable {

    private int id;
    private String nome;
    private String descricao;
    private double latitude;
    private double longitude;
    private int contato;


    public Lugar(String nome, String descricao, double latitude, double longitude, int contato){
        this.nome = nome;
        this.descricao = descricao;
        this.latitude = latitude;
        this.longitude = longitude;
        this.contato = contato;
    }

    public Lugar(){

    }



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public int getContato() {
        return contato;
    }

    public void setContato(int contato) {
        this.contato = contato;
    }
}
