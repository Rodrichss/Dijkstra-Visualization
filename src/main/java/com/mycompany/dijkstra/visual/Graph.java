/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dijkstra.visual;

/**
 *
 * @author Rodrigo
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
public class Graph {
    int [][] mAdy;
    Vector<Node> nodes;
    List<Edge> edges;
    int[] shortestPathPrevious;

    public Graph(int size){
        mAdy = new int[size][size];
        nodes = new Vector<>();
        edges = new ArrayList();
    }

    public void addNode(Node node){
        nodes.add(node);
        resizeMatrix(nodes.size());
    }

    public void addEdge(int source, int destination, int weight){
        mAdy[source][destination] = weight;
        mAdy[destination][source] = weight;
        edges.add(new Edge(source, destination, weight));
    }
    
    public List<Edge> getEdges() {
        return edges;
    }

//    public void print(){
//        System.out.print("  ");
//        for (Node node : nodes) {
//            System.out.print(node.data+" ");
//        }
//        System.out.println();
//
//        for(int i=0;i<mAdy.length;i++){
//            System.out.print(nodes.get(i).data+ " ");
//            for(int j=0;j<mAdy.length;j++){
//                System.out.print(mAdy[i][j]+" ");
//            }
//            System.out.println();
//        }
//    }
    
    public void removeNode(int index) {
        nodes.remove(index);
        int[][] newMAdy = new int[nodes.size()][nodes.size()];
        for (int i = 0, k = 0; i < mAdy.length; i++) {
            if (i == index) continue;
            for (int j = 0, l = 0; j < mAdy[i].length; j++) {
                if (j == index) continue;
                newMAdy[k][l] = mAdy[i][j];
                l++;
            }
            k++;
        }
        mAdy = newMAdy;
    }

    public void resizeMatrix(int newSize) {
        int[][] newMAdy = new int[newSize][newSize];
        for (int i = 0; i < mAdy.length; i++) {
            for (int j = 0; j < mAdy[i].length; j++) {
                newMAdy[i][j] = mAdy[i][j];
            }
        }
        mAdy = newMAdy;
    }
    
    public void shortestPath(int inicio, int fin){
        int size = mAdy.length;
        boolean[] explored = new boolean[size];
        int[] pesoViaje = new int[size];
        shortestPathPrevious = new int[size];

        Arrays.fill(explored,false);
        Arrays.fill(pesoViaje, Integer.MAX_VALUE);
        Arrays.fill(shortestPathPrevious, -1);

        int actual = inicio;
        
        explored[actual] = true;
        pesoViaje[actual] = 0;

        for(int i=0;i<size-1;i++){
            //update estimates
            for(int j=0;j<size;j++){
                if(!explored[j] && mAdy[actual][j]>0 && 
                pesoViaje[actual]+mAdy[actual][j]<pesoViaje[j]){
                    pesoViaje[j] = pesoViaje[actual]+mAdy[actual][j];
                    shortestPathPrevious[j] = actual;
                }
            }
            //choose next vertex
            int shortest=-1;
            for(int k=0;k<pesoViaje.length;k++){
                if((shortest==-1||pesoViaje[k]<pesoViaje[shortest])&&!explored[k]){
                    shortest = k;
                    actual = shortest;
                }
            }
            explored[actual] = true;
            if(actual==fin){
                break;
            }
        }
        System.out.println("Peso del camino más corto: " + pesoViaje[fin]);

    }
    
    public void markShortestPath(int inicio, int fin) {
        int current = fin;
        while (current != inicio) {
            int prev = shortestPathPrevious[current];
            for (Edge edge : edges) {
                if ((edge.source == prev && edge.destination == current) || (edge.source == current && edge.destination == prev)) {
                    edge.isShortestPath = true;
                    break;
                }
            }
            current = prev;
        }
    }
    
    public void resetEdges() {
        for (Edge edge : edges) {
            edge.isShortestPath = false;
        }
    }
    /*public boolean checkEdge(int source, int destination){
        if(mAdy[source][destination]==1){
            return true;
        }else{
            return false;
        }
    }*/
}
