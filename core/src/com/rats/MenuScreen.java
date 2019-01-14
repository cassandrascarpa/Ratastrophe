package com.rats;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class MenuScreen implements Screen {

    Rats game;

    private Stage stage;

    private Texture playTexture, instructionTexture, creditsTexture, settingsTexture, scoresTexture;
    private Drawable playDrawable, instructionDrawable, creditsDrawable, settingsDrawable, scoresDrawable;
    private Texture playTextureHover, instructionTextureHover, creditsTextureHover, settingsTextureHover, scoresTextureHover;
    private Drawable playDrawableHover, instructionDrawableHover, creditsDrawableHover, settingsDrawableHover, scoresDrawableHover;
    private ImageButton playButton, instructionButton, creditsButton, settingsButton, scoresButton;

    public MenuScreen(final Rats game) {
        this.game = game;
        initializeButtons();
        stage = new Stage(new ScreenViewport());
        stage.addActor(playButton);
        stage.addActor(instructionButton);
        stage.addActor(creditsButton);
        stage.addActor(settingsButton);
        stage.addActor(scoresButton);
        Gdx.input.setInputProcessor(stage);

    }

    private void initializeButtons() {
        playTexture = new Texture("core/assets/menu/button_play.png");
        playDrawable = new TextureRegionDrawable(new TextureRegion(playTexture));
        playTextureHover = new Texture("core/assets/menu/button_play_hover.png");
        playDrawableHover = new TextureRegionDrawable(new TextureRegion(playTextureHover));
        playButton = new ImageButton(playDrawable);

        playButton.addListener(new InputListener() {
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                playButton.getStyle().imageUp = playDrawableHover;
            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                playButton.getStyle().imageUp = playDrawable;
            }

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game, 0, 100));
                return true;
            }
        });

        playButton.setPosition(Gdx.graphics.getWidth()/2 - 70, 240);

        instructionTexture = new Texture("core/assets/menu/button_instructions.png");
        instructionDrawable = new TextureRegionDrawable(new TextureRegion(instructionTexture));
        instructionTextureHover = new Texture("core/assets/menu/button_instructions_hover.png");
        instructionDrawableHover = new TextureRegionDrawable(new TextureRegion(instructionTextureHover));
        instructionButton = new ImageButton(instructionDrawable);

        instructionButton.addListener(new InputListener() {
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                instructionButton.getStyle().imageUp = instructionDrawableHover;
            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                instructionButton.getStyle().imageUp = instructionDrawable;
            }

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new InstructionScreen(game));
                return true;
            }
        });

        instructionButton.setPosition(Gdx.graphics.getWidth()/2 - 120, 200);

        creditsTexture = new Texture("core/assets/menu/button_credits.png");
        creditsDrawable = new TextureRegionDrawable(new TextureRegion(creditsTexture));
        creditsTextureHover = new Texture("core/assets/menu/button_credits_hover.png");
        creditsDrawableHover = new TextureRegionDrawable(new TextureRegion(creditsTextureHover));
        creditsButton = new ImageButton(creditsDrawable);

        creditsButton.addListener(new InputListener() {
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                creditsButton.getStyle().imageUp = creditsDrawableHover;
            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                creditsButton.getStyle().imageUp = creditsDrawable;
            }

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new CreditScreen(game));
                return true;
            }
        });

        creditsButton.setPosition(Gdx.graphics.getWidth()/2 - 80, 160);

        settingsTexture = new Texture("core/assets/menu/button_settings.png");
        settingsDrawable = new TextureRegionDrawable(new TextureRegion(settingsTexture));
        settingsTextureHover = new Texture("core/assets/menu/button_settings_hover.png");
        settingsDrawableHover = new TextureRegionDrawable(new TextureRegion(settingsTextureHover));
        settingsButton = new ImageButton(settingsDrawable);

        settingsButton.addListener(new InputListener() {
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                settingsButton.getStyle().imageUp = settingsDrawableHover;
            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                settingsButton.getStyle().imageUp = settingsDrawable;
            }

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new SettingScreen(game));
                return true;
            }
        });

        settingsButton.setPosition(Gdx.graphics.getWidth()/2 - 80, 120);

        scoresTexture = new Texture("core/assets/menu/button_highscores.png");
        scoresDrawable = new TextureRegionDrawable(new TextureRegion(scoresTexture));
        scoresTextureHover = new Texture("core/assets/menu/button_highscores_hover.png");
        scoresDrawableHover = new TextureRegionDrawable(new TextureRegion(scoresTextureHover));
        scoresButton = new ImageButton(scoresDrawable);

        scoresButton.addListener(new InputListener() {
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                scoresButton.getStyle().imageUp = scoresDrawableHover;
            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                scoresButton.getStyle().imageUp = scoresDrawable;
            }

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new HighScoreScreen(game));
                return true;
            }
        });

        scoresButton.setPosition(Gdx.graphics.getWidth()/2 - 120, 80);

    }

    public void render(float f) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(new Texture("core/assets/menu/logo.png"), 0, 300, Gdx.graphics.getWidth(), 200);
        game.batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void show() {

    }

    public void hide() {
        dispose();

    }

    public void pause() {

    }

    public void resume() {

    }

    public void resize(int x, int y) {

    }


    public void dispose() {
        stage.dispose();

    }
}
