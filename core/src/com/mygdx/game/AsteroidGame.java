
package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.PlayerInput;
import com.mygdx.game.WrapEffect;
import java.util.ArrayList;
import java.util.List;

public class AsteroidGame  extends ApplicationAdapter implements Screen {
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
    Body shipBody;
    World worldPhysics;
    PolygonShape shipHitbox;
     Box2DDebugRenderer renderer;
    Matrix4 dm;


    public   AsteroidGame  () {
        create();
     }

     public void create()
     {
         batch = new SpriteBatch();
            renderer = new Box2DDebugRenderer();
         playerModel = new Texture("Player/shipTest.png");
         shipSound = Gdx.audio.newSound(Gdx.files.internal("Player/ship_move2.wav"));
         bulletModel = new Texture("Laser/Bullet.png");
         laserSound = Gdx.audio.newSound(Gdx.files.internal("Laser/laser_sound2.wav"));
         playerSprite = new Sprite(playerModel);
         playerSprite.setPosition(Gdx.graphics.getWidth() / 2 - playerSprite.getWidth()/2, Gdx.graphics.getHeight()/2 - playerSprite.getHeight()/2);
         input = new PlayerInput();
         wrapScreen = new WrapEffect();


         // Physcis sections
         // gravity
         worldPhysics = new World(new Vector2(0f,0f),true );
         BodyDef bodydefination = new BodyDef();
         bodydefination.type = BodyDef.BodyType.DynamicBody;
         bodydefination.position.set((playerSprite.getX() + playerSprite.getWidth()/2)/100,
                 (playerSprite.getY() + playerSprite.getHeight()/2)/100 );
         shipBody = worldPhysics.createBody(bodydefination);
         shipBody.setLinearDamping(1);
         shipBody.setAngularDamping(1);

         shipHitbox = new PolygonShape();
         shipHitbox.setAsBox((playerSprite.getWidth()/2) / 100,
                 (playerSprite.getHeight()/2)/100);
         FixtureDef fixDef = new FixtureDef();
         fixDef.shape = shipHitbox;
         fixDef.density = 0.1f;
         fixDef.friction = 10f;


         shipBody.createFixture(fixDef);

         //Asteroid:
         String[] bigAsteroid = {"Asteroids/Big/Asteroid_Big1.png", "Asteroids/Big/Asteroid_Big2.png"};
         String[] medAsteroid = {"Asteroids/Med/Asteroid_Med1.png","Asteroids/Med/Asteroid_Med2.png"};
         String[] smallAsteroid = {"Asteroids/Small/Asteroid_Small1.png","Asteroids/Small/Asteroid_Small2.png"};
         spawner = new AsteroidSpawner(bigAsteroid, medAsteroid, smallAsteroid);

         shipHitbox.dispose();
     }
    public void render () {
        wrapScreen.wrapScreen(playerSprite , shipBody);

        worldPhysics.step(1f/150f , 1, 1);
        wrapScreen.wrapScreen(playerSprite , shipBody);

        playerSprite.setPosition((shipBody.getPosition().x * 100) - ( playerSprite.getWidth()/2 ),
                (shipBody.getPosition().y * 100) - ( playerSprite.getHeight()/2 ));
        playerSprite.setRotation((float) Math.toDegrees((shipBody.getAngle())));

        spawner.trySpawn();

        bullet = input.keyInput(playerSprite, bulletModel, laserSound, shipSound, shipBody);

        //playerSprite.setBounds();

        if(bullet != null){
            bullets.add(bullet);
        }


        spawner.moveAll();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        dm = batch.getProjectionMatrix().cpy().scale(100f,100f,0);
        create();
        batch.begin();


        playerSprite.draw(batch);
        wrapScreen.wrapScreen(playerSprite , shipBody);

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
        wrapScreen.wrapScreen(playerSprite , shipBody);

        batch.end();
        renderer.render(worldPhysics,dm);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        worldPhysics.step(1f/150f , 6, 2);
        wrapScreen.wrapScreen(playerSprite , shipBody);

       playerSprite.setPosition((shipBody.getPosition().x * 100) - ( playerSprite.getWidth()/2 ),
               (shipBody.getPosition().y * 100) - ( playerSprite.getHeight()/2 ));
        playerSprite.setRotation((float) Math.toDegrees((shipBody.getAngle())));

        spawner.trySpawn();

       bullet = input.keyInput(playerSprite, bulletModel, laserSound, shipSound, shipBody);

        //playerSprite.setBounds();

        if(bullet != null){
            bullets.add(bullet);
        }


        spawner.moveAll();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        dm = batch.getProjectionMatrix().cpy().scale(100f,100f,0);
        batch.begin();


          playerSprite.draw(batch);
        wrapScreen.wrapScreen(playerSprite , shipBody);

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
        wrapScreen.wrapScreen(playerSprite , shipBody);

        batch.end();
        renderer.render(worldPhysics,dm);
        wrapScreen.wrapScreen(playerSprite , shipBody);

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        wrapScreen.wrapScreen(playerSprite , shipBody);

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose () {
        batch.dispose();
        playerModel.dispose();
        bulletModel.dispose();
        laserSound.dispose();
        shipSound.dispose();
        worldPhysics.dispose();

    }
}