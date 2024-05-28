package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PanelListener implements MouseListener {
    private EditPage listener;

    public void setWhosListening(EditPage input) {
        this.listener = input;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object input = e.getSource();
        JPanel panelclicked = (JPanel) input;
        String cellname = panelclicked.getName();
        String[] coords = cellname.split(" ");
        int[] output = new int[2];
        output[0] = Integer.parseInt(coords[0]);
        output[1] = Integer.parseInt(coords[1]);
        listener.mazeChange(output);
        int color = listener.changecolor();
        switch (color) {
            case 0:
                panelclicked.setBackground(Color.GRAY);
                break;
            case 1:
                panelclicked.setBackground(Color.BLACK);
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
