/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife;

import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

/**
 *
 * @author Andy
 */
public class Grid extends javax.swing.JFrame {
    
    private JFrame background = new JFrame("The Game of Life");
    private JPanel grid = new JPanel();
    private JButton start = new JButton("Start");
    private JLabel title = new JLabel("The Game of Life");
    
    private final int columns = 40;
    private final int rows = 40;
    int gridwidth = 740;
    int gridheight = 630;
    
    boolean[][] cell = new boolean[rows][columns];
    boolean[][] nextcell = new boolean[rows][columns];
    boolean on;
    
    Image Img;
    Graphics Graph;
    

    public Grid() {
        Design();
        
        Img = grid.createImage(gridwidth, gridheight);
        Graph = Img.getGraphics();
        
        // Changing the grid to make it come to life //
        Timer time = new Timer();
        TimerTask task = new TimerTask(){
            public void run(){
                if(on){
                    for(int i = 0; i < rows; i++){
                        for(int j = 0; j < columns; j++){
                            nextcell[i][j] = livecells(i,j);
                        }
                    }
                    for(int i = 0; i < rows; i++){
                        for(int j = 0; j < columns; j++){
                            cell[i][j]= nextcell[i][j];
                        }
                    }
                    draw();
                }
            }
        };
        time.scheduleAtFixedRate(task, 0, 100);
        draw();
    }
    
    public void draw(){
        Graph.setColor(Color.white);
        Graph.fillRect(0,0, gridwidth, gridheight);
        
        // Adding color to each cell when you click a cell //
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                if(cell[i][j]){
                    Graph.setColor(Color.black);
                    int y = i * gridheight / rows;
                    int x = j * gridwidth / columns;
                    Graph.fillRect(x, y, gridwidth / columns, gridheight/rows);
                }
            }
        }
        // Adding the borders to each cell //
        Graph.setColor(Color.black);
        for(int i = 1; i < rows; i++){
            int y = i * gridheight / rows;
            Graph.drawLine(0, y, gridwidth, y);
        }
        for(int j = 1; j < columns; j++){
            int x = j * gridwidth / columns;
            Graph.drawLine(x, 0, x, gridheight);
        }
        
        grid.getGraphics().drawImage(Img, 0, 0, grid);
    }
    private void gridMouseClicked(MouseEvent evt) {
        // To display the grid in the JPanel by clicking the Panel //
        
        int i = columns * evt.getY() / gridheight;
        int j = rows * evt.getX() / gridwidth;
        cell[i][j] = !cell[i][j];
        draw();
    }                                 
    
    private void gridComponentResized(ComponentEvent evt) {                                      
        Img = grid.createImage(gridwidth, gridheight);
        Graph = Img.getGraphics();
        draw();
    }
    private void startnstop(ActionEvent evt){
        // Actions when clicking on the button to stop or start the run //
        
        on = !on;
        if(on == true){
            start.setText("Stop");
        }
        else{
            start.setText("Start");
        }
        draw();
        
    }
    public boolean livecells(int i, int j){
        // Counting cells that are alive around 1 cell in the centre //
        int totallive = 0;
        
        if(i == 0 || i == rows - 1 || j == 0 || j == columns - 1){
            cell[i][j] = cell[i][j];
            Graph.setColor(Color.black);
        }
        else{
            if(cell[i-1][j-1])totallive++;
            if(cell[i-1][j])totallive++;
            if(cell[i-1][j+1])totallive++;
            if(cell[i][j-1])totallive++;
            if(cell[i][j+1])totallive++;
            if(cell[i+1][j-1])totallive++;
            if(cell[i+1][j])totallive++;
            if(cell[i+1][j+1])totallive++;
        }
        
        // The rules of the game //
        
        if(totallive == 3){
            return true;
        }
        if(cell[i][j] && totallive == 2){
            return true;
        }
        else{
            return false;
        }
    }
    
    private void Design(){
        
        // Design of the game //
        
        background.setLayout(null);
        background.setBounds(0, 0, 800, 800);
        background.getContentPane();
       
        grid.setBounds(20, 70, gridwidth, gridheight);
        grid.setBorder(BorderFactory.createLineBorder(Color.black));
        grid.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                gridMouseClicked(evt);
            }
        });
        grid.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentresize(java.awt.event.ComponentEvent evt){
                gridComponentResized(evt);
            }
        });
        
        title.setBounds(310, 10, 200 ,50);
        title.setFont(new java.awt.Font("Arial", 0, 24));
        
        start.setBounds(20, 720, 100, 25);
        start.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                startnstop(ae);
            }
        });
        
        background.add(title);
        background.add(grid);
        background.add(start);
        background.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        background.setVisible(true);
    }
}