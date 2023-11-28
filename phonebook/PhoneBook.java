package tutes.db.phonebook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class PhoneBook extends WindowAdapter implements ActionListener {
    private JFrame gui;
    private AddContact addGUI;
    private JTable tblContacts;
    private Connection conn = null;
    private Statement stmt = null;
    private ArrayList<Integer> ids;

    public PhoneBook() {
        connectDB();
        reloadTable();
        createGUI();
    }

    private void connectDB() {
        try {
            conn = DriverManager.getConnection(
                    "jdbc:sqlite:phonebook.sqlite3");
            stmt = conn.createStatement();
            System.out.println("Connected to database!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            shutDown();
        }
    }

    private void reloadTable() {
        DefaultTableModel tm = null;
        ids = new ArrayList<>();
        if (tblContacts == null) {
            // tblContacts doesn't exist --> create new
            String[] headers = {"Name", "Phone", ""};
            Object[][] data = new Object[0][3];
            tm = new DefaultTableModel(data, headers) {
                @Override
                public Class<?> getColumnClass(int column) {
                    if (column == 2) {
                        return Boolean.class;
                    } else {
                        return super.getColumnClass(column);
                    }
                }

                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 2;
                }
            };
            tblContacts = new JTable(tm);
            tblContacts.getColumnModel().getColumn(0).setPreferredWidth(150);
            tblContacts.getColumnModel().getColumn(1).setPreferredWidth(120);
        } else {
            // tblContacts exists --> remove all rows
            tm = (DefaultTableModel) tblContacts.getModel();
            for (int i = tm.getRowCount() - 1; i >= 0; i--) {
                tm.removeRow(i);
            }
        }
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM contacts");
            while (rs.next()) {
                tm.addRow(new Object[]{
                        rs.getString("name"),
                        rs.getString("phone"),
                        false
                });
                ids.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createGUI() {
        gui = new JFrame("Phone Book");
        gui.setSize(360, 300);
        gui.addWindowListener(this);

        JMenuBar menu = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(this);
        fileMenu.add(exitMenuItem);
        menu.add(fileMenu);

        gui.setJMenuBar(menu);

        // north panel
        JPanel pnlTop = new JPanel();
        pnlTop.setBackground(Color.YELLOW);
        JLabel lblTitle = new JLabel("Phone Book Application");
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 15f));
        pnlTop.add(lblTitle);
        gui.add(pnlTop, BorderLayout.NORTH);

        // center panel
        JPanel pnlMiddle = new JPanel();
        pnlMiddle.setLayout(new BorderLayout());
        gui.add(pnlMiddle);
        JScrollPane scrContacts = new JScrollPane(tblContacts);
        pnlMiddle.add(scrContacts);

        // bottom
        JPanel pnlBottom = new JPanel();
        gui.add(pnlBottom, BorderLayout.SOUTH);

        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(this);
        pnlBottom.add(btnAdd);

        JButton btnCheckAll = new JButton("Check All");
        btnCheckAll.addActionListener(this);
        pnlBottom.add(btnCheckAll);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(this);
        pnlBottom.add(btnDelete);

        gui.setLocationRelativeTo(null);
    }

    public void display() {
        gui.setVisible(true);
    }

    @Override
    public void windowClosing(WindowEvent e) {
        shutDown();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Add")) {
            if (addGUI == null) addGUI = new AddContact(gui, this);
            addGUI.display();
        } else if (command.equals("Check All")) {
            for (int i = 0; i < tblContacts.getRowCount(); i++) {
                tblContacts.setValueAt(true, i, 2);
            }
        } else if (command.equals("Delete")) {
            int result = JOptionPane.showConfirmDialog(gui, "Are you sure?", "Delete confirmation", JOptionPane.WARNING_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                for (int i = tblContacts.getRowCount() - 1; i >= 0; i--) {
                    boolean delete = (boolean) tblContacts.getValueAt(i, 2);
                    if (delete) {
                        try {
                            stmt.execute("DELETE FROM contacts WHERE id = " + ids.get(i));
                        } catch (SQLException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                }
                reloadTable();
            }
        } else if (command.equals("Exit")) {
            shutDown();
        }
    }

    private void shutDown() {
        if (conn != null) {
            try {
                conn.close(); // beware: conn may be null
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        System.exit(0);
    }

    public void addContact(String name, String phone) {
        // 1. insert contact to database
        try {
            stmt.execute("INSERT INTO contacts (name, phone) VALUES ('" + name + "', '" + phone + "')");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        // 2. reloadTable
        reloadTable();
    }

    public static void main(String[] args) {
        PhoneBook app = new PhoneBook();
        app.display();
    }
}
