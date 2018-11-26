package com.mygdx.game.Menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MainMenu;

public class OptionMenu implements Screen {

    Game game;
    Stage stage;
    Skin skin;


    public OptionMenu(Game gamePass)
    {
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        game = gamePass;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        //
        Label gameTitle = new Label("Option",skin ,"default");
        gameTitle.setAlignment(Align.center);
        gameTitle.setHeight(Gdx.graphics.getHeight()* 1.5f);
        gameTitle.setWidth(Gdx.graphics.getWidth());
        gameTitle.setFontScale(2);
        Group groupTitle = new Group();
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
