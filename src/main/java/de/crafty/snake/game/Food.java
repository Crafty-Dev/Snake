package de.crafty.snake.game;

import de.crafty.snake.SnakeGame;

import java.awt.*;

public class Food extends GameComponent {


    public Food(int xPos, int yPos) {
        super(xPos, yPos);
    }


    @Override
    public void render() {
        SnakeGame.getInstance().getRenderManager().drawRect(this.xPos * SIZE, this.yPos * SIZE, SIZE, SIZE, Color.GREEN);
    }
}
