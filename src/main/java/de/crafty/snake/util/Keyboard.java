package de.crafty.snake.util;

import java.util.HashMap;
import java.util.List;

public class Keyboard {

    //Key cache
    //HashMap<KeyCode, initialPress>
    private static final HashMap<Integer, Boolean> KEYS = new HashMap<>();

    public static void handleKeyAction(int keyCode, int scancode, int action) {

        if(action == 0)
            KEYS.remove(keyCode);

        if(action == 1)
            KEYS.put(keyCode, true);

    }

    public static void update(){
        KEYS.keySet().forEach(key -> KEYS.put(key, false));
    }

    public static boolean isKeyPressed(int keyCode){
        return KEYS.containsKey(keyCode) && KEYS.get(keyCode);
    }

    public static boolean isKeyDown(int keyCode){
        return KEYS.containsKey(keyCode);
    }

}
