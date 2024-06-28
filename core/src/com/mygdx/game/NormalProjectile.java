/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class NormalProjectile extends Projectile {
	
    NormalProjectile(Vector2 position, Vector2 velocity, float speed) {
        
        setSprite(new Sprite(new Texture(Gdx.files.internal("Rocket2.png"))));
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
        return new NormalProjectile(pos, getVelocity(), getSpeed());
    }
}

