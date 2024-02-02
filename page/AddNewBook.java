package a2_2001040230.page;

import a2_2001040230.common.Genre;
import a2_2001040230.dao.BookDao;
import a2_2001040230.model.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


/**
 * Create New Book
 */
public class AddNewBook extends JPanel {

    private final BookDao bookDao;
    private final JTextField textFieldTitle;
    private final JTextField textFieldAuthor;
    private final JComboBox<Genre> comboBoxGenre;
    private final JTextField textFieldPublicationYear;
    private final JTextField textFieldNumberCopies;
    private final JLabel labelError;
    private final JLabel labelSuccess;

    public AddNewBook() {
        bookDao = new BookDao();

        setSize(600, 400);
        setLayout(null);

        // Title
        JLabel titleLabel = new JLabel("Title");
        titleLabel.setBounds(50, 60, 200, 16);
        add(titleLabel);

        textFieldTitle = new JTextField();
        textFieldTitle.setBounds(250, 55, 350, 26);
        add(textFieldTitle);

        // Author
        JLabel authorLabel = new JLabel("Author");
        authorLabel.setBounds(50, 100, 200, 16);
        add(authorLabel);

        textFieldAuthor = new JTextField();
        textFieldAuthor.setBounds(250, 95, 350, 26);
        add(textFieldAuthor);

        // Genre
        JLabel genreTypeLabel = new JLabel("Genre");
        genreTypeLabel.setBounds(50, 140, 200, 16);
        add(genreTypeLabel);

        comboBoxGenre = new JComboBox<>(Genre.values());
        comboBoxGenre.setSelectedIndex(0);
        comboBoxGenre.setBounds(250, 135, 150, 27);
        add(comboBoxGenre);

        // Publication Year
        JLabel publicationYearLabel = new JLabel("Publication Year");
        publicationYearLabel.setBounds(50, 180, 200, 16);
        add(publicationYearLabel);

        textFieldPublicationYear = new JTextField();
        textFieldPublicationYear.setBounds(250, 175, 350, 26);
        add(textFieldPublicationYear);

        // Number Copies Available
        JLabel numberCopiesLabel = new JLabel("Number of Copies Available");
        numberCopiesLabel.setBounds(50, 220, 200, 16);
        add(numberCopiesLabel);

        textFieldNumberCopies = new JTextField();
        textFieldNumberCopies.setBounds(250, 215, 350, 26);
        add(textFieldNumberCopies);

        // Error Label
        labelError = new JLabel("");
        labelError.setForeground(Color.RED);
        labelError.setHorizontalAlignment(SwingConstants.CENTER);
        labelError.setBounds(0, 260, 600, 16);
        add(labelError);

        // Success Label
        labelSuccess = new JLabel("");
        labelSuccess.setForeground(Color.GREEN);
        labelSuccess.setHorizontalAlignment(SwingConstants.CENTER);
        labelSuccess.setBounds(0, 260, 600, 16);
        add(labelSuccess);

        // Button Save
        JButton btnAdd = new JButton("Add Book");
        btnAdd.setBounds(240, 300, 120, 29);
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    handleAddBook();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        add(btnAdd);
    }

    private void handleAddBook() throws Exception {
        labelError.setText("");
        labelSuccess.setText("");

        String title = textFieldTitle.getText();
        String author = textFieldAuthor.getText();
        int publicationYear, numberCopiesAvailable;

        try {
            publicationYear = Integer.parseInt(textFieldPublicationYear.getText());
            numberCopiesAvailable = Integer.parseInt(textFieldNumberCopies.getText());
        } catch (NumberFormatException exception) {
            labelError.setText("Wrong number format input");
            return;
        }

        Genre genre = (Genre) comboBoxGenre.getSelectedItem();

        Book newBook = new Book(title, author, genre, publicationYear, numberCopiesAvailable);

        try {
            bookDao.add(newBook);
            labelSuccess.setText("New book added");
            clearFields();
        } catch (SQLException exception) {
            labelError.setText(exception.getMessage());
        } catch (Exception exception) {
            labelError.setText(exception.getMessage());
        }
    }

    private void clearFields() {
        textFieldTitle.setText("");
        textFieldAuthor.setText("");
        textFieldPublicationYear.setText("");
        textFieldNumberCopies.setText("");
    }
}