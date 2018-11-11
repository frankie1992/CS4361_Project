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
import java.util.ArrayList;
import java.util.List;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture playerModel;
	Texture bulletModel;
	Sprite playerSprite;
	PlayerInput input;
	WrapEffect wrapScreen;
	AsteroidSpawner spawner;
	Bullet bullet;
	List<Bullet> bullets = new ArrayList<>();
	List<Bullet> deletebullets = new ArrayList<>();

	@Override
	public void create () {
		batch = new SpriteBatch();
		playerModel = new Texture("Player/shipTest.png");
		bulletModel = new Texture("Player/shipTest.png");
		playerSprite = new Sprite(playerModel);
		playerSprite.setPosition(Gdx.graphics.getWidth() / 2 - playerSprite.getWidth()/2, Gdx.graphics.getHeight()/2 - playerSprite.getHeight()/2);
		playerSprite.setOrigin(playerSprite.getWidth()/2,playerSprite.getHeight()/2);
		input = new PlayerInput();
		wrapScreen = new WrapEffect();

		//Asteroid:
		spawner = new AsteroidSpawner("Asteroids/Asteroid(Test).png", "Asteroids/AsteroidSmall(Test).png");
		//spawner.spawn();
	}

	@Override
	public void render () {
		spawner.spawn();
		bullet = input.keyInput(playerSprite, bulletModel);
		if(bullet != null){
			bullets.add(bullet);
		}

		wrapScreen.wrapScreen(playerSprite);
		spawner.moveAll();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		playerSprite.draw(batch);
		for(Bullet bulletlist: bullets){
			bulletlist.bulletSprite.draw(batch);
			bulletlist.updateBullet();
			wrapScreen.wrapScreen(bulletlist.bulletSprite);
			if(bulletlist.time > 1.76)
				deletebullets.add(bulletlist);

		}
		bullets.removeAll(deletebullets);
		for(int i = 0; i < spawner.asteroidCount(); i++) { //Draw each asteroid currently spawned
			spawner.getAsteroid(i).sprite.draw(batch);
		}
		batch.end();
	}
	

	@Override
	public void dispose () {
		batch.dispose();
		playerModel.dispose();
	}
}
