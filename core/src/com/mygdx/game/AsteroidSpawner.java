package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import java.util.Random;
import java.util.LinkedList;

public class AsteroidSpawner { //Spawns asteroid in game with random initial direction
    Random rand;
    int asteroidCount=0;
    int max = 5;
    Texture[] models = new Texture[1]; //Array of possible textures to use for spawned asteroids
    Asteroid[] asteroidArray = new Asteroid[max];


    AsteroidSpawner(String path) { //Internal path for texture given
        for (int i = 0; i < models.length; i++) { //
            models[i] = new Texture(path);
        }
    }

    public void spawn() { //Spawns asteroid with random rotation
        if (asteroidCount < max) {
            rand = new Random();
            float speed = (rand.nextFloat() * 3) + 1; //speed is random float between 1 and 4
            float rotation = rand.nextFloat() * 360;
            float translateX = (float) (speed * Math.cos(Math.toRadians(rotation))); //Set velocity
            float translateY = (float) (speed * Math.sin(Math.toRadians(rotation)));
            //asteroidArray[asteroidCount] = new Asteroid(spriteList[0], speed, translateX, translateY, rotation);
            asteroidArray[asteroidCount] = new Asteroid(models[0], speed, translateX, translateY, rotation);
            asteroidCount++;
        }
    }

    public void moveAll() { //Moves asteroid in set direction after spawning
        for(int i = 0; i < asteroidCount; i++) {
            asteroidArray[i].move();
        }
    }
}

class Asteroid {
    float speed, translateX, translateY;;
    //Texture model;
    Sprite sprite;
    Asteroid(Texture t, float s, float x, float y, float angle) {
        //Set Attributes
        //asteroidModel = t;
        sprite = new Sprite(t); //Sprite is spawned in game with given texture
        speed = s;
        translateX = x;
        translateY = y;

        //Set spawn conditions
        sprite.rotate(angle);
        sprite.setPosition(10,10);
        sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
    }

    public void move() {
        sprite.translate(translateX, translateY);
        wrapScreen();
    }

    public void wrapScreen()
    {
        // Left bounds limit
        if (sprite.getX()<= -50 )
        {
            sprite.translateX(Gdx.graphics.getWidth());

        }

        // Right bounds limit
        if (sprite.getX() >= Gdx.app.getGraphics().getWidth())
        {
            sprite.translateX(-Gdx.app.getGraphics().getWidth());
        }

        // Botton bounds limit
        if (sprite.getY()<= -50  )
        {
            sprite.translateY(Gdx.graphics.getHeight());

        }

        // Top bounds limit
        if (sprite.getY() >= Gdx.app.getGraphics().getHeight())
        {
            sprite.translateY(-Gdx.app.getGraphics().getHeight());
        }

    }
}
