package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Director {
    private AsteroidBuilder builder;

    public Director(AsteroidBuilder builder) {
        this.builder = builder;
    }
    
	public Asteroid BuildnormalAsteroid(Vector2 pos,Vector2 vel) {
		Sprite asteroidSprite = new Sprite(new Texture(Gdx.files.internal("aGreyMedium4.png")));
	    Rectangle collisionRect = new Rectangle(pos.x, pos.y, asteroidSprite.getWidth(), asteroidSprite.getHeight());
      return builder.position(pos)
    		  	.sprite(asteroidSprite)
    		  	.speed(100)
                .velocity(vel)
                .collisionRectangle(collisionRect)
                .vidas(1)
                .build();
    }
	
	public BigAsteroid BuildBigAsteroid(Vector2 pos,Vector2 vel) {
		Sprite asteroidSprite = new Sprite(new Texture(Gdx.files.internal("aGreyLarge.png")));
	    Rectangle collisionRect = new Rectangle(pos.x, pos.y, asteroidSprite.getWidth(), asteroidSprite.getHeight());
	     return builder.position(pos)
	    	  	.sprite(asteroidSprite)
	  		    .speed(50)
	            .velocity(vel)
	            .collisionRectangle(collisionRect)
	            .vidas(3)
	            .buildBigAsteroid();
	    }
	
	
}

