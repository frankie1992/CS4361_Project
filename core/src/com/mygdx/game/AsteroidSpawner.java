package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class AsteroidSpawner { //Spawns asteroid in game with random initial direction
    Random rand;
    int max = 3; //maximum amount of asteroids that will be spawned
    Texture[] models = new Texture[2]; //Array of possible textures to use for spawned asteroids
    Asteroid[] asteroidArray = new Asteroid[max];
    List<Asteroid> asteroids = new ArrayList<>();
    float currentTime = 0f; //seconds that have passed since game start
    float nextSpawn = 5f;   //spawn asteroids when currentTime is greater
    float spawnWait = 5f;   //seconds between waves


    AsteroidSpawner(String m1, String m2) { //Internal path for texture given
        models[0] = new Texture(m1);
        models[1] = new Texture(m2);
    }

    public void trySpawn() { //Spawns asteroid with random rotation
        currentTime += Gdx.graphics.getDeltaTime(); //advance time
        if (waveIsReady() && asteroidCount() < max) {
            asteroids.add(new Asteroid(models));
        }
    }

    private boolean waveIsReady() {
        if(currentTime > nextSpawn) {
            nextSpawn += spawnWait; //nextSpawn set time is set
            //spawnWait /= 2; //time between waves decreases
            System.out.println("Next Wave! (@ " + currentTime + " sec)");
            return true;
        }
        else {
            return false;
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
    float speed, translateX, translateY, rotation;;
    Texture bigAsteroid;
    Texture smallAsteroid;
    Sprite sprite;
    int size; //Determines whether it can be split



    public Asteroid(Texture[] t) { //Spawn asteroid with default size
        this(2, t);
    }

    public Asteroid(int si, Texture[] t) {
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
        Random rand = new Random();
        speed = (rand.nextFloat() * 3) + 1;   //random speed from 1 to 4
        rotation = rand.nextFloat() * 360;    //direction asteroid will move
        translateX = speed * (float) (Math.cos(Math.toRadians(rotation))); //Set velocity
        translateY = speed * (float) (Math.sin(Math.toRadians(rotation)));

        //Set spawn conditions
        sprite.rotate(rotation);
        sprite.setPosition(rand.nextInt(20),rand.nextInt(20));
        sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
        sprite.setBounds(); //Collision
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
            asteroids.add(new Asteroid(size-1, t));
            asteroids.add(new Asteroid(size-1, t));
            asteroids.remove(this);
        }
    }
}
