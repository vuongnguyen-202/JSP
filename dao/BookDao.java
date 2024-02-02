package a2_2001040230.dao;

import a2_2001040230.DbConnections;
import a2_2001040230.common.Genre;
import a2_2001040230.model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

    /**
     * Refer to /library.db
     */
    private static final String idColumn = "id";
    private static final String isbnColumn = "ISBN";
    private static final String titleColumn = "title";
    private static final String authorColumn = "author";
    private static final String genreColumn = "genre";
    private static final String pubYearColumn = "pubYear";
    private static final String numCopiesAvailableColumn = "numCopiesAvailable";


    /**
     * Add Enrolment
     */
    public void add(Book book) throws SQLException {
        String query = "INSERT INTO book(ISBN, title, author, genre, pubYear, numCopiesAvailable) VALUES(?,?,?,?,?,?)";
        Connection connection = DbConnections.connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, book.getIsbn());
        preparedStatement.setString(2, book.getTitle());
        preparedStatement.setString(3, book.getAuthor());
        preparedStatement.setString(4, book.getGenre().toString());
        preparedStatement.setInt(5, book.getPublicYear());
        preparedStatement.setInt(6, book.getNumCopiesAvailable());
        preparedStatement.executeUpdate();
        connection.close();
    }

    public List<Book> getAll() throws Exception {
        List<Book> books = new ArrayList<>();

        String sql = "SELECT * FROM book";
        Connection con = DbConnections.connect();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt(idColumn);
            String isbn = rs.getString(isbnColumn);
            String title = rs.getString(titleColumn);
            String author = rs.getString(authorColumn);
            Genre genre = Genre.valueOf(rs.getString(genreColumn));
            int pubYear = rs.getInt(pubYearColumn);
            int numCopiesAvailable = rs.getInt(numCopiesAvailableColumn);
            Book book = new Book(id, isbn, title, author, genre, pubYear, numCopiesAvailable);
            books.add(book);
        }
        con.close();
        return books;
    }

    public List<Book> filterBookByIds(List<Integer> bookIds) throws Exception {
        List<Book> books = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM book WHERE id IN (");
        for (int i = 0; i < bookIds.size(); i++) {
            sql.append(i == 0 ? "?" : ", ?");
        }
        sql.append(")");

        Connection con = DbConnections.connect();
        PreparedStatement preparedStatement = con.prepareStatement(sql.toString());

        for (int i = 0; i < bookIds.size(); i++) {
            preparedStatement.setInt(i + 1, bookIds.get(i));
        }

        // Execute the query
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            int id = rs.getInt(idColumn);
            String isbn = rs.getString(isbnColumn);
            String title = rs.getString(titleColumn);
            String author = rs.getString(authorColumn);
            Genre genre = Genre.valueOf(rs.getString(genreColumn));
            int pubYear = rs.getInt(pubYearColumn);
            int numCopiesAvailable = rs.getInt(numCopiesAvailableColumn);
            Book book = new Book(id, isbn, title, author, genre, pubYear, numCopiesAvailable);
            books.add(book);
        }

        con.close();

        return books;
    }
}
