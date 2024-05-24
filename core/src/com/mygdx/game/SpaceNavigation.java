package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class SpaceNavigation extends Game {

	private SpriteBatch batch;
	private BitmapFont font;
	private int highScore;	

	public void create() {
		highScore = 0;
		batch = new SpriteBatch();
		font = new BitmapFont(); // usa Arial font x defecto
		font.getData().setScale(2f);
		
		//Screen startScreen = new PantallaMenu(this);
		//this.setScreen(startScreen);
		
		Screen startScreen = new MenuScreen(this, 1200, 800);
		this.setScreen(startScreen);
	}

	public void render() {
		super.render(); // important!
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}
	
	public SpriteBatch getBatch() {
		return batch;
	}
	

	public BitmapFont getFont() {
		return font;
	}
	
	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}

}