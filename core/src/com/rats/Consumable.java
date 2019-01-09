package com.rats;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;

public class Consumable {

    private int num_points;
    private int xPos;
    private int yPos;
    private int width = 50;
    private int height = 50;
    private Texture texture;
    private Sprite sprite;

    private boolean consumed = false;

    private enum consumable_types { Cheese, Time, Speed, Health, Carrot};

    private consumable_types type;

    public Consumable(int xPos, int yPos, int typenum) {
        this.xPos = xPos;
        this.yPos = yPos;
        switch (typenum) {
            case 0:
                type = consumable_types.Health;
                texture = new Texture(Gdx.files.internal("core/assets/health.png"));
                num_points = 1;
                break;
            case 1:
                type = consumable_types.Speed;
                texture = new Texture(Gdx.files.internal("core/assets/speed.png"));
                num_points = 0;
                break;
            case 2:
                type = consumable_types.Carrot;
                texture = new Texture(Gdx.files.internal("core/assets/carrot.png"));
                num_points = 1;
                break;
            case 3:
                type = consumable_types.Time;
                texture = new Texture(Gdx.files.internal("core/assets/time.png"));
                num_points = 0;
                break;
            default:
                type = consumable_types.Cheese;
                texture = new Texture(Gdx.files.internal("core/assets/cheese.png"));
                num_points = 5;
                break;
        }
        sprite = new Sprite(texture);
    }

    public int consume(Character rat, GameScreen gs) {
        if (!consumed) {
            consumed = true;
            switch (type) {
                case Carrot:
                    rat.setVision(rat.getVision() + 10);
                    break;
                case Time:
                    gs.setTimeOut(gs.getTimeOut() + 20000);
                    break;
                case Health:
                    rat.setHealth(100);
                    break;
                case Speed:
                    rat.setSpeedX(rat.getSpeedX() + 2);
                    rat.setSpeedY(rat.getSpeedY() + 2);
                    break;
            }
            return num_points;
        }
        return 0;
    }


    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Rectangle getBoundingRectangle() {
        return new Rectangle(xPos, yPos, width, height);
    }

    public boolean getConsumed() {
        return consumed;
    }
}
