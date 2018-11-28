package com.mygdx.game.Menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MainMenu;

import javax.swing.event.ChangeEvent;

public class OptionMenu implements Screen {

    Game game;
    Stage stage;
    Skin skin;
    float soundVolume;
    Slider volume;
    Table table;

    public OptionMenu(Game gamePass)
    {
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        table = new Table();
        game = gamePass;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        //
        Label gameTitle = new Label("Option",skin ,"default");
        Label effectSound = new Label("Effect Sound",skin ,"default");
        Label window = new Label("Window",skin ,"default");
        Label mode = new Label("Game mode",skin ,"default");

        //
        volume = new Slider(0,10f,1f,false,skin);
        gameTitle.setAlignment(Align.center);
        gameTitle.setHeight(Gdx.graphics.getHeight()* 1.5f);
        gameTitle.setWidth(Gdx.graphics.getWidth());
         Group groupTitle = new Group();
        groupTitle.addActor(gameTitle);

        //

        gameTitle.setAlignment(Align.center);
        gameTitle.setHeight(Gdx.graphics.getHeight()* 1.9f);
        gameTitle.setWidth(Gdx.graphics.getWidth());

        //
        table.add(effectSound).left();
         table.add(volume).pad(10f).fillX().colspan(3);
        table.row();
        table.add(window);
        table.row();
        table.add(mode);



        //
        TextButton backButton = new TextButton("Back",skin,"default");



        //
        backButton.setWidth(200);
        backButton.setHeight(100);
        backButton.setPosition(100,100,Align.left);


        //
        backButton.setColor(0,0,0,1f);


        //
        table.align(Align.center|Align.top);
        table.setHeight(Gdx.graphics.getHeight()* 0.6f);
        table.setWidth(Gdx.graphics.getWidth());

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



        stage.addActor(table);
        stage.addActor(groupTitle);
        stage.addActor(backButton);

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(stage);
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

    public void setSoundVolume(float soundVolume)
    {

    }


    public float getSoundVolume( )
    {
        return soundVolume;
    }

}
