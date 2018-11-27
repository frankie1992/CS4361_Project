package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Menu.HighScoreMenu;
import com.mygdx.game.Menu.InstructionMenu;
import com.mygdx.game.Menu.OptionMenu;

public class MainMenu implements Screen
{

    //
     Stage stage;
    Skin skin;
    Table table;
    SpriteBatch batch;
     Sprite background;
    Game game;
    AsteroidSpawner spawner;
    public MainMenu(Game gamePass)
    {
        batch = new SpriteBatch();

        table = new Table();
        game = gamePass;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        //
        Label gameTitle = new Label("ASTEROIDS",skin ,"default");
        gameTitle.setAlignment(Align.center);
        gameTitle.setHeight(Gdx.graphics.getHeight()* 1.5f);
        gameTitle.setWidth(Gdx.graphics.getWidth());
        gameTitle.setFontScale(2);
        Group groupTitle = new Group();
        groupTitle.addActor(gameTitle);

        //



//////////////////////////////////////////////////
        //Gdx.input.setInputProcessor(stage);

        //////////////////////////////////////////////////////////////////
        //Asteroid:
        String[] bigAsteroid = {"Asteroids/Big/Asteroid_Big1.png", "Asteroids/Big/Asteroid_Big2.png"};
        String[] medAsteroid = {"Asteroids/Med/Asteroid_Med1.png","Asteroids/Med/Asteroid_Med2.png"};
        String[] smallAsteroid = {"Asteroids/Small/Asteroid_Small1.png","Asteroids/Small/Asteroid_Small2.png"};
        spawner = new AsteroidSpawner(bigAsteroid, medAsteroid, smallAsteroid);
        Gdx.input.setInputProcessor(stage);


        //
        TextButton startButton = new TextButton("START GAME",skin,"default");
        TextButton highscoreButton = new TextButton("HIGH SCORE",skin,"default");
        TextButton optionButton = new TextButton("OPTION",skin,"default");
        TextButton instructionButton = new TextButton("INSTRUCTIONS ",skin,"default");
        TextButton exitButton = new TextButton("EXIT",skin,"default");



        //
        startButton.setWidth(200);
        startButton.setHeight(100);
        optionButton.setWidth(200);
        optionButton.setHeight(100);
        instructionButton.setWidth(200);
        instructionButton.setHeight(100);
        exitButton.setWidth(200);
        exitButton.setHeight(100);
        highscoreButton.setWidth(200);
        highscoreButton.setHeight(100);


        //
        startButton.setColor(0,0,0,1f);
        optionButton.setColor(0,0,0,1f);
        instructionButton.setColor(0,0,0,1f);
        exitButton.setColor(0,0,0,1f);
        highscoreButton.setColor(0,0,0,1f);

        startButton.addListener(new InputListener()
        {

            public void touchUp (InputEvent event, float x, float y, int pointer , int button)
            {
                startButton.setTouchable(Touchable.disabled);
                optionButton.setTouchable(Touchable.disabled);
                instructionButton.setTouchable(Touchable.disabled);
                exitButton.setTouchable(Touchable.disabled);
                highscoreButton.setTouchable(Touchable.disabled);

                game.setScreen(new AsteroidGame(  ));
            }


            @Override
            public boolean touchDown ( InputEvent event, float x, float y, int pointer , int button)
            {
                return true;
              }
        });

        highscoreButton.addListener(new InputListener()
        {

            public void touchUp (InputEvent event, float x, float y, int pointer , int button)
            {
               game.setScreen(new HighScoreMenu( game));
            }


            @Override
            public boolean touchDown ( InputEvent event, float x, float y, int pointer , int button)
            {
                return true;
            }
        });

        optionButton.addListener(new InputListener()
        {

            public void touchUp (InputEvent event, float x, float y, int pointer , int button)
            {
                game.setScreen(new OptionMenu(game));
            }


            @Override
            public boolean touchDown ( InputEvent event, float x, float y, int pointer , int button)
            {
                return true;
            }
        });

        instructionButton.addListener(new InputListener()
        {

            public void touchUp (InputEvent event, float x, float y, int pointer , int button)
            {
              game.setScreen(new InstructionMenu(game));
            }


            @Override
            public boolean touchDown ( InputEvent event, float x, float y, int pointer , int button)
            {
                return true;
            }
        });

        exitButton.addListener(new InputListener()
        {

            public void touchUp (InputEvent event, float x, float y, int pointer , int button)
            {
                Gdx.app.exit();
             }


            @Override
            public boolean touchDown ( InputEvent event, float x, float y, int pointer , int button)
            {
                return true;
            }
        });



        //
        stage.addActor(groupTitle);

        //
        table.add(startButton);
        table.row();

        table.add(highscoreButton);
        table.row();

        table.add(optionButton);
        table.row();

        table.add(instructionButton);
        table.row();

        table.add(exitButton);
        table.row();
        //
        table.align(Align.center|Align.top);
        table.setHeight(Gdx.graphics.getHeight()* 0.6f);
        table.setWidth(Gdx.graphics.getWidth());
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);







    }


    @Override
    public void show() {
     }

    @Override
    public void render(float delta) {
        spawner.max = 20;

        spawner.trySpawn();


         spawner.moveAll();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        for(int i = 0; i < spawner.asteroidCount(); i++) { //Draw each asteroid currently spawned
            spawner.getAsteroid(i).sprite.draw(batch);
        }
        batch.end();


         stage.draw();
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
    }
}




