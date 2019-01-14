package com.rats;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class InstructionScreen implements Screen {

    Rats game;
    private Stage stage;
    private Texture backarrowTexture;
    private Drawable backarrowDrawable;
    private Texture backarrowTextureHover;
    private Drawable backarrowDrawableHover;
    private ImageButton backButton;

    public InstructionScreen(final Rats game) {
        this.game = game;

        backarrowTexture = new Texture("core/assets/menu/backarrow.png");
        backarrowDrawable = new TextureRegionDrawable(new TextureRegion(backarrowTexture));
        backarrowTextureHover = new Texture("core/assets/menu/backarrow_hover.png");
        backarrowDrawableHover = new TextureRegionDrawable(new TextureRegion(backarrowTextureHover));
        backButton = new ImageButton(backarrowDrawable);

        backButton.addListener(new InputListener() {
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                backButton.getStyle().imageUp = backarrowDrawableHover;
            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                backButton.getStyle().imageUp = backarrowDrawable;
            }

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MenuScreen(game));
                return true;
            }
        });

        backButton.setPosition(10, 10);

        stage = new Stage(new ScreenViewport());
        stage.addActor(backButton);
        Gdx.input.setInputProcessor(stage);
    }


    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.font.setColor(Color.WHITE);
        game.font.draw(game.batch, "Instructions", Gdx.graphics.getWidth() / 2, 460);
        game.batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }

    public void resize(int width, int height) {

    }

    public void pause() {

    }

    public void resume() {

    }

    public void show() {

    }

    public void hide() {
        dispose();

    }

    public void dispose() {
        stage.dispose();
    }
}
