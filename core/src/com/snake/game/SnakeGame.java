package com.snake.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.LinkedList;
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
	
	private ArrayList<Rectangle> SnakeBodyList;
	
	private Rectangle WallUP;
	private Rectangle WallDOWN;
	private Rectangle WallLEFT;
	private Rectangle WallRIGHT;
	
	
	private Direction CurrentDirection;
	
	private int WallThickness;
	private int EatenFruits;
	
	private Random rand;
	private ShapeRenderer shapeRenderer;
	
	private Float SnakeSpeed;
	private String SpeedScore;
	BitmapFont SpeedScoreFont;
	
	private String FruitScore;
	BitmapFont FruitScoreFont;
	
	private String OutScore;
	BitmapFont OutScoreFont;
	private int Outs;
	
	@Override
	public void create() {
	
	      snakeHeadImageUP = new Texture(Gdx.files.internal("graphics\\head_up.png"));
	      snakeHeadImageDOWN = new Texture(Gdx.files.internal("graphics\\head_down.png"));
	      snakeHeadImageRIGHT = new Texture(Gdx.files.internal("graphics\\head_right.png"));
	      snakeHeadImageLEFT = new Texture(Gdx.files.internal("graphics\\head_left.png"));
	      
	      snakeBodyImageHorizontal = new Texture(Gdx.files.internal("graphics\\body_horizontal.png"));
	      snakeBodyImageVertical = new Texture(Gdx.files.internal("graphics\\body_vertical.png"));
	      
	      SnakeBodyList= new ArrayList<Rectangle>();
	      


	      fruitImage = new Texture(Gdx.files.internal("graphics\\apple.png"));

	      eatingSound = Gdx.audio.newSound(Gdx.files.internal("sound\\bling4.wav"));
	      crashSound = Gdx.audio.newSound(Gdx.files.internal("sound\\stop.wav"));
	      
	      rand = new Random(); 
	      
	      camera = new OrthographicCamera();
	      camera.setToOrtho(false, 800, 800);
	      
	      FruitScore = "Fruits: "+EatenFruits;
	      FruitScoreFont = new BitmapFont();
	      
	      OutScore   = "Outs:  " + Outs;
	      OutScoreFont=new BitmapFont();
	      
	      SpeedScore   = "Speed:  " + SnakeSpeed;
	      SpeedScoreFont=new BitmapFont();
	      
	      
	      batch = new SpriteBatch();
	      
	      shapeRenderer = new ShapeRenderer();

	      
	      WallUP=new Rectangle();
	      WallDOWN=new Rectangle();
	      WallRIGHT=new Rectangle();
	      WallLEFT=new Rectangle();

	      
	      update_Walls();
	      
	      snakeHead = new Rectangle();
	      snakeHead.x =  Gdx.graphics.getWidth()/2;
	      snakeHead.y = Gdx.graphics.getHeight()/2;
	      snakeHead.width = 40;
	      snakeHead.height = 40;
	      
	      SnakeBodyList.add(snakeHead);
	      
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
	      Outs=0;
	      
	      WallThickness=20;
	      update_Walls();
	      
	      
	   }

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		
		
		batch.begin();
		
		batch.draw(createBgTexture((int)WallUP.width,(int)WallUP.height),WallUP.x,WallUP.y);
		batch.draw(createBgTexture((int)WallDOWN.width,(int)WallDOWN.height),WallDOWN.x,WallDOWN.y);
		batch.draw(createBgTexture((int)WallRIGHT.width,(int)WallRIGHT.height),WallRIGHT.x,WallRIGHT.y);
		batch.draw(createBgTexture((int)WallLEFT.width,(int)WallLEFT.height),WallLEFT.x,WallLEFT.y);
		
		switch (CurrentDirection) {
		case UP:
			snakeHead.y+=1*SnakeSpeed;
			
			
			SnakeBodyList[1] =  snakeHead.x;
		    SnakeBodyList[1].y = snakeHead.y-40;

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
		
		FruitScoreFont.setColor(Color.GREEN);
	    FruitScoreFont.draw(batch, FruitScore, 5,Gdx.graphics.getHeight()-5);
	    
	    
	    OutScoreFont.setColor(Color.RED);
	    OutScoreFont.draw(batch, OutScore, 5,Gdx.graphics.getHeight()-25);
	    
	    
	    SpeedScore   = "Speed:  " + SnakeSpeed;
	    SpeedScoreFont.setColor(Color.CORAL);
	    SpeedScoreFont.draw(batch, SpeedScore, 5,Gdx.graphics.getHeight()-50);
		batch.end();
		
		if(snakeHead.overlaps(Fruit)) {
			EatenFruits+=1; 
			eatingSound.play();
			update_fruit();
			FruitScore="Fruits: "+EatenFruits;
			SnakeSpeed+=0.3f;
			}
		
		
		
		if(snakeHead.overlaps(WallUP)||snakeHead.overlaps(WallDOWN)||snakeHead.overlaps(WallRIGHT)||snakeHead.overlaps(WallLEFT)) {
			Outs+=1;
			snakeHead.x=Gdx.graphics.getWidth()/2;
			snakeHead.y=Gdx.graphics.getHeight()/2;
			crashSound.play();
			OutScore   = "Outs:  " + Outs;
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
		update_Walls();
	}
	private void update_fruit() {
		Fruit.x=rand.nextInt(Gdx.graphics.getWidth()-(WallThickness+50));
	    Fruit.y=rand.nextInt(Gdx.graphics.getHeight()-(WallThickness+50));

	}
	private void CalcNewBody(Direction direction,LinkedList<Rectangle> list,Rectangle rec) {
		
		
	}
	
	private void update_Walls() {
		
		WallUP.x=0;
	    WallUP.y=Gdx.graphics.getHeight()-WallUP.height;
	    WallUP.width=Gdx.graphics.getWidth();
	    WallUP.height=WallThickness;
	    
	    
	    WallDOWN.x=0;
	    WallDOWN.y=0;
	    WallDOWN.width=Gdx.graphics.getWidth();
	    WallDOWN.height=WallThickness;
	    
	    
	    WallLEFT.x=0;
	    WallLEFT.y=0;
	    WallLEFT.width=WallThickness;
	    WallLEFT.height=Gdx.graphics.getHeight();
		
	    
	    
	    WallRIGHT.x=Gdx.graphics.getWidth()-WallRIGHT.width;
	    WallRIGHT.y=0;
	    WallRIGHT.width=WallThickness;
	    WallRIGHT.height=Gdx.graphics.getHeight();
	}
	public static Texture createBgTexture(int width,int height) {
	    Pixmap pixmap = new Pixmap(width,height, Format.RGBA8888);
	    pixmap.setColor(Color.GRAY);
	    pixmap.fill();
	    Texture texture = new Texture(pixmap); // must be manually disposed
	    pixmap.dispose();

	    return texture;
	}
	@Override
	public void dispose () {
		batch.dispose();
	}
}