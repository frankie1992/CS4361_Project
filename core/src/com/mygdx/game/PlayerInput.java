
package com.mygdx.game;

        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.Input;
        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.graphics.g2d.Sprite;
        import com.badlogic.gdx.audio.Sound;
        import com.badlogic.gdx.graphics.g2d.TextureAtlas;
        import com.badlogic.gdx.physics.box2d.Body;
        import com.badlogic.gdx.physics.box2d.BodyDef;
        import com.badlogic.gdx.physics.box2d.World;

        import java.util.Random;


public class PlayerInput {
    Random randNumber;
    World Physics;
    boolean pauseButton = true;
    boolean pause = true;

    public  Bullet keyInput(Sprite playerSprite, Texture bulletTexture, Sound laserSound, Sound shipSound, Body shipBody, World physics  )
    {
        randNumber = new Random();
        Physics = physics;
         float translateSpeed = 0.5f;
        float torque = 0f;

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
                     shipSound.play(0.5f);


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
         else
             shipSound.stop();


        // Move the ship to a new random position
        if (Gdx.input.isKeyPressed(Input.Keys.X))
        {
            int randPosX = randNumber.nextInt((Gdx.app.getGraphics().getWidth()) /100);
            int randPosY = randNumber.nextInt((Gdx.app.getGraphics().getHeight()) /100);

            // Set the X-axis
            shipBody.setTransform(randPosX,randPosY,shipBody.getAngle());

        }

        // shot a bullet
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
        {
            Bullet bullet = new Bullet(playerSprite.getX(),playerSprite.getY(),8,(float)Math.toRadians(playerSprite.getRotation()), bulletTexture, shipBody, Physics  );
            laserSound.play(1f);
            return bullet;
        }

        return null;
    }


}