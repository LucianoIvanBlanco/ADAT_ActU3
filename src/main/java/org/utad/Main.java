package org.utad;

import models.Authors;
import models.Books;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Session session = null;

    public static void main(String[] args) {
        try {
            session = HibernateUtil.getSession();

            // Menu interactivo
            int option;
            do {
                mostrarMenu();
                Scanner sc = new Scanner(System.in);
                option = sc.nextInt();
                sc.nextLine();                                      // Consumimos el salto de línea

                switch (option) {
                    case 1:
                        createAuthorAndBooks();
                        break;
                    case 2:
                        readAuthorAndBooks();
                        break;
                    case 3:
                        updateTables();
                        break;
                    case 4:
                        deleteAuthor(checkDni());
                        break;
                    case 5:
                        deleteBook(checkId());
                        break;
                    case 6:
                        queryBooksByAuthor(checkDni());
                        break;
                    case 7:
                        queryAuthorsByPublicationYearRange();
                        break;
                    case 0:
                        System.out.println("Saliendo del programa. ¡Hasta luego!");
                        break;
                    default:
                        System.out.println("Opción no válida. Inténtelo de nuevo.");
                }
            } while (option != 0);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSession(session);
        }
    }

    private static void mostrarMenu() {
        System.out.println("=== Menú Principal ===");
        System.out.println("1. Crear autor y libros");
        System.out.println("2. Mostrar autores y libros");
        System.out.println("3. Actualizar valores de autores y libros");
        System.out.println("4. Eliminar autor");
        System.out.println("5. Eliminar libro");
        System.out.println("6. Buscar libros de un autor especifico");
        System.out.println("7. Buscar autores que han publicado libros en un rango de años específico");
        System.out.println("0. Salir");
        System.out.println("=======================");
        System.out.print("Seleccione una opción: ");
    }


    private static void createAuthorAndBooks() {                        // Creamos autores y libros deseados
        session.beginTransaction();

        Authors author1 = new Authors();
        author1.setDni(12345678);
        author1.setName("J. R. R. Tolkien");
        session.persist(author1);

        Books book1 = new Books();
        book1.setId(book1.getId());
        book1.setTitle("El señor de los anillos");
        LocalDate date1 = LocalDate.of(1954, 7, 29);
        book1.setPublicationDate(java.sql.Date.valueOf(date1));  // Utiliza java.sql.Date
        book1.setDniAuthor(author1.getDni());
        book1.setEditorial("U-tadBooks");
        session.persist(book1);

        Books book2 = new Books();
        book2.setId(book2.getId());
        book2.setTitle("Beowulf");
        LocalDate date2 = LocalDate.of(2014, 5, 22);
        book2.setPublicationDate(java.sql.Date.valueOf(date2));  // Utiliza java.sql.Date
        book2.setDniAuthor(author1.getDni());
        book2.setEditorial("U-tadBooks");
        session.persist(book2);

        session.getTransaction().commit();
    }

    private static void readAuthorAndBooks() {                              // Imprimimos autores y libros existentes

        List<Authors> authorsList = session.createQuery("FROM Authors", Authors.class).list();
        for (Authors at : authorsList) {
            System.out.println("===== AUTOR: ===== " +
                    " \nDNI: " + at.getDni() + ", Nombre: " + at.getName() + ", \n===== LIBROS: =====");
        }

        List<Books> bookList = session.createQuery("FROM Books", Books.class).list();
        for (Books bk : bookList) {
            System.out.println(" ID: " + bk.getId() + ", Nombre: " + bk.getTitle() + ", Author: " + bk.getDniAuthor() +
                    ", Fecha de publicacion: " + bk.getPublicationDate() + ", Editorial: " + bk.getEditorial());
        }
        System.out.println("================================================================");

    }


    private static void updateTables() {                                     // Actualizamos valores de autores y libros

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

    private static String checkDni() {
        // Elegimos el autor que queremos eliminar
        System.out.println("Ingrese el dni del autor: ");
        Scanner sc = new Scanner(System.in);
        String dni = sc.nextLine();
        return dni;
    }


    private static int checkId() {
        // Elegimos el libro que queremos eliminar
        System.out.println("Ingrese el id del libro que desea eliminar: ");
        Scanner sc = new Scanner(System.in);
        int idDelete = sc.nextInt();
        return idDelete;
    }

    private static void deleteAuthor(String dni) {                                   // Eliminamos autores

        Authors author = session.get(Authors.class, dni);
        if (author != null) {
            session.beginTransaction();
            session.remove(author);
            session.getTransaction().commit();

            System.out.println("Autor eliminado correctamente.");
            readAuthorAndBooks();
        } else {
            System.out.println("No se encontro el autor con el dni: " + dni);
        }
    }

    private static void deleteBook(int id) {                                            // Eliminamos libros

        Books book = session.get(Books.class, id);
        if (book != null) {
            session.beginTransaction();
            session.remove(book);
            session.getTransaction().commit();

            System.out.println("Libro eliminado correctamente.");
            readAuthorAndBooks();
        } else {
            System.out.println("No se encontro el libro con el ID: " + id);
        }
    }

    private static void queryBooksByAuthor(String authorDni) {
        String hql = "FROM Books WHERE dniAuthor = :authorDni";
        Query<Books> query = session.createQuery(hql, Books.class);
        query.setParameter("authorDni", authorDni);
        List<Books> booksList = query.list();

        System.out.println("=== Libros del autor ===");
        for (Books bk : booksList) {
            System.out.println("ID: " + bk.getId() + ", Nombre: " + bk.getTitle() + ", Fecha de publicacion: " + bk.getPublicationDate() + ", Editorial: " + bk.getEditorial());
        }
        System.out.println("========================");
    }

    // Método para encontrar autores que han publicado libros en un rango de años específico usando HQL
    private static void queryAuthorsByPublicationYearRange() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el año desde el cual quiere comenzar a buscar: ");
        int startYear = sc.nextInt();
        System.out.println("Ingrese el año hasta el cual quiere buscar:");
        int endYear = sc.nextInt();
        String hql = "SELECT DISTINCT a FROM Authors a JOIN FETCH a.books b WHERE YEAR(b.publicationDate) BETWEEN :startYear AND :endYear";
        Query<Authors> query = session.createQuery(hql, Authors.class);
        query.setParameter("startYear", startYear);
        query.setParameter("endYear", endYear);
        List<Authors> authorsList = query.list();

        System.out.println("=== Autores que han publicado libros en el rango de años ===");
        for (Authors at : authorsList) {
            System.out.println("DNI: " + at.getDni() + ", Nombre: " + at.getName());
        }
        System.out.println("===========================================================");
    }

}

