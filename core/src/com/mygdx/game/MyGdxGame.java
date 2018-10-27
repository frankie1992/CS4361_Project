package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.PlayerInput;
import com.mygdx.game.WrapEffect;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture playerModel;
	Sprite playerSprite;
	PlayerInput input;
	WrapEffect wrapScreen;
	@Override
	public void create () {
		batch = new SpriteBatch();
 		playerModel = new Texture("Player/shipTest.png");
		playerSprite = new Sprite(playerModel);
		playerSprite.setPosition(Gdx.graphics.getWidth() / 2 - playerSprite.getWidth()/2, Gdx.graphics.getHeight()/2 - playerSprite.getHeight()/2);
		playerSprite.setOrigin(playerSprite.getWidth()/2,playerSprite.getHeight()/2);
		input = new PlayerInput();
		wrapScreen = new WrapEffect();
	}

	@Override
	public void render () {
		input.keyInput(playerSprite);
		wrapScreen.wrapScreen(playerSprite);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		playerSprite.draw(batch);
		batch.end();

	}





	
	@Override
	public void dispose () {
		batch.dispose();
		playerModel.dispose();

	}
}
