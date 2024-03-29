package com.rats;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Trap {

    private enum trap_types { Zapper, Snapper };

    private int xPos;
    private int yPos;
    private int height;
    private int width;
    private trap_types type;

    private int damage;

    private boolean sprung = false;

    private Texture texture;
    private Sprite sprite;

    public Trap(int xpos, int ypos, int typenum) {
        this.xPos = xpos;
        this.yPos = ypos;
        switch(typenum) {
            case 0:
                type = trap_types.Snapper;
                texture = new Texture(Gdx.files.internal("core/assets/sprites/snapper.png"));
                damage = 50;
                height = 80;
                width = 80;
                break;
            default:
                type = trap_types.Zapper;
                texture = new Texture(Gdx.files.internal("core/assets/sprites/zapper.png"));
                damage = 20;
                height = 40;
                width = 130;
                break;
        }
        sprite = new Sprite(texture);
    }


    public void spring(Character rat) {
        if (!sprung) {
            rat.setHealth(rat.getHealth() - damage);
            sprung = true;
        }
    }

    public void animate() {

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getBoundingRectangle() {
        return new Rectangle(xPos, yPos-height, width, height);
    }

    public boolean isSprung() {
        return sprung;
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
}

