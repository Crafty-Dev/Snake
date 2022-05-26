package de.crafty.snake.game;

import de.crafty.snake.SnakeGame;

public class Extension extends GameComponent {

    private final GameComponent parent;

    public Extension(GameComponent parent) {
        super(parent.xPos, parent.yPos);

        this.parent = parent;

        if(parent.prevXPos < parent.xPos)
            this.xPos--;
        if(parent.prevXPos > parent.xPos)
            this.xPos++;

        if(parent.prevYPos < parent.yPos)
            this.yPos--;
        if(parent.prevYPos > parent.yPos)
            this.yPos++;
    }

    @Override
    public void update() {
        super.update();

        this.xPos = this.parent.prevXPos;
        this.yPos = this.parent.prevYPos;

    }

}
