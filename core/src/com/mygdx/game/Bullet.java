package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Bullet implements Screen {

    Texture bulletTexture;
    float x,y,dx,dy,dt, radians, time, speed;
    boolean delete;
    Sprite bulletSprite;
    Body bulletBody;
    PolygonShape bulletHitbox;
    World worldPhysics;


    public Bullet(float x, float y, float speed, float radians, Texture bulletTexture){
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.radians=radians;
        this.bulletTexture = bulletTexture;
        this.dt = Gdx.graphics.getDeltaTime();
        dx = MathUtils.cos(radians)* speed;
        dy = MathUtils.sin(radians)* speed;
        bulletSprite = new Sprite(bulletTexture);
        bulletSprite.setPosition(x,y);
        worldPhysics = new World(new Vector2(0f,0f),true );

        bulletSprite.setOrigin(bulletSprite.getWidth()/2, bulletSprite.getHeight()/2);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((bulletSprite.getX() + bulletSprite.getWidth()/2/100),
                (bulletSprite.getY() + bulletSprite.getHeight()/2)/100);

        bulletBody = worldPhysics.createBody(bodyDef);
        bulletBody.setLinearDamping(1);
        bulletBody.setAngularDamping(1);

        bulletHitbox = new PolygonShape();
        bulletHitbox.setAsBox((bulletSprite.getWidth()/2) / 100,
                (bulletSprite.getHeight()/2)/100);
        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = bulletHitbox;
        fixDef.density = 0.1f;
        fixDef.friction = 10f;

        bulletBody.createFixture(fixDef);


    }

    public void updateBullet(){
        bulletSprite.translate(dx, dy);
        time = time + dt;

    }

    public float returnTime(){
        return dt;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        worldPhysics = new World(new Vector2(0f,0f), true);
        BodyDef bulletBodyDef = new BodyDef();
        bulletBodyDef.type = BodyDef.BodyType.DynamicBody;
        bulletBodyDef.bullet = true;
        bulletBodyDef.position.set(x/100,y/100);
        //     bulletBodyDef.position.set(300,300);

        bulletBody = worldPhysics.createBody(bulletBodyDef);
        bulletHitbox = new PolygonShape();
        bulletHitbox.setAsBox((bulletSprite.getWidth()/2)/100, (bulletSprite.getHeight()/2)/100);
        FixtureDef fixtureDefBullet = new FixtureDef();
        fixtureDefBullet.shape = bulletHitbox;
        fixtureDefBullet.density = 0.01f;
        bulletBody.createFixture(fixtureDefBullet);

        bulletSprite.setPosition((bulletBody.getPosition().x * 100) - ( bulletSprite.getWidth()/2 ),
                (bulletBody.getPosition().y * 100) - ( bulletSprite.getHeight()/2 ));
        bulletSprite.setRotation((float) Math.toDegrees((bulletBody.getAngle())));

        bulletHitbox.dispose();
    }


    public void renderBullet( ) {
        worldPhysics.step(1f/150f , 6, 2);
      //  wrapScreen.wrapScreen(playerSprite , shipBody);



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
    public void dispose() {

    }
}
