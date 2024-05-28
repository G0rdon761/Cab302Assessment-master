package Gui;
import BackEnd.BrokenWallMaze;
import BackEnd.JDBCMazeDatabaseSource;
import BackEnd.PictureMaze;
import BackEnd.PictureObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import static Gui.Main.createButton;

public class EditPage extends JFrame{
    private static final int WIDTH = 1100;
    private static final int HEIGHT = 800;
    private int selectedColor = 5;
    private int whichIsIt = 2;
    private PanelListener listner = new PanelListener();
    private BrokenWallMaze editMaze1;
    private PictureMaze editMaze2;
    private JPanel leftPanel, mazeHolder, toolbarPanel, actionPanel,
            cellTypePanel, cellTypeInforPanel, locationPanel, topPanel, setIconPanel, setIconStartEndPanel;
    private JPanel mainPanel;
    private JDBCMazeDatabaseSource database;
    Main mainPage;
    //sample array maze
    private Integer[] [] sampleMaze =
            {
                    {1,4,1,1,1,1,1,1},
                    {1,0,0,0,0,0,0,1},
                    {1,0,1,1,1,1,1,1},
                    {1,0,0,0,0,0,0,1},
                    {1,1,1,1,0,1,0,1},
                    {1,0,0,0,0,1,1,1},
                    {1,1,1,1,0,0,0,1},
                    {1,1,1,1,1,1,3,1},
            };
    /**
     * Class constructor
     */
    public EditPage() {
        super("Edit maze");
        whichIsIt = 2;
        createGUI(null);
        listner.setWhosListening(this);
        setVisible(true);
    }
    public EditPage(BrokenWallMaze input){
        super("Edit Maze");
        whichIsIt = 1;
        editMaze1 = input;
        createGUI(input.getMazeName());
        listner.setWhosListening(this);
        setVisible(true);
    }
    public EditPage(PictureMaze input){
        super("Edit Maze");
        whichIsIt = 0;
        editMaze2 = input;
        createGUI(input.getMazeName());
        listner.setWhosListening(this);
        setVisible(true);
    }
    private void createGUI(String title){

        setSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        createMainPanel(title);
        mazeHolder();
        JScrollPane scroller = new JScrollPane(leftPanel);
        mainPanel.add(scroller);

    }

    /**
     * Panel for holding the maze
     */
    private void mazeHolder(){
        createLeftPanel();
        leftPanel = new JPanel();
        mazeHolder = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        MapVisualiser mapVisualiser = new MapVisualiser(mazeHolder);
        if (whichIsIt == 0) {
            mapVisualiser.visualiser(editMaze2.getMaze(),listner );
            if(editMaze2.getIcon() != null){
                PictureObject icon = editMaze2.getIcon();
                mapVisualiser.addImage(editMaze2.getMaze(),icon);
                if(editMaze2.getStartImg()!=null){
                    mapVisualiser.addImage(editMaze2.getMaze(),editMaze2.getStartImg());

                }
                if(editMaze2.getEndImg()!=null){
                    mapVisualiser.addImage(editMaze2.getMaze(),editMaze2.getEndImg());
                }
            }
        }
        else if (whichIsIt == 1) {
            mapVisualiser.visualiser(editMaze1.getMaze(),listner);
            if(editMaze1.getIcon() != null){
                PictureObject icon = editMaze1.getIcon();
                mapVisualiser.addImage(editMaze1.getMaze(),icon);
            }
        }
        mazeHolder.setBorder(BorderFactory.createEmptyBorder(30,50,10,40));
        leftPanel.remove(mazeHolder);
        leftPanel.add(mazeHolder);
    }

    private void updateMaze() {
        MapVisualiser mapVisualiser = new MapVisualiser(mazeHolder);
        if (whichIsIt == 0) {
            mapVisualiser.visualiser(editMaze2.getMaze(),listner);
            if(editMaze2.getStartImg() != null){
                PictureObject startIcon = editMaze2.getStartImg();
                mapVisualiser.addImage(editMaze2.getMaze(), startIcon);
            }
            if(editMaze2.getEndImg() != null){

                PictureObject endIcon = editMaze2.getEndImg();
                mapVisualiser.addImage(editMaze2.getMaze(), endIcon);
            }
        }
        else if (whichIsIt == 1) {
            mapVisualiser.visualiser(editMaze1.getMaze(),listner);
        }
        mazeHolder.setBorder(BorderFactory.createEmptyBorder(30,50,10,40));
        leftPanel.remove(mazeHolder);
        leftPanel.add(mazeHolder);
        SwingUtilities.updateComponentTreeUI(leftPanel);
    }

    private void createTitleLabel(String title){
        topPanel = new JPanel();
        JLabel titleLabel = new JLabel("Currently Editing: " + title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        topPanel.add(titleLabel);
    }
    private void createLeftPanel(){
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        mazeHolder = new JPanel();
        leftPanel.add(mazeHolder);
    }

    /**
     * Panel for showing the "At cell" label
     */
    private void atCell(){
        locationPanel = new JPanel();
        JLabel atPosLabel = new JLabel("At cell: row:   column:");
        atPosLabel.setFont(new Font("Arial", Font.BOLD, 20));
        atPosLabel.setBorder(BorderFactory.createEmptyBorder(10,0,30,280));
        locationPanel.add(atPosLabel);
    }

    private void createMainPanel(String title){
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        createTitleLabel(title);
        createLeftPanel();
        createToolBar();
        atCell();
        mainPanel.add(topPanel, BorderLayout.NORTH);
        //JScrollPane scroller = new JScrollPane(leftPanel);
        //mainPanel.add(scroller, BorderLayout.WEST);
        mainPanel.add(toolbarPanel, BorderLayout.EAST);
        mainPanel.add(locationPanel, BorderLayout.SOUTH);
        getContentPane().add(mainPanel);
    }
    /**
     * Button Panel for holding buttons to change cell type
     */
    private void cellTypeButtonPanel(){
        cellTypeInforPanel = new JPanel();
        cellTypeInforPanel.setLayout(new BoxLayout(cellTypeInforPanel, BoxLayout.Y_AXIS));
        JLabel label = new JLabel("--Setting cell as--");
        label.setBorder(BorderFactory.createEmptyBorder(20,0,20,60));
        label.setFont(new Font("Arial", Font.BOLD, 18));

        cellTypePanel = new JPanel();
        cellTypePanel.setLayout(new GridLayout(2,2, 0, 0));
        //JLabel label = new JLabel("--Setting cell as--");
        //JLabel emptyLabel = new JLabel();
        JButton wallButton = createButton("Wall",this::CellType, "Arial", 20);
        wallButton.setName("1");
        JButton pathButton = createButton("Path", this::CellType,"Arial", 20);
        pathButton.setName("0");
        JButton startButton = createButton("Start", this::CellType,"Arial", 20);
        startButton.setName("2");
        JButton endButton = createButton("End", this::CellType,"Arial", 20);
        endButton.setName("3");


        //cellTypePanel.add(label);
        //cellTypePanel.add(emptyLabel);
        cellTypePanel.add(wallButton);
        cellTypePanel.add(pathButton);
        cellTypePanel.add(startButton);
        cellTypePanel.add(endButton);
        //cellTypePanel.setBorder(BorderFactory.createEmptyBorder(0,0,50,0));

        cellTypeInforPanel.add(label);
        cellTypeInforPanel.add(cellTypePanel);
        cellTypeInforPanel.setBorder(BorderFactory.createEmptyBorder(30,0,30,0));
    }
    public void CellType(ActionEvent actionEvent) {
        JButton buttonpressed = (JButton) actionEvent.getSource();
        String buttonname = buttonpressed.getName();
        this.selectedColor = Integer.parseInt(buttonname);
    }

    /**
     * Panel for holding the set icon buttons in the maze
     */
    private void setIconPanel(){
        setIconPanel = new JPanel();
        setIconPanel.setLayout(new GridLayout(2,1));
        JButton setStart = createButton("Set Start", this::setIcon,"Arial",20);
        setStart.setName("start");
        JButton setEnd = createButton("Set End", this::setIcon, "Arial", 20);
        setEnd.setName("end");
        setIconStartEndPanel = new JPanel();
        setIconStartEndPanel.setLayout(new GridLayout(1,2));
        setIconStartEndPanel.add(setStart);
        setIconStartEndPanel.add(setEnd);

        setIconPanel.add(new JButton("Set Icon"));
        setIconPanel.add(setIconStartEndPanel);
        setIconPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

    }
    public void setIcon(ActionEvent actionEvent){
        JButton buttonPressed = (JButton) actionEvent.getSource();
        String buttonName = buttonPressed.getName();
        System.out.println(buttonName);
        if(editMaze2 != null) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(".png", "png"));
            fileChooser.setAcceptAllFileFilterUsed(true);
            int rs = fileChooser.showOpenDialog(null);
            if (rs == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = fileChooser.getSelectedFile();
                    BufferedImage buffImage = ImageIO.read(file);
                    if (buttonName == "start") {
                        editMaze2.setImage(new PictureObject("Start", 1, 1, buffImage, mazeHolder.getWidth() / 50));
                        updateMaze();
                    } else if (buttonName == "end") {
                        editMaze2.setImage(new PictureObject("End", 1, 1, buffImage, mazeHolder.getWidth() / 50));
                        updateMaze();
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }

    }

    /**
     * Panel for holding cancel and edit button
     */
    private void bottomRightPanel(){
        actionPanel = new JPanel();
        actionPanel.setLayout(new GridLayout(1,2, 0, 0));
//        JButton cancelButton = new JButton("Cancel");
//        cancelButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                dispose();
//                new Main();
//            }
//        });
//        JButton editButton = new JButton("Edit");
//        editButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                dispose();
//                new Main();
//            }
//        });
//        actionPanel.add(cancelButton);
//        actionPanel.add(editButton);
        actionPanel.add(createButton("Cancel", this::CancelClicked, "Arial", 20));
        actionPanel.add(createButton("Save", this::SaveClicked, "Arial", 20));
        actionPanel.setBorder(BorderFactory.createEmptyBorder(40,0,0,0));

    }

    private void CancelClicked(ActionEvent actionEvent) {
        dispose();
        if (whichIsIt == 1){
            new Main(editMaze1);
        }
        else if (whichIsIt == 0){
            new Main(editMaze2);
        }
    }

    private void SaveClicked(ActionEvent actionEvent) {
        database = new JDBCMazeDatabaseSource();
        if (whichIsIt == 1) {
            database.insertMaze(editMaze1);
            JOptionPane.showMessageDialog(null,"saved successfully");
            new Main(editMaze1);
            dispose();
        }
        else if (whichIsIt == 0)  {
            database.insertMaze(editMaze2);
            JOptionPane.showMessageDialog(null,"saved successfully");
            new Main(editMaze2);
            dispose();
        }



    }


    private void createToolBar(){
        toolbarPanel = new JPanel();
        toolbarPanel.setLayout(new BoxLayout(toolbarPanel, BoxLayout.Y_AXIS));
        //toolbarPanel.setLayout(new GridLayout(3,1, 0, 0));
        cellTypeButtonPanel();
        setIconPanel();
        bottomRightPanel();
        toolbarPanel.add(cellTypeInforPanel);
        toolbarPanel.add(setIconPanel);
        toolbarPanel.add(actionPanel);
        toolbarPanel.setBorder(BorderFactory.createEmptyBorder(0,0,10,50));
    }

//    /**
//     * For checking which cell the user selected
//     */
//    private void checkPosition(){
//        ActionListener cursor = new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//            }
//        }
//    }

    public void mazeChange(int[] editcoords) {
        if (whichIsIt == 0 & this.selectedColor != 5) {
            if (selectedColor == 2) {
                editMaze2.setStart(editcoords[0],editcoords[1]);
                updateMaze();
            }
            else if (selectedColor == 3) {
                editMaze2.setEnd(editcoords[0],editcoords[1]);
                updateMaze();
            }
            else {
                editMaze2.editMaze(editcoords[0],editcoords[1],this.selectedColor);
            }


        }
        else if (whichIsIt == 1 & this.selectedColor != 5) {
            if (selectedColor == 2) {
                editMaze1.setStart(editcoords[0],editcoords[1]);
                updateMaze();
            }
            else if (selectedColor == 3) {
                editMaze1.setEnd(editcoords[0],editcoords[1]);
                updateMaze();
            }
            else {
                editMaze1.editMaze(editcoords[0],editcoords[1],this.selectedColor);
            }
        }
    }
    public int changecolor() {
        return this.selectedColor;
    }


}

