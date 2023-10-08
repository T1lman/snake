package com.snake.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.Random;

public class SnakeGame extends ApplicationAdapter {
	enum Direction{
		UP,
		DOWN,
		RIGHT,
		LEFT,
	}
	private Texture snakeHeadImageUP;
	private Texture snakeHeadImageDOWN;
	private Texture snakeHeadImageRIGHT;
	private Texture snakeHeadImageLEFT;
	

	
	private Texture snakeBodyImageHorizontal;
	private Texture snakeBodyImageVertical;
	
	private Texture fruitImage;
	
	private Sound eatingSound;
	private Sound crashSound;
	
	
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private Rectangle snakeHead;
	private Rectangle snakeBody;
	private Rectangle Fruit;
	
	
	private Direction CurrentDirection;
	private Float SnakeSpeed;
	private int EatenFruits;
	
	private Random rand;
	
	private String FruitScore;
	BitmapFont FruitScoreFont;
	
	@Override
	public void create() {
	
	      snakeHeadImageUP = new Texture(Gdx.files.internal("graphics\\head_up.png"));
	      snakeHeadImageDOWN = new Texture(Gdx.files.internal("graphics\\head_down.png"));
	      snakeHeadImageRIGHT = new Texture(Gdx.files.internal("graphics\\head_right.png"));
	      snakeHeadImageLEFT = new Texture(Gdx.files.internal("graphics\\head_left.png"));
	      
	      snakeBodyImageHorizontal = new Texture(Gdx.files.internal("graphics\\body_horizontal.png"));
	      snakeBodyImageVertical = new Texture(Gdx.files.internal("graphics\\body_vertical.png"));


	      fruitImage = new Texture(Gdx.files.internal("graphics\\apple.png"));

	      eatingSound = Gdx.audio.newSound(Gdx.files.internal("sound\\stop.wav"));
	      crashSound = Gdx.audio.newSound(Gdx.files.internal("sound\\stop.wav"));

	      
	      rand = new Random(); 
	      
	      camera = new OrthographicCamera();
	      camera.setToOrtho(false, 800, 800);
	      
	      FruitScore = "Fruits: "+EatenFruits;
	      FruitScoreFont = new BitmapFont();
	      
	      batch = new SpriteBatch();
	      
	     

	      snakeHead = new Rectangle();
	      snakeHead.x =  Gdx.graphics.getWidth()/2;
	      snakeHead.y = Gdx.graphics.getHeight()/2;
	      snakeHead.width = 40;
	      snakeHead.height = 40;
	      
	      snakeBody = new Rectangle();
	      snakeBody.x =  snakeHead.x;
	      snakeBody.y = snakeHead.y-64;
	      snakeBody.width = 40;
	      snakeBody.height = 40;
	      
	      Fruit= new Rectangle();
	      Fruit.x=rand.nextInt(Gdx.graphics.getWidth());
	      Fruit.y=rand.nextInt(Gdx.graphics.getHeight());
	      Fruit.width=40;
	      Fruit.height=40;
	      SnakeSpeed=3.0f;
	      CurrentDirection=Direction.UP;
	      EatenFruits=0;
	      
	   }

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		switch (CurrentDirection) {
		case UP:
			snakeHead.y+=1*SnakeSpeed;
			
			snakeBody.x =  snakeHead.x;
		    snakeBody.y = snakeHead.y-40;

			batch.draw(snakeHeadImageUP, snakeHead.x, snakeHead.y);
			for (int i=0;i<EatenFruits;i++) {
		    	batch.draw(snakeBodyImageVertical, snakeBody.x, snakeBody.y-(i*40));
		    }

			break;
		case DOWN:
			snakeHead.y-=1*SnakeSpeed;
			
			snakeBody.x =  snakeHead.x;
		    snakeBody.y = snakeHead.y+40;

			batch.draw(snakeHeadImageDOWN, snakeHead.x, snakeHead.y);
			for (int i=0;i<EatenFruits;i++) {
		    	batch.draw(snakeBodyImageVertical, snakeBody.x, snakeBody.y+(i*40));
		    }

			break;
		case RIGHT:
			snakeHead.x+=1*SnakeSpeed;
			
			snakeBody.x =  snakeHead.x-40;
		    snakeBody.y = snakeHead.y;

			
			batch.draw(snakeHeadImageRIGHT, snakeHead.x, snakeHead.y);
			for (int i=0;i<EatenFruits;i++) {
		    	batch.draw(snakeBodyImageHorizontal, snakeBody.x-(i*40), snakeBody.y);
		    }

			break;
		case LEFT:
			snakeHead.x-=1*SnakeSpeed;
			
			snakeBody.x =  snakeHead.x+40;
		    snakeBody.y = snakeHead.y;
		    
		    batch.draw(snakeHeadImageLEFT, snakeHead.x, snakeHead.y);
		    for (int i=0;i<EatenFruits;i++) {
		    	batch.draw(snakeBodyImageHorizontal, snakeBody.x+(i*40), snakeBody.y);
		    }

			break;
		}
		batch.draw(fruitImage,Fruit.x,Fruit.y);
		
		FruitScoreFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
	    FruitScoreFont.draw(batch, FruitScore, 5,Gdx.graphics.getHeight()-5);
		batch.end();
		
		if(snakeHead.overlaps(Fruit)) {
			EatenFruits+=1; 
			update_fruit();
			FruitScore="Fruits: "+EatenFruits;
			SnakeSpeed+=0.3f;
			}
		if (Gdx.graphics.getHeight()==snakeHead.x||Gdx.graphics.getWidth()==snakeHead.y ) System.out.println("Out!");
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) CurrentDirection=Direction.LEFT;
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) CurrentDirection=Direction.RIGHT;
		
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) CurrentDirection=Direction.UP;
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) CurrentDirection=Direction.DOWN;
		
		
		
		
		
		
	}
	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, width, height);
	}
	private void update_fruit() {
		Fruit.x=rand.nextInt(Gdx.graphics.getWidth()-25);
	    Fruit.y=rand.nextInt(Gdx.graphics.getHeight()-25);

	}
	@Override
	public void dispose () {
		batch.dispose();
	}
}