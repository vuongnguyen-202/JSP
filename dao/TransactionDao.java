package a2_2001040230.dao;

import a2_2001040230.DbConnections;
import a2_2001040230.common.DateUtils;
import a2_2001040230.model.LibraryTransaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao {
    /**
     * Refer to /db_script/a2_1901040142.sql
     */
    private static final String idColumn = "id";
    private static final String bookIdColumn = "book_id";
    private static final String patronIdColumn = "patron_id";
    private static final String checkoutDateColumn = "checkoutDate";
    private static final String dueDateColumn = "dueDate";

    /**
     * Add Enrolment
     */
    public void add(LibraryTransaction transaction) throws SQLException {
        String query = "INSERT INTO 'transaction'(book_id, patron_id, checkoutDate, dueDate) VALUES(?,?,?,?)";
        Connection connection = DbConnections.connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, transaction.getBookId());
        preparedStatement.setInt(2, transaction.getPatronId());
        preparedStatement.setString(3, DateUtils.convertDateFormatStr(transaction.getCheckoutDate()));
        preparedStatement.setString(4, DateUtils.convertDateFormatStr(transaction.getDueDate()));
        preparedStatement.executeUpdate();
        connection.close();
    }

    /**
     * Get All Patrons
     */
    public List<LibraryTransaction> getAll() throws SQLException, ParseException {
        List<LibraryTransaction> transactions = new ArrayList<>();

        String sql = "SELECT * FROM 'transaction'";
        Connection con = DbConnections.connect();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt(idColumn);
            int patronId = rs.getInt(patronIdColumn);
            int bookId = rs.getInt(bookIdColumn);
            String checkoutDate = rs.getString(checkoutDateColumn);
            String dueDate = rs.getString(dueDateColumn);
            LibraryTransaction transaction = new LibraryTransaction(id, patronId, bookId, DateUtils.convertDateFormat(checkoutDate),
                    DateUtils.convertDateFormat(dueDate));
            transactions.add(transaction);
        }
        con.close();
        return transactions;
    }

    public int getCountTransactionByBook(int bookId) throws SQLException {
        int records = 0;

        String sql = "SELECT COUNT(*) FROM 'transaction' WHERE book_id = ?";
        Connection con = DbConnections.connect();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, bookId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            records = rs.getInt(1);
        }

        return records;
    }

    public List<LibraryTransaction> getTransactionByPatron(int targetPatronId) throws SQLException, ParseException {
        List<LibraryTransaction> transactions = new ArrayList<>();

        System.out.println("Target Patron Id " + targetPatronId);

        String sql = "SELECT * FROM 'transaction' WHERE patron_id = ?";
        Connection con = DbConnections.connect();
        System.out.println("DB Connection");
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, targetPatronId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt(idColumn);
            int patronId = rs.getInt(patronIdColumn);
            int bookId = rs.getInt(bookIdColumn);
            String checkoutDate = rs.getString(checkoutDateColumn);
            String dueDate = rs.getString(dueDateColumn);
            LibraryTransaction transaction = new LibraryTransaction(id, patronId, bookId, DateUtils.convertDateFormat(checkoutDate),
                    DateUtils.convertDateFormat(dueDate));
            transactions.add(transaction);
        }
        con.close();
        return transactions;
    }

    public void returnBook(int patronId, int bookId) throws SQLException {
        String sql = "DELETE FROM 'transaction' WHERE patron_id = ? AND book_id = ?";
        Connection con = DbConnections.connect();
        System.out.println("DB Connection");
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, patronId);
        ps.setInt(2, bookId);
        ps.executeUpdate();
        con.close();
    }
}
