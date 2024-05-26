package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class SpaceShip implements IGameObject {
	
	
    private int vidas = 3;
    private float speed;
    private Vector2 velocity;
    private Sprite sprite;
    private Sound sonidoHerido;
    private boolean destruida = false;
    private boolean herido = false;
    private int tiempoHeridoMax = 50;
    private int tiempoHerido;
    
    private Vector2 position;
    
    
    public SpaceShip(int posX, int posY) {
    	
    	this.sonidoHerido = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
    	sprite = new Sprite(new Texture(Gdx.files.internal("SpaceShip.png")));
    	this.position = new Vector2(posX,posY);
    	sprite.setPosition(position.x,position.y);
    	//spr.setOriginCenter();
    	sprite.setBounds(position.x, position.y, 45, 45);
    	
    	//
    	this.speed= 200f;

    }
    
    
    private Vector2 keyboardInput()
    {
    	Vector2 inputVector = new Vector2();    	
    	if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) inputVector.x +=(-1);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))inputVector.x +=(1);
    	if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) inputVector.y +=(-1);     
        if (Gdx.input.isKeyPressed(Input.Keys.UP))   inputVector.y +=(1);
    	//Returns a boolean vector wich indicates velocity added.
        return inputVector;
    }
    
    private void outOfBounds(float delta) //Se preocupa solamente de sus propios inputs
    {
    	final Rectangle collision = sprite.getBoundingRectangle();
    	if (0 >= collision.getX())
    		position.add(new Vector2(speed*delta,0));
        else if (Gdx.graphics.getWidth() <= collision.getX()+collision.width)
        	position.add(new Vector2(-speed*delta,0));
    	
    	if ( 0 >= collision.getY())
    		position.add(new Vector2(0,speed*delta));
    	else if ( Gdx.graphics.getHeight() <= collision.getY()-collision.height)
    		position.add(new Vector2(0,-speed*delta));

        
    }
    
    
    @Override
	public void update(float delta) {
		
    	if (!herido) {
	        // que se mueva con teclado
	        Vector2 inputVector = keyboardInput();
	        velocity =inputVector.scl(speed*delta);
	        
	        //ApplyVelocity
	        position = position.add(velocity);
	        
	        //Que no se salga de pantalla
	        
	        outOfBounds(delta);
	        return;
    	}
		herido=false;
		long idSound = sonidoHerido.play();
		sonidoHerido.setVolume(idSound,1f);
		
		vidas--;
		if (vidas <=0)
			this.destruida = true;
			
	}
    
    @Override
    public void draw(SpriteBatch batch){
        
    	//Sets the new position calculated.
        sprite.setX(position.x);
        sprite.setY(position.y);
        sprite.draw(batch);
        // disparo
        

        
    }
    
    public boolean checkCollision(Asteroid b)
    {
    	return getCollision().overlaps(b.getCollision());
    }
    
    
    
    
    public boolean estaDestruido() {
       return !herido && destruida;
    }
    
    public void setHerido() {this.herido = true;}
    public boolean estaHerido() {
 	   return herido;
    }
    
    public int getVidas() {return vidas;}
    public int getX() {return (int) position.x;}
    public int getY() {return (int) position.y;}
	public void setVidas(int vidas) {this.vidas = vidas;}
	public void setSpeed(float speed) {this.speed=speed;}
	public Vector2 getVelocity() {return (Vector2) this.velocity;}

	@Override
	public Vector2 getPosition() {
		// TODO Auto-generated method stub
		return this.position.cpy();
	}


	@Override
	public Rectangle getCollision() {
		// TODO Auto-generated method stub
		return sprite.getBoundingRectangle();
	}
	
}
