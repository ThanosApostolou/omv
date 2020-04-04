package omv.server;

import java.util.Arrays;

import io.vertx.core.Launcher;

public class Main extends Launcher {
    public static void main(String[] args) {
        System.out.println("Running application with arguments:");
        System.out.println(Arrays.toString(args));

        new Main().dispatch(args);
    }
}
