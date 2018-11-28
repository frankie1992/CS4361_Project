package com.mygdx.game.Menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
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

public class GameOver implements Screen {

    Game game;
    Stage stage;
    Skin skin;
    Table table;

    public GameOver(Game gamePass, int score)
    {
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        table = new Table();
        game = gamePass;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        //
        Label gameTitle = new Label("Game Over",skin ,"default");
        Label finalScore = new Label("Final Score " + score ,skin ,"default");

        gameTitle.setAlignment(Align.center);
        gameTitle.setHeight(Gdx.graphics.getHeight()* 1.5f);
        gameTitle.setWidth(Gdx.graphics.getWidth());
           TextButton backButton = new TextButton("Back",skin,"default");

        //
        table.add(gameTitle);
        table.row();
        table.add(finalScore);
        table.row();

        table.add(backButton);



        table.setHeight(Gdx.graphics.getHeight()* 1.6f);
        table.setWidth(Gdx.graphics.getWidth());

         stage.addActor(table);


        //



        //



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
