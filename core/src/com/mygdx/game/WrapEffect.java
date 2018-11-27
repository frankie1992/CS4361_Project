package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

import java.util.Random;

public class WrapEffect {


    public void wrapScreen(Sprite gameSprite)
    {
        // Left bounds limit
        if (gameSprite.getX()<= -50 )
        {
            gameSprite.translateX(Gdx.graphics.getWidth());


        }

        // Right bounds limit
        if (gameSprite.getX() >= Gdx.app.getGraphics().getWidth())
        {
            gameSprite.translateX(-Gdx.app.getGraphics().getWidth());

        }

        // Botton bounds limit
        if (gameSprite.getY()<= -50  )
        {
            gameSprite.translateY(Gdx.graphics.getHeight());

        }

        // Top bounds limit
        if (gameSprite.getY() >= Gdx.app.getGraphics().getHeight())
        {
            gameSprite.translateY(-Gdx.graphics.getHeight());
        }

    }

    public void wrapScreen(Sprite gameSprite, Body shipBody )
    {
        // Left bounds limit
        if (shipBody.getPosition().x   < 0 )
        {

            shipBody.setTransform(new Vector2(  11, shipBody.getPosition().y),shipBody.getAngle());
            //  gameSprite.translateX(Gdx.graphics.getWidth()); 1152
         //   shipBody.setTransform(Gdx.graphics.getWidth() / 2 - gameSprite.getWidth()/2, Gdx.graphics.getHeight()/2 - gameSprite.getHeight()/2, 0);
          // shipBody.setTransform(new Vector2(1152 ,shipBody.getPosition().y), shipBody.getAngle());


        }

        // Right bounds limit
        // works

        if (shipBody.getPosition().x *100>= Gdx.app.getGraphics().getWidth())
        {



//            shipBody.setTransform(new Vector2(0,shipBody.getPosition().y), shipBody.getAngle());
            shipBody.setTransform(new Vector2(0,shipBody.getPosition().y), shipBody.getAngle());
        }

        // Botton bounds limit
        if (shipBody.getPosition().y *100 < 0 )
        {

            shipBody.setTransform(new Vector2(shipBody.getPosition().x, 8), shipBody.getAngle());

        }

        // Top bounds limit
        // works
        if (shipBody.getPosition().y * 100 >= Gdx.app.getGraphics().getHeight())
        {
        //    gameSprite.translateY(-Gdx.graphics.getHeight());

            shipBody.setTransform(new Vector2(shipBody.getPosition().x, 0), shipBody.getAngle());
        }

    }


}
