package a2_2001040230.model;

import a2_2001040230.common.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LibraryTransaction {

//    	"id"	INTEGER NOT NULL UNIQUE,
//	"book_id"	TEXT NOT NULL,
//            "patron_id"	TEXT NOT NULL,
//            "checkoutDate"	TEXT NOT NULL,
//            "dueDate"	TEXT NOT NULL,
//    PRIMARY KEY("id" AUTOINCREMENT)

    private int id;

    private int bookId;

    private int patronId;
    private Date checkoutDate;
    private Date dueDate;
    private Date returnDate;
    private int fineAmount;

    public LibraryTransaction(int patronId, int bookId, Date checkoutDate, Date dueDate) {
        this.bookId = bookId;
        this.patronId = patronId;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
    }

    public LibraryTransaction(int id, int patronId, int bookId, Date checkoutDate, Date dueDate) {
        this(patronId, bookId, checkoutDate, dueDate);
        this.id = id;
    }

    public long calculateFine() {
        if (returnDate == null) {
            return 0;
        }
        int diffDate = calculateDateDiff(returnDate, dueDate);
        if (diffDate <= 7) {
            return diffDate;
        } else if (diffDate <= 14) {
            return diffDate * 2;
        }
        return diffDate * 3L;
    }

    public int calculateDateDiff(Date date1, Date date2) {
        int dateDiff;
        long diff = date1.getTime() - date2.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        dateDiff = (int) diffDays;
        return dateDiff;
    }

    public static String formatDateStr(Date date) {
        SimpleDateFormat formatDate = new SimpleDateFormat("EEE, MMM dd yyyy");
        return formatDate.format(date);
    }

    public String getDescription() {
        String returnDateStr = "TBD";
        if (returnDate != null) {
            returnDateStr = formatDateStr(returnDate);
        }
        return "\nTransaction Details:" +
                "\n    Patron ID: " +
                //"\n    Book ISBN: " + book.getIsbn() +
                "\n    Checkout Date: " + formatDateStr(checkoutDate) +
                "\n    Due Date: " + formatDateStr(dueDate) +
                "\n    Return Date: " + returnDateStr +
                "\n    Fine Amount: $" + calculateFine() +
                "\n";
    }

//    public Patron getPatron() {
//        return patron;
//    }
//
//    public void setPatron(Patron patron) {
//        this.patron = patron;
//    }
//
//    public Book getBook() {
//        return book;
//    }
//
//    public void setBook(Book book) {
//        this.book = book;
//    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(int fineAmount) {
        this.fineAmount = fineAmount;
    }

    public static boolean checkOverDate(Date date) {
        return new DateUtils().getCurrentDate().compareTo(date) > 0;
    }

    public boolean isOverdue() {
        return returnDate == null && checkOverDate(dueDate);
    }

    @Override
    public String toString() {
        return getDescription();
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getPatronId() {
        return patronId;
    }

    public void setPatronId(int patronId) {
        this.patronId = patronId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
