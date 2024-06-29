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
	private boolean isShooting;
    private SpaceShip nave;
    private ArrayList<Asteroid> asteroides = new ArrayList<>();
    private ArrayList<Asteroid> asteroidesGrandes = new ArrayList<>();
        
    private ArrayList<Projectile> balas = new ArrayList<>();
	
    public GameScreen(SpaceNavigation game, int width, int height, int ronda, int vidas, int score,  
			int velXAsteroides, int velYAsteroides, int cantAsteroides)
    {
    	super(game, width, height);
		
    	this.isShooting=false;
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

        for (int i = 0; i < cantAsteroides/2; i++) {
        	AsteroidBuilder astBuilder = new AsteroidBuilder();
        	Director director = new Director(astBuilder);
            Vector2 position = new Vector2(r.nextInt((int) Gdx.graphics.getWidth()),
                    50 + r.nextInt((int) Gdx.graphics.getHeight() - 50));
            Vector2 velocity = new Vector2(velXAsteroides + r.nextInt(4), velYAsteroides + r.nextInt(4));
            
            director.BuildnormalAsteroid(position, velocity);
            asteroides.add(astBuilder.build());
            position = new Vector2(r.nextInt((int) Gdx.graphics.getWidth()),
                    50 + r.nextInt((int) Gdx.graphics.getHeight() - 50));
            velocity = new Vector2(velXAsteroides + r.nextInt(4), velYAsteroides + r.nextInt(4));
            director.BuildBigAsteroid(position, velocity);
            asteroides.add(astBuilder.build());
            
            //Añadir asteroides grandes
            BigAsteroidBuilder astBuilderBig = new BigAsteroidBuilder();
            
            director.changeBuilder((Builder) astBuilderBig);
            position = new Vector2(r.nextInt((int) Gdx.graphics.getWidth()),
                    50 + r.nextInt((int) Gdx.graphics.getHeight() - 50));
            velocity = new Vector2(velXAsteroides + r.nextInt(4), velYAsteroides + r.nextInt(4));
            
            director.BuildnormalAsteroid(position, velocity);
            asteroidesGrandes.add(astBuilderBig.build());
            position = new Vector2(r.nextInt((int) Gdx.graphics.getWidth()),
                    50 + r.nextInt((int) Gdx.graphics.getHeight() - 50));
            velocity = new Vector2(velXAsteroides + r.nextInt(4), velYAsteroides + r.nextInt(4));
            director.BuildBigAsteroid(position, velocity);
            asteroidesGrandes.add(astBuilderBig.build());

        }
        
        nave.setProjectile( new NormalProjectile(Vector2.Zero, nave.getVelocity(), 100));
    }
    
    
    @Override
	protected void uiBuilder() {
 
    	CharSequence str = "Vidas: "+nave.getVidas()+" Ronda: "+ronda;
    	getGame().getFont().getData().setScale(2f);		
    	drawText(str, 10, 30);
    	drawText("Score:"+this.score, Gdx.graphics.getWidth() - 150, 30);
    	drawText("HighScore:" + getGame().getHighScore(), Gdx.graphics.getWidth()/2-100, 30);
    
	}

	@Override
	protected void onInput() {
		// TODO Auto-generated method stub
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
			isShooting=true;
                
                if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
                    nave.setProjectile( new NormalProjectile(Vector2.Zero, nave.getVelocity(), 100));
                }
                
                if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
                    nave.setProjectile( new ExplosiveProjectile(Vector2.Zero, nave.getVelocity(), 20));
                }
	}
	
    // TODO: Fix this crap
    public static GameScreen getInstance(SpaceNavigation game, int width, int height, int ronda, int vidas, int score,
            int velXAsteroides, int velYAsteroides, int cantAsteroides) {
        return new GameScreen(game, width, height, ronda, vidas, score, velXAsteroides, velYAsteroides, cantAsteroides);
    }
    
    @Override
    protected void onUpdate(float delta) {
		
        colisiones(delta);
	nave.update(delta);
			

	if (nave.estaDestruido()) {
            
            if (score > getGame().getHighScore()) {
                getGame().setHighScore(score);
            }

	    Screen ss = new GameOverScreen(getGame(), 1200, 800);
            ss.resize(1200, 800);
            getGame().setScreen(ss);
            nave.reset();
            dispose();
        }
		
	// actualizar movimiento de asteroides dentro del area
	for (Asteroid asteroid : asteroides) {
	    asteroid.update(delta);
	}
        
		//spawnear bullets
        if (this.isShooting) {
        	isShooting=false;
            //Ajustar para que la bullet aparezca frente a la nave
            Vector2 bulletPos = nave.getPosition(); 
            Rectangle collision = nave.getCollision();
            bulletPos.add(collision.width/5,collision.height);
            
            nave.getProjectile().setPosition(bulletPos);
            nave.getProjectile().setVelocity(nave.getVelocity());
            //nave.getProjectile().setSpeed(100);

            agregarBala(nave.getProjectile().create(bulletPos));
	}
		
        // actualizar movimiento de asteroides dentro del area
	for (Asteroid asteroid : asteroides) {
	    asteroid.update(delta);
        }
	for(Asteroid bigAsteroid:asteroidesGrandes) {
		bigAsteroid.update(delta);
	}
		
	if (asteroides.isEmpty() && asteroidesGrandes.isEmpty()) {
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
        
			
	//dibujar balas
        for (Projectile p : balas) {
            p.draw(getBatch());
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
	
	for (Asteroid bigAsteroid : asteroidesGrandes) {
        bigAsteroid.draw(getBatch());
        if (nave.checkCollision(bigAsteroid)) {
            asteroidesGrandes.remove(bigAsteroid);
            nave.setHerido();
            break;
        }
    }
	      
	
	
    }
    
    public void colisiones(float delta) {
	
        if (nave.estaHerido()) return;
		
        // colisiones entre balas y asteroides y su destruccion  
        
        
       
        
        for (int i = 0; i < balas.size(); i++) {
        	
            Projectile p = balas.get(i);
            p.update(delta);
		    
            for (int j = 0; j <asteroides.size(); j++) {  
            
                Asteroid asteroid = asteroides.get(j);
                if (!p.checkCollision(asteroid))
                    continue;
		
                asteroid.setHerido();
                explosionSound.play();
		
                if(asteroid.estaDestruido()) {
                    asteroides.remove(asteroid);
                    score += 10;
                    break;
                }
                
            }
            
            for (int j = 0; j < asteroidesGrandes.size(); j++) {
            	
                Asteroid bigAsteroid = asteroidesGrandes.get(j);
                if (p.checkCollision(bigAsteroid)) {
                    bigAsteroid.setHerido();
                    explosionSound.play();

                    if (bigAsteroid.estaDestruido()) {
                        asteroidesGrandes.remove(bigAsteroid);
                        score += 20;
                        break;
                    }
                }
            }
                      
            // b.draw(batch);
            if (p.isDestroyed()) {
                balas.remove(p);
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
         
        
        for (int i = 0; i < asteroidesGrandes.size(); i++) {
            Asteroid bigAsteroid1 = asteroidesGrandes.get(i);
            for (int j = 0; j < asteroidesGrandes.size(); j++) {
                Asteroid bigAsteroid2 = asteroidesGrandes.get(j);
                if (bigAsteroid1.getCollision().overlaps(bigAsteroid2.getCollision())) {
                    bigAsteroid1.invertirDireccion();
                    bigAsteroid2.invertirDireccion();
                	}
            	}
        	}
        
    }
    
        
    public boolean agregarBala(Projectile p) {
        return balas.add(p);
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
