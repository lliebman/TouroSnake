package touro.snake;

import touro.snake.strategy.astar.Node;
import touro.snake.strategy.astar.liebman.AStarStrategy;

import javax.swing.*;
import java.awt.*;

public class GardenView extends JComponent {

    private final Garden garden;
    private final AStarStrategy strategy;
    public static final int CELL_SIZE = 10;

    public GardenView(Garden garden, AStarStrategy strategy) {
        this.garden = garden;
        this.strategy = strategy;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintGrass(g);
        paintOpenNodes(g);
        paintClosedNodes(g);
        paintSnakePath(g);
        paintSnake(g);
        paintFood(g);
    }

    void paintGrass(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    void paintSnake(Graphics g) {
        g.setColor(Color.RED);
        for (Square s : garden.getSnake().getSquares()) {
            g.fillRect(s.getX()*CELL_SIZE, s.getY()*CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }

    void paintFood(Graphics g) {
        if (garden.getFood() != null) {
            Food food = garden.getFood();
            g.setColor(Color.LIGHT_GRAY);

            int x = food.getX() * CELL_SIZE;
            int y = food.getY() * CELL_SIZE;
            g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
        }
    }

    void paintSnakePath(Graphics g) {
        g.setColor(Color.BLUE);
        for (Square s : strategy.getPath()) {
            g.fillRect(s.getX()*CELL_SIZE, s.getY()*CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }

    void paintOpenNodes(Graphics g) {
        g.setColor(Color.CYAN);
        for (Square s : strategy.getOpenSearchSpace()) {
            g.fillRect(s.getX()*CELL_SIZE, s.getY()*CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }

    void paintClosedNodes(Graphics g) {
        g.setColor(Color.orange);
        for (Square s : strategy.getClosedSearchSpace()) {
            g.fillRect(s.getX()*CELL_SIZE, s.getY()*CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }
}
