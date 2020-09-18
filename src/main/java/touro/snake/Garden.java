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

        int padding = 2;

        //if snake is close to border and background should scroll
        if (x <= padding || x >= Garden.WIDTH - padding || y <= padding || y >= Garden.HEIGHT - padding) {
            scrollGarden();
            return true;
         }

        return false;
    }


    /**
     * Scrolls active cells (i.e. food, rocks, AI snakes) in direction snake is moving.
     */
    void scrollGarden() {
        Direction direction = snake.getSnakeHeadStateMachine().getDirection();

        //to scroll AI snakes probs just need to call their snake.move()

        //to scroll food/rocks

        int x = food.getX();
        int y = food.getY();

        switch (direction) {
            case North -> {
                food.setX(x);
                food.setY(y - 1);
            }
            case East -> {
                food.setX(x + 1);
                food.setY(y);
            }
            case South -> {
                food.setX(x);
                food.setY(y + 1);
            }
            case West -> {
                food.setX(x - 1);
                food.setY(y);
            }
        }
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
