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
	private ArrayList<Ball2> balls1 = new ArrayList<>();
	private ArrayList<Ball2> balls2 = new ArrayList<>();
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
	    nave = new SpaceShip(Gdx.graphics.getWidth()/2-50,30); 
	    
        nave.setVidas(vidas);
        
        //crear asteroides
        Random r = new Random();
	    for (int i = 0; i < cantAsteroides; i++) {
	        Ball2 bb = new Ball2(r.nextInt((int)Gdx.graphics.getWidth()),
	  	            50+r.nextInt((int)Gdx.graphics.getHeight()-50),
	  	            20+r.nextInt(10), velXAsteroides+r.nextInt(4), velYAsteroides+r.nextInt(4), 
	  	            new Texture(Gdx.files.internal("aGreyMedium4.png")));	   
	  	    balls1.add(bb);
	  	    balls2.add(bb);
	  	}
	}

	@Override
	protected void onUpdate(float delta) {
		
		colisiones(delta);
		nave.update(delta);
		
		
		if (nave.estaDestruido()) {
			if (score > getGame().getHighScore())
				getGame().setHighScore(score);
			
	    	Screen ss = new PantallaGameOver(getGame());
			ss.resize(1200, 800);
			getGame().setScreen(ss);
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
	    for (Ball2 ball : balls1) {
	    	ball.update();
		}
		
		if (balls1.size() == 0) {
			
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
	     for (int i = 0; i < balls1.size(); i++) {
	    	Ball2 b=balls1.get(i);
	    	b.draw(getBatch());
		          //perdió vida o game over
	        if (nave.checkCollision(b)) {
		            //asteroide se destruye con el choque             
	           balls1.remove(i);
	           balls2.remove(i);
	           nave.setHerido();
	           i--;
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
		    
		    for (int j = 0; j < balls1.size(); j++) {    
		    	if (b.checkCollision(balls1.get(j))) {          
		            	 explosionSound.play();
		            	 balls1.remove(j);
		            	 balls2.remove(j);
		            	 j--;
		            	 score +=10;
		         }   	  
		  	}
		                
		    //   b.draw(batch);
		    if (b.isDestroyed()) {
		    	balas.remove(b);
		        i--; //para no saltarse 1 tras eliminar del arraylist
		    }
		 }
		      
	    //colisiones entre asteroides y sus rebotes  
		for (int i=0;i<balls1.size();i++) {
			Ball2 ball1 = balls1.get(i);   
		    for (int j=0;j<balls2.size();j++) {
		    	Ball2 ball2 = balls2.get(j); 
		    	
		    	if (i < j) {
		    		ball1.checkCollision(ball2);
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
