package com.example.springbootthymeleafweb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
public class Prestec {
    @Id
    @Column(name = "codi")
    private int codi;
    @Basic
    @Column(name = "ISBN")
    private String isbn;
    @Basic
    @Column(name = "DNI")
    private String dni;
    @Basic
    @Column(name = "data_prestec")
    private Date dataPrestec;
    @Basic
    @Column(name = "data_retorn")
    private Date dataRetorn;
    @Basic
    @Column(name = "retornat")
    private Byte retornat;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ISBN", referencedColumnName = "Isbn",insertable = false,updatable = false)
    private Llibre llibreByIsbn;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "DNI", referencedColumnName = "DNI",insertable = false,updatable = false)
    private Usuari usuariByDni;

    public int getCodi() {
        return codi;
    }

    public void setCodi(int codi) {
        this.codi = codi;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getDataPrestec() {
        return dataPrestec;
    }

    public void setDataPrestec(Date dataPrestec) {
        this.dataPrestec = dataPrestec;
    }

    public Date getDataRetorn() {
        return dataRetorn;
    }

    public void setDataRetorn(Date dataRetorn) {
        this.dataRetorn = dataRetorn;
    }

    public Byte getRetornat() {
        return retornat;
    }

    public void setRetornat(Byte retornat) {
        this.retornat = retornat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prestec prestec = (Prestec) o;
        return codi == prestec.codi && Objects.equals(isbn, prestec.isbn) && Objects.equals(dni, prestec.dni) && Objects.equals(dataPrestec, prestec.dataPrestec) && Objects.equals(dataRetorn, prestec.dataRetorn) && Objects.equals(retornat, prestec.retornat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codi, isbn, dni, dataPrestec, dataRetorn, retornat);
    }

    public Llibre getLlibreByIsbn() {
        return llibreByIsbn;
    }

    public void setLlibreByIsbn(Llibre llibreByIsbn) {
        this.llibreByIsbn = llibreByIsbn;
    }

    public Usuari getUsuariByDni() {
        return usuariByDni;
    }

    public void setUsuariByDni(Usuari usuariByDni) {
        this.usuariByDni = usuariByDni;
    }
}
