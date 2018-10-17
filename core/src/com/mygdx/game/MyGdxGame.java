package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.physics.box2d.Box2D;

public class MyGdxGame extends ApplicationAdapter {
	OrthographicCamera camera;
	ExtendViewport viewport;
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		Box2D.init();
		batch = new SpriteBatch();
		img = new Texture("still_ship.gif");
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(800, 600, camera);

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
