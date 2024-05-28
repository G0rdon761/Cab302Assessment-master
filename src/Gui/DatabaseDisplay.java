package Gui;
import BackEnd.*;
import BackEnd.MazeSolver.MazeSolver;

import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.io.File;
import java.util.List;

import static Gui.Main.createButton;


public class DatabaseDisplay extends JFrame{

    JDBCMazeDatabaseSource Database = new JDBCMazeDatabaseSource();
    private JCheckBox[] boxes;
    private File file;
    private String[] items;
    private JCheckBox withsol;
    private JPanel content;
    /**
     *
     * @param background determines navbar background color
     * @param border determines navbar border color
     * @return returns completed navbar for display
     */
    public JPanel navbarMake(Color background, Color border) {
        JPanel navbar = new JPanel();
        navbar.setBorder(BorderFactory.createLineBorder(border));
        navbar.setBackground(background);
        navbar.setLayout(new BoxLayout(navbar, BoxLayout.X_AXIS));
        navbar.add(Box.createHorizontalGlue());
        navbar = populatenav(navbar);
        return navbar;
    }

    /**
     *
     * @param background determines background color
     * @param border determines border color
     * @return returns completed content section for display
     */
    public JScrollPane contentMake(Color background, Color border) {
        content = new JPanel();
        content.setBackground(background);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.add(Box.createHorizontalGlue());
        content.add(Box.createVerticalGlue());
        content = populateContent(content);

        JScrollPane contentScroll = new JScrollPane(content);
        contentScroll.add(Box.createHorizontalGlue());
        contentScroll.add(Box.createVerticalGlue());

        return contentScroll;
    }

    /**
     *
     * @param navbar accepts unpopulated navbar panel
     * @return returns navbar panel with buttons added
     */
    public JPanel populatenav(JPanel navbar) {
        JButton refreshbutton = createButton("Refresh",this::refresh,"Arial",11);
        navbar.add(refreshbutton);
        JButton newmazebutton =createButton("New Maze",null, "Arial", 11);
        newmazebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Main();
            }
        });
        navbar.add(newmazebutton);
        JButton directoryButton = createButton("Export Directory", this::changedir,"Arial",11);
        navbar.add(directoryButton);
        JButton exportButton = createButton("Export All Selected",this::export, "Arial",11);
        navbar.add(exportButton);
        JPanel selectionPannel = new JPanel();
        selectionPannel.setBackground(Color.WHITE);
        selectionPannel.add(new JLabel("With Solution:"));
        withsol = new JCheckBox();
        selectionPannel.add(withsol);
        navbar.add(selectionPannel);
        return navbar;
    }
    public void refresh(ActionEvent e) {
        content.removeAll();
        populateContent(content);
        SwingUtilities.updateComponentTreeUI(content);
    }

    public void changedir(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(".png","png"));
        fileChooser.setAcceptAllFileFilterUsed(true);
        int rs = fileChooser.showSaveDialog(null);
        if(rs == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        }
    }

    public void export(ActionEvent e) {
        if (file != null) {
            for (int x = 0; x < items.length; x++) {
                if (boxes[x].isSelected()) {
                    int[][] toexport = Database.getMaze(items[x]);
                    //find the maze object
                    Maze mazeClass = Database.getMazeDetails(items[x]);
                    MazeSolver solver = new MazeSolver();
                    File export = new File(file + (x + ".png"));
                    File exportSol = new File(file + (x + "S.png"));
                    if (withsol.isSelected()) {
                        new ExportPage(toexport, solver.getPath(toexport), exportSol, mazeClass);
                        new ExportPage(toexport, null, export, mazeClass);
                    } else {
                        new ExportPage(toexport, null, export, mazeClass);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null,"Please choose a directory first");
        }
    }
    /**
     *
     * @param content accepts unpopulated content section
     * @return returns content section with database entries added for display
     */
    public JPanel populateContent(JPanel content) {

        List<String> listItems = Database.getAllMaze();
        JPanel[] panellist = new JPanel[listItems.size()];
        boxes = new JCheckBox[listItems.size()];
        items = new String[listItems.size()];
        for (int i = 0; i < listItems.size(); i++) {
            panellist[i] = new JPanel();
            String currentitem = (listItems.get(i));
            items[i] = (currentitem.split(",", 2))[0];
            panellist[i].add(new JLabel(currentitem));
            currentitem = (currentitem.split(",", 2))[0];
            JButton editButton = createButton("Edit",this::editClicked,"arial",10 );
            editButton.setName(currentitem);
            panellist[i].add(editButton);
            boxes[i] = new JCheckBox();
            panellist[i].add(boxes[i]);
            panellist[i].setBackground(Color.WHITE);
            content.add(panellist[i]);
        }
        return content;
    }
    public void editClicked(ActionEvent e) {
        Object input = e.getSource();
        JButton buttonPressed = (JButton) input;
        String buttonName = buttonPressed.getName();
        try {
            BrokenWallMaze output = (BrokenWallMaze) Database.getMazeDetails(buttonName);
            new Main(output);
            dispose();
        }
        catch(Exception ex) {
            PictureMaze output = (PictureMaze) Database.getMazeDetails(buttonName);
            new Main(output);
            dispose();
        }
    }

    public DatabaseDisplay() {
        super("Database Page");

        final int width = 600;
        final int height = 300;

        JPanel navbar = navbarMake(Color.white, Color.black);
        JScrollPane content = contentMake(Color.white, Color.black);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().add(navbar);
        getContentPane().add(content);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(width, height));
        pack();
        setVisible(true);

    }

}

