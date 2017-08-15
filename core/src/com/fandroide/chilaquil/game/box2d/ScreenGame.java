package com.fandroide.chilaquil.game.box2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.fandroide.chilaquil.game.Actores.ActorChilaquil;
import com.fandroide.chilaquil.game.Actores.ActorComidaChilaquil;
import com.fandroide.chilaquil.game.Actores.ActorPoli;
import com.fandroide.chilaquil.game.BaseScreen;
import com.fandroide.chilaquil.game.MainGame;

/**
 * Created by usuario on 20/05/17.
 */
public class ScreenGame extends BaseScreen {
    public ScreenGame(MainGame game) {
        super(game);
        texturaChilaquil=new Texture("actores/chilaquil1.png");
        texturaPoli=new Texture("actores/policia.png");
        texturaComidaChilaquil=new Texture("actores/comidaChilaquil.jpg");
    }
    Stage stage;
    private ActorChilaquil chilaquil;
    private ActorPoli poli;
    private ActorComidaChilaquil comida;
    private Texture texturaChilaquil,texturaPoli,texturaComidaChilaquil;
    @Override
    public void show() {
        stage=new Stage();
        stage.setDebugAll(true);
        chilaquil=new ActorChilaquil(texturaChilaquil);
        poli=new ActorPoli(texturaPoli);
        comida=new ActorComidaChilaquil(texturaComidaChilaquil);
        stage.addActor(chilaquil);
        stage.addActor(poli);
        stage.addActor(comida);
        chilaquil.setPosition(20,100);
        poli.setPosition(250,100);
        comida.setPosition(500,100);
    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0.74902f,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose(){
        texturaChilaquil.dispose();
        texturaPoli.dispose();
        texturaComidaChilaquil.dispose();
    }
}
