package a2_2001040230.page;

import a2_2001040230.common.DateUtils;
import a2_2001040230.dao.TransactionDao;
import a2_2001040230.model.LibraryTransaction;
import a2_2001040230.model.ReportType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Report Assessment screen is a screen that shows a table of transactions
 */
public class TransactionReport extends JPanel {

    private static final String[] COLUMN_NAMES = {"ID", "Patron", "Book", "Checkout Date", "Due Date"};

    private final TransactionDao transactionDao;

    private JTable table;

    private List<LibraryTransaction> transactions;

    public TransactionReport() {
        transactionDao = new TransactionDao();

        try {
            transactions = transactionDao.getAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            transactions = new ArrayList<>();
        }

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JComboBox<ReportType> comboBoxReportType = new JComboBox<>(ReportType.values());
        comboBoxReportType.setSelectedIndex(0);
        comboBoxReportType.addActionListener(e -> {
            ReportType selectedReportType = (ReportType) comboBoxReportType.getSelectedItem();
            List<LibraryTransaction> filteredTransactions;

            switch (selectedReportType) {
                case ALL:
                    filteredTransactions = transactions;
                    break;
                case OVERDUE:
                    filteredTransactions = filterTransactions(true);
                    break;
                case CHECKOUT:
                    filteredTransactions = filterTransactions(false);
                    break;
                default:
                    filteredTransactions = new ArrayList<>();
                    break;
            }

            table.setModel(initTableModel(filteredTransactions));
        });

        add(comboBoxReportType);

        table = new JTable();
        table.setModel(initTableModel(transactions));
        table.setDefaultEditor(Object.class, null); // Disable cell editing
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }

    private List<LibraryTransaction> filterTransactions(boolean isOverdue) {
        return transactions.stream()
                .filter(transaction -> transaction.isOverdue() == isOverdue)
                .toList();
    }

    private DefaultTableModel initTableModel(List<LibraryTransaction> transactions) {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(COLUMN_NAMES);

        for (LibraryTransaction transaction : transactions) {
            Object[] row = {
                    transaction.getId(),
                    transaction.getPatronId(),
                    transaction.getBookId(),
                    DateUtils.convertDateFormatStr(transaction.getCheckoutDate()),
                    DateUtils.convertDateFormatStr(transaction.getDueDate())
            };
            model.addRow(row);
        }

        return model;
    }
}