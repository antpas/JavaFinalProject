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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import javafx.scene.text.Text;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

public class MyGdxGame extends ApplicationAdapter {

	private Texture candypicture;
	private Texture hippopicture;
	private Texture bombpicture;
	private OrthographicCamera camera; //Camera
	private SpriteBatch batch; //Sprites
	private Rectangle hippo;
	private Array<Rectangle> candyarray;
	private Array<Rectangle> bombsarray;
	private long lastcandyTime;
	private long lastBombTime;
	int score;
	private String yourScoreName;
	BitmapFont yourBitmapFontName;
	
	private boolean personheld;
	private int state = 1;
	static int MENU_STATE = 0;
	static int GAME_STATE = 1;
	static int END_STATE = 2;
	
	private void spawncandys()
	{
		Rectangle candy = new Rectangle();
		candy.width = candypicture.getWidth();
		candy.height = candypicture.getHeight();
		candy.x = MathUtils.random(0, Gdx.graphics.getWidth() - candy.width); //Random between 0 and right hand side
		candy.y = Gdx.graphics.getHeight();
		candyarray.add(candy); //Add candy to candy array
		lastcandyTime = TimeUtils.nanoTime();
	}
	
	private void spawnBombs()
	{
		Rectangle bomb = new Rectangle();
		bomb.width = bombpicture.getWidth();
		bomb.height = candypicture.getHeight();
		bomb.x = MathUtils.random(0, Gdx.graphics.getWidth() - bomb.width); //Random between 0 and right hand side
		bomb.y = Gdx.graphics.getHeight();
		bombsarray.add(bomb); //Add bomb to bomb array
		lastBombTime = TimeUtils.nanoTime();
	}
	
	@Override
	public void create () //Load sprites
	{
		
		//Sprites here
		candypicture  = new Texture(Gdx.files.internal("candy.png"));
		hippopicture = new Texture(Gdx.files.internal("hippo.png"));
		bombpicture = new Texture(Gdx.files.internal("bomb.png"));
		
		
		//Camera stuff here
		camera = new OrthographicCamera(); //Create new camera
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //Set camera. False means y points up
		batch = new SpriteBatch(); //Helper class to draw images
		
		//Instatiate hippo 'rectangle'
		hippo = new Rectangle();
		hippo.width = (int) (Gdx.graphics.getWidth() * 187 /480.0);
		hippo.height = Gdx.graphics.getHeight() * 200 /800;
		hippo.x = Gdx.graphics.getWidth()/2 - hippo.width / 2;
		hippo.y = 0;
		
		//Instatiate candys
		candyarray = new Array<Rectangle>();
		spawncandys();
		
		//Instatiate bombs
		bombsarray = new Array<Rectangle>();
		spawnBombs();
		
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
	         if(!personheld && hippo.contains(touchPos.x, touchPos.y)) //If rectangle for person contains finger touch, the person is held
		         personheld = true;
	        	 
	         if(personheld)
	        	 hippo.x = touchPos.x - hippo.width /2; //Adding on change in mouse position
	     }
	     else
	      personheld = false;
	     
	     if(hippo.x < 0) //Doesn't let person go left past bound
	    	 hippo.x = 0;
	     
	     if(hippo.x + hippo.getWidth() > Gdx.graphics.getWidth()) //Doesn't let person go right past bound
	    	 hippo.x = Gdx.graphics.getWidth() - hippo.getWidth();
	     
	     //Creates new bomb after time
	     if(TimeUtils.nanoTime() - lastBombTime > 700000000)
	    	  spawnBombs();
	     
	     
	     //Creates new candy after a certain amount of time
	      if(score < 10)
	      {
		      if(TimeUtils.nanoTime() - lastcandyTime > 500000000)
		    	  spawncandys();
	      }
	      else if(score >= 10 && score < 30)
	      {
		      if(TimeUtils.nanoTime() - lastcandyTime > 400050000)
		    	  spawncandys();
	      }
	      
	      else if(score >= 30 && score < 50)
	      {
		      if(TimeUtils.nanoTime() - lastcandyTime > 400000000)
		    	  spawncandys();
	      }
	      
	      else if(score >= 50 && score < 70)
	      {
		      if(TimeUtils.nanoTime() - lastcandyTime > 400000000)
		    	  spawncandys();
	      }
	      
	      else if(score >= 70 && score < 90)
	      {
		      if(TimeUtils.nanoTime() - lastcandyTime > 300000000)
		    	  spawncandys();
	      }
	      
	      else if(score >= 90 && score < 110)
	      {
		      if(TimeUtils.nanoTime() - lastcandyTime > 200050000)
		    	  spawncandys();
	      }
	      
	      else if(score >= 110 && score < 130)
	      {
		      if(TimeUtils.nanoTime() - lastcandyTime > 200000000)
		    	  spawncandys();
	      }
	      else 
	      {
		      if(TimeUtils.nanoTime() - lastcandyTime > 100000000)
		    	  spawncandys();
	      }
	      
	      //Iterate through bomb array
	      Iterator<Rectangle> iterbomb = bombsarray.iterator();
	      while(iterbomb.hasNext())
	      {
	    	 Rectangle bomb = iterbomb.next();
	    	 
	    	 if(bomb.y +64 < 0) //When it leaves screen remove candy
			   	{
			   		 iterbomb.remove();  
			   	}
			   	
	    	bomb.y = bomb.y - (400* Gdx.graphics.getDeltaTime()); //Move 400 pixels/unit
		    
	    	if(!bomb.overlaps(hippo)) //If bomb does NOT hit hippo
		    {
		    	iterbomb.remove();
		    	score++;
		    	yourScoreName = "Score: " + score; 
		    }
	      }
	      
		    
		   //Iterate through candy array
	      Iterator<Rectangle> iter = candyarray.iterator();
	      while(iter.hasNext())
	      {
	    	 Rectangle candy = iter.next();
	    	 
	    	 if(candy.y +64 < 0) //When it leaves screen remove candy
			   	{
			   		 iter.remove();  
			   		 state = END_STATE;
			   	}
			   	
	    	//These if else statements change speed of candys as your score goes up
	    	if(score < 10)
	    	{
		    	
		    	candy.y = candy.y - (400* Gdx.graphics.getDeltaTime()); //Move 400 pixels/unit
		    	if(candy.overlaps(hippo)) 
		    	{
		    		iter.remove();
		    		score++;
		    		yourScoreName = "Score: " + score; 
		    	}
	    	}
	    	
	    	else if(score >= 10 && score < 30)
	    	{
		    	
		    	candy.y = candy.y - (600* Gdx.graphics.getDeltaTime()); 
		    	if(candy.overlaps(hippo)) 
		    	{
		    		iter.remove();
		    		score++;
		    		yourScoreName = "Score: " + score;  
		    	}
	    	}
	    	
	    	else if(score >= 30 && score < 50)
		    {
			    
			   	candy.y = candy.y - (800* Gdx.graphics.getDeltaTime()); 

		    	if(candy.overlaps(hippo)) 
		    	{
		    		iter.remove();
			    	score++;
			    	yourScoreName = "Score: " + score;
			   	}
		    }
	    	
	    	else if(score >= 50 && score < 70)
		    {
			   
			   	candy.y = candy.y - (900* Gdx.graphics.getDeltaTime()); 
			   	
		    	if(candy.overlaps(hippo)) 
		    	{
		    		iter.remove();
			    	score++;
			    	yourScoreName = "Score: " + score;  
			   	}
		    }
	    	
	    	else 
		    {
			   	candy.y = candy.y - (1000* Gdx.graphics.getDeltaTime()); //Move 1000 pixels/unit
			   	
		    	if(candy.overlaps(hippo)) 
		    	{
		    		iter.remove();
			    	score++;
			    	yourScoreName = "Score: " + score;
			   	}
		    }
	    	
	    
	      }
	
	}
	
	@Override
	 public void render() 
	 {
		  Gdx.gl.glClearColor(1, 1, 1, 1);
		  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); 
		  
		  if (state == MENU_STATE)
		  {
			  
		  }
		  
		  else if(state == GAME_STATE)
		  {
			  update();

		      //Text Score
		      String s = "frames: " + Gdx.graphics.getFramesPerSecond();
		      batch.begin(); 
		      yourBitmapFontName.setColor(1.0f, 0, 0, 1.0f);
		      yourBitmapFontName.draw(batch, yourScoreName, 25, 100); 
		      yourBitmapFontName.draw(batch, s, 25, 200); 
		      
		      batch.end();
		      
		      //Render hippo
		      batch.setProjectionMatrix(camera.combined);
		      batch.begin();
		      batch.draw(hippopicture, hippo.x, hippo.y, Gdx.graphics.getWidth() * hippopicture.getWidth() /480, Gdx.graphics.getHeight() * hippopicture.getHeight() /800);
		      batch.end();
		      
		      //Render candy
		      batch.begin();
		      
		      for(Rectangle candy: candyarray) {
			      batch.draw(candypicture, candy.x, candy.y, Gdx.graphics.getWidth() * candypicture.getWidth() /480, Gdx.graphics.getHeight() * candypicture.getHeight() /800);
		      }
		      batch.end();
		      
		      //Render bombs
		      batch.begin();
		      
		      for(Rectangle bomb: bombsarray) {
			      batch.draw(candypicture, bomb.x, bomb.y, Gdx.graphics.getWidth() * bombpicture.getWidth() /480, Gdx.graphics.getHeight() * bombpicture.getHeight() /800);
		      }
		      batch.end();
		  }
		  
		  else if(state == END_STATE)
		  {
			  /*Skin uiSkin = new Skin(Gdx.files.internal("uiskin.json"));
			  TextButton startgame = new TextButton("Start Game",uiSkin);
			  startgame.setPosition(300, 300);
			  startgame.setSize(300, 60);
			  score = 0;*/
			  
		  }
	     

	 }
	
	 @Override
	   public void dispose() {
	      // dispose of all the native resources
	      hippopicture.dispose();
	      candypicture.dispose();
	      bombpicture.dispose();
	      batch.dispose();
	   }

}

