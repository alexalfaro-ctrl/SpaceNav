package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;

public class MenuScreen extends GenericScreen {

	public MenuScreen(SpaceNavigation game, int width, int height) {
		super(game, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onUpdate(float delta) {
		
		getCamera().update();
		
		if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
			
			SpaceShip nave = SpaceShip.getInstance(Gdx.graphics.getWidth() / 2 - 50, 30);
			
			Screen ss = GameScreen.getInstance(getGame(), 800, 640, 1, nave.getVidas(), 0, 1, 1, 10);
            ss.resize(1200, 800);
            getGame().setScreen(ss);
            dispose();
		}
	}
	
	@Override
	public void onRender() {
		clearScreen(0, 0, 51);
		updateProjectionMatrix();
		
		beginBatch();
		
			drawText("Bienvenido a Space Navigation !", 140, 400);
			drawText("Pincha en cualquier lado o presiona cualquier tecla para comenzar ...", 100, 300);
		
		endBatch();
	}

}
