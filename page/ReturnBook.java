package a2_2001040230.page;

import a2_2001040230.controller.LibraryController;
import a2_2001040230.dao.BookDao;
import a2_2001040230.dao.PatronDao;
import a2_2001040230.dao.TransactionDao;
import a2_2001040230.model.Book;
import a2_2001040230.model.Patron;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ReturnBook extends JPanel {

    private PatronDao patronDao;
    private BookDao bookDao;
    private TransactionDao transactionDao;

    private JComboBox<Patron> patronComboBox;
    private JComboBox<Book> bookComboBox;
    private JTextField textFieldReturnDate;

    private List<Patron> patrons;
    private List<Book> books;

    public ReturnBook() {
        setSize(600, 400);
        setLayout(null);

        patronDao = new PatronDao();
        bookDao = new BookDao();
        transactionDao = new TransactionDao();

        // Patron
        JLabel patronLabel = new JLabel("Patron");
        patronLabel.setBounds(50, 60, 130, 16);
        add(patronLabel);

        try {
            patrons = patronDao.getAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            patrons = new ArrayList<>();
        }

        patronComboBox = new JComboBox<>(new DefaultComboBoxModel<>(new Vector<>(patrons)));
        patronComboBox.setSelectedIndex(0);
        patronComboBox.setBounds(200, 55, 250, 27);
        patronComboBox.addActionListener(e -> {
            int selectedPatronIndex = patronComboBox.getSelectedIndex();
            if (selectedPatronIndex >= 0) {
                int patronId = patrons.get(selectedPatronIndex).getId();
                books = findBookByTransaction(patronId);
                bookComboBox.setModel(new DefaultComboBoxModel<>(new Vector<>(books)));
            }
        });

        add(patronComboBox);

        // Book
        JLabel bookLabel = new JLabel("Book");
        bookLabel.setBounds(50, 100, 130, 16);
        add(bookLabel);

        if (!patrons.isEmpty()) {
            int patronId = patrons.get(0).getId();
            books = findBookByTransaction(patronId);
        } else {
            books = new ArrayList<>();
        }

        bookComboBox = new JComboBox<>(new DefaultComboBoxModel<>(new Vector<>(books)));
        bookComboBox.setSelectedIndex(0);
        bookComboBox.setBounds(200, 95, 250, 27);
        add(bookComboBox);

        // Return Date
        JLabel returnDateLabel = new JLabel("Return Date");
        returnDateLabel.setBounds(50, 140, 130, 16);
        add(returnDateLabel);

        textFieldReturnDate = new JTextField();
        textFieldReturnDate.setBounds(200, 135, 350, 26);
        add(textFieldReturnDate);

        // Label Result
        // Error
        JLabel labelError = new JLabel("");
        labelError.setForeground(Color.RED);
        labelError.setHorizontalAlignment(SwingConstants.CENTER);
        labelError.setBounds(0, 260, 600, 16);
        add(labelError);

        // Success
        JLabel labelSuccess = new JLabel("");
        labelSuccess.setForeground(Color.GREEN);
        labelSuccess.setHorizontalAlignment(SwingConstants.CENTER);
        labelSuccess.setBounds(0, 260, 600, 16);
        add(labelSuccess);

        // Button Return
        JButton btnReturnConfirm = new JButton("Return");
        btnReturnConfirm.setBounds(240, 200, 120, 29);
        btnReturnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedPatronIndex = patronComboBox.getSelectedIndex();
                int selectedBookIndex = bookComboBox.getSelectedIndex();

                if (selectedPatronIndex >= 0 && selectedBookIndex >= 0) {
                    int patronId = patrons.get(selectedPatronIndex).getId();
                    int bookId = books.get(selectedBookIndex).getId();

                    try {
                        transactionDao.returnBook(patronId, bookId);
                        labelSuccess.setText("Book returned successfully.");
                        labelError.setText("");
                    } catch (SQLException ex) {
                        labelError.setText("Error returning book: " + ex.getMessage());
                        labelSuccess.setText("");
                    }
                }
            }
        });

        add(btnReturnConfirm);
    }

    public List<Book> findBookByTransaction(int patronId) {
        List<Book> books;
        try {
            books = LibraryController.getInstance().getBookByTransaction(patronId);
            System.out.println("Books: " + books);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            books = new ArrayList<>();
        }

        return books;
    }
}