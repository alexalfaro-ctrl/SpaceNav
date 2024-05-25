package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;

public class GameOverScreen extends GenericScreen{

	public GameOverScreen(SpaceNavigation game, int width, int height) {
		super(game, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onUpdate(float delta) {
		
		getCamera().update();
		
		if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
			Screen ss = new GameScreen(getGame(), 800, 640, 1, 3, 0, 1, 1, 10);
			ss.resize(1200, 800);
			getGame().setScreen(ss);
			dispose();
		}
	}

	@Override
	protected void onRender() {
		clearScreen(0, 0, 51);
		updateProjectionMatrix();

		beginBatch();
			drawText("Game Over !!! ", 120, 400);
			drawText("Pincha en cualquier lado para reiniciar ...", 100, 300);
		endBatch();
	}	
}
