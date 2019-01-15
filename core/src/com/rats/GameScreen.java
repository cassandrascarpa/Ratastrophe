package com.rats;

import box2dLight.DirectionalLight;
import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Rectangle;

public class GameScreen implements Screen {

    Rats game;
    Character rat;
    private int score;
    OrthographicCamera camera;
    TiledMap tiledMap;
    OrthogonalTiledMapRenderer tiledMapRenderer;
    float w,h;
    Maze maze;

    private long startTime;
    private long timeOut = 120000;
    private long previewTimeOut = 15000;

    private enum GameState {Preview, Ongoing}

    private GameState state;

    private Vector2 startPos;

    private int startHealth;

    private Texture dark;

    PointLight pl;


    public GameScreen(final Rats game, int score, int startHealth) {

        this.score = score;
        this.startHealth = startHealth;

        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();

        this.game = game;
        startNewLevel();

        state = GameState.Preview;

        dark = new Texture("core/assets/dark_fov.png");

    }

    public void startNewLevel() {

        maze = new Maze(33,33);
        maze.createRandomMaze();
        maze.generateConsumables();
        maze.generateTraps();


        startPos = maze.getEntranceCoords();//screenToWorldCoords(new Vector2(100, 300));
        rat = new Character((int) startPos.x,(int) startPos.y,10,10);
        rat.setHealth(startHealth);

        tiledMap = new TmxMapLoader().load("core/assets/tilemaps/random_maze.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        startTime = System.currentTimeMillis();
    }


    public Vector2 screenToWorldCoords(Vector2 screenCoords) {
        Vector3 c = camera.unproject(new Vector3(screenCoords.x, screenCoords.y, 0));
        Vector2 worldCoords = new Vector2(c.x, c.y);
        return  worldCoords;

    }

    public Vector2 worldToScreenCoords(Vector2 worldCoords) {
        Vector3 c = camera.project(new Vector3(worldCoords.x, worldCoords.y, 0));
        Vector2 screenCoords = new Vector2(c.x, c.y);
        return  screenCoords;
    }

    public void poll() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            rat.turnLeft();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            rat.turnRight();
          //  rat.setHealth(rat.getHealth() - 1);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            Vector2 pan = rat.moveForwards(maze);
            camera.translate(pan);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            Vector2 pan = rat.moveBackwards(maze);
            camera.translate(pan);
        }
    }

    private String formatTime(long timeRemaining) {
        int minutesLeft = (int) timeRemaining / 60000;
        int secondsLeft = (int) timeRemaining % 60000 / 1000;
        String timeString = "";
        timeString += minutesLeft;
        timeString += ":";
        if (secondsLeft < 10) {
            timeString += 0;
        }
        timeString += secondsLeft;
        return timeString;
    }

    private void checkWin() {
        if (maze.atExit(new Vector2(rat.getxPos(), rat.getyPos()))) {
            score += 10;
            game.setScreen(new LevelWinScreen(game, score, rat.getHealth()));
        }
    }

    private void checkCollisions() {
        for (Consumable c : maze.getConsumables()) {
            if (c.getBoundingRectangle().overlaps(rat.getBoundingRectangle())) {
                score += c.consume(rat, this);
            }
        }
        for (Trap t : maze.getTraps()) {
            if (t.getBoundingRectangle().overlaps(rat.getBoundingRectangle())) {
                t.spring(rat);
            }
        }

    }


    public void render(float f) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (state == GameState.Preview) {
            long timeElapsed = System.currentTimeMillis() - startTime;
            long timeRemaining = previewTimeOut - timeElapsed;
            camera.setToOrtho(false,33*128,33*128);
            camera.update();
            tiledMapRenderer.setView(camera);
            tiledMapRenderer.render();
            game.batch.begin();
            for (Consumable c : maze.getConsumables()) {
                if (!c.getConsumed()) {
                    Vector2 cPos = worldToScreenCoords(new Vector2(c.getxPos(), c.getyPos()));
                    c.getSprite().setPosition(cPos.x, cPos.y);
                    c.getSprite().setSize(20, 20);
                    c.getSprite().draw(game.batch);
                }
            }

            for (Trap t : maze.getTraps()) {
                if (!t.isSprung()) {
                    Vector2 tPos = worldToScreenCoords(new Vector2(t.getxPos(), t.getyPos()));
                    t.getSprite().setPosition(tPos.x, tPos.y);
                    t.getSprite().setSize(20, 20);
                    t.getSprite().draw(game.batch);
                }
            }
            game.font.setColor(Color.WHITE);
            game.font.draw(game.batch, "Level Preview", 10, 460);
            game.font.draw(game.batch, formatTime(timeRemaining), 400, 460);
            game.batch.end();
            if (timeRemaining <= 0) {
                startTime = System.currentTimeMillis();
                state = GameState.Ongoing;
                camera.setToOrtho(false,w,h);
                camera.position.set(startPos.x, startPos.y, 0);
                camera.update();
            }
        }
        else {

            long timeElapsed = System.currentTimeMillis() - startTime;
            long timeRemaining = timeOut - timeElapsed;

            if (rat.getHealth() <= 0 || timeElapsed >= timeOut) {
                game.setScreen(new GameOverScreen(game, score));
                return;
            }

            checkWin();
            checkCollisions();

            poll();


            camera.update();
            tiledMapRenderer.setView(camera);
            tiledMapRenderer.render();

            game.batch.begin();

            for (Consumable c : maze.getConsumables()) {
                if (!c.getConsumed()) {
                    Vector2 cPos = worldToScreenCoords(new Vector2(c.getxPos(), c.getyPos()));
                    c.getSprite().setPosition(cPos.x, cPos.y);
                    c.getSprite().setSize(c.getWidth(), c.getHeight());
                    c.getSprite().draw(game.batch);
                }
            }

            for (Trap t : maze.getTraps()) {
                if (!t.isSprung()) {
                    Vector2 tPos = worldToScreenCoords(new Vector2(t.getxPos(), t.getyPos()));
                    t.getSprite().setPosition(tPos.x, tPos.y);
                    t.getSprite().setSize(t.getWidth(), t.getHeight());
                    t.getSprite().draw(game.batch);
                }
            }

            Vector2 ratPos = worldToScreenCoords(new Vector2(rat.getxPos(), rat.getyPos()));

            game.batch.draw(rat.getTextureRegion(), ratPos.x, ratPos.y, rat.getCenterX(), rat.getCenterY(), rat.getWidth(),
                    rat.getHeight(), 1, 1, rat.getAngle());

            if (!rat.getNightvision()) {
                game.batch.draw(dark, 0, 0, w, h);
            }

            game.font.setColor(Color.WHITE);
            game.font.draw(game.batch, "Score: " + score, 10, 460);

            if (timeRemaining <= 30000) {
                game.font.setColor(Color.RED);
            }
            game.font.draw(game.batch, formatTime(timeRemaining), 400, 460);

            game.batch.end();

            game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            game.shapeRenderer.setColor(Color.BLACK);
            game.shapeRenderer.rect(10, 430, 100, 10);
            game.shapeRenderer.setColor(Color.RED);
            game.shapeRenderer.rect(10, 430, rat.getHealth(), 10);
            game.shapeRenderer.end();


            try {
                Thread.sleep(17);
            } catch (Exception e) {
                System.out.println("error: " + e.getMessage());
            }
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
        rat.dispose();
    }

    public long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(long to) {
        timeOut = to;
    }

}

