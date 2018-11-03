package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Bullet {

    Texture bulletTexture;
    float x,y,dx,dy,dt, radians, time, speed;
    boolean delete;
    Sprite bulletSprite;

    public Bullet(float x, float y,float speed, float radians, Texture bulletTexture){
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
        bulletSprite.setOrigin(bulletSprite.getWidth()/2, bulletSprite.getHeight()/2);

    }

    public void updateBullet(){
        bulletSprite.translate(dx, dy);
        time = time + dt;
    }

    public float returnTime(){
        return dt;
    }
}
