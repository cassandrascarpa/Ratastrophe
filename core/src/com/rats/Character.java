package com.rats;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class Character {

    private int xPos;
    private int yPos;
    private int speedX;
    private int speedY;

    private int height = 200;
    private int width = 200;
    private int centerX = width/2;
    private int centerY = height/2;
    private float angle = -90;

    private int health = 100;

    private boolean nightvision = false;

    private enum Direction {Up, Down, Left, Right}
    private Direction facing = Direction.Right;

    private TextureAtlas textureAtlas;
    private Animation animation;
    private float elapsedTime = 0;
    private TextureRegion textureRegion;

    public Character(int startX, int startY, int speedX, int speedY) {
        this.xPos = startX;
        this.yPos = startY;
        this.speedX = speedX;
        this.speedY = speedY;
        textureAtlas = new TextureAtlas(Gdx.files.internal("core/assets/anim/spritesheet.atlas"));
        animation = new Animation(1/10f, textureAtlas.getRegions());
        textureRegion = (TextureRegion) animation.getKeyFrame(0, true);
    }

    public void animateWalk() {
        elapsedTime += Gdx.graphics.getDeltaTime();
        textureRegion = (TextureRegion) animation.getKeyFrame(elapsedTime, true);
    }

    public void animateTurn() {

    }

    public Vector2 moveForwards(Maze maze) {
        Vector2 rv = new Vector2(0,0);
        switch(facing) {
            case Up:
                int nextY = yPos + speedY;
                if (maze.isWalkable(new Vector2(xPos, nextY))) {
                    yPos = nextY;
                    rv.y =  speedY;
                }
                break;
            case Down:
                nextY = yPos - speedY;
                if (maze.isWalkable(new Vector2(xPos, nextY))) {
                    yPos = nextY;
                    rv.y = -speedY;
                }
                break;
            case Left:
                int nextX = xPos - speedX;
                if (maze.isWalkable(new Vector2(nextX, yPos))) {
                    xPos = nextX;
                    rv.x = -speedX;
                }
                break;
            case Right:
                nextX = xPos + speedX;
                if (maze.isWalkable(new Vector2(nextX, yPos))) {
                    xPos = nextX;
                    rv.x = speedX;
                }
                break;
        }
        animateWalk();
        return rv;
    }

    public Vector2 moveBackwards(Maze maze) {
        Vector2 rv = new Vector2(0,0);
        switch(facing) {
            case Up:
                int nextY = yPos - speedY;
                if (maze.isWalkable(new Vector2(xPos, nextY))) {
                    yPos = nextY;
                    rv.y = -speedY;
                }
                break;
            case Down:
                nextY = yPos + speedY;
                if (maze.isWalkable(new Vector2(xPos, nextY))) {
                    yPos = nextY;
                    rv.y =  speedY;
                }
                break;
            case Left:
                int nextX = xPos + speedX;
                if (maze.isWalkable(new Vector2(nextX, yPos))) {
                    xPos = nextX;
                    rv.x = speedX;
                }
                break;
            case Right:
                nextX = xPos - speedX;
                if (maze.isWalkable(new Vector2(nextX, yPos))) {
                    xPos = nextX;
                    rv.x = -speedX;
                }
                break;
        }
        animateWalk();
        return rv;
    }

    public void turnLeft() {
        switch(facing) {
            case Left:
                facing = Direction.Down;
                angle = 180;
                break;
            case Right:
                facing = Direction.Up;
                angle = 0;
                break;
            default:
                facing = Direction.Left;
                angle = 90;
                break;
        }
        animateTurn();
    }

    public void turnRight() {
        switch(facing) {
            case Left:
                facing = Direction.Up;
                angle = 0;
                break;
            case Right:
                facing = Direction.Down;
                angle = 180;
                break;
            default:
                facing = Direction.Right;
                angle = -90;
                break;
        }
        animateTurn();
    }

    public void dispose() {

    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public float getAngle() {
        return angle;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Rectangle getBoundingRectangle() {
        return new Rectangle(xPos, yPos, width, height);
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public boolean getNightvision() {
        return nightvision;
    }

    public void setNightvision(boolean vision) {
        this.nightvision = vision;
    }
}
