/*
 * Anthony Pasquariello 
 * Boston University
 * EC327 Final Project
 * Hungry Hippo
 * 
 * Sources:
 * 		Tutorial on how to create a basic java game: https://github.com/libgdx/libgdx/wiki/A-simple-game
 * 			This was used to help me start to understand how games are structured in java. Basic functionality was based off of this tutorial
 * 
 * 		Images: http://cliparts.co/surprised-face-clip-art
 * 				http://www.clipartlord.com/category/military-clip-art/bomb-clip-art/page/2/
 * 				http://yeschefgame.com/
 * 				http://content.mycutegraphics.com/graphics/food/blue-hard-candy.png
 * 				http://tirdusoleil.com/wp-content/uploads/2014/10/soft-yellow-backgrounds.jpg
 * 				
 * 
 */

package com.mygdx.game;

import java.util.Iterator;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;


public class MyGdxGame extends ApplicationAdapter {

	private Texture candypicture;
	private Texture hippopicture;
	private Texture bombpicture;
	private Texture playpicture;
	private Texture retrypicture;
	private Texture gameoverpicture;
	private Texture background;
	private Texture icon;
	private OrthographicCamera camera; //Camera
	private SpriteBatch batch; //Sprites
	private Rectangle hippo;
	private Rectangle playbutton;
	private Rectangle retrybutton;
	private Array<Rectangle> candyarray;
	private Array<Rectangle> bombsarray;
	private long lastcandyTime;
	private long lastBombTime;
	public double get_candyx;
	public double get_candyy;
	public double get_bombx;
	int score;
	public int highscore;
	public int permhighscore;
	private String yourScoreName;
	private String yourHighScoreName;
	BitmapFont yourBitmapFontName;
	private boolean hippoheld;
	private int state = 0;
	static int MENU_STATE = 0;
	static int GAME_STATE = 1;
	static int END_STATE = 2;
	
	private void spawnCandy()
	{
		Rectangle candy = new Rectangle();
		candy.width = candypicture.getWidth();
		candy.height = candypicture.getHeight();
		candy.x = MathUtils.random(0, Gdx.graphics.getWidth() - candy.width) % Gdx.graphics.getWidth(); //Random between 0 and right hand side
		candy.y = Gdx.graphics.getHeight();
		get_candyx = candy.x; //getter for spawnBombs method
		get_candyy = candy.y; //getter for spawnBombs method
		candyarray.add(candy); //Add candy to candy array
		lastcandyTime = TimeUtils.nanoTime();
		
	}
	
	private void spawnBombs()
	{
		Rectangle bomb = new Rectangle();
		bomb.width = bombpicture.getWidth();
		bomb.height = bombpicture.getHeight();
		bomb.x = MathUtils.random(0, Gdx.graphics.getWidth() - bomb.width)% (Gdx.graphics.getWidth()); //Random between 0 and right hand side
		bomb.y = Gdx.graphics.getHeight();
		get_bombx = bomb.x;
		lastBombTime = TimeUtils.nanoTime();
		if(Math.abs(get_candyx - bomb.x) > Gdx.graphics.getWidth()/4 && TimeUtils.nanoTime() - lastcandyTime > 100050000) //Only spawn bomb if not near candy
			bombsarray.add(bomb); //Add bomb to bomb array
		
		
	}
	
	@Override
	public void create () //Load sprites
	{
		
		//Sprites here
		candypicture  = new Texture(Gdx.files.internal("candy.png"));
		hippopicture = new Texture(Gdx.files.internal("hippo.png"));
		bombpicture = new Texture(Gdx.files.internal("bomb.png"));
		playpicture = new Texture(Gdx.files.internal("button-play.png"));
		retrypicture = new Texture(Gdx.files.internal("restart.png"));
		gameoverpicture = new Texture(Gdx.files.internal("hungryhippo.png"));
		background = new Texture(Gdx.files.internal("junglebackground.jpg"));
		icon = new Texture(Gdx.files.internal("Logo.png"));
		
		//Camera stuff here
		camera = new OrthographicCamera(); //Create new camera
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //Set camera. False means y points up
		batch = new SpriteBatch(); //Helper class to draw images
		
		//Instantiate hippo 'rectangle'
		hippo = new Rectangle();
		hippo.width = (int) (Gdx.graphics.getWidth() * hippopicture.getWidth() /480);
		hippo.height = Gdx.graphics.getHeight() * hippopicture.getHeight() /800;
		hippo.x = Gdx.graphics.getWidth()/2 - hippo.width / 2;
		hippo.y = 0;
		
		//Instantiate candy
		candyarray = new Array<Rectangle>();
		
		//Instantiate bombs
		bombsarray = new Array<Rectangle>();
		spawnBombs();
		
		//Buttons
		playbutton = new Rectangle();
		retrybutton = new Rectangle();
		
		//Load high score from phone
		Preferences prefs = Gdx.app.getPreferences("highscore");
		permhighscore = prefs.getInteger("highscore");
		
		//Score
		score = 0;
		highscore = 0;
		yourScoreName = "Score: 0";
		if (highscore == 0)
		{
			yourHighScoreName = "High Score: 0";
		}
		else
		{
			yourHighScoreName = "High Score: " + highscore;
		}
		yourBitmapFontName = new BitmapFont();
		yourBitmapFontName.getData().scale(2);	
		
		
	} 
	public void updatebutton()
	{
		camera.update(); //Update camera once per frame
		 if(Gdx.input.isTouched()) 
	     {
	         Vector3 touchPos2 = new Vector3();
	         touchPos2.set(Gdx.input.getX(), Gdx.input.getY(), 0);
	         camera.unproject(touchPos2);
	         if(playbutton.contains(touchPos2.x, touchPos2.y)) //If rectangle for play contains finger touch, change state
		         state = GAME_STATE;

	     }
	}
	
	public void updateretry()
	{
		camera.update(); //Update camera once per frame
		 if(Gdx.input.isTouched()) 
	     {
	         Vector3 touchPos3 = new Vector3();
	         touchPos3.set(Gdx.input.getX(), Gdx.input.getY(), 0);
	         camera.unproject(touchPos3);
	         if(retrybutton.contains(touchPos3.x, touchPos3.y)) //If rectangle for play contains finger touch, change state
		         state = GAME_STATE;

	     }
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
	         if(!hippoheld && hippo.contains(touchPos.x, touchPos.y)) //If rectangle for hippo contains finger touch, the hippo is held
		         hippoheld = true;
	        	 
	         if(hippoheld)
	        	 hippo.x = touchPos.x - hippo.width /2; //Adding on change in mouse position
	     }
	     else
	      hippoheld = false;
	     
	     if(hippo.x < 0) //Doesn't let hippo go left past bound
	    	 hippo.x = 0;
	     
	     if(hippo.x + hippo.getWidth() > Gdx.graphics.getWidth()) //Doesn't let hippo go right past bound
	    	 hippo.x = Gdx.graphics.getWidth() - hippo.getWidth();
	     
	     //Creates new bomb if certain criteria are met
	     if(Math.abs(get_candyx - get_bombx) > Gdx.graphics.getWidth()/4 && TimeUtils.nanoTime() - lastcandyTime > 100050000 && (TimeUtils.nanoTime() - lastBombTime)/2 > 1000000000)
	    	  spawnBombs();
	     
	     //Creates new candy after a certain amount of time
		  if(TimeUtils.nanoTime() - lastcandyTime > (500000000 - (score * 3000000)))
			  spawnCandy();

		  
	      //Iterate through bomb array
	      Iterator<Rectangle> iterbomb = bombsarray.iterator();
	      while(iterbomb.hasNext())
	      {
	    	 Rectangle bomb = iterbomb.next();
	    	 
	    	 if(bomb.y +64 < 0) //If bomb does NOT hit hippo add point, delete
			   	{
			   		iterbomb.remove();  
			   		score++;
			    	yourScoreName = "Score: " + score; 
			    	if (score > highscore)
			    	{
			    	    highscore = score;
			    	    yourHighScoreName = "High Score: " + highscore;
			    	}
			   	}
	    	 else if (bomb.overlaps(hippo))
	    	 {
	    		 iterbomb.remove();  
		   		 state = END_STATE;
	    	 }
			   	
	    	 bomb.y = (float) (bomb.y - ((750 + 2.8*(score +1) ) * Gdx.graphics.getDeltaTime())); //Move n pixels/unit (changes based on score to make it harder:) )
		  
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
	    	  
	    	  candy.y = (float) (candy.y - ((750 + 2.8*(score +1) ) * Gdx.graphics.getDeltaTime())); //Move n pixels/unit (changes based on score to make it harder:) )
	    	  
	    	  //When candy hits hippo get rid of it and add score
	    	  if(candy.overlaps(hippo)) 
			     {
			    	iter.remove();
			    	score++;
			    	yourScoreName = "Score: " + score; 
			    	if (score > highscore)
			    	{
			    	    highscore = score;
			    	    yourHighScoreName = "High Score: " + highscore;
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
			  updatebutton();
			  camera.update();
			  batch.setProjectionMatrix(camera.combined);
				
			  //Draw background
			  batch.setProjectionMatrix(camera.combined);
			  batch.begin();
			  batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			  batch.end();
			  
			  //Draw in hippo icon
			  batch.setProjectionMatrix(camera.combined);
			  batch.begin();
			  batch.draw(icon, Gdx.graphics.getWidth()/2 - playbutton.width / 2, Gdx.graphics.getHeight()/2 - playbutton.height / 2, Gdx.graphics.getWidth() * icon.getWidth() /480, Gdx.graphics.getHeight() * icon.getHeight() /800);
			  batch.end();
			  
			  //Render in Play Box
			  playbutton.width = (int) (Gdx.graphics.getWidth() * playpicture.getWidth() /480);
			  playbutton.height = Gdx.graphics.getHeight() * playpicture.getHeight() /800;
			  playbutton.x = Gdx.graphics.getWidth()/2 - playbutton.width / 2;
			  playbutton.y = Gdx.graphics.getHeight()/3 - playbutton.height / 2;
				
			  //Draw in play button
			  batch.setProjectionMatrix(camera.combined);
			  batch.begin();
			  batch.draw(playpicture, playbutton.x, playbutton.y, Gdx.graphics.getWidth() * playpicture.getWidth() /480, Gdx.graphics.getHeight() * playpicture.getHeight() /800);
			  batch.end();
			  
						  
		  }
		  
		  else if(state == GAME_STATE)
		  {
			  update();

			  //Draw background
			  batch.setProjectionMatrix(camera.combined);
			  batch.begin();
			  batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			  batch.end();
			  
		      //Text Score
		      batch.begin(); 
		      yourBitmapFontName.setColor(1.0f, 0, 0, 1.0f);
		      yourBitmapFontName.draw(batch, yourScoreName, (float)(Gdx.graphics.getWidth() * .05), (float)(Gdx.graphics.getHeight() * .98)); 
		      batch.end();
		      
		      //Render Hippo
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
			      batch.draw(bombpicture, bomb.x, bomb.y, Gdx.graphics.getWidth() * bombpicture.getWidth() /480, Gdx.graphics.getHeight() * bombpicture.getHeight() /800);
		      }
		      batch.end();		      
		     
		  }
		  
		  else if(state == END_STATE)
		  {
			  updateretry();
			  camera.update();
			  batch.setProjectionMatrix(camera.combined);
				
			  //Draw background
			  batch.setProjectionMatrix(camera.combined);
			  batch.begin();
			  batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			  batch.end();
			  
			  //Render in retry Box
			  retrybutton.width = (int) (Gdx.graphics.getWidth() * retrypicture.getWidth() /480);
			  retrybutton.height = Gdx.graphics.getHeight() * retrypicture.getHeight() /800;
			  retrybutton.x = Gdx.graphics.getWidth()/2 - retrybutton.width / 2;
			  retrybutton.y = (float) (Gdx.graphics.getHeight()/1.3 - retrybutton.height / 2);
				
			  //Draw in retry button
			  batch.setProjectionMatrix(camera.combined);
			  batch.begin();
			  batch.draw(retrypicture, retrybutton.x, retrybutton.y, Gdx.graphics.getWidth() * retrypicture.getWidth() /480, Gdx.graphics.getHeight() * retrypicture.getHeight() /800);
			  batch.end();
			  
			  //Draw game over image
			  batch.setProjectionMatrix(camera.combined);
			  batch.begin();
			  batch.draw(gameoverpicture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getWidth());
			  batch.end();
			  
			  //Draw in high score
			  batch.begin();
			  yourBitmapFontName.setColor(1.0f, 0, 0, 1.0f);
			  if(permhighscore > highscore)//Draw correct high score
			  {
				  yourHighScoreName = "High Score: " + permhighscore;
				  yourBitmapFontName.draw(batch, yourHighScoreName, (float)(Gdx.graphics.getWidth() * .05), (float)(Gdx.graphics.getHeight() * .98)); 
			  }
			  else
				  yourBitmapFontName.draw(batch, yourHighScoreName, (float)(Gdx.graphics.getWidth() * .05), (float)(Gdx.graphics.getHeight() * .98)); 
				  
			  batch.end();
			  
		      if(highscore > permhighscore) //Only write if new highscore is higher then old
		      {
			      Preferences prefs = Gdx.app.getPreferences("highscore");
				  prefs.putInteger("highscore", highscore);
				  prefs.flush(); 
		      }
			  //Clear the board
			  candyarray.clear();
			  bombsarray.clear();
			  hippo.x = Gdx.graphics.getWidth()/2 - hippo.width / 2;
			  score = 0;
			  yourScoreName = "Score: " + score;
		  }

	 }

	 @Override
	   public void dispose() {
	      // dispose of all the native resources
	      hippopicture.dispose();
	      candypicture.dispose();
	      bombpicture.dispose();
	      yourBitmapFontName.dispose();
	      batch.dispose();
	      if(highscore > permhighscore) //Only write if new highscore is higher then old
	      {
		      Preferences prefs = Gdx.app.getPreferences("highscore");
			  prefs.putInteger("highscore", highscore);
			  prefs.flush(); 
	      }

	   }

}
