package a2_2001040230.page;

import a2_2001040230.common.DateUtils;
import a2_2001040230.controller.LibraryController;
import a2_2001040230.dao.TransactionDao;
import a2_2001040230.model.LibraryTransaction;
import a2_2001040230.model.OnShowConfirmDialog;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Swing UI
 */
public class SwingPageUI implements OnShowConfirmDialog {

    private JPanel body;
    private JFrame frame;

    private final TransactionDao transactionDao;

    private static final String fileMenu = "File";
    private static final String patronMenu = "Patron";
    private static final String bookMenu = "Book";
    private static final String transactionMenu = "Transaction";

    public SwingPageUI() {
        initializeUI();
        frame.setVisible(true);
        transactionDao = new TransactionDao();
    }

    @Override
    public void showConfirmDialog(LibraryTransaction transaction) {
        JDialog dialog = new JDialog(frame);

        dialog.setLayout(new BorderLayout());
        dialog.setTitle("Confirm Checkout");

        // Panel for the content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(4, 2));

        // Patron
        JLabel patronLabel = new JLabel("Patron:");
        JLabel patronValue = new JLabel("Patron Value");
        contentPanel.add(patronLabel);
        contentPanel.add(patronValue);

        // Book
        JLabel bookLabel = new JLabel("Book:");
        JLabel bookValue = new JLabel("Book Value");
        contentPanel.add(bookLabel);
        contentPanel.add(bookValue);

        // Checkout Date
        JLabel checkoutDateLabel = new JLabel("Checkout Date:");
        JLabel checkoutDateValue = new JLabel(DateUtils.convertDateFormatStr(transaction.getCheckoutDate()));
        contentPanel.add(checkoutDateLabel);
        contentPanel.add(checkoutDateValue);

        // Due Date
        JLabel dueDateLabel = new JLabel("Due Date:");
        JLabel dueDateValue = new JLabel(DateUtils.convertDateFormatStr(transaction.getDueDate()));
        contentPanel.add(dueDateLabel);
        contentPanel.add(dueDateValue);

        dialog.add(contentPanel, BorderLayout.CENTER);

        // Label for error message
        JLabel labelError = new JLabel("");
        labelError.setForeground(Color.RED);
        dialog.add(labelError, BorderLayout.SOUTH);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton btnConfirm = new JButton("Confirm");
        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println("Add Transaction");
                    LibraryController.getInstance().addTransaction(transaction);
                    dialog.setVisible(false);
                } catch (Exception ex) {
                    System.out.println("Add Transaction " + ex.getMessage());
                    labelError.setText(ex.getMessage());
                }
            }
        });
        buttonPanel.add(btnConfirm);

        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    private void initializeUI() {
        frame = new JFrame();
        frame.setBounds(100, 100, 700, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Library Management");

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        // Menu File
        JMenu menuFile = new JMenu("File");
        JMenuItem menuExit = new JMenuItem("Exit");
        menuExit.addActionListener(e -> System.exit(0));
        menuFile.add(menuExit);

        // Menu Patron
        JMenu menuPatron = new JMenu("Patron");
        JMenuItem menuNewPatron = new JMenuItem("New Patron");
        menuNewPatron.addActionListener(e -> navigateTo(new AddNewPatron()));
        JMenuItem menuListPatron = new JMenuItem("List Patrons");
        menuListPatron.addActionListener(e -> navigateTo(new ListViewPatron()));
        menuPatron.add(menuNewPatron);
        menuPatron.add(menuListPatron);

        // Menu Book
        JMenu menuBook = new JMenu("Book");
        JMenuItem menuNewBook = new JMenuItem("New Book");
        menuNewBook.addActionListener(e -> navigateTo(new AddNewBook()));
        JMenuItem menuListBook = new JMenuItem("List Books");
        menuListBook.addActionListener(e -> navigateTo(new ListViewBook()));
        menuBook.add(menuNewBook);
        menuBook.add(menuListBook);

        // Menu Transaction
        JMenu menuTransaction = new JMenu("Transaction");
        JMenuItem menuCheckoutBook = new JMenuItem("Checkout Book");
        menuCheckoutBook.addActionListener(e -> navigateTo(new CheckoutBook(this)));
        JMenuItem menuTransactionReport = new JMenuItem("Transaction Report");
        menuTransactionReport.addActionListener(e -> navigateTo(new TransactionReport()));
        JMenuItem menuReturnBook = new JMenuItem("Return Book");
        menuReturnBook.addActionListener(e -> navigateTo(new ReturnBook()));
        menuTransaction.add(menuCheckoutBook);
        menuTransaction.add(menuTransactionReport);
        menuTransaction.add(menuReturnBook);

        menuBar.add(menuFile);
        menuBar.add(menuPatron);
        menuBar.add(menuBook);
        menuBar.add(menuTransaction);

        frame.getContentPane().setLayout(new BorderLayout());
        body = new JPanel();
        body.setLayout(new BorderLayout());
        frame.getContentPane().add(body, BorderLayout.CENTER);
    }


    private void navigateTo(JPanel jpanel) {
        body.removeAll();
        body.add(jpanel);
        body.revalidate();
        body.repaint();
    }
}
