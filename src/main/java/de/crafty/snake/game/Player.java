package de.crafty.snake.game;

import de.crafty.snake.SnakeGame;
import de.crafty.snake.util.Direction;

import java.util.ArrayList;
import java.util.List;

public class Player extends GameComponent {

    public final List<Extension> extensions;
    public Direction movingDirection;

    public Player() {
        super(0, 0);

        this.extensions = new ArrayList<>();
        this.movingDirection = Direction.EAST;

    }


    public void updateMovingDirection(Direction newDirection) {
        this.movingDirection = newDirection;
    }

    @Override
    public void update() {
        super.update();

        switch (this.movingDirection) {
            case NORTH -> this.yPos--;
            case EAST -> this.xPos++;
            case SOUTH -> this.yPos++;
            case WEST -> this.xPos--;
        }

        if (this.xPos > SnakeGame.getInstance().columns - 1)
            this.xPos = 0;
        if (this.xPos < 0)
            this.xPos = SnakeGame.getInstance().columns - 1;

        if (this.yPos > SnakeGame.getInstance().rows - 1)
            this.yPos = 0;
        if (this.yPos < 0)
            this.yPos = SnakeGame.getInstance().rows - 1;


    }

    public boolean hasLost() {
        return this.extensions.stream().filter(extension -> extension.xPos == this.xPos && extension.yPos == this.yPos).toList().size() > 0;
    }

    public void grow() {
        this.extensions.add(new Extension(this.extensions.size() == 0 ? this : this.extensions.get(this.extensions.size() - 1)));
    }

    public Food collidingFood() {
        for(Food food : SnakeGame.getInstance().foodList){
            if(food.xPos == this.xPos && food.yPos == this.yPos)
                return food;
        }
        return null;
    }

    @Override
    public void render() {
        super.render();
    }

    public int getLength() {
        return this.extensions.size();
    }
}
