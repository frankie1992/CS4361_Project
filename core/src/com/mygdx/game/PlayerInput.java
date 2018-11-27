
package com.mygdx.game;

        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.Input;
        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.graphics.g2d.Sprite;
        import com.badlogic.gdx.audio.Sound;
        import com.badlogic.gdx.graphics.g2d.TextureAtlas;
        import com.badlogic.gdx.physics.box2d.Body;
        import com.badlogic.gdx.physics.box2d.BodyDef;

        import java.util.Random;


public class PlayerInput {
    Random randNumber;
    public  Bullet keyInput(Sprite playerSprite, Texture bulletTexture, Sound laserSound, Sound shipSound, Body shipBody, Body astoridBody)
    {
        randNumber = new Random();

        float rotateDegree = 4f;
        float translateSpeed = 0.5f;
        float torque = 0f;
        Texture moveShip;
        Sprite moveShipMode;
        // Rotate left button
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
           // playerSprite.rotate( rotateDegree);
            torque = torque + 0.01f;
            shipBody.applyTorque(torque, true);

        }


        // Rotate right button
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
         //   playerSprite.rotate(-rotateDegree);
            torque = torque - 0.01f;
            shipBody.applyTorque(torque, true);





        }

        // Move Foward Button
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
        {
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
                shipSound.play();

            //   shipBody.applyForceToCenter(0f,5f,true);

            }
            // Speed Button
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT))
            {
                // Change to dash

                translateSpeed = 0.5f;

            }


            // Player Movement in regard to the rotation

            playerSprite.translate(  (float ) (  translateSpeed * Math.cos(Math.toRadians(playerSprite.getRotation())))
                     , (float ) (  translateSpeed * Math.sin(Math.toRadians(playerSprite.getRotation())))) ;

            float x = (float ) (  translateSpeed * Math.cos(Math.toRadians(playerSprite.getRotation())));
            float y = (float ) (  translateSpeed * Math.sin(Math.toRadians(playerSprite.getRotation())));
          //  shipBody.setLinearVelocity(2,2);

            shipBody.applyForceToCenter(x,y,true);
           // astoridBody.applyForceToCenter(10,10,true);
         }


        // Move the ship to a new random position
        if (Gdx.input.isKeyPressed(Input.Keys.X))
        {
            int randPosX = randNumber.nextInt((Gdx.app.getGraphics().getWidth()) - (10)+10);
            int randPosY = randNumber.nextInt((Gdx.app.getGraphics().getHeight()) - (10)+10);

            // Set the X-axis
            shipBody.setTransform(randPosX,randPosY,shipBody.getAngle());

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