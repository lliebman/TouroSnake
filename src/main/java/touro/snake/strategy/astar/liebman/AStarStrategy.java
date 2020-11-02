package touro.snake.strategy.astar.liebman;

import touro.snake.*;
import touro.snake.strategy.SnakeStrategy;
import touro.snake.strategy.astar.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Made mostly together by Lillian Liebman and Michal Berger
 */
public class AStarStrategy implements SnakeStrategy {
    Direction[] directions = Direction.values();
    List<Square> snakePath = new ArrayList<>();

    //for use within class
    ArrayList<Node> openNodes = new ArrayList<>();
    HashSet<Node> closedNodes = new HashSet<>();

    //for overridden return functions
    List<Square> open = new ArrayList<>();
    List<Square> closed = new ArrayList<>();

    @Override
    public List<Square> getPath() {
        return snakePath;
    }

    @Override
    public List<Square> getOpenSearchSpace() {
        return open;
    }

    @Override
    public List<Square> getClosedSearchSpace() {
        return closed;
    }

    @Override
    public void turnSnake(Snake snake, Garden garden) {
        Node startNode = new Node(snake.getHead());
        Food food = garden.getFood();
        if (food == null) {
            return;
        }
        clearLists();
        Node endNode = new Node(food);
        Direction finalDirection = getSnakeDirection(snake, startNode, endNode);

        snake.turnTo(finalDirection);
    }

    private Direction getSnakeDirection(Snake snake, Node startNode, Node endNode) {
        Node currentNode = null;
        openNodes.add(startNode);

        while (!openNodes.isEmpty()) {
            currentNode = getLowestNode(openNodes);

            openNodes.remove(currentNode);
            closedNodes.add(currentNode);
            closed.add(currentNode);
            if (currentNode.equals(endNode)) {
                break;
            }
            sortNeighbors(snake, currentNode, endNode);
        }
        return backtrack(startNode, currentNode);
    }

    private void sortNeighbors(Snake snake, Node currentNode, Node endNode) {
        for (Direction d : directions) {
            Node neighbor =  new Node(currentNode.moveTo(d), currentNode, endNode);
            if (snake.contains(neighbor) ||
                    !neighbor.inBounds() ||
                    closedNodes.contains(neighbor)) {
                continue;
            }
            else if (open.contains(neighbor)){
                int index = openNodes.indexOf(neighbor);
                Node olNode = openNodes.get(index);
                if (neighbor.getCost() < olNode.getCost()) {
                    open.set(index, neighbor);
                }
            }
            else {
                openNodes.add(neighbor);
                open.add(neighbor);
            }
        }
    }

    private Direction backtrack(Node startNode, Node currentNode) {
        while (currentNode.getParent() != startNode) {
            snakePath.add(currentNode);
            currentNode = currentNode.getParent();
        }
        return startNode.directionTo(currentNode);
    }

    private Node getLowestNode(ArrayList<Node> openNodes) {
        Node minNode = openNodes.get(0);
        for (Node node : openNodes) {
            if (node.getCost() < minNode.getCost()) {
                minNode = node;
            }
        }
        return minNode;
    }

    private void clearLists() {
        snakePath.clear();
        open.clear();
        closed.clear();
        openNodes.clear();
        closedNodes.clear();;
    }
}
