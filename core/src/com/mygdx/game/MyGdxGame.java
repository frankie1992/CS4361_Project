package com.mygdx.game;



import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class MyGdxGame extends Game {

	static Skin skin;
 	@Override

	public void create () {
		 skin = new Skin(Gdx.files.internal("uiskin.json"));
	     this.setScreen(new MainMenu(this));
 		//asteroidGame.create();
	}

	@Override
	public void render () {
	//	asteroidGame.render();
		super.render();

	}
	

	@Override
	public void dispose () {
	//	asteroidGame.dispose();
		super.dispose();

 	}
}
