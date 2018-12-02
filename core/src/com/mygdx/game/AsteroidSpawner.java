package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.util.Scanner;

public class AsteroidSpawner { //Spawns asteroid in game with random initial direction
    int max = 2; //Max asteroids that will spawn this wave
    int toSpawn = max; //Asteroids left to spawn during wave
    List<Texture> bigModels = new ArrayList<Texture>(); //Asteroids spawn using big models
    List<Texture> medModels = new ArrayList<Texture>(); //Asteroids split into medium models
    List<Texture> smallModels = new ArrayList<Texture>(); //Asteroids split into small models, and can be destroyed
    List<Asteroid> asteroids = new ArrayList<Asteroid>(); //Active asteroids on screen
    List<Body> destroyAsteroids = new ArrayList<Body>(); //Asteroids to be destroyed (when body is removed)
    private float currentTime = 0f; //seconds that have passed since game start
    float spawnWait = 5f;   //seconds between waves
    private float nextSpawn = currentTime + spawnWait;   //spawn asteroids when currentTime is greater
    World physics;


    AsteroidSpawner(String[] b, String[] m, String[] s, World phys) { //Paths for small, medium, and big asteroids given
        for(String path : b) { //Each file is added to respective list
            bigModels.add(new Texture(path));
        }
        for(String path : m) {
            medModels.add(new Texture(path));
        }
        for(String path : s) {
            smallModels.add(new Texture(path));
        }
        physics = phys;
    }

    public void trySpawn() { //Spawns asteroid with random rotation
        currentTime += Gdx.graphics.getDeltaTime(); //advance time
        if (spawnIsReady() && toSpawn > 0) {
            asteroids.add(new Asteroid(bigModels, physics, this));
            toSpawn--;
        }

        else if(toSpawn <= 0 && asteroidCount() == 0) {
            System.out.println("NEXT WAVE!");
            max += 2; //More asteroids will spawn
            toSpawn = max;
            spawnWait *= 0.9f; //Wait time between spawns is shorter

        }
    }

    private boolean spawnIsReady() {
        if(currentTime > nextSpawn) {
            nextSpawn += spawnWait; //nextSpawn set time is set
            destroyAsteroids.clear(); //No memory leaks, please!
            return true;
        }
        else {
            return false;
        }
    }

    public void moveAll() { //Moves asteroid in set direction after spawning
        for(int i = 0; i < asteroidCount(); i++) {
            getAsteroid(i).move();
            if(Gdx.input.isKeyJustPressed(Input.Keys.Z)) { //Press 'Z' to destroy asteroids
                getAsteroid(i).destroyFrom(this);
            }
        }
    }

    public void collisionCheck() {
        physics.setContactListener(new ContactListener() {
            public void beginContact(Contact contact) {
                Body objA = contact.getFixtureA().getBody();
                Body objB = contact.getFixtureB().getBody();
                if (objA.getUserData() == "asteroid") {
                    if(objB.getUserData() == "bullet") { //Asteroid hits bullet
                        destroyAsteroids.add(objA); //Asteroid will be removed next frame
                        //System.out.println("Destroying: " + asteroid.getUserData());
                    }
                    else if(objB.getUserData() == "ship") { //Player hits asteroid
                        destroyAsteroids.add(contact.getFixtureB().getBody()); //Asteroid will be removed next frame
                    }
                }
                else if (objA.getUserData() == "bullet") {
                    if(objB.getUserData() == "asteroid") { //Bullet hits asteroid
                        destroyAsteroids.add(objB);
                        System.out.println("BULLET HIT ASTEROID");
                    }
                }
            }
            @Override
            public void endContact(Contact contact) {


            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {}

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {}
        }); //END setContactListener
    } //END OF checkCollision

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

class Asteroid   {
    //Attributes:
    float speed, translateX, translateY, rotation;
    int size; //Determines whether it can be split
    AsteroidSpawner spawner;

    //Sprites:
    SpriteBatch batch;
    Sprite sprite;

    //Hitbox:
    World physics;
    PolygonShape hitbox;
    Body body;
    Box2DDebugRenderer renderer;
    public Asteroid(List<Texture> models, World phys, AsteroidSpawner a) { //Default spawn
        spawner = a;
        size = 3; //Big asteroid
        batch = new SpriteBatch();
        physics = phys;
        Random rand = new Random();

        int index = rand.nextInt(models.size()); //choose sprite randomly
        sprite = new Sprite(models.get(index)); //Assign sprite to asteroid
        speed = (rand.nextFloat() * 60) + 20;   //random speed from 20 to 80
        rotation = rand.nextFloat() * 360;    //direction asteroid will move
        translateX = speed * (float) (Math.cos(Math.toRadians(rotation))); //Set velocity
        translateY = speed * (float) (Math.sin(Math.toRadians(rotation)));

        //Set spawn conditions
        sprite.rotate(rotation);
        int x = rand.nextInt(20);
        int y = rand.nextInt(20);
        sprite.setPosition(x,y);
        sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
        setHitBox();
        hitbox.dispose();
    }

    private Asteroid(List<Texture> models, int si, float sp, float x, float y, float rot, World phys, AsteroidSpawner a) { //If spawned from parent asteroid
        spawner = a;
        physics = phys;
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

        setHitBox();
        hitbox.dispose();
    }

    public void move() {
        if(asteroidRemoved()) {
            this.destroyFrom(spawner);
            return;
        }
        sprite.setPosition((body.getPosition().x * 100) - ( sprite.getWidth()/2),
                (body.getPosition().y * 100) - ( sprite.getHeight()/2 ));
        sprite.setRotation((float) Math.toDegrees((body.getAngle())));

    }

    private boolean asteroidRemoved() { //Checks if this asteroid should be removed
        if(spawner.destroyAsteroids.contains(body)) {
            System.out.println("ASTEROID DESTROYED");
            spawner.destroyAsteroids.remove(body);
            return true;
        }
        else return false;
    }

    public void destroyFrom(AsteroidSpawner a) { //When hit, asteroid is removed from list
        if(size-1 == 0) {
            //hitbox.dispose();
            a.asteroids.remove(this);//Remove asteroid
            physics.destroyBody(body);

            //System.out.println("Asteroid Destroyed");
        }
        else //if asteroid can be split
        {
            split();
            physics.destroyBody(body);


        }
    }

    private void split() { //Split asteroid when hit
        Asteroid a1, a2;
        float speedUp = speed*0.3f;
        float angle = 45f;
        switch(size) {
            case 3: //Big Asteroid
                a1 = new Asteroid(spawner.medModels, size-1, speedUp, sprite.getX(), sprite.getY(), rotation + angle, physics, spawner);
                a2 = new Asteroid(spawner.medModels, size-1, speedUp, sprite.getX(), sprite.getY(), rotation - angle, physics, spawner);
                break;
            case 2: //Medium Asteroid
                a1 = new Asteroid(spawner.smallModels, size-1, speedUp, sprite.getX(), sprite.getY(), rotation + angle, physics, spawner);
                a2 = new Asteroid(spawner.smallModels, size-1, speedUp, sprite.getX(), sprite.getY(), rotation - angle, physics, spawner);
                break;
            case 1: //Small Asteroid -- do nothing
            default:
                return;
        }
        spawner.asteroids.add(a1);
        spawner.asteroids.add(a2);
        spawner.asteroids.remove(this); //Remove after splitting

    }



    private void setHitBox() { //Set up Hitbox for this asteroid using Box2D methods
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(((sprite.getX() + sprite.getWidth()/2)/100),
                ( (sprite.getY() + sprite.getHeight()/2)/100) );
        body = physics.createBody(bodyDef);
        hitbox = new PolygonShape();
        hitbox.setAsBox((sprite.getWidth()/2) / 100,
                (sprite.getHeight()/2)/100);
        FixtureDef fixDefast = new FixtureDef();
        fixDefast.filter.groupIndex = -1;
        fixDefast.shape = hitbox;
        fixDefast.density = 0.1f;
        fixDefast.friction = 0f;
        fixDefast.isSensor = true;
        body.createFixture(fixDefast);
        body.setUserData("asteroid");
        //System.out.println("Apply Force: (" + translateX + ", " + translateY + ")");
        body.applyForceToCenter(translateX,translateY+3,true);
        body.applyTorque(0.09f,true);
        body.setLinearDamping(0);
    }
}
