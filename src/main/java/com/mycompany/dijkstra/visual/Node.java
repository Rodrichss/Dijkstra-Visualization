/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dijkstra.visual;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Rodrigo
 */
public class Node {
    int data;
    int x, y;

    public Node(int data){
        this.data = data;
    }
    public Node(int data, int x, int y) {
        this.data = data;
        this.x = x;
        this.y = y;
    }
    
    public void draw(Graphics g){
        g.setColor(Color.BLUE);
        g.fillOval(x - 15, y - 15, 30, 30);
        g.setColor(Color.WHITE);
        g.drawString(Integer.toString(data), x - 5, y + 5);
    }
}
