package omv.server;

import java.util.Arrays;

import io.vertx.core.Launcher;

public class Main extends Launcher {

    public static void main(String[] args) {
        System.out.println(args);
        System.out.println("helloooooo");
        String[] myargs = new String[] {"run", "omv.server.MainVerticle", "--launcher-class=omv.server.Main"};
        String[] allargs = Arrays.copyOf(myargs, myargs.length + args.length);
        System.arraycopy(args, 0, allargs, myargs.length, args.length);
        new Main().dispatch(allargs);
    }

}
