package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public interface IGameObject {

	public void update(float delta);
	public void draw(SpriteBatch batch);
	public Vector2 getPosition();
	public Rectangle getCollision();
	
}
