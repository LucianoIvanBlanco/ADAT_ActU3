package models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "authors", schema = "public", catalog = "postgres")
public class Authors {
    private int dni;
    private String name;
    private int idLibro;

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "dni")
    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "id_libro")
    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authors authors = (Authors) o;
        return dni == authors.dni && idLibro == authors.idLibro && Objects.equals(name, authors.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni, name, idLibro);
    }
}
