package models;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "books", schema = "public", catalog = "postgres")
public class Books {
    private int id;
    private String title;
    private Date publicationDate;
    private int dniAuthor;  // Cambiado de Integer a int
    private String editorial;
    private Authors author;  // Agregada relación bidireccional

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "publication_date")
    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    @Basic
    @Column(name = "dni_author")
    public int getDniAuthor() {
        return dniAuthor;
    }

    public void setDniAuthor(int dniAuthor) {
        this.dniAuthor = dniAuthor;
    }

    @Basic
    @Column(name = "editorial")
    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    @ManyToOne
    @JoinColumn(name = "dni_author", referencedColumnName = "dni", insertable = false, updatable = false)
    public Authors getAuthor() {
        return author;
    }

    public void setAuthor(Authors author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Books books = (Books) o;
        return id == books.id && dniAuthor == books.dniAuthor && Objects.equals(title, books.title) && Objects.equals(publicationDate, books.publicationDate) && Objects.equals(editorial, books.editorial);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, publicationDate, dniAuthor, editorial);
    }
}

