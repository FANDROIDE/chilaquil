package com.fandroide.chilaquil.game.Actores;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by usuario on 24/05/17.
 */
public class ActorChilaquil extends Actor {
    private Texture TexturaChilaquil;
    public ActorChilaquil(Texture TexturaChilaquil){
        this.TexturaChilaquil=TexturaChilaquil;
        this.setSize(TexturaChilaquil.getWidth(),TexturaChilaquil.getHeight());
    }
    @Override
    public void act(float delta) {
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(TexturaChilaquil,getX(),getY());
    }
}
