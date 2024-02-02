package a2_2001040230.page;

import a2_2001040230.dao.BookDao;
import a2_2001040230.model.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Page for listing books
 */
public class ListViewBook extends JPanel {

    private static final String[] COLUMN_NAMES = {"ID", "ISBN", "Title", "Author", "Genre", "Publication Year", "Number Copy Available"};

    private BookDao bookDao;

    public ListViewBook() {
        bookDao = new BookDao();

        List<Book> books = new ArrayList<>();

        try {
            books = bookDao.getAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        setLayout(new BorderLayout());

        JTable table = new JTable();
        table.setModel(initTableModel(books));
        table.setDefaultEditor(Object.class, null); // Disable cell editing

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    private DefaultTableModel initTableModel(List<Book> books) {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(COLUMN_NAMES);

        for (Book book : books) {
            Object[] row = {
                    book.getId(),
                    book.getIsbn(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getGenre().toString(),
                    book.getPublicYear(),
                    book.getNumCopiesAvailable()
            };
            model.addRow(row);
        }

        return model;
    }
}