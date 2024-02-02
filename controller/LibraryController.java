package a2_2001040230.controller;

import a2_2001040230.dao.BookDao;
import a2_2001040230.dao.TransactionDao;
import a2_2001040230.model.Book;
import a2_2001040230.model.LibraryTransaction;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class LibraryController {

    private final BookDao bookDao;
    private final TransactionDao transactionDao;

    private static LibraryController controller;

    public static LibraryController getInstance() {
        if (controller == null) {
            controller = new LibraryController();
        }

        return controller;
    }

    public LibraryController() {
        bookDao = new BookDao();
        transactionDao = new TransactionDao();
    }

    public void addTransaction(LibraryTransaction transaction) throws Exception {
        int countTransactionByBook = transactionDao.getCountTransactionByBook(transaction.getBookId());
        Book book = bookDao.filterBookByIds(List.of(transaction.getBookId())).get(0);

        if (book.getNumCopiesAvailable() <= countTransactionByBook) {
            throw new Exception("Not enough Copies Book Available");
        }

        transactionDao.add(transaction);
    }

    public List<Book> getBookByTransaction(int patronId) throws Exception {
        List<LibraryTransaction> transactionsFilter = null;
        try {
            transactionsFilter = transactionDao.getTransactionByPatron(patronId);
        } catch (SQLException | ParseException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

        if (transactionsFilter.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> bookIds = transactionsFilter.stream().map(LibraryTransaction::getBookId).toList();

        return bookDao.filterBookByIds(bookIds);
    }
}
