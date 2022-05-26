package de.crafty.snake;

import de.crafty.snake.discord.DiscordIntegration;
import de.crafty.snake.game.Extension;
import de.crafty.snake.game.Food;
import de.crafty.snake.game.Player;
import de.crafty.snake.game.GameComponent;
import de.crafty.snake.render.RenderManager;
import de.crafty.snake.util.Direction;
import de.crafty.snake.util.Keyboard;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.stb.STBTruetype;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnakeGame {

    private static SnakeGame instance;

    public final int rows, columns;
    public final int displayWidth, displayHeight;
    public Player player;
    public final List<Food> foodList;

    private final RenderManager renderManager;

    private long prevSystemTime;
    private final Random random;
    private int score = 0;


    public SnakeGame(int rows, int columns) {
        instance = this;

        this.rows = rows;
        this.columns = columns;

        this.displayWidth = columns * GameComponent.SIZE;
        this.displayHeight = rows * GameComponent.SIZE;

        this.renderManager = new RenderManager();
        this.foodList = new ArrayList<>();

        this.random = new Random();

        this.reset();

    }

    public void reset() {
        this.score = 0;
        GameComponent.CACHE.clear();
        this.foodList.clear();
        this.player = new Player();
        this.player.grow();
        this.player.grow();

        this.summonFood();
    }

    public void run() {

        DiscordIntegration.start();

        this.renderManager.setup();

        while (!this.renderManager.shouldShutdown()) {

            Keyboard.update();
            this.renderManager.update();

            //Game Logic
            if (Keyboard.isKeyPressed(GLFW.GLFW_KEY_W) || Keyboard.isKeyPressed(GLFW.GLFW_KEY_UP))
                this.player.updateMovingDirection(Direction.NORTH);

            if (Keyboard.isKeyPressed(GLFW.GLFW_KEY_A) || Keyboard.isKeyPressed(GLFW.GLFW_KEY_LEFT))
                this.player.updateMovingDirection(Direction.WEST);

            if (Keyboard.isKeyPressed(GLFW.GLFW_KEY_S) || Keyboard.isKeyPressed(GLFW.GLFW_KEY_DOWN))
                this.player.updateMovingDirection(Direction.SOUTH);

            if (Keyboard.isKeyPressed(GLFW.GLFW_KEY_D) || Keyboard.isKeyPressed(GLFW.GLFW_KEY_RIGHT))
                this.player.updateMovingDirection(Direction.EAST);

            long sysTime = System.currentTimeMillis();
            if (sysTime % 250 < this.prevSystemTime % 250)
                this.tick();

            this.prevSystemTime = sysTime;

            //Rendering
            GameComponent.CACHE.forEach(GameComponent::render);


        }

        this.shutdown();
    }


    public void tick() {
        GameComponent.CACHE.forEach(GameComponent::update);

        if (this.player.hasLost())
            this.gameOver();


        Food food = this.player.collidingFood();
        if(food != null){
            this.foodList.remove(food);
            GameComponent.CACHE.remove(food);
            this.score++;
            this.player.grow();
            this.summonFood();
            System.out.println(this.score);
        }
    }

    public void gameOver() {
        this.reset();
    }

    //Shutdown the application
    public void shutdown() {
        System.out.println("Shutting down...");
        DiscordIntegration.shutdown();
        System.exit(0);
    }

    private void summonFood() {
        int x = this.random.nextInt(this.columns);
        int y = this.random.nextInt(this.rows);

        int finalX = x;
        int finalY = y;
        boolean b = GameComponent.CACHE.stream().filter(gameComponent -> gameComponent.xPos == finalX && gameComponent.yPos == finalY).toList().size() > 0;

        while (b) {
            x = this.random.nextInt(this.columns);
            y = this.random.nextInt(this.rows);
            int finalX1 = x;
            int finalY1 = y;
            b = GameComponent.CACHE.stream().filter(gameComponent -> gameComponent.xPos == finalX1 && gameComponent.yPos == finalY1).toList().size() > 0;
        }

        this.foodList.add(new Food(x, y));

    }

    public RenderManager getRenderManager() {
        return this.renderManager;
    }

    public static SnakeGame getInstance() {
        return instance;
    }
}
