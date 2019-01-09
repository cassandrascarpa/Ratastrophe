package com.rats;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class LevelWinScreen implements Screen {

    Rats game;
    int score;
    int health;

    public LevelWinScreen(Rats game, int score, int health) {
        this.game = game;
        this.score = score;
        this.health = health;
    }

    public void render(float f) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.font.draw(game.batch, "Nice! ", 100, 150);
        game.font.draw(game.batch, "Your score: " + score, 100, 130);
        game.font.draw(game.batch, "Click for next level", 100, 100);
        game.batch.end();

        if(Gdx.input.justTouched()) {
            game.setScreen(new GameScreen(game, score, health));
        }

    }

    public void show() {

    }

    public void hide() {

    }

    public void pause() {

    }

    public void resume() {

    }

    public void resize(int x, int y) {

    }


    public void dispose() {

    }

}

