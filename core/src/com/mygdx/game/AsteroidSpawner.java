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
    //int asteroidCount=0;
    int max = 3; //maximum amount of asteroids that will be spawned
    Texture[] models = new Texture[2]; //Array of possible textures to use for spawned asteroids
    Asteroid[] asteroidArray = new Asteroid[max];
    List<Asteroid> asteroids = new ArrayList<>();


    AsteroidSpawner(String m1, String m2) { //Internal path for texture given
        models[0] = new Texture(m1);
        models[1] = new Texture(m2);
    }

    public void spawn() { //Spawns asteroid with random rotation
        if (asteroidCount() < max) {
            rand = new Random();
            float speed = (rand.nextFloat() * 3) + 1;   //speed is random float between 1 and 4
            float rotation = rand.nextFloat() * 360;    //direction asteroid will move
            float translateX = (float) (Math.cos(Math.toRadians(rotation))); //Set velocity
            float translateY = (float) (Math.sin(Math.toRadians(rotation)));
            //asteroidArray[asteroidCount] = new Asteroid(models[0], speed, translateX, translateY, rotation);
            asteroids.add(new Asteroid(models, speed, translateX, translateY, rotation));
            //asteroidCount++;
        }
    }

    public void moveAll() { //Moves asteroid in set direction after spawning
        for(int i = 0; i < asteroidCount(); i++) {
            getAsteroid(i).move();
            if(Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
                getAsteroid(i).destroyFrom(asteroids);
            }
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

    public int asteroidCount() {return asteroids.size();}
}

class Asteroid {
    float speed, translateX, translateY;;

    Texture bigAsteroid;
    Texture smallAsteroid;
    Sprite sprite;
    int size; //Default size of asteroid



    public Asteroid(Texture[] t, float sp, float x, float y, float angle) { //Spawn asteroid with default size
        this(2, t, sp, x, y, angle);
    }

    public Asteroid(int si, Texture[] t, float sp, float x, float y, float angle) { //Asteroid size is small if spawned by split
        size = si;
        bigAsteroid = t[0];
        smallAsteroid = t[1];
        if (size == 2) { //Default size for initial spawn
            sprite = new Sprite(bigAsteroid);
        }
        else if (size == 1) { //Size after splitting
            System.out.println("NEW SMALL ASTEROID");
            sprite = new Sprite(smallAsteroid);
        }
        else System.out.println("ERROR: Can't spawn asteroid! (size = " + size + ")");
        speed = sp;
        translateX = speed * x;
        translateY = speed * y;

        //Set spawn conditions
        sprite.rotate(angle);
        sprite.setPosition(10,10);
        sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);

        sprite.setBounds();
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

    public void destroyFrom(List<Asteroid> asteroids) { //When hit, asteroid is removed from list
        if(size-1 == 0) {
            asteroids.remove(this);//Remove asteroid
            //System.out.println("Asteroid Destroyed");
        }
        else //if asteroid can be split
        split(asteroids);
    }

    public void split(List<Asteroid> asteroids) { //Split asteroid when hit
        if (size > 0) {
            Texture[] t = {bigAsteroid, smallAsteroid};
            asteroids.add(new Asteroid((size-1),t, speed*1.2f, translateX*-1,translateY*1, 1));
            asteroids.add(new Asteroid((size-1),t, speed*1.2f, translateX*1,translateY*-1, 1));
            asteroids.remove(this);
        }
    }
}
