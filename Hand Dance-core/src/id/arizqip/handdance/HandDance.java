package id.arizqip.handdance;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Random;

interface HandDanceVoiceInput {
	
}

public class HandDance extends ApplicationAdapter {
	private SpriteBatch batch;
    private BitmapFont font;
    Viewport viewport;
    Camera camera;
    Stage stage;
    float accelX;
    float accelY;
    float accelZ;
    float rot;
    String mesg;
    float time=0, timeElapsed=0;
    int lastPressed,nowPressed;
    int speed = 200; //pixel per second
    ArrayList<ArrowSprite> spriteList = new ArrayList<ArrowSprite>();
    ArrayList<ArrowSprite> toDelete = new ArrayList<ArrowSprite>();
    Texture arrowtextures[] = new Texture[4];
    Sprite[] spr = new Sprite[4];
	Sprite background;
    
    @Override
    public void create() {    
        batch = new SpriteBatch();    
        font = new BitmapFont();
        font.setColor(Color.RED);
        camera = new PerspectiveCamera();
        viewport = new FitViewport(480, 800,camera);
        stage = new Stage(viewport);
        accelX=accelY=accelZ=0;
        arrowtextures[0] = new Texture(Gdx.files.internal("basicgame/arrow_left.png"));
        arrowtextures[1] = new Texture(Gdx.files.internal("basicgame/arrow_up.png"));
        arrowtextures[2] = new Texture(Gdx.files.internal("basicgame/arrow_down.png"));
        arrowtextures[3] = new Texture(Gdx.files.internal("basicgame/arrow_right.png"));
        background = new Sprite( new Texture(Gdx.files.internal("basicgame/background.png")));
        generateSequence();
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        for(int a = 0;a<4;a++){
        	spr[a] = new Sprite(arrowtextures[a]);
        	
        	spr[a].setPosition(w/2 -spr[a].getWidth()/2, h/2 - spr[a].getHeight()/2);
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void render() {        
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        
        float delta = Gdx.graphics.getDeltaTime();
        time += delta;

        checkPressed();
        batch.begin();
        
        background.draw(batch);
        
        //arrows going down
        for(ArrowSprite arrow:spriteList){
        	arrow.moveDown(delta/2);
        }
        
        //draw arrows. only in screen 
        for(int a=0;a<spriteList.size()&&spriteList.get(a).curPos<Gdx.graphics.getHeight();a++){
        	ArrowSprite ar = spriteList.get(a);
        	ar.setX(Gdx.graphics.getWidth()/2-ar.getWidth()/2);
        	ar.draw(batch);
        }

        //actions. need to differentiate hold and press, later.
        int justPressed=0;
        if(lastPressed!=nowPressed)justPressed=nowPressed;
        ArrowSprite nextArrow = spriteList.get(0);
        if(justPressed==nextArrow.dir){
        	//perfect
        	if(100<=nextArrow.curPos&&nextArrow.curPos<=300){
        		mesg = "Hit";
        		spriteList.remove(nextArrow);
        	}
        	//good
        	//bad
        }
        
        //remove arrows out
        for(ArrowSprite arrow:spriteList){
        	if(arrow.curPos<(-arrow.getHeight())){
        		toDelete.add(arrow);
        	}
        }
        for(ArrowSprite arrow:toDelete){
        	mesg="Miss";
        	spriteList.remove(arrow);
        }
        toDelete.clear();
        
        font.drawMultiLine(batch, "" + mesg, 50, 700);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    	stage.getViewport().update(width,height);   
    	
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    public void checkPressed(){
        lastPressed = nowPressed;
        //keyboard OR motion
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            nowPressed=1;
            return;
        } 
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            nowPressed=2;
            return;
        } 
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            nowPressed=3;
            return;
        } 
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            nowPressed=4;
            return;
        } 
            nowPressed=0;
            return;
    }

    private void generateSequence(){
        //Dummy, to be replaced with real parser
        Random rnd = new Random();
        for (int a=1; a<100; a++) {
            int initPos = a*1000;
            int dir = rnd.nextInt(4)+1;
            spriteList.add(new ArrowSprite(initPos,dir,arrowtextures));
        }
    }
    
    class ArrowSprite extends Sprite{
    	int dir;
    	float curPos;
    	boolean out=false;
    	
    	public ArrowSprite(float initPos, int dir, Texture[] arrowTextures){
			super(arrowTextures[dir-1]);
			this.dir=dir;
			curPos=initPos;
			this.setPosition(Gdx.graphics.getWidth()/2-this.getWidth()/2,curPos);
    	}
    	
    	public void moveDown(float deltaTime){
    		curPos -= 1000 * deltaTime;
			this.setPosition(Gdx.graphics.getWidth()/2-this.getWidth()/2,curPos);
    	}
    }
}