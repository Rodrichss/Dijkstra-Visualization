/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dijkstra.visual;

/**
 *
 * @author Rodrigo
 */
public class Edge {
    int source;
    int destination;
    int weight;
    boolean isShortestPath;

    public Edge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.isShortestPath = false;
    }
}
