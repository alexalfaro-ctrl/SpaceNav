package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Projectile implements IGameObject {
    
    private Vector2 velocity;
    private Vector2 position;
    private Sprite sprite;
    private boolean destroyed = false;
    private float speed;
    
    public abstract void onCollision();
    public abstract Projectile create();
    
    public boolean checkCollision(Asteroid b2) {
         
        boolean collision = this.getCollision().overlaps(b2.getCollision());
	if(collision) {
            onCollision();
	    return true;
	}
                
	return false;
    }
    
    public void update(float delta) {
        position.add(velocity.add(0, speed * delta));
    }
    
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
	sprite.setPosition(position.x,position.y);
    }
    
    public void setSpeed(float speed) {
        this.speed = speed;
    }
    
    public float getSpeed() {
        return speed;
    }
    
    public void setPosition(Vector2 pos) {
        position = pos;
    }
    
    public Vector2 getPosition() {
        return position;
    }
    
    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }
    
    public Vector2 getVelocity() {
        return velocity;
    }
    
    public Rectangle getCollision() {
        return sprite.getBoundingRectangle();
    }
    
    public void setSprite(Sprite spr) {
        sprite = spr;
    }
    
    public Sprite getSprite() {
        return sprite;
    }
    
    public void destroy() {
        destroyed = true;
    }
    
    public boolean isDestroyed() {
        return destroyed;
    }
}
