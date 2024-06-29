package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class ExplosiveProjectile extends Projectile{
    
    ExplosiveProjectile(Vector2 position, Vector2 velocity, float speed) {
        
        setSprite(new Sprite(new Texture(Gdx.files.internal("Rocket3.png"))));
        setPosition(position);
        setVelocity(velocity);
        setSpeed(speed);
    }
    
    @Override
    public void onCollision() {
        destroy();
    }
    
    @Override
    public Projectile create(Vector2 pos) {
        return new ExplosiveProjectile(pos, getVelocity(), getSpeed());
    }
    
}
