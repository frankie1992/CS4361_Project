package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.audio.Sound;
import java.util.Random;


public class PlayerInput {
    Random randNumber;
    public  Bullet keyInput(Sprite playerSprite, Texture bulletTexture, Sound laserSound, Sound shipSound)
    {
        randNumber = new Random();

        float rotateDegree = 4f;
        float translateSpeed = 3f;

        // Rotate left button
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            playerSprite.rotate(rotateDegree);
        }


        // Rotate right button
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            playerSprite.rotate(-rotateDegree);
        }

        // Move Foward Button
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
        {
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
                shipSound.play();
            }
            // Speed Button
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT))
            {
                // Change to dash

                translateSpeed = 12;

            }
            // Player Movement in regard to the rotation
            playerSprite.translate(  (float ) (  translateSpeed * Math.cos(Math.toRadians(playerSprite.getRotation())))
                    , (float ) (  translateSpeed * Math.sin(Math.toRadians(playerSprite.getRotation())))) ;
        }


        // Move the ship to a new random position
        if (Gdx.input.isKeyPressed(Input.Keys.X))
        {
            int randPosX = randNumber.nextInt((Gdx.app.getGraphics().getWidth()-50) - (10)+10);
            int randPosY = randNumber.nextInt((Gdx.app.getGraphics().getHeight()-50) - (10)+10);

            // Set the X-axis
            playerSprite.setX(randPosX);
            // Set the Y-axis
            playerSprite.setY(randPosY);
        }

        // shot a bullet
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
        {
            Bullet bullet = new Bullet(playerSprite.getX(),playerSprite.getY(),8,(float)Math.toRadians(playerSprite.getRotation()), bulletTexture);
            laserSound.play();
            return bullet;
        }

        return null;
    }
}
