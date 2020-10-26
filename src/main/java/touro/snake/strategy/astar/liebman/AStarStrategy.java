package touro.snake.strategy.astar.liebman;

import touro.snake.*;
import touro.snake.strategy.SnakeStrategy;
import touro.snake.strategy.astar.Node;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Made together by Lillian Liebman and Michal Berger
 */
public class AStarStrategy implements SnakeStrategy {
    Direction[] directions = Direction.values();

    @Override
    public void turnSnake(Snake snake, Garden garden) {
        Node startNode = new Node(snake.getHead());
        Food food = garden.getFood();
        if (food == null) {
            return;
        }

        Node endNode = new Node(food);
        ArrayList<Node> openNodes = new ArrayList<>();
        HashSet<Node> closedNodes = new HashSet<>();

        openNodes.add(startNode);

        Direction finalDirection;


        while (true) {
            Node currentNode = getLowestNode(openNodes);
            openNodes.remove(currentNode);
            closedNodes.add(currentNode);
            if (currentNode.equals(endNode)) {
                finalDirection = backtrack(startNode, currentNode);
                break;
            }
            for (Direction d : directions) {
                Node neighbor =  new Node(currentNode.moveTo(d), currentNode, endNode);
                if (snake.contains(neighbor) ||
                        !neighbor.inBounds() ||
                        closedNodes.contains(neighbor)) {
                    continue;
                }
                double newCostToNeighbour = currentNode.fromStart + currentNode.distance(neighbor);
                 if (newCostToNeighbour < currentNode.fromStart || !openNodes.contains(neighbor)) {
                    openNodes.add(neighbor);
                }
            }
        }
        snake.turnTo(finalDirection);
    }

    private Direction backtrack(Node startNode, Node currentNode) {
        while (currentNode.getParent() != startNode) {
            currentNode = currentNode.getParent();
        }
        return getDirection(startNode, currentNode);
    }

    private Direction getDirection(Node startNode, Node endNode) {
        if (startNode.getX() + 1 == endNode.getX()) {
            return Direction.East;
        }
        else if (startNode.getX() - 1 == endNode.getX()) {
            return Direction.West;
        }
        else if (startNode.getY() - 1 == endNode.getY()) {
            return Direction.North;
        }
        else if (startNode.getY() + 1 == endNode.getY()) {
            return Direction.South;
        }
        return null;
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
}
