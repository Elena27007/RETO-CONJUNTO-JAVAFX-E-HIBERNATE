package org.example.retoconjunto_javafx_hibernate.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una película.
 */
@Data
@Entity
@Table(name = "Peliculas")
public class Pelicula implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String genero;

    private Long año;

    @Lob
    private String descripcion;

    private String director;

    @OneToMany(mappedBy = "pelicula", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Copia> copias = new ArrayList<>();

    @Override
    public String toString() {
        return "Pelicula{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", genero='" + genero + '\'' +
                ", año=" + año +
                ", descripcion='" + descripcion + '\'' +
                ", director='" + director + '\'' +
                ", copias=" + copias +
                '}';
    }

    public void addCopia(Copia c) {
        copias.add(c);
        c.setPelicula(this);
    }
}