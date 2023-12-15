package org.utad;

import models.Authors;
import models.Books;
import org.hibernate.Session;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Session session = null;

    public static void main(String[] args) {

        try {
            session = HibernateUtil.getSession();
            // Operaciones CRUD
//            createAuthorAndBooks();                                      // Creamos autores y libros deseados
            readAuthorAndBooks();                                        // Imprimimos autores y libros existentes
//            updateTables();                                              // Actualizamos valores de autores y libros
            deleteAuthor(checkDni());                                  // Eliminamos autores
//            deleteBook(checkId());                                     // Eliminamos libros
//
//            // Consultas HQL
//            queryBooksByAuthor();
//            queryAuthorsByPublicationYearRange();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSession(session);
        }

    }

    private static int checkId() {

        // Elegimos el libro que queremos eliminar
        System.out.println("Ingrese el id del libro que desea eliminar: ");
        Scanner sc = new Scanner(System.in);
        int idDelete = sc.nextInt();
        return idDelete;
    }

    private static String checkDni() {
        // Elegimos el autor que queremos eliminar
        System.out.println("Ingrese el dni del autor que desea eliminar: ");
        Scanner sc = new Scanner(System.in);
        String dniDelete = sc.nextLine();
        return dniDelete;
    }


    private static void createAuthorAndBooks() {
        session.beginTransaction();

        Authors author1 = new Authors();
        author1.setDni(12345678);
        author1.setName("J. R. R. Tolkien");
        session.persist(author1);

        Books book1 = new Books();
        book1.setId(book1.getId());
        book1.setTitle("El señor de los anillos");
        Date date1 = new Date(1954 / 7 / 29);
        book1.setPublicationDate(date1);
        book1.setDniAuthor(author1.getDni());
        book1.setEditorial("U-tadBooks");
        session.persist(book1);


        Books book2 = new Books();
        book2.setId(book2.getId());
        book2.setTitle("Beowulf");
        Date date2 = new Date(2014 / 5 / 22);
        book2.setPublicationDate(date2);
        book2.setDniAuthor(author1.getDni());
        book2.setEditorial("U-tadBooks");
        session.persist(book2);

        session.getTransaction().commit();
    }

    private static void readAuthorAndBooks() {
        List<Authors> authorsList = session.createQuery("FROM Authors", Authors.class).list();

        for (Authors at : authorsList) {
            System.out.println("AUTOR: -----------------------------------------------------------" +
                    " \n-DNI: " + at.getDni() + ", Nombre: " + at.getName() + ", \n Libros:");
        }

        List<Books> bookList = session.createQuery("FROM Books", Books.class).list();

        for (Books bk : bookList) {
            System.out.println(" ID: " + bk.getId() + ", Nombre: " + bk.getTitle() + ", Author: " + bk.getDniAuthor() +
                    ", Fecha de publicacion: " + bk.getPublicationDate() + ", Editorial: " + bk.getEditorial());
        }
        System.out.println("--------------------------------------------------------------------------------");

    }


    private static void updateTables() {

        System.out.println("1 --> para actualizar editorial del libro \n2 --> para actualizar nombre del autor");
        Scanner sc = new Scanner(System.in);
        int idUpdate = sc.nextInt();
        sc.nextLine();
        if (idUpdate == 1) {
            System.out.println("Ingrese el ID del libro que desea actualizar: ");
            int bookId = sc.nextInt();
            sc.nextLine();
            Books book = session.get(Books.class, bookId);

            if (book != null) {
                // Actualizamos la propiedad que querramos
                System.out.println("Ingrese el nombre de la nueva editorial:");
                String newName = sc.nextLine();
                book.setEditorial(newName);
                session.beginTransaction();
                session.merge(book);
                session.getTransaction().commit();

                System.out.println("Editorial actualizado correctamente");
                readAuthorAndBooks();

            } else {
                System.out.println("No se encontro el libro con el ID: " + bookId);
            }

        } else if (idUpdate == 2) {
            System.out.println("Ingrese el dni del autor que desea actualizar: ");
            String authorDni = sc.nextLine();
            Authors author = session.get(Authors.class, authorDni);

            if (author != null) {
                // Actualizamos la propiedad que querramos
                System.out.println("Ingrese el nuevo nombre del autor: ");
                String newName = sc.nextLine();
                author.setName(newName);

                session.beginTransaction();
                session.merge(author);
                session.getTransaction().commit();

                System.out.println("Nombre actualizado correctamente");
                readAuthorAndBooks();


            } else {
                System.out.println("No se encontro el autor con el DNI: " + authorDni);
            }
        } else {
            System.out.println("Valores incorrectos");
        }

    }

    private static void deleteAuthor(String dni) {

        Authors author = session.get(Authors.class, dni);
        if (author != null) {
            // Iniciamos la transaccion y eliminamos al autor
            session.beginTransaction();
            session.remove(author);
            session.getTransaction().commit();

            System.out.println("Autor eliminado correctamente.");
            readAuthorAndBooks();
        } else {
            System.out.println("No se encontro el autor con el dni: " + dni);
        }
    }

    private static void deleteBook(int id) {

        Books book = session.get(Books.class, id);
        if (book != null) {
            // Iniciamos la transaccion y eliminamos el libro
            session.beginTransaction();
            session.remove(book);
            session.getTransaction().commit();

            System.out.println("Libro eliminado correctamente.");
            readAuthorAndBooks();
        } else {
            System.out.println("No se encontro el libro con el ID: " + id);
        }
    }

}

