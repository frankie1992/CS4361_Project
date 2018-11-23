package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
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
	Sound laserSound;
	Sound shipSound;

	@Override
	public void create () {
		batch = new SpriteBatch();
		playerModel = new Texture("Player/shipTest.png");
		shipSound = Gdx.audio.newSound(Gdx.files.internal("Player/ship_move2.wav"));
		bulletModel = new Texture("Laser/Bullet.png");
		laserSound = Gdx.audio.newSound(Gdx.files.internal("Laser/laser_sound2.wav"));
		playerSprite = new Sprite(playerModel);
		playerSprite.setPosition(Gdx.graphics.getWidth() / 2 - playerSprite.getWidth()/2, Gdx.graphics.getHeight()/2 - playerSprite.getHeight()/2);
		playerSprite.setOrigin(playerSprite.getWidth()/2,playerSprite.getHeight()/2);
		input = new PlayerInput();
		wrapScreen = new WrapEffect();

		//Asteroid:
		String[] bigAsteroid = {"Asteroids/Big/Asteroid_Big1.png", "Asteroids/Big/Asteroid_Big2.png"};
		String[] medAsteroid = {"Asteroids/Med/Asteroid_Med1.png","Asteroids/Med/Asteroid_Med2.png"};
		String[] smallAsteroid = {"Asteroids/Small/Asteroid_Small1.png","Asteroids/Small/Asteroid_Small2.png"};
		spawner = new AsteroidSpawner(bigAsteroid, medAsteroid, smallAsteroid);
		//spawner.spawn();
	}

	@Override
	public void render () {
		spawner.trySpawn();
		bullet = input.keyInput(playerSprite, bulletModel, laserSound, shipSound);

		//playerSprite.setBounds();
		
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
		bulletModel.dispose();
		laserSound.dispose();
		shipSound.dispose();
	}
}
