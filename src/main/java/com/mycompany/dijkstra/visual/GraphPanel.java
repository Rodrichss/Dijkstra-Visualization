/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dijkstra.visual;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 *
 * @author Rodrigo
 */
public class GraphPanel extends JPanel {
    private List<Node> nodes;
    private Graph graph;
    private JPopupMenu popupMenu;
    private Point clickedPoint;

    public GraphPanel() {
        System.out.println("GraphPanel initialized");
        nodes = new ArrayList<>();
        graph = new Graph(0);
        createPopupMenu();
        
        //setBackground(Color.BLUE);
        setPreferredSize(new Dimension(800, 600));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickedPoint = e.getPoint();
                
                if (e.getButton()==MouseEvent.BUTTON1) {
                    
                    addNode(e.getX(), e.getY());
                } else if (e.getButton()==MouseEvent.BUTTON3) {
                    
                    popupMenu.show(GraphPanel.this, e.getX(), e.getY());
                }
            }
        });
    }

    private void createPopupMenu() {
        popupMenu = new JPopupMenu();

        JMenuItem addEdgeItem = new JMenuItem("Añadir arista");
        addEdgeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String startNodeLabel = JOptionPane.showInputDialog("Ingresa el nodo de inicio:");
                String endNodeLabel = JOptionPane.showInputDialog("Ingresa el nodo de destino:");
                String weightStr = JOptionPane.showInputDialog("Ingresa el peso de la arista:");

                try {
                    Node startNode = getNodeByLabel(startNodeLabel);
                    Node endNode = getNodeByLabel(endNodeLabel);
                    int weight = Integer.parseInt(weightStr);
                    addEdge(startNode, endNode, weight);
                    repaint();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(GraphPanel.this, "Valor de peso inválido.");
                } catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(GraphPanel.this, "Valor de nodo inválido.");
                }
            }
        });
        popupMenu.add(addEdgeItem);

//        JMenuItem removeNodeItem = new JMenuItem("Remover Nodo");
//        removeNodeItem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String nodeLabel = JOptionPane.showInputDialog("Ingresa el nodo a remover:");
//                Node node = getNodeByLabel(nodeLabel);
//                if (node != null) {
//                    removeNode(node);
//                    repaint();
//                } else {
//                    JOptionPane.showMessageDialog(GraphPanel.this, "Nodo no encontrado.");
//                }
//            }
//        });
//        popupMenu.add(removeNodeItem);

        JMenuItem runDijkstraItem = new JMenuItem("Correr Dijkstra");
        runDijkstraItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String startNodeLabel = JOptionPane.showInputDialog("Ingresa el nodo de inicio:");
                String endNodeLabel = JOptionPane.showInputDialog("Ingresa el nodo destino:");
                Node startNode = getNodeByLabel(startNodeLabel);
                Node endNode = getNodeByLabel(endNodeLabel);
                if (startNode != null && endNode != null) {
                    runDijkstra(startNode, endNode);
                } else {
                    JOptionPane.showMessageDialog(GraphPanel.this, "Número de nodo inválido.");
                }
            }
        });
        popupMenu.add(runDijkstraItem);
    }

    private void addNode(int x, int y) {
        String label = JOptionPane.showInputDialog("Ingresa el número del nodo:");
        if (label != null && !label.isEmpty() && getNodeByLabel(label) == null) {
            Node node = new Node(Integer.parseInt(label), x, y);
            nodes.add(node);
            graph.addNode(node);
            graph.resizeMatrix(nodes.size());
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Número de nodo duplicado o inválido.");
        }
    }

    private void addEdge(Node start, Node end, int weight) {
        if (start != null && end != null && start != end) {
            graph.addEdge(nodes.indexOf(start), nodes.indexOf(end), weight);
        }
    }

    private void removeNode(Node node) {
        int index = nodes.indexOf(node);
        if (index != -1) {
            nodes.remove(node);
            graph.removeNode(index);
        }
    }

    private Node getNodeByLabel(String label) {
        for (Node node : nodes) {
            if (Integer.toString(node.data).equals(label)) {
                return node;
            }
        }
        return null;
    }

    public void runDijkstra(Node start, Node end) {

    int startIndex = nodes.indexOf(start);
    int endIndex = nodes.indexOf(end);
    if (startIndex != -1 && endIndex != -1) {
        graph.resetEdges();
        graph.shortestPath(startIndex, endIndex);
        graph.markShortestPath(startIndex, endIndex);
        repaint(); 
    } else {
        JOptionPane.showMessageDialog(GraphPanel.this, "Número de nodo inválido.");
    }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < nodes.size(); j++) {
                int weight = graph.mAdy[i][j];
                if (weight > 0) {
                    Node start = nodes.get(i);
                    Node end = nodes.get(j);
                    g.setColor(Color.BLACK);
                    g.drawLine(start.x, start.y, end.x, end.y);
                    g.drawString(Integer.toString(weight), (start.x + end.x) / 2, (start.y + end.y) / 2);
                }
            }
        }
        for (Node node : nodes) {
            node.draw(g);
        }

        for (Edge edge : graph.getEdges()) {
            Node startNode = nodes.get(edge.source);
            Node endNode = nodes.get(edge.destination);

            if (edge.isShortestPath) {
                g.setColor(Color.RED); 
            } else {
                g.setColor(Color.BLACK); 
            }
     
            g.drawLine(startNode.x, startNode.y, endNode.x, endNode.y);

            g.drawString(Integer.toString(edge.weight), (startNode.x + endNode.x) / 2, (startNode.y + endNode.y) / 2);
        }
    }

    
}
