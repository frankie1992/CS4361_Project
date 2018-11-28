package com.mygdx.game.Menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MainMenu;

public class InstructionMenu implements Screen {

    Game game;
    Stage stage;
    Skin skin;


    public InstructionMenu(Game gamePass)
    {

         skin = new Skin(Gdx.files.internal("uiskin.json"));

         game = gamePass;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        //
        Label gameTitle = new Label("Instructions ",skin ,"default");
        Label textAreaStory = new Label( " Objective\n" +
                                              " The main objective of the game is to attempt to achieve  the highest score possible. \n" +
                                              " This is achieved by invading the asteroid belt and destroying innocent asteroids\n " +
                                               "while attempting to stay alive as long as possible." ,skin ,"default" );

        Label scoreArea = new Label( " Score System\n\n" +
                " Big Asteroid 100 Points. \n" +
                " Medium Asteroid 500 Points.\n" +
                " Small Asteroid 1000 Points.\n" +
                " Satellite 2000 Points.\n" +
                " UFO 5000 Points." ,skin ,"default" );




        Label controlLable = new Label("Controlling The Ship",skin ,"default");

        Label left = new Label("Left",skin ,"default");
        Label right = new Label("Right",skin ,"default");
        Label up = new Label("Up",skin ,"default");
        Label  space = new Label("Space",skin ,"default");
        Label  z = new Label("z",skin ,"default");


        Image leftButtonImg = new Image (new Texture("ControlButton/left.png"))  ;
        Image rightButtonImg = new Image (new Texture("ControlButton/right.png"))  ;
        Image upButtonImg = new Image (new Texture("ControlButton/up.png"))  ;
        Image  spaceButtonImg = new Image (new Texture("ControlButton/space.png"))  ;
        Image  zButtonImg = new Image (new Texture("ControlButton/z.png"))  ;


        //
        Image bigAsteroid = new Image (new Texture("ControlButton/Asteroid.png"))  ;
        Image medAsteroid = new Image (new Texture("ControlButton/Asteroid_Med.png"))  ;
        Image smallAsteroid = new Image (new Texture("ControlButton/Asteroid_Small.png"))  ;
        Image  ufo = new Image (new Texture("ControlButton/ufo.png"))  ;
        Image  satellite = new Image (new Texture("ControlButton/Satellite.png"))  ;

        gameTitle.setAlignment(Align.center);
        gameTitle.setHeight(Gdx.graphics.getHeight()* 1.9f);
        gameTitle.setWidth(Gdx.graphics.getWidth());

        textAreaStory.setAlignment(Align.left);
        textAreaStory.setHeight(Gdx.graphics.getHeight()* 1.6f);
         textAreaStory.setWidth(1000);
        textAreaStory.setFontScale(.5f);

        scoreArea.setFontScale(0.5f);
        controlLable.setFontScale(0.5f);
        left.setFontScale(0.5f);
        right.setFontScale(0.5f);
         space.setFontScale(0.5f);
        up.setFontScale(0.5f);
        z.setFontScale(0.5f);

        controlLable.setPosition(10,550);
        leftButtonImg.setPosition(200,500);
        left.setPosition(10,500);



        rightButtonImg.setPosition(200,450);
        right.setPosition(10,450);
        upButtonImg.setPosition(200,400);
        up.setPosition(10,400);
        spaceButtonImg.setPosition(200,350);
        space.setPosition(10,350);
        zButtonImg.setPosition(200,300);
        z.setPosition(10,300);

        scoreArea.setPosition(500, 310);
        bigAsteroid.setPosition(800,520);
        medAsteroid.setPosition(850,480);
        smallAsteroid.setPosition(845,450);
        satellite.setPosition(800,420);
        ufo.setPosition(800,400);


        Group groupTitle = new Group();
        Table controlGroupTable = new Table();


        controlGroupTable.setPosition(120,550);


        groupTitle.addActor(gameTitle);

        //




        //
        TextButton backButton = new TextButton("Back",skin,"default");



        //
        backButton.setWidth(200);
        backButton.setHeight(100);
        backButton.setPosition(100,100,Align.left);


        //
        backButton.setColor(0,0,0,1f);


        backButton.addListener(new InputListener()
        {

            public void touchUp (InputEvent event, float x, float y, int pointer , int button)
            {
                game.setScreen(new MainMenu( game ));
            }


            @Override
            public boolean touchDown ( InputEvent event, float x, float y, int pointer , int button)
            {
                return true;
            }
        });

        stage.addActor(bigAsteroid);
        stage.addActor(medAsteroid);
        stage.addActor(smallAsteroid);
        stage.addActor(satellite);
        stage.addActor(ufo);
        stage.addActor(leftButtonImg);
        stage.addActor(left);
        stage.addActor(rightButtonImg);
        stage.addActor(right);
        stage.addActor(spaceButtonImg);
        stage.addActor(space);
        stage.addActor(zButtonImg);
        stage.addActor(z);
        stage.addActor(upButtonImg);
        stage.addActor(up);
        stage.addActor(controlLable);
        stage.addActor(scoreArea);
        stage.addActor(textAreaStory);
         stage.addActor(groupTitle);
         stage.addActor(controlGroupTable);
        stage.addActor(backButton);
        Gdx.input.setInputProcessor(stage);

}
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
