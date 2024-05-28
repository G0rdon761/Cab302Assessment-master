package Gui;

import BackEnd.*;
import BackEnd.MazeSolver.DifficultyPercentage;
import BackEnd.MazeSolver.MazeSolver;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main extends JFrame {
    private static final int WIDTH = 1100;
    private static final int HEIGHT = 800;
    private int[] coords = {0,0};
    private int[] pastcoords = {0,0};
    private JPanel panel;
    private MapVisualiser mapvisualiser;
    private boolean solve = false;
    private JDBCMazeDatabaseSource database;
    private JPanel titlePanel;
    private JLabel mazeName;
    private JSlider slider;
    private JPanel panelLeft, panelRight, mazeDetailPanel, mazeHolder;
    private BrokenWallMaze newMaze;
    private PictureMaze newMaze2;
    private JButton solutiontext;
    private int whichMaze = 2; //check for which type of maze is currently being worked on

    static MazeDatabaseData mazeData;



    /**
     * This function creates a new button and display the completed button on display
     *
     * @param name the text display on the button
     * @param listener the action when button is pressed
     * @param font setting the text font for the button
     * @param txt_size setting the text size for the button
     * @return returns completed button
     */
    public static JButton createButton(String name, ActionListener listener, String font, int txt_size){
        JButton button = new JButton();
        button.setText(name);
        button.setFont(new Font(font, Font.BOLD, txt_size));
        button.addActionListener(listener);
        return button;
    }


    /**
     * This function creates the menu item in the menu bar
     *
     * @param name the text display for the menu item
     * @param listener the action when the menu item is pressed
     * @return returns the completed menu item
     */
    private JMenuItem createMenuItem(String name, ActionListener listener){
        JMenuItem item = new JMenuItem();
        item.setText(name);
        item.addActionListener(listener);
        return item;
    }

    /**
     * This function creates the main GUI of the Maze generator
     */
    private void createGUI(){

        setSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //JLabel label = new JLabel("Maze");
        //getContentPane().add(label);
        setLocationRelativeTo(null);


    }


    /**
     * This function creates the menu bar, containing multiple menu items
     */
    private void createMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        //Create menu bar item File
        JMenu fileMenu = new JMenu("File");
        //When new button clicked, it will generate new maze
        fileMenu.add(createMenuItem("New", this::generateMazeClicked));
        //Add menu open button to database
        fileMenu.add(createMenuItem("Open", this::OpenButtonClicked));
        //When save button clicked, it will save current displaying maze
        fileMenu.add(createMenuItem("Save", this::SaveButtonClicked));
        //When export button clicked, it will export current maze as image
        fileMenu.add(createMenuItem("Export", this::exportMazeAsImg));

        fileMenu.add(createMenuItem("Close", this::closeButtonClicked));
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    /**
     * This function close the application
     * @param actionEvent
     */
    private void closeButtonClicked(ActionEvent actionEvent) {
        dispose();
        new Main();
    }


    /**
     * This function creates the label for displaying the Maze name
     *
     * @param mazeName the text display as the maze name
     * @param txtSize the maze name text size
     * @return return the completed label
     */
    private JLabel createMazeNameLabel(String mazeName, int txtSize){
        JLabel maze_name = new JLabel(mazeName);
        maze_name.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
        maze_name.setFont(new Font("Arial", Font.BOLD, txtSize));
        return maze_name;
    }


    private void OpenButtonClicked(ActionEvent actionEvent) {
        new DatabaseDisplay();
    }


    public void SaveButtonClicked(ActionEvent actionEvent) {
        database = new JDBCMazeDatabaseSource();
        if (whichMaze == 1) {
            database.insertMaze(newMaze);
            JOptionPane.showMessageDialog(null,"saved successfully");
        }
        else if (whichMaze == 0) {
            database.insertMaze(newMaze2);
            JOptionPane.showMessageDialog(null,"saved successfully");
        }

    }

    //Create Main Panel which contains Maze and multiple buttons
    private void createPanel(){
        panel = new JPanel();
        titlePanel = new JPanel();
        panel.setLayout(new BorderLayout());
        //Create left sub-panel for maze
        createLeftPanel();
        //Create right sub-panel for a slider and multiple buttons
        createRightPanel();
        //Create bottom sub-panel for maze information button
        createMazeDetailPanel();
        //Add all sub-panel into the main panel with border-layout
        mazeName = createMazeNameLabel("Maze Name",40);
        panel.add((titlePanel),BorderLayout.NORTH);
        titlePanel.add(mazeName,BorderLayout.NORTH);
        
        JScrollPane scroller = new JScrollPane(panelLeft);
        panel.add(scroller, BorderLayout.CENTER);
        panel.add(panelRight, BorderLayout.EAST);
        panel.add(mazeDetailPanel, BorderLayout.SOUTH);
        getContentPane().add(panel);
        //test stuff for co-ordinates;
    }
    private void updateTitle (String title) {
        mazeName.setText(title);
    }

    //Create left-hand side Panel which contains Maze
    private void createLeftPanel(){
        panelLeft = new JPanel();
        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
        mazeHolder = new JPanel();
        panelLeft.add(mazeHolder);

    }
    private void addMaze(JPanel mazeHolder) {
        int outcome = 0;
        mapvisualiser = new MapVisualiser(mazeHolder);
        int[][] dummymaze = null;
        MazeSolver solver = new MazeSolver();
        if (whichMaze == 1) {
            if (solve == true) {
                mapvisualiser.visualiser(newMaze.getMaze(),solver.getPath(newMaze.getMaze()));
                String displaynum = String.format("%.2f",solver.difficultyPercentage(solver.getPath(newMaze.getMaze()),newMaze.getMaze()));
                solutiontext.setText("Solution uses " + displaynum + "% of maze");
            }
            else {
                mapvisualiser.visualiser(newMaze.getMaze(),dummymaze);
                solutiontext.setText("Toggle Solution");
            }
            if(newMaze.getIcon() != null){
                mapvisualiser.addImage(newMaze.getMaze(), newMaze.getIcon());
            }
        }
        else if (whichMaze == 0) {
            if (solve == true) {
                mapvisualiser.visualiser(newMaze2.getMaze(),solver.getPath(newMaze2.getMaze()));
                String displaynum = String.format("%.2f",solver.difficultyPercentage((solver.getPath(newMaze2.getMaze())),newMaze2.getMaze()));
                solutiontext.setText("Solution uses " + displaynum + "% of maze");
            }
            else {
                mapvisualiser.visualiser(newMaze2.getMaze(),dummymaze);
                solutiontext.setText("Toggle Solution");
            }
            if(newMaze2.getIcon() != null){
                mapvisualiser.addImage(newMaze2.getMaze(), newMaze2.getIcon());
            }
            if(newMaze2.getStartImg() != null){
                mapvisualiser.addImage(newMaze2.getMaze(), newMaze2.getStartImg());
            }
            if(newMaze2.getEndImg() != null){
                mapvisualiser.addImage(newMaze2.getMaze(), newMaze2.getEndImg());
            }
        }
        else {
            return;
        }
        mazeHolder.setBorder(BorderFactory.createEmptyBorder(30,50,10,50));
        panelLeft.remove(mazeHolder);
        panelLeft.add(mazeHolder);
        SwingUtilities.updateComponentTreeUI(panelLeft);

    }




    //Create bottom side Panel which contains two maze info
    private void createMazeDetailPanel(){
        mazeDetailPanel = new JPanel();
        mazeDetailPanel.setLayout(new FlowLayout());
        mazeDetailPanel.add(createButton("X% Dead ends", this::getPercentageDeadEnds, "Arial", 20));
        mazeDetailPanel.add(solutiontext = createButton("Toggle Solution", this::toggleSolution, "Arial", 20));
        //Adjust the buttons to the center position
        mazeDetailPanel.setBorder(BorderFactory.createEmptyBorder(0,0,30,350));
    }

    public void toggleSolution(ActionEvent e) {
        if (!solve) {
            solve = true;
        }
        else {
            solve = false;
        }
        addMaze(mazeHolder);
    }

    public void getPercentageDeadEnds(ActionEvent e) {
        double result = 0;
        JButton button = (JButton) e.getSource();
        DifficultyPercentage dp = new DifficultyPercentage();
        if(whichMaze == 0){
            result = dp.getDeadEndPercentage(newMaze2.getMaze());
        } else if (whichMaze == 1) {
            result = dp.getDeadEndPercentage(newMaze.getMaze());
        }
        String output = String.format("%.2f",result);
        button.setText(output + "% Dead ends");
    }

    //Create right-hand side Panel which contains slider and four buttons
    private void createRightPanel(){
        panelRight = new JPanel();
        panelRight.setLayout(new GridLayout(5,1,0,10));
        //Create a size panel for Label, slider info and slider
        JPanel panelMazeSize = new JPanel();
        panelMazeSize.setLayout(new BoxLayout(panelMazeSize, BoxLayout.Y_AXIS));
        //Create a size sub-panel for slider info and slider
        JPanel panelMazeSlider = new JPanel();
        panelMazeSlider.setLayout(new BoxLayout(panelMazeSlider, BoxLayout.X_AXIS));
        panelMazeSlider.add(new JLabel("Size 20px"));
        //Create a slider for adjusting maze size
        slider = new JSlider(20, 100);
        panelMazeSlider.add(slider);
        panelMazeSlider.add(new JLabel("Size 100px"));
        //Add Label, slider info and slider into size panel
        panelMazeSize.add(new JLabel("Maze size"));
        panelMazeSize.add(panelMazeSlider);
        //Adjust the slider to the center position
        panelMazeSize.setBorder(BorderFactory.createEmptyBorder(50,10,0,20));
        JButton importMaze = new JButton("Import image to Maze");
        JButton exportMaze = new JButton("Export Maze as img");
        //Add the slider and buttons to right panel
        panelRight.add(panelMazeSize);
        panelRight.add(createButton("Generate Maze", this::generateMazeClicked, "Arial", 20));
        panelRight.add(createButton("Edit Maze", this::editMazeClicked, "Arial", 20));
        panelRight.add(createButton("Import image to Maze", this::importImageToMaze, "Arial", 20));
        panelRight.add(createButton("Export Maze as img", this::exportMazeAsImg, "Arial", 20));
        //Create a border for cleaner looking
        panelRight.setBorder(BorderFactory.createEmptyBorder(0,0,13,30));


    }

    private void editMazeClicked(ActionEvent actionEvent) {
        if (whichMaze == 0) {
            new EditPage(newMaze2);
            dispose();
        }
        else if (whichMaze == 1) {
            new EditPage(newMaze);
            dispose();
        }
    }

    private void generateMazeClicked(ActionEvent actionEvent) {
        String title = "", author = "";
        while (title.isEmpty()) {
            title = JOptionPane.showInputDialog("Please enter the title of your maze");
            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Title must not be empty!");
            }
        }
        updateTitle(title);
        while (author.isEmpty()) {
            author = JOptionPane.showInputDialog("Please enter your name:");
            if (author.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Name must not be empty!");
            }
        }
        int size = slider.getValue();
        size = size / 3;
        int mazetype = JOptionPane.showConfirmDialog(null, "would you like to generate a picture maze?");
        int fromscratch = JOptionPane.showConfirmDialog(null, "would you like to automatically generate maze?");
        if (mazetype == 1) {
            newMaze = new BrokenWallMaze(title, author, size, size);
            if (fromscratch == 0) {
                // ask the user to add the maze icon
                PictureObject icon = importImage("Icon", 1, 1, mazeHolder.getWidth() / slider.getValue());
                if (icon != null) {
                    newMaze = new BrokenWallMaze(title, author, size, size);

                    newMaze.drawMaze();
                    // get the desired icon position
                    icon.setRow(newMaze.getStartpos()[0]);
                    icon.setColumn(newMaze.getStartpos()[1]);
                    newMaze.setIcon(icon);
                    whichMaze = 1;
                    addMaze(mazeHolder);
                }
            } else if (fromscratch == 1) {
                int[][] scratchmaze = new int[size][size];
                for (int[] col : scratchmaze) {
                    for (int item : col) {
                        item = 0;
                    }
                }
                newMaze.setMaze(scratchmaze);
            }
            whichMaze = 1;
            addMaze(mazeHolder);
        } else if (mazetype == 0) {
            newMaze2 = new PictureMaze(title, author, size, size);
            if (fromscratch == 0) {
                // ask the user to add the icons
                PictureObject icon = importImage("Icon", 1, 1, mazeHolder.getWidth() / slider.getValue());
                PictureObject start = importImage("Start", 1, 1, mazeHolder.getWidth() / slider.getValue());
                PictureObject end = importImage("End", 1, 1, mazeHolder.getWidth() / slider.getValue());
                if (icon != null && start != null && end != null) {
                    newMaze2 = new PictureMaze(title, author, size, size);
                    newMaze2.drawMaze();
                    // get the desired icon position
                    icon.setRow(newMaze2.getStartpos()[0]);
                    icon.setColumn(newMaze2.getStartpos()[1]);
                    newMaze2.setImage(icon);
                    newMaze2.setImage(start);
                    newMaze2.setImage(end);
                    whichMaze = 0;
                    addMaze(mazeHolder);
                } else if (fromscratch == 1) {
                    int[][] scratchmaze = new int[size][size];
                    for (int[] col : scratchmaze) {
                        for (int item : col) {
                            item = 0;
                        }
                    }
                    newMaze2.setMaze(scratchmaze);
                }
                whichMaze = 0;
                addMaze(mazeHolder);
            }
        }
    }
    // not finished
    private void importImageToMaze(ActionEvent actionEvent){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("png","PNG"));
        fileChooser.setAcceptAllFileFilterUsed(true);
        int rs = fileChooser.showOpenDialog(null);
        if(rs == JFileChooser.APPROVE_OPTION){
            try {
                File file = fileChooser.getSelectedFile();
                BufferedImage buffImage = ImageIO.read(file);
                if(newMaze!=null) {
                    if(newMaze.getIcon() != null) {
                        PictureObject tempIcon = newMaze.getIcon();
                        tempIcon.updateImg(buffImage);
                        newMaze.setIcon(tempIcon);

                    }else{
                        newMaze.setIcon(new PictureObject("Icon", 1, 1, buffImage, mazeHolder.getWidth() / slider.getValue()));
                    }
                }else if(newMaze2!= null){
                    if(newMaze.getIcon() != null) {
                        PictureObject tempIcon = newMaze2.getIcon();
                        tempIcon.updateImg(buffImage);
                        newMaze2.setImage(tempIcon);

                    }else{
                        newMaze2.setImage(new PictureObject("Icon", 1, 1, buffImage, mazeHolder.getWidth() / slider.getValue()));
                    }
                }
                addMaze(mazeHolder);
                System.out.print(mazeHolder);
            }
            catch(Exception e) {

            }
        }
    }
    //create PictureObject when generate a new maze
    private PictureObject importImage(String imageCategory,int row, int column, int tileSize) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Please select the "+imageCategory);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("png", "PNG"));
        fileChooser.setAcceptAllFileFilterUsed(true);
        int rs = fileChooser.showOpenDialog(null);
        if (rs == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                BufferedImage buffImage = ImageIO.read(file);
                PictureObject image = new PictureObject(imageCategory,row,column,buffImage,tileSize);
                return image;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
    // not finished
    private void exportMazeAsImg(ActionEvent actionEvent){
        //check if any maze on this window
        if(newMaze !=null || newMaze2 != null) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(".png", "png"));
            fileChooser.setAcceptAllFileFilterUsed(true);
            int rs = fileChooser.showSaveDialog(null);
            if (rs == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if (file.toString().contains(".png")) {
                    return;
                } else {
                    file = new File(file + ".png");
                }
                try {
                    MazeSolver solver = new MazeSolver();
                    if (whichMaze == 1) {
                        if (solve) {

                            new ExportPage(newMaze.getMaze(), solver.getPath(newMaze.getMaze()), file, newMaze);
                        } else {
                            new ExportPage(newMaze.getMaze(), null, file, newMaze);
                        }
                    } else if (whichMaze == 0) {
                        if (solve) {

                            new ExportPage(newMaze2.getMaze(), solver.getPath(newMaze2.getMaze()), file, newMaze2);
                        } else {
                            new ExportPage(newMaze2.getMaze(), null, file, newMaze2);
                        }
                    }
                } catch (Exception e) {
                }
            }
        }
    }


    public Main()  {
        super("Maze");
        mazeData = new MazeDatabaseData();
        whichMaze = 2;
        createGUI();
        createMenuBar();
        createPanel();

        setVisible(true);

    }
    public Main(BrokenWallMaze input)  {
        super("Maze");
        whichMaze = 1;
        newMaze = input;
        createGUI();
        createMenuBar();
        createPanel();
        updateTitle(newMaze.getMazeName());
        addMaze(mazeHolder);
        setVisible(true);

    }
    public Main(PictureMaze input)  {
        super("Maze");
        whichMaze = 0;
        newMaze2 = input;
        createGUI();
        createMenuBar();
        createPanel();
        updateTitle(newMaze2.getMazeName());
        addMaze(mazeHolder);
        setVisible(true);

    }

    public static void main(String[] args){
        new Main();
    }
}
