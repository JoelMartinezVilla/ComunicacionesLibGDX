package com.joelmartinez;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketListener;
import com.github.czyzby.websocket.WebSockets;

public class GameScreen implements Screen {
    private WebSocket socket;
    private boolean isConnected = false;

    public GameScreen() {
        String address = "ws://ieticloudpro.ieti.cat";
        int port = 8888;

        try {
            socket = WebSockets.newSocket(WebSockets.toWebSocketUrl("10.0.2.2", port));
            socket.setSendGracefully(false);
            socket.addListener(new MyWSListener());
            socket.connect();
        } catch (Exception e) {
            Gdx.app.error("WebSocket", "Error al conectar con WebSocket", e);
        }
    }


    public void sendTouchData(float x, float y) {
        if (socket != null && isConnected) {
            String message = "TOUCH:" + x + "," + y;
            socket.send(message);
            Gdx.app.log("WebSocket", "Mensaje enviado: " + message);
        } else {
            Gdx.app.log("WebSocket", "No se pudo enviar el mensaje. Socket no conectado.");
        }
    }

    @Override public void show() {}
    @Override public void render(float delta) {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        if (socket != null) {
            socket.close();
        }
    }

    private class MyWSListener implements WebSocketListener {
        @Override
        public boolean onOpen(WebSocket webSocket) {
            isConnected = true;
            Gdx.app.log("WebSocket", "Conectado al servidor.");
            return true;
        }

        @Override
        public boolean onClose(WebSocket webSocket, int closeCode, String reason) {
            isConnected = false;
            Gdx.app.log("WebSocket", "Conexión cerrada: " + reason);
            return true;
        }

        @Override
        public boolean onMessage(WebSocket webSocket, String packet) {
            System.out.println("Message:"+packet);
            return false;
        }

        @Override
        public boolean onMessage(WebSocket webSocket, byte[] packet) {
            System.out.println("Message:"+packet);
            return false;
        }

        @Override
        public boolean onError(WebSocket webSocket, Throwable error) {
            System.out.println("ERROR:"+error.toString());
            return false;
        }
    }
}
