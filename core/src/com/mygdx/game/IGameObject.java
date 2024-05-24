package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface IGameObject {

	public void update(float delta);
	public void draw(SpriteBatch batch);
	
}
