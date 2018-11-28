package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.Menu.GameOver;
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



    Skin skin;
    int gameScore = 0;
    int gameLives = 3;
    Table table;
    BitmapFont bitmapFont;
    //Asteroid:
    Sprite sprite;
    Texture astroid;
    PolygonShape astoridhitbox;
    Body astoridBody;
    String scoreTitle;
    String livesTitle;
    Label score;
    Label lives;
    AsteroidSpawner spawner;
    String[] bigAsteroid = {"Asteroids/Big/Asteroid_Big1.png", "Asteroids/Big/Asteroid_Big2.png"};
    String[] medAsteroid = {"Asteroids/Med/Asteroid_Med1.png","Asteroids/Med/Asteroid_Med2.png"};
    String[] smallAsteroid = {"Asteroids/Small/Asteroid_Small1.png","Asteroids/Small/Asteroid_Small2.png"};
    Game gameOverStae;
    public   AsteroidGame  (Game game) {
        gameOverStae = game;
        create();
    }

    public void create()
    {
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        table = new Table();

          bitmapFont = new BitmapFont();
        score = new Label("Score: " + gameScore,skin ,"default");
        lives = new Label("Lives: " + gameLives,skin ,"default");
        scoreTitle = "Score: 0";
        livesTitle = "Lives: 3";
        score.setWidth(100);
        score.setHeight(800);
        lives.setWidth(100);
        lives.setHeight(700);
       table.add(score);
      table.row();
       table.add(lives);
        table.setPosition(100,800);


        batch = new SpriteBatch();
        renderer = new Box2DDebugRenderer();
        playerModel = new Texture("Player/shipTest.png");
        astroid = new Texture("Asteroids/Small/Asteroid_Small2.png");

        shipSound = Gdx.audio.newSound(Gdx.files.internal("Player/ship_move.wav"));
        bulletModel = new Texture("Laser/Bullet.png");
        laserSound = Gdx.audio.newSound(Gdx.files.internal("Laser/laser_sound.wav"));
        playerSprite = new Sprite(playerModel);

        sprite = new Sprite(astroid);




        // rand spawn
        Random rand = new Random();
        int x = rand.nextInt(50);
        int y = rand.nextInt(50);
        ///


        sprite.setPosition(600,250);


        playerSprite.setPosition(Gdx.graphics.getWidth() / 2 - playerSprite.getWidth()/2, Gdx.graphics.getHeight()/2 - playerSprite.getHeight()/2);
        input = new PlayerInput();
        wrapScreen = new WrapEffect();


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
        fixDef.filter.groupIndex = 1;
         shipBody.setUserData("ship");
       // fixDef.filter.groupIndex = -1;

        shipBody.createFixture(fixDef);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////    ASTEROID SPAWNER    /////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        spawner = new AsteroidSpawner(bigAsteroid, medAsteroid, smallAsteroid, worldPhysics);
        // rocks
        renderer = new Box2DDebugRenderer();
        collision();
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
        collision();
        for(int i = 0; i < spawner.asteroidCount(); i++) { //Apply wrap effect for each asteroid
            wrapScreen.wrapScreen(spawner.getAsteroid(i).sprite, spawner.getAsteroid(i).body);
        }
        playerSprite.setPosition((shipBody.getPosition().x * 100) - ( playerSprite.getWidth()/2 ),
                (shipBody.getPosition().y * 100) - ( playerSprite.getHeight()/2 ));
        playerSprite.setRotation((float) Math.toDegrees((shipBody.getAngle())));

        spawner.trySpawn();

        bullet = input.keyInput(playerSprite, bulletModel, laserSound, shipSound, shipBody, worldPhysics);


        if(bullet != null){
            bullets.add(bullet);
        }


        spawner.moveAll();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        dm = batch.getProjectionMatrix().cpy().scale(100f,100f,0);
        batch.begin();
         bitmapFont.draw(batch, scoreTitle,100,800);
        bitmapFont.draw(batch, livesTitle,100,750);
        score.setText("Score: " + gameScore);
        lives.setText("Lives: " + gameLives);

        playerSprite.draw(batch);

        for(int i = 0; i < spawner.asteroidCount(); i++) {
            spawner.getAsteroid(i).sprite.draw(batch);
        }

        table.draw(batch,10f);

        wrapScreen.wrapScreen(playerSprite , shipBody);

        for(int i = 0; i < spawner.asteroidCount(); i++) { //Apply wrap effect for each asteroid
            wrapScreen.wrapScreen(spawner.getAsteroid(i).sprite, spawner.getAsteroid(i).body);
        }






        for(Bullet bulletlist: bullets){
            bulletlist.bulletSprite.draw(batch);

            bulletlist.updateBullet();


            wrapScreen.wrapScreen(bulletlist.bulletSprite );
            if(bulletlist.time > 1.76) {
                deletebullets.add(bulletlist);
                worldPhysics.destroyBody(bulletlist.body);

            }


        }













        bullets.removeAll(deletebullets);
        wrapScreen.wrapScreen(playerSprite , shipBody);

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


    public void collision()
    {
        worldPhysics.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {


                if(contact.getFixtureA().getBody().getUserData()=="ship" && contact.getFixtureB().getBody().getUserData()=="asteroid"
                        || contact.getFixtureA().getBody().getUserData()=="asteroid" && contact.getFixtureB().getBody().getUserData()=="ship")
                {
                    System.out.println("ship Contact");
                      scoreTitle = "Score: " + ++gameScore ;
                    livesTitle = "Lives: " + --gameLives ;
                    if (gameLives <= 0)
                    {
                        gameOverStae.setScreen(new GameOver(gameOverStae, gameScore));

                    }

                }

            }

            @Override
            public void endContact(Contact contact) {



            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }
}