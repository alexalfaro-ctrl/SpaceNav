package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Director {
    private Builder builder;

    public Director(AsteroidBuilder builder) {
        this.builder = builder;
    }
    
	public void BuildnormalAsteroid(Vector2 pos,Vector2 vel) {
		Sprite asteroidSprite = new Sprite(new Texture(Gdx.files.internal("aGreyMedium4.png")));
	    Rectangle collisionRect = new Rectangle(pos.x, pos.y, asteroidSprite.getWidth(), asteroidSprite.getHeight());
	    builder.sprite(asteroidSprite).position(pos)
    		  	.speed(100)
                .velocity(vel)
                .collisionRectangle(collisionRect)
                .vidas(1);
                
    }
	
	public void BuildBigAsteroid(Vector2 pos,Vector2 vel) {
		Sprite asteroidSprite = new Sprite(new Texture(Gdx.files.internal("aGreyLarge.png")));
	    Rectangle collisionRect = new Rectangle(pos.x, pos.y, asteroidSprite.getWidth(), asteroidSprite.getHeight());
	    builder.sprite(asteroidSprite).position(pos)
	  		    .speed(50)
	            .velocity(vel)
	            .collisionRectangle(collisionRect)
	            .vidas(3);
	    }
	public void changeBuilder(Builder builder)
	{
		this.builder = builder;
	}
	public Builder getBuilder()
	{
		return builder;
	}
}

