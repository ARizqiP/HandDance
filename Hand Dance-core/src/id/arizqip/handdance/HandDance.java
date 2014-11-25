package id.arizqip.handdance;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

interface HandDanceVoiceInput {
	
}

public class HandDance extends ApplicationAdapter {
	private SpriteBatch batch;
    private BitmapFont font;
    Viewport viewport;
    Camera camera;
    Stage stag;
    float accelX;
    float accelY;
    float accelZ;
    
    @Override
    public void create() {        
        batch = new SpriteBatch();    
        font = new BitmapFont();
        font.setColor(Color.RED);
        camera = new PerspectiveCamera();
        viewport = new FitViewport(480, 800,camera);
        accelX=accelY=accelZ=0;
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void render() {        
         accelX = 10*Gdx.input.getAccelerometerX();
         accelY = 10*Gdx.input.getAccelerometerY();
         accelZ = 10*Gdx.input.getAccelerometerZ();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        font.draw(batch, Math.round(accelX)+","+Math.round(accelY)+","+Math.round(accelZ), 200, 300);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);    	
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
