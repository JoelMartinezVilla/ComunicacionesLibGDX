package com.joelmartinez;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import java.util.ArrayList;
import java.util.List;

public class Main extends ApplicationAdapter implements GestureListener {
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private List<float[]> touches;
    private GameScreen gameScreen;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        touches = new ArrayList<>();

        Gdx.input.setInputProcessor(new GestureDetector(this));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.end();

        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 1);
        for (float[] touch : touches) {
            shapeRenderer.circle(touch[0], touch[1], 20);
        }
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        touches.add(new float[]{x, Gdx.graphics.getHeight() - y});

        if (gameScreen == null) {
            gameScreen = new GameScreen();
        }

        gameScreen.sendTouchData(x, Gdx.graphics.getHeight() - y);
        return true;
    }

    @Override public boolean tap(float x, float y, int count, int button) { return false; }
    @Override public boolean longPress(float x, float y) { return false; }
    @Override public boolean fling(float velocityX, float velocityY, int button) { return false; }
    @Override public boolean pan(float x, float y, float deltaX, float deltaY) { return false; }
    @Override public boolean panStop(float x, float y, int pointer, int button) { return false; }
    @Override public boolean zoom(float initialDistance, float distance) { return false; }
    @Override public boolean pinch(com.badlogic.gdx.math.Vector2 initialPointer1, com.badlogic.gdx.math.Vector2 initialPointer2,
                                   com.badlogic.gdx.math.Vector2 pointer1, com.badlogic.gdx.math.Vector2 pointer2) { return false; }
    @Override public void pinchStop() { }
}
