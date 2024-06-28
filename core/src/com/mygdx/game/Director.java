package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Director {
    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }
    
	public Asteroid BuildnormalAsteroid(Vector2 pos,Vector2 vel) {
      return builder.position(pos)
    		  	.sprite(new Sprite(new Texture(Gdx.files.internal("aGreyMedium4.png"))))
    		  	.speed(100)
                .velocity(vel)
                .vidas(1)
                .build();
    }
	
	//public Asteroid BuildBigAsteroid(Vector2 pos,Vector2 vel) {
	  //    return builder.position(pos)
	   	//	  	.sprite(new Sprite(new Texture(Gdx.files.internal("aGreyLarge.png"))))
	  		//  	.speed(50)
	          //  .velocity(vel)
	          // .vidas(3)
	            //   .build();
	    //}
	
	
}

