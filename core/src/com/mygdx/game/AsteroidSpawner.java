package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class AsteroidSpawner { //Spawns asteroid in game with random initial direction
    Random rand;
    int asteroidCount=0;
    int max = 3; //maximum amount of asteroids that will be spawned
    Texture[] models = new Texture[1]; //Array of possible textures to use for spawned asteroids
    Asteroid[] asteroidArray = new Asteroid[max];
    List<Asteroid> asteroids = new ArrayList<>();


    AsteroidSpawner(String path) { //Internal path for texture given
        for (int i = 0; i < models.length; i++) { //
            models[i] = new Texture(path);
        }
    }

    public void spawn() { //Spawns asteroid with random rotation
        if (asteroidCount < max) {
            rand = new Random();
            float speed = (rand.nextFloat() * 3) + 1;   //speed is random float between 1 and 4
            float rotation = rand.nextFloat() * 360;    //direction asteroid will move
            float translateX = (float) (speed * Math.cos(Math.toRadians(rotation))); //Set velocity
            float translateY = (float) (speed * Math.sin(Math.toRadians(rotation)));
            //asteroidArray[asteroidCount] = new Asteroid(models[0], speed, translateX, translateY, rotation);
            asteroids.add(new Asteroid(models[0], speed, translateX, translateY, rotation));
            asteroidCount++;
        }
    }

    public void moveAll() { //Moves asteroid in set direction after spawning
//        for(int i = 0; i < asteroidCount; i++) {
//            asteroidArray[i].move();
//        }
        for(int i = 0; i < asteroidCount; i++) {
            getAsteroid(i).move();
        }
    }

    public Asteroid getAsteroid(int i) { //Returns asteroid of given index, if not null
        Asteroid a = asteroids.get(i);
        if (a != null) {
            return a;
        }
        else {
            System.out.println("ERROR: Cannot get asteroid [" + i + "]");
            return null;
        }
    }
}

class Asteroid {
    float speed, translateX, translateY;;

    //Texture model;
    Sprite sprite;
    int size = 3; //Default size of asteroid

    private Asteroid(int si, Texture t, float s, float x, float y, float angle) { //Set size when spawning asteroid
        this(t, s, x, y, angle);
        size = si;
        System.out.println("Asteroid Size = " + size);
    }

    public Asteroid(Texture t, float s, float x, float y, float angle) { //Spawn asteroid with default size
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

    private void wrapScreen()
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

    public void split() { //Split asteroid when hit
        if (size > 0) {
            //Spawn new asteroid
        }
    }
}
