
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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.PlayerInput;
import com.mygdx.game.WrapEffect;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AsteroidGame  extends ApplicationAdapter implements Screen {
    SpriteBatch batch;
    Texture playerModel;
    Texture bulletModel;
    Sprite playerSprite;
    PlayerInput input;
    WrapEffect wrapScreen;
    Bullet bullet;
    List<Bullet> bullets = new ArrayList<>();
    List<Bullet> deletebullets = new ArrayList<>();
    Sound laserSound;
    Sound shipSound;
    Sprite bSprite;
    Body shipBody;
    World worldPhysics;
    PolygonShape shipHitbox;
     Box2DDebugRenderer renderer;
    Matrix4 dm;
    Body bulletBody;
    PolygonShape bulletHitbox;
    float speed, translateX, translateY, rotation;
    World worldphysics;



    Skin skin;
    int gameScore = 0;
    int gameLives = 3;
    Table table;

    //Asteroid:
    Sprite sprite;
    Texture astroid;
    PolygonShape astoridhitbox;
    Body astoridBody;
    Sprite sprite1;
    Texture astroid1;
    PolygonShape astoridhitbox1;
    Body astoridBod1y;
    Sprite sprite2;
    Texture astroid2;
    PolygonShape astoridhitbox2;
    Body astoridBod12;
    Sprite sprite3;
    Texture astroid3;
    PolygonShape astoridhitbox3;
    Body astoridBod13;
    Sprite sprite4;
    Texture astroid4;
    PolygonShape astoridhitbox4;
    Body astoridBod14;
    Sprite sprite5;
    Texture astroid5;
    PolygonShape astoridhitbox5;
    Body astoridBod15;

     AsteroidSpawner spawner;
     String[] bigAsteroid = {"Asteroids/Big/Asteroid_Big1.png", "Asteroids/Big/Asteroid_Big2.png"};
     String[] medAsteroid = {"Asteroids/Med/Asteroid_Med1.png","Asteroids/Med/Asteroid_Med2.png"};
     String[] smallAsteroid = {"Asteroids/Small/Asteroid_Small1.png","Asteroids/Small/Asteroid_Small2.png"};

    public   AsteroidGame  () {
        create();
     }

     public void create()
     {
         skin = new Skin(Gdx.files.internal("uiskin.json"));
         table = new Table();

         Label score = new Label("Score: " + gameScore,skin ,"default");
         Label lives = new Label("Lives: " + gameLives,skin ,"default");
         score.setWidth(200);
         score.setHeight(100);
         lives.setWidth(200);
         lives.setHeight(100);
         table.add(score);
         table.row();
         table.add(lives);
         table.setPosition(100,800);


         batch = new SpriteBatch();
         renderer = new Box2DDebugRenderer();
         playerModel = new Texture("Player/shipTest.png");
         astroid = new Texture("Asteroids/Small/Asteroid_Small2.png");
         astroid1 = new Texture("Asteroids/Big/Asteroid_Big2.png");
         astroid2 = new Texture("Asteroids/Med/Asteroid_Med2.png");
         astroid3 = new Texture("Asteroids/Big/Asteroid_Big1.png");
         astroid4 = new Texture("Asteroids/Big/Asteroid_Big1.png");
         // Issue with render
         astroid5 = new Texture("Asteroids/Big/Asteroid_Big1.png");
         shipSound = Gdx.audio.newSound(Gdx.files.internal("Player/ship_move2.wav"));
         bulletModel = new Texture("Laser/Bullet.png");
         laserSound = Gdx.audio.newSound(Gdx.files.internal("Laser/laser_sound2.wav"));
         playerSprite = new Sprite(playerModel);

         sprite = new Sprite(astroid);
         sprite1 = new Sprite(astroid1);
         sprite2 = new Sprite(astroid2);
                 sprite3 = new Sprite(astroid3);
         sprite4 = new Sprite(astroid4);
                 sprite5 = new Sprite(astroid5);



                 // rand spawn
         Random rand = new Random();
         int x = rand.nextInt(50);
         int y = rand.nextInt(50);
         ///


         sprite.setPosition(600,250);
         sprite1.setPosition(200, 300);
         sprite1.setPosition(50, 100);
         sprite3.setPosition(700 ,600);
         sprite4.setPosition(500,1000);
         sprite5.setPosition(300,800);

         playerSprite.setPosition(Gdx.graphics.getWidth() / 2 - playerSprite.getWidth()/2, Gdx.graphics.getHeight()/2 - playerSprite.getHeight()/2);
         input = new PlayerInput();
         wrapScreen = new WrapEffect();


         // Physcis sections
         // World phyiscs
         worldPhysics = new World(new Vector2(0f,0f),true );

         // Ship
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

         ////////////////////////////////////////////////////////////////////////////////////////////////////////
         ///////////////////////////////    ASTEROID SPAWNER    /////////////////////////////////////////////////
         ////////////////////////////////////////////////////////////////////////////////////////////////////////
         spawner = new AsteroidSpawner(bigAsteroid, medAsteroid, smallAsteroid, worldPhysics);
         // rocks
         renderer = new Box2DDebugRenderer();

         /******************************************
         //Rock 1
         BodyDef bodyDef = new BodyDef();
         bodyDef.type = BodyDef.BodyType.DynamicBody;
         bodyDef.position.set(((sprite.getX() + sprite.getWidth()/2)/100),
                 ( (sprite.getY() + sprite.getHeight()/2)/100) );
         astoridBody = worldPhysics.createBody(bodyDef);
         astoridhitbox = new PolygonShape();
         astoridhitbox.setAsBox((sprite.getWidth()/2) / 100,
                 (sprite.getHeight()/2)/100);
         FixtureDef fixDefast = new FixtureDef();
         fixDefast.filter.categoryBits = 0x0002;
         fixDefast.filter.groupIndex = -1;

         fixDefast.shape = astoridhitbox;
         fixDefast.density = 0.1f;
         fixDefast.friction = 10f;
         astoridBody.createFixture(fixDefast);

         speed = (rand.nextFloat() * 3) + 1;   //random speed from 1 to 4
         rotation = rand.nextFloat() * 25;    //direction asteroid will move
         translateX = speed * (float) (Math.cos(Math.toRadians(rotation))); //Set velocity
         translateY = speed * (float) (Math.sin(Math.toRadians(rotation)));
         astoridBody.applyForceToCenter(translateX,translateY+3,true);
         astoridBody.applyTorque(0.09f,true);




         // rock 2
         BodyDef bodyDef1 = new BodyDef();
         bodyDef1.type = BodyDef.BodyType.DynamicBody;
         bodyDef1.position.set(((sprite1.getX() + sprite1.getWidth()/2)/100),
                 ( (sprite1.getY() + sprite1.getHeight()/2)/100) );
         astoridBod1y = worldPhysics.createBody(bodyDef1);

         astoridhitbox1 = new PolygonShape();
         astoridhitbox1.setAsBox((sprite1.getWidth()/2) / 100,
                 (sprite1.getHeight()/2)/100);
         FixtureDef fixDefast2 = new FixtureDef();
         fixDefast2.filter.categoryBits = 0x0002;
         fixDefast2.filter.groupIndex = -1;

         fixDefast2.shape = astoridhitbox1;
         fixDefast2.density = 0.1f;
         fixDefast2.friction = 10f;
         astoridBod1y.createFixture(fixDefast2);
       //  astoridBod1y.setLinearDamping(0.5f);
        // astoridBod1y.setAngularDamping(1f);
         speed = 100;
         translateX = speed * (float) (Math.cos(Math.toRadians(astoridBod1y.getAngle()+10))); //Set velocity
         translateY = speed * (float) (Math.sin(Math.toRadians(astoridBod1y.getAngle()+10)));
         astoridBod1y.applyForceToCenter(translateX,translateY,true);
         astoridBod1y.applyTorque(0.2f,true);

         // rock 3
         BodyDef  bodyDef12 = new BodyDef();
         bodyDef12.type = BodyDef.BodyType.DynamicBody;
         bodyDef12.position.set(((sprite2.getX() + sprite2.getWidth()/2)/100),
                 ( (sprite2.getY() + sprite2.getHeight()/2)/100) );
         astoridBod12 = worldPhysics.createBody(bodyDef12);
         astoridhitbox2 = new PolygonShape();
         astoridhitbox2.setAsBox((sprite2.getWidth()/2) / 100,
                 (sprite2.getHeight()/2)/100);
         FixtureDef fixDefast3= new FixtureDef();
         fixDefast3.filter.categoryBits = 0x0002;

         fixDefast3.shape = astoridhitbox2;
         fixDefast3.density = 0.1f;
         fixDefast3.friction = 10f;
         fixDefast3.filter.groupIndex = -1;

         astoridBod12.createFixture(fixDefast3);
       //  astoridBod12.setLinearDamping(0.5f);
        // astoridBod12.setAngularDamping(1f);
         speed = 10;
         translateX = speed * (float) (Math.cos(Math.toRadians(astoridBod12.getAngle()+10))); //Set velocity
         translateY = speed * (float) (Math.sin(Math.toRadians(astoridBod12.getAngle()+10)));
         astoridBod12.applyForceToCenter(translateX,translateY,true);
         astoridBod12.applyTorque(0.1f,true);

         // rock 4
         BodyDef  bodyDef3 = new BodyDef();
         bodyDef3.type = BodyDef.BodyType.DynamicBody;
         bodyDef3.position.set(((sprite3.getX() + sprite3.getWidth()/2)/100),
                 ( (sprite3.getY() + sprite3.getHeight()/2)/100) );
         astoridBod13 = worldPhysics.createBody(bodyDef3);
         astoridhitbox3 = new PolygonShape();
         astoridhitbox3.setAsBox((sprite3.getWidth()/2) / 100,
                 (sprite3.getHeight()/2)/100);
         FixtureDef fixDefast4 = new FixtureDef();
         fixDefast4.shape = astoridhitbox3;
         fixDefast4.filter.categoryBits = 0x0002;
         fixDefast4.filter.groupIndex = -1;

         fixDefast4.density = 0.1f;
         fixDefast4.friction = 10f;
         astoridBod13.createFixture(fixDefast4);
       //  astoridBod13.setLinearDamping(0.5f);
        // astoridBod13.setAngularDamping(1f);
         speed = 65;
         translateX = speed * (float) (Math.cos(Math.toRadians(astoridBod13.getAngle()+8))); //Set velocity
         translateY = speed * (float) (Math.sin(Math.toRadians(astoridBod13.getAngle()+10)));
         astoridBod13.applyForceToCenter(translateY,translateX,true);
         astoridBod13.applyTorque(0.2f,true);

         // rock 5
         BodyDef bodyDef4 = new BodyDef();
         bodyDef4.type = BodyDef.BodyType.DynamicBody;
         bodyDef4.position.set(((sprite4.getX() + sprite4.getWidth()/2)/100),
                 ( (sprite4.getY() + sprite4.getHeight()/2)/100) );
         astoridBod14 = worldPhysics.createBody(bodyDef4);
         astoridhitbox4 = new PolygonShape();
         astoridhitbox4.setAsBox((sprite4.getWidth()/2) / 100,
                 (sprite4.getHeight()/2)/100);
         FixtureDef fixDefast5 = new FixtureDef();
         fixDefast5.shape = astoridhitbox4;
         fixDefast5.density = 0.1f;
         fixDefast5.friction = 5f;
         fixDefast5.filter.categoryBits = 0x0002;
         fixDefast5.filter.groupIndex = -1;
         astoridBod14.createFixture(fixDefast5);
        // astoridBod14.setLinearDamping(0.5f);
         //astoridBod14.setAngularDamping(1f);
         speed = 45;
         translateX = speed * (float) (Math.cos(Math.toRadians(astoridBod14.getAngle()+13))); //Set velocity
         translateY = speed * (float) (Math.sin(Math.toRadians(astoridBod14.getAngle()+8)));
         astoridBod14.applyForceToCenter(translateY,translateX,true);
         astoridBod14.applyTorque(0.5f,true);

         // rock 6
         BodyDef bodyDef5 = new BodyDef();
         bodyDef5.type = BodyDef.BodyType.DynamicBody;
         bodyDef5.position.set(((sprite5.getX() + sprite5.getWidth()/2)/100),
                 ( (sprite5.getY() + sprite5.getHeight()/2)/100) );
         astoridBod15 = worldPhysics.createBody(bodyDef5);
         astoridhitbox5 = new PolygonShape();
         astoridhitbox5.setAsBox((sprite5.getWidth()/2) / 100,
                 (sprite5.getHeight()/2)/100);
         FixtureDef fixDefast6 = new FixtureDef();
         fixDefast6.shape = astoridhitbox5;
         fixDefast6.density = 0.1f;
         fixDefast6.friction = 6f;
         fixDefast6.filter.categoryBits = 0x0002;
         fixDefast6.filter.groupIndex = -1;

         astoridBod15.createFixture(fixDefast6);
       //  astoridBod15.setLinearDamping(0.5f);
     //    astoridBod15.setAngularDamping(1f);
         speed = 18;
         translateX = speed * (float) (Math.cos(Math.toRadians(astoridBod14.getAngle()+9))); //Set velocity
         translateY = speed * (float) (Math.sin(Math.toRadians(astoridBod14.getAngle()+10)));
         astoridBod15.applyForceToCenter(translateX,translateY,true);
         astoridBod15.applyTorque(0.9f,true);


         //    createAstroid(sprite2, astoridhitbox2, astoridBod12);
        // createAstroid(sprite3, astoridhitbox3, astoridBod13);
        // createAstroid(sprite4, astoridhitbox4, astoridBod14);
        // createAstroid(sprite5, astoridhitbox5, astoridBod15);

         //Asteroid:

         astoridhitbox.dispose();
         astoridhitbox1.dispose();
         astoridhitbox2.dispose();
         astoridhitbox3.dispose();
         astoridhitbox4.dispose();
         astoridhitbox5.dispose();
          ****************************************/
         shipHitbox.dispose();
     }
    public void render () {

    }

    public void createAstroid(Sprite sprt, PolygonShape hitbox, Body body)
    {
        BodyDef bodyDef1 = new BodyDef();
        bodyDef1.type = BodyDef.BodyType.DynamicBody;
        bodyDef1.position.set(((sprt.getX() + sprt.getWidth()/2)/100),
                ( (sprt.getY() + sprt.getHeight()/2)/100) );
        body = worldPhysics.createBody(bodyDef1);
        hitbox = new PolygonShape();
        hitbox.setAsBox((sprt.getWidth()/2) / 100,
                (sprt.getHeight()/2)/100);
        FixtureDef fix = new FixtureDef();
        fix.shape = hitbox;
        fix.density = 0.1f;
        fix.friction = 10f;
        body.createFixture(fix);
        body.setLinearDamping(0.5f);
        body.setAngularDamping(1f);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        worldPhysics.step(1f/150f , 6, 2);
        wrapScreen.wrapScreen(playerSprite , shipBody);
        /**wrapScreen.wrapScreen(sprite , astoridBody);
        wrapScreen.wrapScreen(sprite1 , astoridBod1y);
        wrapScreen.wrapScreen(sprite2 , astoridBod12);
        wrapScreen.wrapScreen(sprite3 , astoridBod13);
        wrapScreen.wrapScreen(sprite4 , astoridBod14);
        wrapScreen.wrapScreen(sprite5 , astoridBod15);
         *****/
        for(int i = 0; i < spawner.asteroidCount(); i++) { //Apply wrap effect for each asteroid
            wrapScreen.wrapScreen(spawner.getAsteroid(i).sprite, spawner.getAsteroid(i).body);
        }
        playerSprite.setPosition((shipBody.getPosition().x * 100) - ( playerSprite.getWidth()/2 ),
               (shipBody.getPosition().y * 100) - ( playerSprite.getHeight()/2 ));
        playerSprite.setRotation((float) Math.toDegrees((shipBody.getAngle())));
        /**
        sprite.setPosition((astoridBody.getPosition().x * 100) - ( sprite.getWidth()/2 ),
                (astoridBody.getPosition().y * 100) - ( sprite.getHeight()/2 ));
        sprite.setRotation((float) Math.toDegrees((astoridBody.getAngle())));

        sprite1.setPosition((astoridBod1y.getPosition().x * 100) - ( sprite1.getWidth()/2 ),
                (astoridBod1y.getPosition().y * 100) - ( sprite1.getHeight()/2 ));
        sprite1.setRotation((float) Math.toDegrees((astoridBod1y.getAngle())));
        sprite2.setPosition((astoridBod12.getPosition().x * 100) - ( sprite2.getWidth()/2 ),
                (astoridBod12.getPosition().y * 100) - ( sprite2.getHeight()/2 ));
        sprite2.setRotation((float) Math.toDegrees((astoridBod12.getAngle())));
        sprite3.setPosition((astoridBod13.getPosition().x * 100) - ( sprite1.getWidth()/2 ),
                (astoridBod13.getPosition().y * 100) - ( sprite3.getHeight()/2 ));
        sprite3.setRotation((float) Math.toDegrees((astoridBod13.getAngle())));
        sprite4.setPosition((astoridBod14.getPosition().x * 100) - ( sprite4.getWidth()/2 ),
                (astoridBod14.getPosition().y * 100) - ( sprite4.getHeight()/2 ));
        sprite4.setRotation((float) Math.toDegrees((astoridBod14.getAngle())));
        sprite5.setPosition((astoridBod15.getPosition().x * 100) - ( sprite1.getWidth()/2 ),
                (astoridBod15.getPosition().y * 100) - ( sprite5.getHeight()/2 ));
        sprite5.setRotation((float) Math.toDegrees((astoridBod15.getAngle())));
         ****/
      spawner.trySpawn();

       bullet = input.keyInput(playerSprite, bulletModel, laserSound, shipSound, shipBody, astoridBody);


        if(bullet != null){
            bullets.add(bullet);
        }


        spawner.moveAll();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        dm = batch.getProjectionMatrix().cpy().scale(100f,100f,0);
        batch.begin();


          playerSprite.draw(batch);
        /**sprite.draw(batch);
        sprite1.draw(batch);
        sprite2.draw(batch);
        sprite3.draw(batch);
        sprite4.draw(batch);
        sprite5.draw(batch);*/
        for(int i = 0; i < spawner.asteroidCount(); i++) {
            spawner.getAsteroid(i).sprite.draw(batch);
        }

        table.draw(batch,10f);

        wrapScreen.wrapScreen(playerSprite , shipBody);
        /**wrapScreen.wrapScreen(sprite , astoridBody);
        wrapScreen.wrapScreen(sprite1 , astoridBod1y);
        wrapScreen.wrapScreen(sprite2 , astoridBod12);
        wrapScreen.wrapScreen(sprite3 , astoridBod13);
        wrapScreen.wrapScreen(sprite4 , astoridBod14);
        wrapScreen.wrapScreen(sprite5 , astoridBod15);*/
        for(int i = 0; i < spawner.asteroidCount(); i++) { //Apply wrap effect for each asteroid
            wrapScreen.wrapScreen(spawner.getAsteroid(i).sprite, spawner.getAsteroid(i).body);
        }


















        for(Bullet bulletlist: bullets){
             bulletlist.bulletSprite.draw(batch);

            bulletlist.updateBullet();


            wrapScreen.wrapScreen(bulletlist.bulletSprite);
            if(bulletlist.time > 1.76)
                deletebullets.add(bulletlist);
          //  bulletHitbox.dispose();

        }











        bullets.removeAll(deletebullets);
       // for(int i = 0; i < spawner.asteroidCount(); i++) { //Draw each asteroid currently spawned
       //     spawner.getAsteroid(i).sprite.draw(batch);
     //   }
         wrapScreen.wrapScreen(playerSprite , shipBody);
//        wrapScreen.wrapScreen(sprite , astoridBody);
//        wrapScreen.wrapScreen(sprite1 , astoridBod1y);
//        wrapScreen.wrapScreen(sprite2 , astoridBod12);
//        wrapScreen.wrapScreen(sprite3 , astoridBod13);
//        wrapScreen.wrapScreen(sprite4 , astoridBod14);
//        wrapScreen.wrapScreen(sprite5 , astoridBod15);
        for(int i = 0; i < spawner.asteroidCount(); i++) { //Apply wrap effect for each asteroid
            wrapScreen.wrapScreen(spawner.getAsteroid(i).sprite, spawner.getAsteroid(i).body);
        }
        batch.end();

        renderer.render(worldPhysics,dm);


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
        wrapScreen.wrapScreen(sprite , astoridBody);

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