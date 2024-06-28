package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class AsteroidBuilder implements Builder {
    private int vidas = 1;
    private float speed;
    private final Vector2 velocity;
    private Vector2 position;
    private Sprite sprite;
    private Rectangle collisionRectangle;
    
    
    public AsteroidBuilder() {
        this.velocity = new Vector2();
    }

    public AsteroidBuilder sprite(Sprite sprite) {
        this.sprite = sprite;
        return this;
    }
    public AsteroidBuilder collisionRectangle(Rectangle collisionRectangle){
    	this.collisionRectangle = collisionRectangle;
        return this;
    }

    @Override
    public AsteroidBuilder vidas(int vidas) {
        this.vidas = vidas;
        return this;
    }

    @Override
    public AsteroidBuilder speed(float speed) {
        this.speed = speed;
        return this;
    }

    @Override
    public AsteroidBuilder position(Vector2 position) {
        this.position = position;
        return this;
    }

    @Override
    public AsteroidBuilder velocity(Vector2 velocity) {
        this.velocity.set(velocity);
        return this;
    }

    public Asteroid build() {
        return new Asteroid(position, velocity, speed, vidas, sprite,collisionRectangle);
    }
    public BigAsteroid buildBigAsteroid() {
        return new BigAsteroid(position, velocity, speed, vidas, sprite,collisionRectangle);
    }
}
