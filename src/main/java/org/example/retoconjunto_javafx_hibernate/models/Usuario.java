package org.example.retoconjunto_javafx_hibernate.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Clase que representa a un usuario.
 */
@Data
@Entity
@Table(name = "Usuarios")
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    private String contraseña;

    @Column(name = "is_admin")
    private Boolean admin;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Copia> copias;

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", admin=" + admin +
                ", copias=" + copias +
                '}';
    }

    public void addCopia(Copia c) {
        copias.add(c);
        c.setUsuario(this);
    }
}