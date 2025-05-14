package com.joelmartinez;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.List;


public class Main extends ApplicationAdapter implements GestureListener {

    private SpriteBatch drawer;
    private ShapeRenderer shapes;
    private final List<Vector2> contacts = new ArrayList<>();

    private GameScreen overlay;

    @Override
    public void create() {
        drawer = new SpriteBatch();
        shapes = new ShapeRenderer();
        Gdx.input.setInputProcessor(new GestureDetector(this));
    }

    @Override
    public void render() {
        wipe();
        drawer.begin();
        drawer.end();

        drawTouches();
    }

    @Override
    public void dispose() {
        drawer.dispose();
        shapes.dispose();
    }


    private void wipe() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void drawTouches() {
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(0f, 0f, 0f, 1f);
        for (Vector2 p : contacts) {
            shapes.circle(p.x, p.y, 20f);
        }
        shapes.end();
    }


    @Override
    public boolean touchDown(float screenX, float screenY, int pointer, int button) {
        Vector2 point = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
        contacts.add(point);

        if (overlay == null) overlay = new GameScreen();
        overlay.sendTouchData(point.x, point.y);
        return true;
    }


    @Override public boolean tap(float x, float y, int count, int button) { return false; }
    @Override public boolean longPress(float x, float y) { return false; }
    @Override public boolean fling(float velocityX, float velocityY, int button) { return false; }
    @Override public boolean pan(float x, float y, float deltaX, float deltaY) { return false; }
    @Override public boolean panStop(float x, float y, int pointer, int button) { return false; }
    @Override public boolean zoom(float initialDistance, float distance) { return false; }
    @Override public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) { return false; }
    @Override public void pinchStop() { }
}
