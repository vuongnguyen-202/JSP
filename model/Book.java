package a2_2001040230.model;

import a2_2001040230.common.Genre;

public class Book {

    private int id;

    private String isbn;
    private String title;
    private String author;
    private Genre genre;
    private int publicYear;
    private int numCopiesAvailable;

    public Book(String title, String author, Genre genre, int publicYear, int numCopiesAvailable) throws Exception {
        if (!validateTitle(title)) {
            throw new Exception("Title is invalid");
        }
        if (!validateAuthor(author)) {
            throw new Exception("Author is invalid");
        }
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicYear = publicYear;
        this.numCopiesAvailable = numCopiesAvailable;
        getIsbn();
    }

    public Book(int id, String isbn, String title, String author, Genre genre, int publicYear, int numCopiesAvailable) throws Exception {
        this(title, author, genre, publicYear, numCopiesAvailable);
        this.id = id;
        this.isbn = isbn;
    }

    public String getIsbn() {
        if (this.isbn == null) {
            String isbn = "-" + publicYear;
            int indexGenre = genre.ordinal() + 1;
            if (indexGenre < 10) {
                isbn = "0" + indexGenre + isbn;
            } else {
                isbn = indexGenre + isbn;
            }
            String[] splitAuthorName = author.split(" ");
            String abbreviateAuthorName = "";
            if (splitAuthorName.length != 0) {
                if (splitAuthorName.length < 2) {
                    abbreviateAuthorName += splitAuthorName[0].charAt(0);
                } else {
                    abbreviateAuthorName = abbreviateAuthorName + splitAuthorName[0].charAt(0) + splitAuthorName[1].charAt(0);
                }
                isbn = abbreviateAuthorName + "-" + isbn;
            }
            setIsbn(isbn);
        }
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getPublicYear() {
        return publicYear;
    }

    public void setPublicYear(int publicYear) {
        this.publicYear = publicYear;
    }

    public int getNumCopiesAvailable() {
        return numCopiesAvailable;
    }

    public void setNumCopiesAvailable(int numCopiesAvailable) {
        this.numCopiesAvailable = numCopiesAvailable;
    }

    public void checkoutBook() {
        numCopiesAvailable -= 1;
    }

    public void returnBook() {
        numCopiesAvailable += 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private boolean validateTitle(String title) {
        return title != null && !title.isEmpty();
    }

    private boolean validateAuthor(String author) {
        return author != null && !author.isEmpty();
    }

    @Override
    public String toString() {
        return this.title;
    }
}
