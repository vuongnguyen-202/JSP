package a2_2001040230.page;

import a2_2001040230.common.DateUtils;
import a2_2001040230.dao.PatronDao;
import a2_2001040230.model.Patron;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Page for listing patrons
 */
public class ListViewPatron extends JPanel {

    private static final String[] COLUMN_NAMES = {"ID", "Name", "Birthday", "Email", "Phone", "Patron Type"};

    private PatronDao patronDao;

    public ListViewPatron() {
        patronDao = new PatronDao();

        List<Patron> patrons = new ArrayList<>();

        try {
            patrons = patronDao.getAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        setLayout(new BorderLayout());

        JTable table = new JTable();
        table.setModel(initTableModel(patrons));
        table.setDefaultEditor(Object.class, null); // Disable cell editing

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    private DefaultTableModel initTableModel(List<Patron> patrons) {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(COLUMN_NAMES);

        for (Patron patron : patrons) {
            Object[] row = {
                    patron.getId(),
                    patron.getName(),
                    DateUtils.convertDateFormatStr(patron.getDob()),
                    patron.getEmail(),
                    patron.getPhone(),
                    patron.getType().toString()
            };
            model.addRow(row);
        }

        return model;
    }
}