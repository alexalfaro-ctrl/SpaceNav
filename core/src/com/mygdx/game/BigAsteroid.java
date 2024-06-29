package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BigAsteroid extends Asteroid {
	private int vidas;
    private float speed;
    private Vector2 velocity;
    private Vector2 position;
    private Sprite sprite;
    private Rectangle collisionRectangle;
	
	
    public BigAsteroid(Vector2 position, Vector2 velocity, float speed, int vidas, Sprite sprite,Rectangle collisionRectangle) {
    	
        super(position, velocity, speed, vidas, sprite,collisionRectangle);
        //Es un asteroide mas lento.
        super.speed /=2;
        
        		
    }
    
}


