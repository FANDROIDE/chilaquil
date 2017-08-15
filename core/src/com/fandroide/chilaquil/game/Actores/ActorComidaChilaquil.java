package com.fandroide.chilaquil.game.Actores;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by usuario on 24/05/17.
 */
public class ActorComidaChilaquil extends Actor {
    private Texture texturaComida;
    public ActorComidaChilaquil(Texture texturaComida){
        this.texturaComida=texturaComida;
        this.setSize(texturaComida.getWidth(),texturaComida.getHeight());
    }
    @Override
    public void act(float delta) {
        setX(getX()-250*delta);
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texturaComida,getX(),getY());
    }
}
