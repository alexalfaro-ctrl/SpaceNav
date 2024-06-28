package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Asteroid implements IGameObject {
	private int vidas = 1;
	private float speed;
	private final Vector2 velocity;
	private Vector2 position;
	private Sprite sprite;
	private Rectangle collisionRectangle;
	private boolean herido = false;
	private boolean destruida = false;
	
	 Asteroid(Vector2 position, Vector2 velocity,float speed ,int vidas,Sprite sprite,Rectangle collisionRectangle) {
		this.vidas=vidas;
		this.sprite=sprite;
		//sprite=new Sprite(new Texture(Gdx.files.internal("aGreyMedium4.png")));
		this.position = position;
    	sprite.setPosition(position.x,position.y);
    	this.velocity = velocity;
    	this.speed= speed;
    	this.collisionRectangle=collisionRectangle;
    	//collisionRectangle = new Rectangle(position.x, position.y, sprite.getWidth(), sprite.getHeight());
    	sprite.setBounds(position.x, position.y, 45, 45);


	}
	 
	@Override
	public void update(float delta) {
		
		if(!herido) {
			position.add(velocity.x * speed * delta, velocity.y * speed * delta);
			
			if (position.x < 0 || position.x + sprite.getWidth() > Gdx.graphics.getWidth()) {
		        velocity.x *= -1;
		    }
			if (position.y < 0 || position.y + sprite.getHeight() > Gdx.graphics.getHeight()) {
		        velocity.y *= -1;
			}
			collisionRectangle.setPosition(position);
		}
		herido=false;


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
	public int getVidas() {return vidas;}
	
	public void setVidas(int vidas) {this.vidas = vidas;}
	
	public void setHerido() {
        this.herido = true;
        vidas--;
        if (vidas <= 0) {
            destruida = true;
        }
    }
	
    public boolean estaHerido() {
 	   return herido;
    }
    
    public boolean estaDestruido() {
        return destruida;
    }
}
