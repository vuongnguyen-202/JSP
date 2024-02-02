package a2_2001040230.page;

import a2_2001040230.common.DateUtils;
import a2_2001040230.dao.BookDao;
import a2_2001040230.dao.PatronDao;
import a2_2001040230.model.Book;
import a2_2001040230.model.LibraryTransaction;
import a2_2001040230.model.OnShowConfirmDialog;
import a2_2001040230.model.Patron;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class CheckoutBook extends JPanel {

    private final JTextField textFieldDueDate;

    PatronDao patronDao;
    BookDao bookDao;

    public CheckoutBook(OnShowConfirmDialog onShowConfirmDialog) {

        setSize(600, 400);
        setLayout(null);

        // transactionDao = new TransactionDao();
        patronDao = new PatronDao();
        bookDao = new BookDao();

        // Patron
        JLabel patronLabel = new JLabel("Patron");
        patronLabel.setBounds(50, 60, 200, 16);
        add(patronLabel);

        List<Patron> patrons = null;
        try {
            patrons = patronDao.getAll();
        } catch (SQLException | ParseException e) {
            System.out.println(e.getMessage());
        }

        Vector modelPatron = new Vector();
        modelPatron.addAll(patrons);

        JComboBox comboBoxPatron = new JComboBox(modelPatron);
        comboBoxPatron.setSelectedIndex(0);
        comboBoxPatron.setBounds(200, 55, 250, 27);
        comboBoxPatron.addActionListener(e -> {

        });
        add(comboBoxPatron);

        // Book
        JLabel authorLabel = new JLabel("Book");
        authorLabel.setBounds(50, 100, 200, 16);
        add(authorLabel);

        List<Book> books = null;
        try {
            books = bookDao.getAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Vector modelBook = new Vector();
        modelBook.addAll(books);

        JComboBox comboBoxBook = new JComboBox(modelBook);
        comboBoxBook.setSelectedIndex(0);
        comboBoxBook.setBounds(200, 95, 250, 27);
        comboBoxBook.addActionListener(e -> {
        });
        add(comboBoxBook);

        // Checkout Date
        JLabel checkoutDateLabel = new JLabel("Checkout Date");
        checkoutDateLabel.setBounds(50, 140, 200, 16);
        add(checkoutDateLabel);

        JLabel currentDateValue = new JLabel(DateUtils.getCurrentDateStr());
        currentDateValue.setBounds(200, 140, 130, 16);
        add(currentDateValue);

        // Due Date
        JLabel dueDateLabel = new JLabel("Due Date");
        dueDateLabel.setBounds(50, 180, 130, 16);
        add(dueDateLabel);

        textFieldDueDate = new JTextField();
        textFieldDueDate.setBounds(200, 175, 350, 26);
        add(textFieldDueDate);

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

        // Button Checkout
        JButton btnCheckout = new JButton("Checkout");
        btnCheckout.setBounds(240, 300, 120, 29);

        btnCheckout.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                labelSuccess.setText("");
                labelError.setText("");

                try {
                    String dueDateStr = textFieldDueDate.getText();
                    String checkoutDateStr = currentDateValue.getText();

                    Book book = (Book) comboBoxBook.getSelectedItem();
                    Patron patron = (Patron) comboBoxPatron.getSelectedItem();

                    Date dueDate, checkoutDate;

                    try {
                        dueDate = DateUtils.convertDateFormat(dueDateStr);
                        checkoutDate = DateUtils.convertDateFormat(checkoutDateStr);

                        if (dueDate.compareTo(checkoutDate) < 0) {
                            throw new Exception("Due Date must be more than Checkout Date");
                        }
                    } catch (ParseException exception) {
                        labelError.setText("Due Date is wrong format");
                        return;
                    } catch (Exception exception) {
                        labelError.setText(exception.getMessage());
                        return;
                    }
                    LibraryTransaction transaction = new LibraryTransaction(patron.getId(), book.getId(),
                            checkoutDate, dueDate);
                    onShowConfirmDialog.showConfirmDialog(transaction);
                } catch (Exception e2) {
                    labelError.setText(e2.getMessage());
                }
            }
        });

        add(btnCheckout);
    }
}
