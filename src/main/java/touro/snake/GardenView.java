package touro.snake;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GardenView extends JComponent {

    private final Garden garden;
    public static final int CELL_SIZE = 10;

    public GardenView(Garden garden) {
        this.garden = garden;
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
        paintListOfSquares(garden.getSnake().getSquares(), g);
    }

    void paintFood(Graphics g) {
        if (garden.getFood() != null) {
            Food food = garden.getFood();
            g.setColor(Color.LIGHT_GRAY);
            paintSquare(food, g);
        }
    }

    void paintSnakePath(Graphics g) {
        g.setColor(Color.BLUE);
        paintListOfSquares(garden.getSnake().getStrategy().getPath(), g);
    }

    void paintOpenNodes(Graphics g) {
        g.setColor(Color.CYAN);
        paintListOfSquares(garden.getSnake().getStrategy().getOpenSearchSpace(), g);
    }

    void paintClosedNodes(Graphics g) {
        g.setColor(Color.orange);
        paintListOfSquares(garden.getSnake().getStrategy().getClosedSearchSpace(), g);
    }

    private void paintListOfSquares(List<Square> squareList, Graphics g) {
        for (Square square : squareList) {
            paintSquare(square, g);
        }
    }

    private void paintSquare(Square square, Graphics g) {
        int x = square.getX() * CELL_SIZE;
        int y = square.getY() * CELL_SIZE;
        g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
    }
}
