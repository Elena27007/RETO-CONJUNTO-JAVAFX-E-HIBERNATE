package org.example.retoconjunto_javafx_hibernate.models;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Clase que representa una copia de una película.
 */
@Data
@Entity
@Table(name = "Copias")
public class Copia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String estado;

    private String soporte;

    @ManyToOne
    @JoinColumn(name = "id_pelicula")
    private Pelicula pelicula;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Override
    public String toString() {
        return "Copia{" +
                "id=" + id +
                ", estado='" + estado + '\'' +
                ", soporte='" + soporte + '\'' +
                ", pelicula=" + pelicula +
                ", usuario=" + usuario +
                '}';
    }
}