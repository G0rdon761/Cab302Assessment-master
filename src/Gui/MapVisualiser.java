package Gui;

import BackEnd.PictureObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class MapVisualiser extends JPanel {
    private JPanel mazeHolder;
    private int[] currcoords;
    private int[] pastcoords;
    private Main main;

    public MapVisualiser(JPanel mazeHolder) {
        this.mazeHolder = mazeHolder;
    }

    public void visualiser(int[][] sampleMaze, PanelListener listener) {
        mazeHolder.removeAll();
        mazeHolder.setLayout(new GridLayout(sampleMaze.length, sampleMaze[0].length));
        for (int i = 0; i < sampleMaze.length; i++) {
            for (int a = 0; a < sampleMaze[0].length; a++) {
                JPanel cell = new JPanel();
                switch (sampleMaze[i][a]) {
                    case 0:
                        cell.setBackground(Color.GRAY);
                        break;
                    case 1:
                        cell.setBackground(Color.BLACK);
                        break;
                    case 2:
                        cell.setBackground(Color.GREEN);
                        break;
                    case 3:
                        cell.setBackground(Color.RED);
                        break;
                }
                cell.addMouseListener(listener);
                cell.setName(i + " " + a);
                mazeHolder.add(cell);
            }
        }
    }

    public void visualiser(int[][] sampleMaze, int[][] solutions) {
        mazeHolder.removeAll();
        mazeHolder.setLayout(new GridLayout(sampleMaze.length, sampleMaze[0].length));
        // Add cell
        for (int i = 0; i < sampleMaze.length; i++) {
            for (int a = 0; a < sampleMaze[0].length; a++) {
                JPanel cell = new JPanel();
                switch (sampleMaze[i][a]) {
                    case 0:
                        cell.setBackground(Color.GRAY);
                        break;
                    case 1:
                        cell.setBackground(Color.BLACK);
                        break;
                    case 2:
                        cell.setBackground(Color.GREEN);
                        break;
                    case 3:
                        cell.setBackground(Color.RED);
                        break;
                }
                cell.setName(i + " " + a);
                mazeHolder.add(cell);

            }
        }
        Component[] mazetiles = mazeHolder.getComponents();
        if (solutions != null) {
            for (int x = 1; x < solutions.length-1; x++) {
                String coords = solutions[x][0] + " " + solutions[x][1];
                for (Component item : mazetiles) {
                    JPanel realitem = (JPanel) item;
                    String itemname = realitem.getName();
                    if (coords.equals(itemname)) {
                        realitem.setBackground(Color.pink);
                    }
                }
            }
        }

    }

    public void addImage(int [][] maze, PictureObject icon){
        if(icon.getName()=="Start"||icon.getName()=="End") {
            HashMap<String, Integer> startEnd = new HashMap<String, Integer>();
            // Add keys and values (Country, City)
            startEnd.put("Start", 2);
            startEnd.put("End", 3);
            for (int x = 0; x < maze.length; x++) {
                for (int y = 0; y < maze[x].length; y++) {
                    if (maze[x][y] == startEnd.get(icon.getName())) {
                        icon.setRow(x);
                        icon.setColumn(y);
                    }
                }
            }
        }

        Component[] mazetiles = mazeHolder.getComponents();
        String imgCoord = ""+icon.getRow()+" "+icon.getColumn();
        for(Component component: mazetiles){
            JPanel iconTile = (JPanel) component;
            String name = iconTile.getName();
            if(imgCoord.equals(name)){
                JLabel iconHolder = new JLabel();
                switch(icon.getName()){
                    case "Icon":
                    iconTile.setBackground(Color.WHITE);
                    break;
                    case "Start":
                    iconTile.setBackground(Color.GRAY);
                    break;
                    case "End":
                    iconTile.setBackground(Color.GRAY);
                    break;
                }

                iconTile.add(iconHolder);
                BufferedImage resizedImage = new BufferedImage(icon.getTileSize(), icon.getTileSize(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g = resizedImage.createGraphics();
                g.drawImage(icon.getImage(), icon.getRow(), icon.getColumn(),icon.getTileSize(), icon.getTileSize(), null);
                g.dispose();
                iconHolder.setIcon(new ImageIcon(resizedImage));

            }
        }
    }
}
