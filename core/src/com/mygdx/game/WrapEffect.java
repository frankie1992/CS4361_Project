package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
            gameSprite.translateY(-Gdx.app.getGraphics().getHeight());
        }

    }

}
