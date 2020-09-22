package touro.snake;

/**
 * A model that contains the Snake and Food and is responsible for logic of moving the Snake,
 * seeing that food has been eaten and generating new food.
 */
public class Garden {

    public static final int WIDTH = 100;
    public static final int HEIGHT = 40;

    private final Snake snake;
    private final FoodFactory foodFactory;
    private Food food;

    public Garden(Snake snake, FoodFactory foodFactory) {
        this.snake = snake;
        this.foodFactory = foodFactory;
    }

    public Snake getSnake() {
        return snake;
    }

    public Food getFood() {
        return food;
    }

    /**
     * Moves the snake, checks to see if food has been eaten and creates food if necessary
     *
     * @return true if the snake is still alive, otherwise false.
     */
    public boolean advance() {

        if (scrollGardenIfNecessary() || moveSnake()) {
            createFoodIfNecessary();
            return true;
        }
        return false;
    }

    /**
     * Moves the Snake, eats the Food or collides with the wall (edges of the Garden), or eats self.
     *
     * @return true if the Snake is still alive, otherwise false.
     */
    boolean moveSnake() {
        snake.move();

        //if collides with self
        if (snake.eatsSelf()) {
            return false;
        }

        //if snake eats the food
        if (snake.getHead().equals(food)) {
            //add square to snake
            snake.grow();
            //remove food
            food = null;
        }
        return true;
    }

    /**
     * If snake reaches borders of garden, scroll background for an "infinite" garden.
     */
    boolean scrollGardenIfNecessary() {
        Square head = snake.getHead();
        int x = head.getX();
        int y = head.getY();

        Direction direction = snake.getSnakeHeadStateMachine().getDirection();

        //if snake is close to border AND facing border, background should scroll
        //this padding works to put 2 squares of padding on all borders. These numbers feel weird though.
        if ((x <= 2 && direction == Direction.West) ||
                (x >= Garden.WIDTH - 3 && direction == Direction.East) ||
                (y <= 2 && direction == Direction.North) ||
                (y >= Garden.HEIGHT - 5 && direction == Direction.South)) {

            scrollGarden(direction);
            return true;
         }

        return false;
    }


    private void scrollGarden(Direction direction) {
        scrollAISnakes(direction);
        scrollRocks(direction);
        scrollFood(direction);
    }

    private void scrollAISnakes(Direction direction) {
        //not implemented yet
/*
        for (Snake snake : garden.getAISnakes()) {
            for(Square s : snake.getSquares()) {
                int[] newSquare = getNewSquare(direction, s.getX() , s.getY());
                s.setX(newSquare[0]);
                s.setY(newSquare[1]);
            }
        }*/
    }

    private void scrollRocks(Direction direction) {
        //not implemented yet

/*
        for (Rock rock : garden.getRocks()){
            int x = rock.getX();
            int y = rock.getY();

            int[] newSquare = getNewSquare(direction, x , y);
            rock.setX(newSquare[0]);
            rock.setY(newSquare[1]);
        }*/
    }

    private void scrollFood(Direction direction) {
        int x = food.getX();
        int y = food.getY();

        int[] newSquare = getNewSquare(direction, x , y);
        food.setX(newSquare[0]);
        food.setY(newSquare[1]);
    }

    /**
     * Scrolls individual active square (i.e. food, rocks, AI snakes) in direction snake is moving.
     */
    private int[] getNewSquare(Direction direction, int x, int y) {
        int[] newSquare = new int[] {0, 0};

        switch (direction) {
            case North -> {
                newSquare[0] = x;
                newSquare[1] = y + 1;
            }
            case East -> {
                newSquare[0] = x - 1;
                newSquare[1] = y;
            }
            case South -> {
                newSquare[0] = x;
                newSquare[1] = y - 1;
            }
            case West -> {
                newSquare[0] = x + 1;
                newSquare[1] = y;
            }
        }

        return newSquare;
    }

    /**
     * Creates a Food if there isn't one, making sure it's not already on a Square occupied by the Snake.
     */
    void createFoodIfNecessary() {
        //if snake ate food, create new one
        if (food == null) {
            food = foodFactory.newInstance();

            //if new food on snake, put it somewhere else
            while (snake.contains(food)) {
                food = foodFactory.newInstance();
            }
        }
    }

}
