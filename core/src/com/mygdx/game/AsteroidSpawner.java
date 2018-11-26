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
    private int spawnMax = 5; //Max number of big asteroids to spawn in a wave
    private int toSpawn = spawnMax; //Number of big asteroids left to spawn
    List<Texture> bigModels = new ArrayList<Texture>(); //Asteroids spawn using big models
    List<Texture> medModels = new ArrayList<Texture>(); //Asteroids split into medium models
    List<Texture> smallModels = new ArrayList<Texture>(); //Asteroids split into small models, and can be destroyed
    List<Asteroid> asteroids = new ArrayList<Asteroid>();
    private float currentTime = 0f; //seconds that have passed since game start
    float spawnTime = 1f;   //seconds between asteroids spawning
    private float nextSpawn = currentTime + spawnTime;  //What currentTime must be to spawn next wave


    AsteroidSpawner(String[] b, String[] m, String[] s) { //Paths for small, medium, and big asteroids given
        for(String path : b) { //Each file is added to respective list
            bigModels.add(new Texture(path));
        }
        for(String path : m) {
            medModels.add(new Texture(path));
        }
        for(String path : s) {
            smallModels.add(new Texture(path));
        }
    }

    public void trySpawn() { //Spawns asteroid with random rotation
        currentTime += Gdx.graphics.getDeltaTime(); //advance time
        if (spawnReady() && toSpawn > 0) {
            toSpawn--;
            System.out.println("Asteroid Spawned (" + toSpawn + " left)");
            asteroids.add(new Asteroid(bigModels));
        }
        else if(toSpawn == 0 && asteroidCount() == 0) {
            System.out.println("NEXT WAVE!");
            spawnMax += 2; //more asteroids in next wave
            toSpawn = spawnMax;
            spawnTime /= 1.3f; //Spawn rate of asteroids gets faster
        }

    }

    private boolean spawnReady() {
        if(currentTime > nextSpawn) {
            nextSpawn += spawnTime; //nextSpawn set time is set
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
                getAsteroid(i).destroyFrom(this);
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

    public int asteroidCount() { //Returns the number of Big Asteroids still in scene
        return asteroids.size();
    }
}

class Asteroid {
    private float speed, translateX, translateY, rotation;;
    Sprite sprite;
    private int size; //Determines whether it can be split

    public Asteroid(List<Texture> models) { //Default spawn
        size = 3; //Big asteroid
        Random rand = new Random();
        int index = rand.nextInt(models.size()); //choose sprite randomly
        sprite = new Sprite(models.get(index)); //Assign sprite to asteroid
        speed = (rand.nextFloat() * 3) + 1;   //random speed from 1 to 4
        rotation = rand.nextFloat() * 360;    //direction asteroid will move
        translateX = speed * (float) (Math.cos(Math.toRadians(rotation))); //Set velocity
        translateY = speed * (float) (Math.sin(Math.toRadians(rotation)));

        //Set spawn conditions
        sprite.rotate(rotation);
        sprite.setPosition(rand.nextInt(20),rand.nextInt(20));
        sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
        //sprite.setBounds(); //Collision
    }

    public Asteroid(List<Texture> models, int si, float sp, float x, float y, float rot) { //If spawned from parent asteroid
        size = si;
        if(size == 0) {
            System.out.println("ERROR: Can't spawn asteroid! (size = " + size + ")"); //ERROR
            return;
        }
        Random rand = new Random();
        int index = rand.nextInt(models.size()); //choose sprite randomly
        sprite = new Sprite(models.get(index)); //Assign sprite to asteroid
        speed = sp;
        rotation = rot;
        translateX = speed * (float) (Math.cos(Math.toRadians(rotation))); //Set velocity
        translateY = speed * (float) (Math.sin(Math.toRadians(rotation)));

        //Set spawn conditions
        sprite.rotate(rotation);
        sprite.setPosition(x, y); //Spawns in same position as parent
        sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
        //sprite.setBounds(); //Collision


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

    public void destroyFrom(AsteroidSpawner spawner) { //When hit, asteroid is removed from list
        if(size-1 == 0) {
            spawner.asteroids.remove(this);//Remove asteroid
            //System.out.println("Asteroid Destroyed");
        }
        else //if asteroid can be split
        split(spawner);
    }

    private void split(AsteroidSpawner spawner) { //Split asteroid when hit
        Asteroid a1, a2;
        float speedUp = speed*1.5f;
        float angle = 45f;
        switch(size) {
            case 3: //Big Asteroid
                a1 = new Asteroid(spawner.medModels, size-1, speedUp, sprite.getX(), sprite.getY(), rotation + angle);
                a2 = new Asteroid(spawner.medModels, size-1, speedUp, sprite.getX(), sprite.getY(), rotation - angle);
                break;
            case 2: //Medium Asteroid
                a1 = new Asteroid(spawner.smallModels, size-1, speedUp, sprite.getX(), sprite.getY(), rotation + angle);
                a2 = new Asteroid(spawner.smallModels, size-1, speedUp, sprite.getX(), sprite.getY(), rotation - angle);
                break;
            case 1: //Small Asteroid -- do nothing
            default:
                return;
        }
        spawner.asteroids.remove(this); //Remove after splitting
        spawner.asteroids.add(a1);
        spawner.asteroids.add(a2);
    }

    public int getSize() {
        return size;
    }
}
