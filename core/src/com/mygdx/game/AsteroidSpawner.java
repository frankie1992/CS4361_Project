package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import java.util.Random;

public class AsteroidSpawner { //Spawns asteroid in game with random initial direction
    Random rand;
    int asteroidCount=0;
    float speed;
    int max = 1;
    Sprite asteroidSprite;

    public void spawn(Sprite s) { //Spawns asteroid with random rotation
        if (true) {
            asteroidCount++;
            rand = new Random();
            speed = 1f;
            asteroidSprite = s;
            asteroidSprite.rotate(rand.nextFloat() * 45);
        }
    }
    public void move() { //Moves asteroid in set direction after spawning
        asteroidSprite.translate((float) (speed * Math.cos(Math.toRadians(asteroidSprite.getRotation())))
                , (float) (speed * Math.sin(Math.toRadians(asteroidSprite.getRotation()))));
    }

}
