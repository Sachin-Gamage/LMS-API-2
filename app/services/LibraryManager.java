package services;

import dto.*;

import java.util.List;

public interface LibraryManager {

    default void addBook(Book book) {
    }
    void addDvd(Dvd dvd);

    DeleteView deleteBook(String isbn);
    DeleteView deleteDvd(String isbn);

    List<Book> getAllBooks();
    List<Dvd> getAllDvds();


    Book getbookbyIsbn(String Isbn);
    Dvd getDvdbyIsbn(String Isbn);

    String updateBook(Book book,String isbn,boolean isBurrow);
    String updateDvd(Dvd dvd,String isbn,boolean isBurrow);

    List<ReportView> getReport();
}
