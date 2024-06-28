package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BigAsteroid extends Asteroid {

    public BigAsteroid(Vector2 position, Vector2 velocity, float speed, int vidas, Sprite sprite,Rectangle collisionRectangle) {
        super(position, velocity, speed, vidas, sprite,collisionRectangle);
    }

}
