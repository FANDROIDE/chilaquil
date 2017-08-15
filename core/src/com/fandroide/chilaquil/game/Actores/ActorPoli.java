package com.fandroide.chilaquil.game.Actores;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by usuario on 24/05/17.
 */
public class ActorPoli extends Actor {
    private Texture texturaPoli;
    public ActorPoli(Texture texturaPoli){
        this.texturaPoli=texturaPoli;
        this.setSize(texturaPoli.getWidth(),texturaPoli.getHeight());
    }
    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texturaPoli,getX(),getY());
    }
}
