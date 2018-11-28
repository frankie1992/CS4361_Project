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

public class Bullet   {

    Texture bulletTexture;
    float x,y,dx,dy,dt, radians, time, speed;
    boolean delete;
    Sprite bulletSprite;
    Body bulletBody;
    PolygonShape bulletHitbox;
    World worldPhysics;
    Body body;
    PolygonShape hitbox;


    public Bullet(float x, float y, float speed, float radians, Texture bulletTexture, Body shipBody, World worldPhy){
        this.x = x;
        this.y = y;

        this.speed = speed;
        this.radians=radians;
        this.bulletTexture = bulletTexture;
        this.dt = Gdx.graphics.getDeltaTime();
        worldPhysics = worldPhy;
        bulletBody = shipBody;
        dx = MathUtils.cos(radians)* speed;
        dy = MathUtils.sin(radians)* speed;
        bulletSprite = new Sprite(bulletTexture);
        bulletSprite.setPosition(x+18,y+18);
         bulletSprite.setOrigin(bulletSprite.getWidth()/2, bulletSprite.getHeight()/2);
        setHitBox();
        hitbox.dispose();


    }

    public void updateBullet(){
        bulletSprite.translate(dx, dy);
        time = time + dt;
        worldPhysics.destroyBody(body);
         setHitBox();

     }



    private void setHitBox() { //Set up Hitbox for this asteroid using Box2D methods
        BodyDef bodyDef = new BodyDef();
        hitbox = new PolygonShape();

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(((bulletSprite.getX() + bulletSprite.getWidth()/2)/100),
                ( (bulletSprite.getY() + bulletSprite.getHeight()/2)/100)   );
        body = worldPhysics.createBody(bodyDef);
         hitbox.setAsBox((bulletSprite.getWidth()/2) / 100,
                (bulletSprite.getHeight()/2)/100);
        FixtureDef fixDefast = new FixtureDef();
          fixDefast.shape = hitbox;
        fixDefast.density = 0.1f;
        fixDefast.friction = 10f;
        fixDefast.filter.groupIndex = 1;
        bulletBody.isBullet();
        bulletBody.setUserData("bullet");

        body.createFixture(fixDefast);
         body.applyForceToCenter(bulletBody.getPosition().x,bulletBody.getPosition().y,true);

     }
}
