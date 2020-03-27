package omv.server;

import io.vertx.core.Vertx;

public class App {
    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
        Vertx vertx = Vertx.vertx();
        System.out.println("test");
        vertx.deployVerticle(new Controller());

    }
}