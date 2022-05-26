package de.crafty.snake.render;

import de.crafty.snake.SnakeGame;
import de.crafty.snake.util.Keyboard;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import java.awt.*;

public class RenderManager {

    private long window;

    public RenderManager() {

    }

    public void setup() {

        int displayWidth = SnakeGame.getInstance().displayWidth;
        int displayHeight = SnakeGame.getInstance().displayHeight;

        System.out.println("LWJGL Version: " + Version.getVersion());

        if (!GLFW.glfwInit())
            throw new IllegalStateException("Failed to initialize LWJGL");

        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);

        window = GLFW.glfwCreateWindow(displayWidth, displayHeight, "Snake", MemoryUtil.NULL, MemoryUtil.NULL);
        if (window == MemoryUtil.NULL)
            throw new RuntimeException("Failed to create Game Window");

        GLFW.glfwSetKeyCallback(this.window, (window, key, scancode, action, mods) -> {
            Keyboard.handleKeyAction(key, scancode, action);
        });

        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        if (vidMode == null)
            throw new RuntimeException("Failed to get Monitor Data");

        GLFW.glfwSetWindowPos(this.window, vidMode.width() / 2 - displayWidth / 2, vidMode.height() / 2 - displayHeight / 2);

        GLFW.glfwMakeContextCurrent(this.window);
        GL.createCapabilities();

        GL11.glViewport(0, 0, displayWidth, displayHeight);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, displayWidth, displayHeight, 0, -10, 10);

        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();

        GLFW.glfwShowWindow(this.window);

    }


    public void update() {
        GLFW.glfwSwapBuffers(this.window);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GLFW.glfwPollEvents();
    }


    public void drawRect(int x, int y, int width, int height, Color color) {

        GL11.glColor3f(color.getRed(), color.getGreen(), color.getBlue());

        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0.0F);

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(0, 0);
        GL11.glVertex2f(0, height);
        GL11.glVertex2f(width, height);
        GL11.glVertex2f(width, 0);
        GL11.glEnd();

        GL11.glPopMatrix();

        GL11.glColor3f(1.0F, 1.0F, 1.0F);
    }


    public boolean shouldShutdown() {
        return GLFW.glfwWindowShouldClose(this.window);
    }

    public void shutdown() {
        GLFW.glfwSetWindowShouldClose(this.window, true);
    }

}
