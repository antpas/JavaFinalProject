package com.mygdx.game;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import javafx.scene.text.Text;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

public class MyGdxGame extends ApplicationAdapter {

	private Texture rockpicture;
	private Texture skierpicture;
	private OrthographicCamera camera; //Camera
	private SpriteBatch batch; //Sprites
	private Rectangle skier;
	private Array<Rectangle> rocksarray;
	private long lastRockTime;
	int score;
	private String yourScoreName;
	BitmapFont yourBitmapFontName;
	
	private boolean personheld;
	private int state = 1;
	static int MENU_STATE = 0;
	static int GAME_STATE = 1;
	static int END_STATE = 2;
	
	private void spawnRocks()
	{
		Rectangle rock = new Rectangle();
		rock.width = rockpicture.getWidth();
		rock.height = rockpicture.getHeight();
		rock.x = MathUtils.random(0, Gdx.graphics.getWidth() - rock.width); //Random between 0 and right hand side
		rock.y = Gdx.graphics.getHeight();
		rocksarray.add(rock); //Add rock to rock array
		lastRockTime = TimeUtils.nanoTime();
	}
	
	@Override
	public void create () //Load sprites
	{
		
		//Sprites here
		rockpicture  = new Texture(Gdx.files.internal("Rock.png"));
		skierpicture = new Texture(Gdx.files.internal("Skier.png"));
		
		
		//Camera stuff here
		camera = new OrthographicCamera(); //Create new camera
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //Set camera. False means y points up
		batch = new SpriteBatch(); //Helper class to draw images
		
		//Instatiate skier 'rectangle'
		skier = new Rectangle();
		skier.width = (int) (Gdx.graphics.getWidth() * 180 /480.0);
		skier.height = Gdx.graphics.getHeight() * 165 /800;
		skier.x = Gdx.graphics.getWidth()/2 - skier.width / 2;
		skier.y = 0;
		
		
		//Instatiate rocks
		rocksarray = new Array<Rectangle>();
		spawnRocks();
		
		//Score
		score = 0;
		yourScoreName = "Score: 0";
		yourBitmapFontName = new BitmapFont();
		
	} 
	
	public void update()
	{
		camera.update(); //Update camera once per frame
	
		 //Check if touched
	     if(Gdx.input.isTouched()) 
	     {
	         Vector3 touchPos = new Vector3();
	         touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
	         camera.unproject(touchPos);
	         if(!personheld && skier.contains(touchPos.x, touchPos.y)) //If rectangle for person contains finger touch, the person is held
		         personheld = true;
	        	 
	         if(personheld)
	        	 skier.x = touchPos.x - skier.width /2; //Adding on change in mouse position
	     }
	     else
	      personheld = false;
	     
	     if(skier.x < 0) //Doesn't let person go left past bound
	    	 skier.x = 0;
	     
	     if(skier.x + skier.getWidth() > Gdx.graphics.getWidth()) //Doesn't let person go right past bound
	    	 skier.x = Gdx.graphics.getWidth() - skier.getWidth();
	     
	     //Creates new rock after a certain amount of time
	      if(score < 10)
	      {
		      if(TimeUtils.nanoTime() - lastRockTime > 500000000)
		    	  spawnRocks();
	      }
	      else if(score >= 10 && score < 30)
	      {
		      if(TimeUtils.nanoTime() - lastRockTime > 400050000)
		    	  spawnRocks();
	      }
	      
	      else if(score >= 30 && score < 50)
	      {
		      if(TimeUtils.nanoTime() - lastRockTime > 400000000)
		    	  spawnRocks();
	      }
	      
	      else if(score >= 50 && score < 70)
	      {
		      if(TimeUtils.nanoTime() - lastRockTime > 400000000)
		    	  spawnRocks();
	      }
	      
	      else if(score >= 70 && score < 90)
	      {
		      if(TimeUtils.nanoTime() - lastRockTime > 300000000)
		    	  spawnRocks();
	      }
	      
	      else if(score >= 90 && score < 110)
	      {
		      if(TimeUtils.nanoTime() - lastRockTime > 200050000)
		    	  spawnRocks();
	      }
	      
	      else if(score >= 110 && score < 130)
	      {
		      if(TimeUtils.nanoTime() - lastRockTime > 200000000)
		    	  spawnRocks();
	      }
	      else 
	      {
		      if(TimeUtils.nanoTime() - lastRockTime > 100000000)
		    	  spawnRocks();
	      }
	      
	      
	      //Iternate through rock array
	      Iterator<Rectangle> iter = rocksarray.iterator();
	      while(iter.hasNext())
	      {
	    	 Rectangle rock = iter.next();
	    	 
	    	 if(rock.y +64 < 0) //When it leaves screen remove rock
			   	{
			   		 iter.remove();  
			   		 state = END_STATE;
			   	}
			   	
	    	//These if else statements change speed of rocks as your score goes up
	    	if(score < 10)
	    	{
		    	
		    	rock.y = rock.y - (400* Gdx.graphics.getDeltaTime()); //Move 200 pixels/unit
		    	if(rock.overlaps(skier)) 
		    	{
		    		iter.remove();
		    		score++;
		    		yourScoreName = "Score: " + score; 
		    	}
	    	}
	    	
	    	else if(score >= 10 && score < 30)
	    	{
		    	
		    	rock.y = rock.y - (600* Gdx.graphics.getDeltaTime()); //Move 200 pixels/unit
		    	if(rock.overlaps(skier)) 
		    	{
		    		iter.remove();
		    		score++;
		    		yourScoreName = "Score: " + score;  
		    	}
	    	}
	    	
	    	else if(score >= 30 && score < 50)
		    {
			    
			   	rock.y = rock.y - (800* Gdx.graphics.getDeltaTime()); //Move 200 pixels/unit

		    	if(rock.overlaps(skier)) 
		    	{
		    		iter.remove();
			    	score++;
			    	yourScoreName = "Score: " + score;
			   	}
		    }
	    	
	    	else if(score >= 50 && score < 70)
		    {
			   
			   	rock.y = rock.y - (900* Gdx.graphics.getDeltaTime()); //Move 200 pixels/unit
			   	
		    	if(rock.overlaps(skier)) 
		    	{
		    		iter.remove();
			    	score++;
			    	yourScoreName = "Score: " + score;  
			   	}
		    }
	    	
	    	else 
		    {
			   	rock.y = rock.y - (1000* Gdx.graphics.getDeltaTime()); //Move 1000 pixels/unit
			   	
		    	if(rock.overlaps(skier)) 
		    	{
		    		iter.remove();
			    	score++;
			    	yourScoreName = "Score: " + score;
			   	}
		    }
	    	
	    
	      }
	
	}
	
	public void updatestop()
	{
		camera.update(); //Update camera once per frame
		
		
	}
	
	@Override
	 public void render() 
	 {
		  if(state == GAME_STATE)
		  {
			  update();
			  Gdx.gl.glClearColor(1, 1, 1, 1); //Background color
		      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); 
		      //Text Score
		      String s = "frames: " + Gdx.graphics.getFramesPerSecond();
		      batch.begin(); 
		      yourBitmapFontName.setColor(1.0f, 0, 0, 1.0f);
		      yourBitmapFontName.draw(batch, yourScoreName, 25, 100); 
		      yourBitmapFontName.draw(batch, s, 25, 200); 
		      
		      batch.end();
		      
		      //Render skier
		      batch.setProjectionMatrix(camera.combined);
		      batch.begin();
		      batch.draw(skierpicture, skier.x, skier.y, Gdx.graphics.getWidth() * 180 /480, Gdx.graphics.getHeight() * 165 /800);
		      batch.end();
		      
		      //Render rocks
		      batch.begin();
		      
		      for(Rectangle rock: rocksarray) {
			      batch.draw(rockpicture, rock.x, rock.y, Gdx.graphics.getWidth() * 120 /480, Gdx.graphics.getHeight() * 105 /800);
		      }
		      batch.end();
		  }
		  
		  else if(state == END_STATE)
		  {
			  updatestop();
		  }
	     

	 }
	
	 @Override
	   public void dispose() {
	      // dispose of all the native resources
	      skierpicture.dispose();
	      rockpicture.dispose();
	      batch.dispose();
	   }

}

