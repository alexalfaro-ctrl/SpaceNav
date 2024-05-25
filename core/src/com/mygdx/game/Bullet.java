package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class Bullet implements IGameObject {

	private float speed;
	private final Vector2 velocity;
	private Vector2 position;
	private Sprite sprite;
	private boolean destroyed = false;
	private Sprite spr;
	    
	    public Bullet(Vector2 position, Vector2 velocity,float speed) {
	    	//Set sprite and assets
	    	System.out.println("position");
	    	sprite = new Sprite(new Texture(Gdx.files.internal("Rocket2.png")));
	    	
	    	this.position = position;
	    	sprite.setPosition(position.x,position.y);
	    	//Give initial velocity to keep the momentum of player
	    	this.velocity = velocity;
	    	this.speed= speed;
	    }
	    
	    
	    @Override
	    public void draw(SpriteBatch batch) {
	    	sprite.draw(batch);
	    	sprite.setPosition(position.x,position.y);
	    }
	    
	    @Override
		public void update(float delta) {
	    	
	    	//Change position by velocity
			position.add(velocity.add(0,speed*delta));
			
		}
	    
	    public boolean checkCollision(Ball2 b2) {
	        if(this.getCollision().overlaps(b2.getArea())){
	        	// Se destruyen ambos
	            this.destroyed = true;
	            return true;
	
	        }
	        return false;
	    }
	    
	    public boolean isDestroyed() {return destroyed;}
		



		@Override
		public Vector2 getPosition() {
			// TODO Auto-generated method stub
			return null;
		}



		@Override
		public Rectangle getCollision() {
			// TODO Auto-generated method stub
			return sprite.getBoundingRectangle();
		}
	
}
