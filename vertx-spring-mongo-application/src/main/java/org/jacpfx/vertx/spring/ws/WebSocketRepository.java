/**
 *
 */
package org.jacpfx.vertx.spring.ws;

import org.vertx.java.core.http.ServerWebSocket;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Andy Moncsek on 25.02.14.
 */
public class WebSocketRepository {
    private final List<ServerWebSocket> webSockets = new CopyOnWriteArrayList<>();

    public void addWebSocket(ServerWebSocket webSocket) {
        if(!webSockets.contains(webSocket))webSockets.add(webSocket);
    }

    public List<ServerWebSocket> getWebSockets() {
        return Collections.unmodifiableList(webSockets);
    }

    public void removeWebSocket(ServerWebSocket webSocket) {
        webSockets.remove(webSocket);
    }
}
