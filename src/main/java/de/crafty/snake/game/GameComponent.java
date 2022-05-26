package de.crafty.snake.game;

import de.crafty.snake.SnakeGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameComponent {

    public static final List<GameComponent> CACHE = new ArrayList<>();
    public static final int SIZE = 32;

    public int xPos, yPos;
    public int prevXPos, prevYPos;

    public GameComponent(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;

        CACHE.add(this);
    }


    public void update() {
        this.prevXPos = this.xPos;
        this.prevYPos = this.yPos;

    }

    public void render() {
        SnakeGame.getInstance().getRenderManager().drawRect(this.xPos * SIZE, this.yPos * SIZE, SIZE, SIZE, Color.WHITE);
    }

}
