package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Asteroid implements IGameObject {
	
	private float speed;
	private final Vector2 velocity;
	private Vector2 position;
	private Sprite sprite;
	private Rectangle collisionRectangle;
	public Asteroid(Vector2 position, Vector2 velocity,float speed) {
		sprite=new Sprite(new Texture(Gdx.files.internal("aGreyMedium4.png")));
		this.position = position;
    	sprite.setPosition(position.x,position.y);
    	this.velocity = velocity;
    	this.speed= speed;
    	collisionRectangle = new Rectangle(position.x, position.y, sprite.getWidth(), sprite.getHeight());
    	sprite.setBounds(position.x, position.y, 45, 45);


	}
	@Override
	public void update(float delta) {
		position.add(velocity.x * speed * delta, velocity.y * speed * delta);
		
		if (position.x < 0 || position.x + sprite.getWidth() > Gdx.graphics.getWidth()) {
	        velocity.x *= -1;
	    }
		if (position.y < 0 || position.y + sprite.getHeight() > Gdx.graphics.getHeight()) {
	        velocity.y *= -1;
		}
		collisionRectangle.setPosition(position);

	}

	@Override
	public void draw(SpriteBatch batch) {
		sprite.setX(position.x);
        sprite.setY(position.y);
        sprite.draw(batch);

	}
	
        
	@Override
	public Vector2 getPosition() {
		return this.position;
		
	}

	@Override
	public Rectangle getCollision() {
		return collisionRectangle;
	}
	public void invertirDireccion() {
		velocity.x *= -1;
        velocity.y *= -1;
		
	}

}