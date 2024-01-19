package com.example.springbootthymeleafweb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
public class Llibre {
    @Id
    @Column(name = "Isbn")
    private String isbn;
    @Basic
    @Column(name = "titol")
    private String titol;
    @Basic
    @Column(name = "categoria")
    private String categoria;
    @Basic
    @Column(name = "preu")
    private Double preu;
    @Basic
    @Column(name = "editorial")
    private String editorial;
    @Basic
    @Column(name = "autor")
    private String autor;
    @JsonIgnore
    @OneToMany(mappedBy = "llibreByIsbn")
    private Collection<Prestec> prestecsByIsbn;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getPreu() {
        return preu;
    }

    public void setPreu(Double preu) {
        this.preu = preu;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Llibre llibre = (Llibre) o;
        return Objects.equals(isbn, llibre.isbn) && Objects.equals(titol, llibre.titol) && Objects.equals(categoria, llibre.categoria) && Objects.equals(preu, llibre.preu) && Objects.equals(editorial, llibre.editorial) && Objects.equals(autor, llibre.autor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, titol, categoria, preu, editorial, autor);
    }

    public Collection<Prestec> getPrestecsByIsbn() {
        return prestecsByIsbn;
    }

    public void setPrestecsByIsbn(Collection<Prestec> prestecsByIsbn) {
        this.prestecsByIsbn = prestecsByIsbn;
    }
}
