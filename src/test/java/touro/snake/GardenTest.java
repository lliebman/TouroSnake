package touro.snake;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GardenTest {

    @Test
    public void moveSnake() {
        /*
        Tests that snake moves and that when snake's move does not result
        in death.
         */
        //given
        Snake snake = mock(Snake.class);
        FoodFactory foodFactory = mock(FoodFactory.class);
        Garden garden = new Garden(snake, foodFactory);

       // doReturn(true).when(snake).inBounds();
        doReturn(false).when(snake).eatsSelf();
        Square square = mock(Square.class);
        doReturn(square).when(snake).getHead();

        //when and then
        assertTrue(garden.moveSnake());
        verify(snake).move();
    }

    @Test
    public void createFoodIfNecessary() {

        //given
        Snake snake = mock(Snake.class);
        FoodFactory foodFactory = mock(FoodFactory.class);
        Garden garden = new Garden(snake, foodFactory);
        when(foodFactory.newInstance()).thenReturn(mock(Food.class));

        //when
        garden.createFoodIfNecessary();

        //then
        verify(foodFactory).newInstance();
        assertNotNull(garden.getFood());
    }

    //does not work yet
    @Test
    public void scrollGardenIfNecessary() {
        //given
        Snake snake = mock(Snake.class);
        Food food = mock(Food.class);
        FoodFactory foodFactory = mock(FoodFactory.class);
        SnakeHeadStateMachine snakeHeadStateMachine = mock(SnakeHeadStateMachine.class);
        Garden garden = new Garden(snake, foodFactory);

        List<Square> previousSquares = new ArrayList<>(snake.getSquares());

        doReturn(snakeHeadStateMachine).when(snake).getSnakeHeadStateMachine();
        doReturn(Direction.North).when(snakeHeadStateMachine).getDirection();

        Square square = mock(Square.class);
        doReturn(square).when(snake).getHead();
        doReturn(2).when(square).getY();
        doReturn(2).when(square).getX();

        doReturn(food).when(foodFactory).newInstance();
        garden.createFoodIfNecessary();
        doReturn(2).when(food).getY();
        doReturn(2).when(food).getX();

        //when
        garden.advance();


        //then
        //make sure snake has not moved
        List<Square> currentSquares = snake.getSquares();
        for (int i = 0; i < currentSquares.size(); i++) {
            Square prevSquare = previousSquares.get(i);
            Square currSquare = currentSquares.get(i);
            assertEquals(prevSquare,  currSquare);
        }

        //make sure garden contents have moved
        assertTrue(garden.scrollGardenIfNecessary());
        verify(food).setX(2);
        verify(food).setY(3);


    }
}