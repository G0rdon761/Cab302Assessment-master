package Gui;

import BackEnd.Maze;

import javax.imageio.ImageIO;
import javax.swing.*;
import BackEnd.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ExportPage extends JFrame{
    public ExportPage(int[][] maze, int[][] solution, File file, Maze mazeClass) {
        JPanel content = new JPanel();
        MapVisualiser visualiser = new MapVisualiser(content);
        visualiser.visualiser(maze,solution);
        //add images to export
        System.out.println(mazeClass.getClass().getName());
        try {
            if (mazeClass.getClass().getName().equals("BackEnd.BrokenWallMaze")) {
                visualiser.addImage(maze, mazeClass.getIcon());
            } else if (mazeClass.getClass().getName().equals("BackEnd.PictureMaze")) {
                PictureMaze tempPictureMaze = (PictureMaze) mazeClass;
                visualiser.addImage(maze, mazeClass.getIcon());
                visualiser.addImage(maze, tempPictureMaze.getStartImg());
                visualiser.addImage(maze, tempPictureMaze.getEndImg());
            }
        }
        catch(Exception e) {

        }
        getContentPane().add(content);
        pack();
        setVisible(true);

        Container c = getContentPane();
        BufferedImage screenCapture = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_RGB);
        c.paint(screenCapture.getGraphics());

        try{
            ImageIO.write(screenCapture, "png", file);
        }
        catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        dispose();
    }

}
