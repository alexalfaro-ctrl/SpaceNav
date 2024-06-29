package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BigAsteroidBuilder implements Builder {
	private int vidas;
    private float speed;
    private Vector2 velocity;
    private Vector2 position;
    private Sprite sprite;
    private Rectangle collisionRectangle;
    
	
	
	public BigAsteroidBuilder()
	{
		this.reset();
	}
	@Override
	public Builder vidas(int vidas) {
		this.vidas = vidas*2;
		return this;
	}

	@Override
	public Builder speed(float speed) {
		// TODO Auto-generated method stub
		this.speed = speed;
		return this;
	}

	@Override
	public Builder position(Vector2 position) {
		this.position = position;
		return this;
	}

	@Override
	public Builder velocity(Vector2 velocity) {
		// TODO Auto-generated method stub
		this.velocity = velocity;
		return this;
	}

	@Override
	public Builder sprite(Sprite sprite) {
		// TODO Auto-generated method stub
		this.sprite = sprite;
		return this;
	}

	
	
	public BigAsteroid build()
	{
		return new BigAsteroid(position, velocity, speed, vidas, sprite, collisionRectangle);
	}

	@Override
	public Builder collisionRectangle(com.badlogic.gdx.math.Rectangle collisionRectangle) {
		// TODO Auto-generated method stub
		this.collisionRectangle = collisionRectangle;
		return this;
	}

	@Override
	public Builder reset() {
		// TODO Auto-generated method stub
		int vidas = 1;
		float speed = 0;
	    Vector2 velocity = new Vector2(0,0);
	    Vector2 position = new Vector2(0,0);
	    Sprite sprite = null;
	    Rectangle collisionRectangle = null;
		return this;
	}
}
