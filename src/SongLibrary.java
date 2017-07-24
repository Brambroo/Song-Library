import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 * Creates a SongLibrary GUI that enables the user to add songs, import songs
 * from a txt file, and save the songs to a txt file.
 * 
 * @author Christian Clarke
 * @version 3/3/17
 *
 */
@SuppressWarnings("serial")
public class SongLibrary extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JButton add;
    private JButton delete;

    /**
     * Default constructor
     */
    public SongLibrary() {
        super("SongLibrary");

        setLayout(new BorderLayout());

        String[][] data = new String[0][0];
        String[] cols = new String[] { "Song", "Artist", "Album", "Year" };

        model = new DefaultTableModel(data, cols);
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(500, 100));
        table.setFillsViewportHeight(true);

        JScrollPane scroll = new JScrollPane(table);

        add(scroll, BorderLayout.CENTER);

        Box theBox = Box.createVerticalBox();

        delete = new JButton("Delete");
        delete.setEnabled(false);

        delete.addActionListener(new ActionListener() {

            /**
             * Disables the delete button and if enabled, it removes the
             * selected row. If no row is selected it displays a message.
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                if (table.getRowCount() == 0) {
                    delete.setEnabled(false);
                }
                else {
                    if (table.getSelectedRow() != -1) {
                        model.removeRow(table.getSelectedRow());
                    }
                    else {
                        displayMessageWhenNoRowSelected();
                    }
                }
            }

        });

        add = new JButton("Add");
        add.addActionListener(new ActionListener() {

            /**
             * Adds a new row to the table and enables the delete button
             */
            @Override
            public void actionPerformed(ActionEvent arg0) {
                model.addRow(new String[] {});
                if (!delete.isEnabled()) {
                    delete.setEnabled(true);
                }
            }

        });

        theBox.add(add);
        theBox.add(delete);
        add(theBox, BorderLayout.EAST);

        /*
         * 
         * 
         * Menu Bar and Menu Item creation
         * 
         * 
         * 
         */
        JMenuBar bar = new JMenuBar();
        setJMenuBar(bar);
        JMenu songlibrary = new JMenu("SongLibrary");
        JMenuItem about = new JMenuItem("About...");

        about.addActionListener(new ActionListener() {

            /**
             * Calls the displayAboutDialog method
             * 
             * @param arg0
             *            does nothing
             */
            @Override
            public void actionPerformed(ActionEvent arg0) {
                displayAboutDialog();
            }

        });

        // closing the program
        JMenuItem exit = createsExit();

        // using close icon
        addWindowListener(new WindowAdapter() {

            /**
             * Shows option pane when user tries to exit from the window's exit
             * button
             * 
             * @param arg0
             *            does nothing
             */
            @Override
            public void windowClosing(WindowEvent arg0) {
                int result = JOptionPane.showConfirmDialog(SongLibrary.this,
                        "Do you want to exit?");
                if (result == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }

        });

        songlibrary.add(about);
        songlibrary.addSeparator();
        songlibrary.add(exit);
        bar.add(songlibrary);

        JMenu tableMenu = new JMenu("Table");
        JMenuItem newItem = createsNewItem();

        JMenuItem open = createsOpen();

        JMenuItem saveAs = createsSaveAs();

        tableMenu.add(newItem);
        tableMenu.addSeparator();
        tableMenu.add(open);
        tableMenu.addSeparator();
        tableMenu.add(saveAs);
        bar.add(tableMenu);

        add(bar, BorderLayout.NORTH);

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

    }

    /**
     * Runs the SongLibrary frame and sets it visable
     * 
     * @param args
     *            defualt param
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            /**
             * Creates a new JFrame from SongLibrary and sets the visability to
             * true
             */
            @Override
            public void run() {
                JFrame f = new SongLibrary();
                f.setVisible(true);
            }
        });
    }

    /*
     * 
     * 
     * Helper methods
     * 
     * 
     * 
     */

    private void displayAboutDialog() {
        JOptionPane.showMessageDialog(this,
                new JLabel(
                        "<html><hr><i>Song Library</i><br>by Christian Clarke<hr></html>"),
                "About...", JOptionPane.INFORMATION_MESSAGE);
    }

    private void displayMessageWhenNoRowSelected() {
        JOptionPane.showMessageDialog(this, new JLabel("No row selected"),
                "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    private JMenuItem createsSaveAs() {

        JMenuItem saveAs = new JMenuItem("Save As...");
        saveAs.addActionListener(new ActionListener() {

            /**
             * Allows the user to save the table to a file
             * 
             * @param e
             *            does nothing
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                SwingUtilities.invokeLater(new Runnable() {
                    /**
                     * Creates a JFileChooser and saves the table to a file
                     */
                    @Override
                    public void run() {
                        JFileChooser chooser = new JFileChooser();
                        int result = chooser.showSaveDialog(SongLibrary.this);
                        if (result == JFileChooser.APPROVE_OPTION) {
                            File file = chooser.getSelectedFile();
                            try {
                                PrintWriter writer = new PrintWriter(file);
                                for (int row = 0; row < model
                                        .getRowCount(); row++) {
                                    for (int column = 0; column < model
                                            .getColumnCount(); column++) {
                                        if (column != 3) {
                                            if (!model.getValueAt(row, column)
                                                    .equals(null)) {
                                                writer.print(model.getValueAt(
                                                        row, column) + ",");
                                            }
                                        }
                                        else {
                                            if (!model.getValueAt(row, column)
                                                    .equals(null)) {
                                                writer.print(model.getValueAt(
                                                        row, column));
                                            }
                                        }
                                    }
                                    writer.println();
                                }
                                writer.flush();
                                writer.close();
                                setTitle("SongLibrary [" + file.getPath()
                                        + "]");
                            }
                            catch (FileNotFoundException e) {

                                e.printStackTrace();
                            }
                        }
                    }
                });

            }

        });
        return saveAs;
    }

    // creates open menu item
    private JMenuItem createsOpen() {

        JMenuItem open = new JMenuItem("Open...");
        open.addActionListener(new ActionListener() {

            /**
             * Allows the user to open a txt file
             * 
             * @param arg0
             *            does nothing
             */
            @Override
            public void actionPerformed(ActionEvent arg0) {

                SwingUtilities.invokeLater(new Runnable() {
                    /**
                     * Creates a JFileChooser to open a txt file that is loaded
                     * into the table
                     */
                    @Override
                    public void run() {
                        JFileChooser chooser = new JFileChooser();
                        int result = chooser.showOpenDialog(SongLibrary.this);
                        if (result == JFileChooser.APPROVE_OPTION) {
                            File file = chooser.getSelectedFile();
                            try {
                                Scanner reader = new Scanner(file);
                                int row = 0;
                                while (reader.hasNextLine()) {
                                    String[] input = reader.nextLine()
                                            .split(",");
                                    model.insertRow(row, input);
                                    row++;
                                }
                                if (model.getRowCount() > 0) {
                                    delete.setEnabled(true);
                                }
                                reader.close();
                                setTitle("SongLibrary [" + file.getPath()
                                        + "]");
                            }
                            catch (FileNotFoundException e) {

                                e.printStackTrace();
                            }
                        }
                    }
                });

            }

        });
        return open;
    }

    private JMenuItem createsNewItem() {

        JMenuItem newItem = new JMenuItem("New");
        newItem.addActionListener(new ActionListener() {

            /**
             * Clears the table's data and resets the row count. Disables the
             * delete button
             * 
             * @param e
             *            does nothing
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(SongLibrary.this,
                        "Clear all table data?");
                if (result == JOptionPane.YES_OPTION) {
                    model.setRowCount(0);
                    delete.setEnabled(false);
                }
            }

        });
        return newItem;
    }

    private JMenuItem createsExit() {
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {

            /**
             * Creates an option pane that confirms whether or not the user
             * wants to exit the program
             * 
             * @param arg0
             *            does nothing
             */
            @Override
            public void actionPerformed(ActionEvent arg0) {
                int result = JOptionPane.showConfirmDialog(SongLibrary.this,
                        "Do you want to exit?");
                if (result == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }

        });
        return exit;
    }
}
