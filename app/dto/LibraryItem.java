
package dto;

public abstract class LibraryItem {

    private String isbn;
    private String title;
    private String sector;
    private String publicationDate;
    private String borrowedDate;
    private String borrowedTime;
    private int reader;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(String borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public String getBorrowedTime() {
        return borrowedTime;
    }

    public void setBorrowedTime(String borrowedTime) {
        this.borrowedTime = borrowedTime;
    }

    public int getReader() {
        return reader;
    }

    public void setName(String title){
        this.title = title;
    }

    public void setReader(int reader) {
        this.reader = reader;
    }
}
