package com.mygdx.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
public class GameScreen extends GenericScreen {

	private Sound explosionSound;
	private Music gameMusic;
	private int score;
	private int ronda;
	private int velXAsteroides; 
	private int velYAsteroides; 
	private int cantAsteroides;
	
	private SpaceShip nave;
	private ArrayList<Asteroid> asteroides = new ArrayList<>();
	private ArrayList<Bullet> balas = new ArrayList<>();
	
	public GameScreen(SpaceNavigation game, int width, int height, int ronda, int vidas, int score,  
			int velXAsteroides, int velYAsteroides, int cantAsteroides) {
		
		super(game, width, height);
		
		this.ronda = ronda;
		this.score = score;
		this.velXAsteroides = velXAsteroides;
		this.velYAsteroides = velYAsteroides;
		this.cantAsteroides = cantAsteroides;
	
		//inicializar assets; musica de fondo y efectos de sonido
		explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
		explosionSound.setVolume(1,0.5f);
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("piano-loops.wav")); //
		
		gameMusic.setLooping(true);
		gameMusic.setVolume(0.5f);
		gameMusic.play();
		
	    // cargar imagen de la nave, 64x64   
		nave = SpaceShip.getInstance();
	    
        nave.setVidas(vidas);
        
        //crear asteroides
        Random r = new Random();
        for (int i = 0; i < cantAsteroides; i++) {
            Vector2 position = new Vector2(r.nextInt((int) Gdx.graphics.getWidth()),
                    50 + r.nextInt((int) Gdx.graphics.getHeight() - 50));
            Vector2 velocity = new Vector2(velXAsteroides + r.nextInt(4), velYAsteroides + r.nextInt(4));
            Asteroid asteroid = new Asteroid(position, velocity, 100,2); // Velocidad arbitraria, ajustar según necesidad
            asteroides.add(asteroid);
        }
	}
	
	
	public static GameScreen getInstance(SpaceNavigation game, int width, int height, int ronda, int vidas, int score,
            int velXAsteroides, int velYAsteroides, int cantAsteroides) {
        return new GameScreen(game, width, height, ronda, vidas, score, velXAsteroides, velYAsteroides, cantAsteroides);
    }
	@Override
	protected void onUpdate(float delta) {
		
		colisiones(delta);
		nave.update(delta);
		
		
		if (nave.estaDestruido()) {
			if (score > getGame().getHighScore())
				getGame().setHighScore(score);
			
	    	Screen ss = new GameOverScreen(getGame(), 1200, 800);
			ss.resize(1200, 800);
			getGame().setScreen(ss);
			nave.reset();
			dispose();
		}
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			//Ajustar para que la bullet aparezca frente a la nave
			Vector2 bulletPos = nave.getPosition(); 
			Rectangle collision = nave.getCollision();
			bulletPos.add(collision.width/5,collision.height);
			agregarBala(new Bullet(bulletPos,nave.getVelocity(),100));
		}  
		
		// actualizar movimiento de asteroides dentro del area
	    for (Asteroid asteroid : asteroides) {
	    	asteroid.update(delta);
		}
		
		if (asteroides.isEmpty()) {
			
			Screen ss = new GameScreen(getGame(), 800, 640 ,ronda+1, nave.getVidas(), score, 
					velXAsteroides+3, velYAsteroides+3, cantAsteroides+10);
			
			ss.resize(1200, 800);
			getGame().setScreen(ss);
			dispose();
		}
		
	}

	@Override
	protected void onRender() {
		clearScreen(0, 0, 51);
		
		beginBatch();
		
		
	      //dibujar balas
	     for (Bullet b : balas) {       
	    	 b.draw(getBatch());
	     }
	     
	     drawObject(nave);
	     
	     //dibujar asteroides y manejar colision con nave
	     for (Asteroid asteroid : asteroides) {
	    	asteroid.draw(getBatch());
		          //perdió vida o game over
	        if (nave.checkCollision(asteroid)) {
		            //asteroide se destruye con el choque 
	           asteroides.remove(asteroid);
	           nave.setHerido();
	           break;
	        } 
	    }
	      
	     dibujaEncabezado();
	     endBatch();
	}
	
	public void colisiones(float delta) {
		if (nave.estaHerido()) return;
		
		// colisiones entre balas y asteroides y su destruccion  
	    for (int i = 0; i < balas.size(); i++) {
	    	Bullet b = balas.get(i);
		    b.update(delta);
		    
		    for (int j = 0; j <asteroides.size(); j++) {  
		    	Asteroid asteroid = asteroides.get(j);
		    	if (b.checkCollision(asteroid)) {
		    			 asteroid.setHerido();
		            	 explosionSound.play();
		            	 if(asteroid.estaDestruido()) {
		            		 asteroides.remove(asteroid);
			            	 score +=10;
			            	 break;
			            	 
		            		 
		            	 }
		            	
		         }   	  
		  	}
		                
		    //   b.draw(batch);
		    if (b.isDestroyed()) {
		    	balas.remove(b);
		        i--; //para no saltarse 1 tras eliminar del arraylist
		    }
		 }
		      
	    //colisiones entre asteroides y sus rebotes  
		for (int i=0;i<asteroides.size();i++) {
			Asteroid asteroid1 = asteroides.get(i);   
		    for (int j=0;j<asteroides.size();j++) {
		    	Asteroid asteroid2 = asteroides.get(j); 
		    	
		    	if (asteroid1.getCollision().overlaps(asteroid2.getCollision())) {
		    		asteroid1.invertirDireccion();
		    		asteroid2.invertirDireccion();
		    	}
		    }
		}   
	}
	
	public boolean agregarBala(Bullet bb) {
    	return balas.add(bb);
    }
	
	public void dibujaEncabezado() {
		CharSequence str = "Vidas: "+nave.getVidas()+" Ronda: "+ronda;
		getGame().getFont().getData().setScale(2f);		
		drawText(str, 10, 30);
		drawText("Score:"+this.score, Gdx.graphics.getWidth()-150, 30);
		drawText("HighScore:" + getGame().getHighScore(), Gdx.graphics.getWidth()/2-100, 30);
	}
	
	@Override
	public void show() {
		gameMusic.play();
	}

	@Override
	public void dispose() {
		this.explosionSound.dispose();
		this.gameMusic.dispose();
	}
}
