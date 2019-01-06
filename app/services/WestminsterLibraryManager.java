package services;

import dto.*;
import io.ebean.Ebean;
import models.BookModel;
import models.DVDModel;

import java.time.LocalDateTime;
import java.util.*;

public class WestminsterLibraryManager implements LibraryManager {


    @Override
    /**
     * Accept a book object as a param instead of multiple varioubles
     */
    public void addBook(Book book) {
        List<Book> books = getAllBooks();

        if(books.size() <=100){
            Ebean.save(mapBookModel(book));
        }else{
            throw  new Error("Out of storage");
        }

    }

    @Override
    public void addDvd(Dvd dvd) {
        List<Dvd> dvds = getAllDvds();

        if(dvds.size() <=50){
            Ebean.save(mapDvdModel(dvd));
        }else{
            throw  new Error("Out of storage");
        }
    }

    @Override
    public List<Book> getAllBooks() {
        List<BookModel> bookModels = Ebean.find(BookModel.class).findList();

        List<Book> books = new ArrayList<>();

        for (BookModel bookModel : bookModels) {
            Book book = getBookDTObyModel(bookModel);
            books.add(book);
        }

        return books;
    }

    @Override
    public List<Dvd> getAllDvds() {
        List<DVDModel> dvdModels = Ebean.find(DVDModel.class).findList();

        List<Dvd> dvds = new ArrayList<>();

        for (DVDModel dvdModel : dvdModels) {
            Dvd dvd = getDvdDTObyModel(dvdModel);
            dvds.add(dvd);
        }

        return dvds;
    }

    @Override
    public DeleteView deleteBook(String isbn) {

        Ebean.find(BookModel.class).where().eq("isbn",isbn).delete();

        List<Book> books = getAllBooks();

        int occupiedSpace = books.size();

        DeleteView deleteView = new DeleteView();

        deleteView.setItemType("Book");
        deleteView.setSpaceLeft(100 - occupiedSpace);

        return  deleteView;
    }

    @Override
    public DeleteView deleteDvd(String isbn) {

        Ebean.find(DVDModel.class).where().eq("isbn",isbn).delete();

        List<Dvd> Dvds = getAllDvds();

        int occupiedSpace = Dvds.size();

        DeleteView deleteView = new DeleteView();

        deleteView.setItemType("Dvd");
        deleteView.setSpaceLeft(50 - occupiedSpace);

        return  deleteView;
    }

    @Override
    public Book getbookbyIsbn (String Isbn){
        BookModel bookModels = Ebean.
                find(BookModel.class).
                where().
                eq("isbn",Isbn).
                findUnique();
        if(bookModels != null){
            return  getBookDTObyModel(bookModels);
        }else{

        }
        return null;
    }

    @Override
    public Dvd getDvdbyIsbn (String Isbn){
        DVDModel dvdModel = Ebean.find(DVDModel.class).findUnique();
        if(dvdModel != null){
            return  getDvdDTObyModel(dvdModel);
        }else{

        }
        return null;
    }

    @Override
    public String updateBook(Book book,String isbn,boolean isBurrow){
        Book bookObj = getbookbyIsbn(isbn);

        if(bookObj != null){
            if(isBurrow && bookObj.getReader() == 0 ){
                Ebean.update(mapBookModel(book));
                return  "Burrow success";
            }else if(isBurrow && bookObj.getReader() != 0){
                return "Book Already burrowed. Book will be available on "+ stringToDate(bookObj.getBorrowedDate()).plusDays(7);
            }
        }else{
            throw new Error("Book not found");
        }

        return null;
    }

    @Override
    public String updateDvd(Dvd dvd,String isbn,boolean isBurrow){

        Dvd dvdObj = getDvdbyIsbn(isbn);

        if(dvdObj != null){
            if(isBurrow && dvdObj.getReader() == 0){
                Ebean.update(mapDvdModel(dvd));
                return  "Burrow Success";
            }
            else if(isBurrow && dvdObj.getReader() != 0){
                return "Book Already burrowed. Book will be available on "+ stringToDate(dvdObj.getBorrowedDate()).plusDays(7);
            }

        }else{
            throw  new Error("Dvd not found");
        }
        return  null;
    }

    public List<ReportView> getReport(){
        List<Book> books= getAllBooks();
        List<Dvd> dvds = getAllDvds();
        List<ReportView> reportViews = new ArrayList<ReportView>();
        for (Book book:books) {
            ReportView reportView = mapReportView(book,"book");

            if(reportView.getFine() != 0.0){
                reportViews.add(reportView);
            }
        }

        for (Dvd dvd:dvds) {
            ReportView reportView = mapReportView(dvd,"dvd");

            if(reportView.getFine() != 0.0){
                reportViews.add(reportView);
            }
        }

        return  reportViews;
    }



    private Book getBookDTObyModel(BookModel bookModel) {
        Book book = new Book();
        book.setIsbn(bookModel.getIsbn());
        book.setTitle(bookModel.getTitle());
        book.setSector(bookModel.getSector());
        book.setPublicationDate(bookModel.getPublicationDate());
        book.setBorrowedDate(bookModel.getBorrowedDate());
        book.setPublisher(bookModel.getPublisher());
        book.setTotalPages(bookModel.getTotalNumberOfPages());
        book.setReader(1);
        //book.setAuthors(bookModel.arrayToString());


        //TODO: write a method to get author list.

        return book;
    }

    private Dvd getDvdDTObyModel(DVDModel dvdModel) {
        Dvd dvd = new Dvd();
        dvd.setIsbn(dvdModel.getIsbn());
        dvd.setTitle(dvdModel.getTitle());
        dvd.setSector(dvdModel.getSector());
        dvd.setPublicationDate(dvdModel.getPublicationDate());
        dvd.setBorrowedDate(dvdModel.getBorrowedDate());
        dvd.setBorrowedTime(dvdModel.getBorrowedTime());
        dvd.setActors(stringToArray(dvdModel.getActors()));
        dvd.setLanguages(stringToArray(dvdModel.getLanguages()));
        dvd.setReader(dvd.getReader());
        dvd.setSubtitles(stringToArray(dvdModel.getSubtitles()));

        //TODO: write a method to get author list.

        return dvd;
    }





    private BookModel mapBookModel(Book book){
        BookModel bookModel = new BookModel();

        bookModel.setIsbn(book.getIsbn());
        bookModel.setTitle(book.getTitle());
        bookModel.setSector(book.getSector());
        bookModel.setPublicationDate(book.getPublicationDate());
        bookModel.setBorrowedDate(book.getBorrowedDate());
        bookModel.setBorrowedTime(book.getBorrowedTime());
        bookModel.setPublisher(book.getPublisher());
        bookModel.setTotalNumberOfPages(book.getTotalPages());
        bookModel.setReader(book.getReader());
        bookModel.setAuthors(arrayToString(book.getAuthors()));

        return  bookModel;
    }

    private DVDModel mapDvdModel(Dvd dvd){
        DVDModel dvdModel = new DVDModel();

        dvdModel.setIsbn(dvd.getIsbn());
        dvdModel.setTitle(dvd.getTitle());
        dvdModel.setSector(dvd.getSector());
        dvdModel.setPublicationDate(dvd.getPublicationDate());
        dvdModel.setBorrowedDate(dvd.getBorrowedDate());
        dvdModel.setBorrowedTime(dvd.getBorrowedTime());
        dvdModel.setActors(arrayToString(dvd.getActors()));
        dvdModel.setLanguages(arrayToString(dvd.getLanguages()));
        dvdModel.setReader(dvd.getReader());
        dvdModel.setSubtitles(arrayToString(dvd.getSubtitles()));

        return  dvdModel;
    }

    private String arrayToString(String[] arr){
        String arrString = null;
        for (int i = 0; i <arr.length ; i++) {

            if(i == 0){
                arrString = arr[i];
            }else if(i != arr.length -1){
                arrString = "," + arr[i];
            }else{
                arrString = arr[i];
            }
        }
        return arrString;
    }

    private String[] stringToArray(String stringObj){
        return  stringObj.split(",");
    }


    private Double calculateFine(String burrowedDate, int fineAfter){
        HashMap<String, String> dateMap = new HashMap<>();
        LocalDateTime dateValues = LocalDateTime.parse(burrowedDate);

        DateTime burrowedDateTime = new DateTime(dateValues.getDayOfMonth(),dateValues.getMonthValue(),dateValues.getYear(),dateValues.getHour(),dateValues.getMinute());
        Date date = new Date();
        Double fine = 0.0;

        DateTime currentDate = new DateTime(date.getDay(),date.getMonth(),date.getYear(),date.getHours(),date.getMinutes());

        if(burrowedDateTime.getDay() + fineAfter > currentDate.getDay()){
            int finedDays = burrowedDateTime.getDay() + fineAfter - currentDate.getDay();

            if(finedDays <=3){
                fine = fine*24*0.2;
            }else {
                Double normalfine = 3 *24*0.2;
                Double extraFine = (finedDays -3) * 0.5;

                fine = normalfine+extraFine;
            }
        }

        return fine;
    }

    private ReportView mapReportView(LibraryItem libraryItem, String itemType){
        ReportView reportView = new ReportView();

        reportView.setISBN(libraryItem.getIsbn());
        reportView.setTitle(libraryItem.getTitle());
        reportView.setBurrowedBy(libraryItem.getReader());
        reportView.setBurrowedDate(libraryItem.getBorrowedDate());
        int finedDays = itemType.equalsIgnoreCase("book") ? 7 : 3;
        reportView.setFine(calculateFine(libraryItem.getBorrowedDate(),finedDays));

        return  reportView;
    }

    private LocalDateTime stringToDate(String date){
        return LocalDateTime.parse(date);

    }

}
