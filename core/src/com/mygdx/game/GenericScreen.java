package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public abstract class GenericScreen implements Screen {

	private SpaceNavigation game;
	private OrthographicCamera camera;
	private float deltaTime;
	
	public GenericScreen(SpaceNavigation game, int width, int height) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);
		
		deltaTime = 0;
	}
	
	protected abstract void onUpdate(float delta);
	protected abstract void onRender();
	
	public SpaceNavigation getGame() {
		return game;
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}
	
	public SpriteBatch getBatch() {
		return getGame().getBatch();
	}
	
	public void updateProjectionMatrix() {
		game.getBatch().setProjectionMatrix(getCamera().combined);
	}
	
	public void beginBatch() {
		game.getBatch().begin();
	}
	
	public void endBatch() {
		game.getBatch().end();
	}
	
	public void drawText(java.lang.CharSequence text, float x, float y) {
		game.getFont().draw(game.getBatch(), text, x, y);
	}
	
	public void clearScreen() {
		ScreenUtils.clear(0, 0, 0, 1);
	}
	
	public void clearScreen(int r, int g, int b, int a) {
		ScreenUtils.clear(r/255.0f, g/255.0f, b/255.0f, a/255.0f);
	}
	
	public void clearScreen(int r, int g, int b) {
		ScreenUtils.clear(r/255.0f, g/255.0f, b/255.0f, 1);
	}
	
	public void drawObject(IGameObject obj) {
		obj.draw(getBatch());
	}
	
	public void updateObject(IGameObject obj) {
		obj.update(deltaTime);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		deltaTime = delta;
		onUpdate(delta); // Lo sé...
		onRender();
	}


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
