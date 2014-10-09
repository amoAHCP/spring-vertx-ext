package org.jacpfx.vertx.spring.ws;

import org.jacpfx.vertx.spring.SpringVerticle;
import org.springframework.stereotype.Component;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.ServerWebSocket;
import org.vertx.java.platform.Verticle;

/**
 * Created by amo on 26.09.14.
 */
@Component(value = "WSEntryVerticle")
@SpringVerticle(springConfig = WSEntryVerticle.class)
public class WSEntryVerticle extends Verticle {
    private final WebSocketRepository repository = new WebSocketRepository();
    public static Integer PORT_NUMER = 8080;

    @Override
    public void start() {
        System.out.println("START WSEntryVerticle  THREAD: " + Thread.currentThread() + "  this:" + this);
        HttpServer server = startServer();
        registerWebsocketHandler(server);
        server.listen(PORT_NUMER);
    }

    /**
     * returns a new http server instance
     *
     * @return
     */
    private HttpServer startServer() {
        return vertx.createHttpServer();
    }

    /**
     * Registers onMessage and onClose message handler for WebSockets
     *
     * @param httpServer
     */
    private void registerWebsocketHandler(final HttpServer httpServer) {
        httpServer.websocketHandler((serverSocket) -> {
            repository.addWebSocket(serverSocket);
            final String path = serverSocket.path();
            switch (path) {
                case "/all":

                    // reply to first contact
                    serverSocket.writeTextFrame("hallo1");
                    // add handler for further calls
                    serverSocket.dataHandler(data -> {
                        serverSocket.writeTextFrame("hallo2");
                    });
                    break;
                case "/update":
                    serverSocket.dataHandler(data -> vertx.eventBus().publish("org.jacpfx.petstore.add", data.getBytes()));
                    break;
                case "/updateAll":
                    serverSocket.dataHandler(data -> vertx.eventBus().publish("org.jacpfx.petstore.addAll", data.getBytes()));
                    break;
            }
            serverSocket.closeHandler((close) -> handleConnectionClose(close, serverSocket));

        });
    }


    /**
     * handles connection close
     *
     * @param event
     */
    private void handleConnectionClose(final Void event, ServerWebSocket socket) {
        repository.removeWebSocket(socket);
    }

}
