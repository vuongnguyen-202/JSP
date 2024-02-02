package a2_2001040230.dao;

import a2_2001040230.DbConnections;
import a2_2001040230.common.DateUtils;
import a2_2001040230.common.PatronType;
import a2_2001040230.model.Patron;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class PatronDao {

    /**
     * Refer to /library.db
     */
    private static final String idColumn = "id";
    private static final String nameColumn = "name";
    private static final String dobColumn = "dob";
    private static final String emailColumn = "email";
    private static final String phoneColumn = "phone";
    private static final String patronTypeColumn = "patronType";

    /**
     * Add Patron
     */
    public void add(Patron patron) throws SQLException {
        String query = "INSERT INTO patron(name, dob, email, phone, patronType) VALUES(?,?,?,?,?)";
        Connection connection = DbConnections.connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, patron.getName());
        preparedStatement.setString(2, DateUtils.convertDateFormatStr(patron.getDob()));
        preparedStatement.setString(3, patron.getEmail());
        preparedStatement.setString(4, patron.getPhone());
        preparedStatement.setString(5, patron.getType().toString());
        preparedStatement.executeUpdate();
        connection.close();
    }

    /**
     * Get All Patrons
     */
    public List<Patron> getAll() throws SQLException, ParseException {
        List<Patron> patrons = new ArrayList<>();

        String sql = "SELECT * FROM patron";
        Connection con = DbConnections.connect();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt(idColumn);
            String name = rs.getString(nameColumn);
            String dob = rs.getString(dobColumn);
            String email = rs.getString(emailColumn);
            String phone = rs.getString(phoneColumn);
            String patronType = rs.getString(patronTypeColumn);
            Patron patron = null;
            try {
                patron = new Patron(id, name, DateUtils.convertDateFormat(dob), email, phone, PatronType.valueOf(patronType));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            patrons.add(patron);
        }
        con.close();
        return patrons;
    }
}
