package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import dto.*;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import scala.util.parsing.json.JSONObject;
import services.LibraryManager;
import services.WestminsterLibraryManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    LibraryManager libraryManager = new WestminsterLibraryManager();

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */

    public Result index() {

        return ok(Json.toJson("Welcome to lms"));
    }

    /**
     * Add book
     * @return
     */

    public Result addBook() {

        JsonNode body = request().body().asJson();
        Book book = new Book();
        if(body !=null){
            book = mapBookObj(body);
        }else{
            System.out.println("");
        }
        libraryManager.addBook(book);

        return ok("Adding new book successful");
    }

    /**
     * Add Dvd
     * @return
     */

    public Result addDvd(){
        JsonNode body = request().body().asJson();
        Dvd dvd = new Dvd();
        if(body !=null){
            dvd = mapDvdObj(body);
        }else{
            System.out.println("");
        }
        libraryManager.addDvd(dvd);

        return ok("Adding new dvd successful");
    }

    /**
     * Get Books
     * @return
     */

    public Result  books() {

        List<Book> books = libraryManager.getAllBooks();

        return ok(Json.toJson(books));
    }

    /**
     * Get Dvds
     * @return
     */

    public Result  dvds() {

        List<Dvd> dvds = libraryManager.getAllDvds();

        return ok(Json.toJson(dvds));
    }

    /**
     * Get All Library Items
     * @return
     */

    public Result getAllItems(){
        List<Dvd> dvds = libraryManager.getAllDvds();

        List<Book> books = libraryManager.getAllBooks();

        List<ListView> libraryItems = new ArrayList<>();

        for (Dvd dvd:dvds) {
            ListView libraryItem = new ListView();

            libraryItem.setIsbn(dvd.getIsbn());
            libraryItem.setTitle(dvd.getTitle());
            libraryItem.setType("dvd");
            libraryItems.add(libraryItem);
        }

        for (Book book:books) {
            ListView libraryItem = new ListView();

            libraryItem.setIsbn(book.getIsbn());
            libraryItem.setTitle(book.getTitle());
            libraryItem.setType("book");
            libraryItems.add(libraryItem);
        }


       return ok(Json.toJson(libraryItems));

    }

    /**
     * Delete Book
     * @return
     */

    public Result deleteBook(){
        String isbn  = request().getQueryString("isbn");


        DeleteView deleteView = libraryManager.deleteBook(isbn);

        return ok(Json.toJson(deleteView));
    }

    /**
     * Delete Dvd
     * @return
     */

    public Result deleteDvd(){
        String isbn  = request().getQueryString("isbn");


        libraryManager.deleteDvd(isbn);

        return ok("dvd deleted");
    }

    /**
     * Get book ISBN
     * @return
     */

    public Result getBookByISBN(){
        String isbn = request().getQueryString("isbn");
        Book book;
        if(isbn != null) {
            book = libraryManager.getbookbyIsbn(isbn);
            return ok(Json.toJson(book));
        }else{
            return ok(Json.toJson("Book Not found"));
        }

    }

    /**
     * Fet Dvd by isbn
     * @return
     */

    public Result getDvdByISBN(){
        String isbn = request().getQueryString("isbn");
        Dvd dvd;
        if(isbn != null) {
            dvd = libraryManager.getDvdbyIsbn(isbn);
            return ok(Json.toJson(dvd));
        }else{
            return ok(Json.toJson("Dvd Not found"));
        }

    }

    /**
     * Burrow Book
     * @return
     */

    public Result burrowBook(){
        JsonNode body = request().body().asJson();
        String isbn  = request().getQueryString("isbn");

        String results = libraryManager.updateBook(mapBookObj(body),isbn,true);

        return ok(Json.toJson(results));

    }

    public Result returnBook(){
        JsonNode body = request().body().asJson();
        String isbn  = request().getQueryString("isbn");


        libraryManager.updateBook(mapBookObj(body),isbn,false);

        return ok(Json.toJson("Book Not found"));

    }

    public Result burrowDvd(){
        JsonNode body = request().body().asJson();
        String isbn  = request().getQueryString("isbn");


        libraryManager.updateDvd(mapDvdObj(body),isbn,true);

        return ok(Json.toJson("Dvd Not found"));

    }

    public Result returnDvd(){
        JsonNode body = request().body().asJson();
        String isbn  = request().getQueryString("isbn");


        libraryManager.updateDvd(mapDvdObj(body),isbn,false);

        return ok(Json.toJson("Dvd Not found"));

    }

    public Result getReport(){
        List<ReportView> reportViews = libraryManager.getReport();

        return ok(Json.toJson(reportViews));
    }

    private Book mapBookObj(JsonNode body){
        Book book = new Book();
        if(
            body.get("ISBN") !=null &&
            body.get("title") != null &&
            body.get("sector") != null &&
            body.get("pubDate") != null &&
            body.get("publisher") != null &&
            body.get("noOfPages") != null &&
            body.get("authors") != null
        ){
            book.setIsbn(body.get("ISBN").asText());
            book.setTitle(body.get("title").asText());
            book.setSector(body.get("sector").asText());
            book.setPublicationDate(body.get("pubDate").asText());
            book.setPublisher(body.get("publisher").asText());
            book.setTotalPages(body.get("noOfPages").asInt());
            String[] authors = body.get("authors").asText().split(",");
            book.setAuthors(authors);

            if(body.get("borrowedDate") != null){
                book.setBorrowedDate(body.get("borrowedDate").asText());
            }

            if(body.get("borrowedTime") != null){
                book.setBorrowedTime(body.get("borrowedTime").asText());
            }

            if(body.get("reader") != null){
                book.setReader(body.get("reader").asInt());
            }
            return book;


        }else{
            return null;
        }
    }

    private Dvd mapDvdObj(JsonNode body){
        Dvd dvd = new Dvd();
        if(
            body.get("isbn") !=null &&
            body.get("title") != null &&
            body.get("sector") != null &&
            body.get("publicationDate") != null &&
            body.get("publisher") != null &&
            body.get("totalPages") != null &&
            body.get("authors") != null
        ){
            dvd.setIsbn(body.get("isbn").asText());
            dvd.setTitle(body.get("title").asText());
            dvd.setSector(body.get("sector").asText());
            dvd.setPublicationDate(body.get("publicationDate").asText());
            dvd.setActors(body.get("actors").asText().split(","));
            dvd.setLanguages(body.get("languages").asText().split(","));
            dvd.setSubtitles(body.get("subtitles").asText().split(","));
            dvd.setProducer(body.get("producer").asText());

            if(body.get("borrowedDate") != null){
                dvd.setBorrowedDate(body.get("borrowedDate").asText());
            }

            if(body.get("borrowedTime") != null){
                dvd.setBorrowedTime(body.get("borrowedTime").asText());
            }

            if(body.get("reader") != null){
                dvd.setReader(body.get("reader").asInt());
            }
            return dvd;


        }else{
            return null;
        }
    }



}
