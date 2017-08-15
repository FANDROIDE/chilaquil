package com.fandroide.chilaquil.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.math.Vector2;
import static com.fandroide.chilaquil.game.Constants.*;

/**
 * Created by usuario on 25/05/17.
 */
public class policiaEntity extends Actor {
    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;
    private boolean alive=true,jumping=false;
    private float X,Y;
    public policiaEntity(World world,Texture texture, Vector2 position){
        this.world=world;
        this.texture=texture;
        BodyDef def=new BodyDef();
        def.position.set(position);
        def.type=BodyDef.BodyType.DynamicBody;
        body=world.createBody(def);

        PolygonShape box=new PolygonShape();
        X=1f;
        Y=2f;
        box.setAsBox((float)X/2,(float)Y/2);
        fixture=body.createFixture(box,1);
        fixture.setUserData("policia");
        box.dispose();
        setSize(PIXELS_IN_METERS *X, PIXELS_IN_METERS*Y);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x-(X/2))*PIXELS_IN_METERS,(body.getPosition().y-(Y/2))*PIXELS_IN_METERS);
        batch.draw(texture,getX(),getY(),getWidth(),getHeight());
    }
    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
}
