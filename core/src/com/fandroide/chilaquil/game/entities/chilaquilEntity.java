package com.fandroide.chilaquil.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.fandroide.chilaquil.game.Constants;

import static com.fandroide.chilaquil.game.Constants.*;

/**
 * Created by usuario on 25/05/17.
 */
public class chilaquilEntity extends Actor {
    public enum State {FALLING,JUMPING,STANDING,RUNNING};
    public State currentState,previousState;
    private Texture texture;
    private World world;
    private Body body;
    private TextureRegion texturechilaquilStand,TextueRegionChilaquil,texturechilaquilJumping,texturechilaquilFalling;
    private Animation<TextureRegion> chilaquilRun,chilaquilJump;
    private float stateTimer;
    private boolean runningRight;

    public boolean isSeguirSaltando() {
        return seguirSaltando;
    }

    public void setSeguirSaltando(boolean seguirSaltando) {
        this.seguirSaltando = seguirSaltando;
    }

    private Fixture fixture;
    private boolean alive=true,jumping=false,seguirSaltando,bloqueado=false;
    private float X,Y;

    public boolean isAlive() {
        return alive;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public chilaquilEntity(World world, Texture texture, Vector2 position){
        this.world=world;
        this.texture=texture;
        BodyDef def=new BodyDef();
        def.position.set(position);
        def.type=BodyDef.BodyType.DynamicBody;
        body=world.createBody(def);

        PolygonShape box=new PolygonShape();
        X=1.5f;
        Y=1f;
        box.setAsBox((float)X/2,(float)Y/2);
        fixture=body.createFixture(box,1);
        fixture.setUserData("chilaquil");
        box.dispose();
        setSize(PIXELS_IN_METERS *X, PIXELS_IN_METERS*Y);
        currentState=State.STANDING;
        previousState=State.STANDING;
        stateTimer=0;
        runningRight=true;
        //////ASIGANDO FRAMES PARA CADA TIPO DE ANIMACION
        Array<TextureRegion> frames=new Array<TextureRegion>();
        for(int i=0;i<5;i++){
            frames.add(new TextureRegion(this.texture,i*140,0,139,105));
        }
        chilaquilRun=new Animation(0.1f,frames);//Asignando los frames para que corra
        frames.clear();
        /*for(int i=1;i<4;i++){
            frames.add(new TextureRegion(this.texture,i*152,0,152,106));//Asignando los frames para que salte
        }
        chilaquilJump=new Animation(0.1f,frames);*/
        texturechilaquilJumping=new TextureRegion(this.texture,1*139,0,138,105);//sustituto a chilaquil saltando
        texturechilaquilFalling=new TextureRegion(this.texture,3*139,0,138,105);
        texturechilaquilStand=new TextureRegion(this.texture,4*139,0,138,105);
        TextueRegionChilaquil=new TextureRegion(this.texture,4*139,0,138,105);


    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x-(X/2))*PIXELS_IN_METERS,(body.getPosition().y-(Y/2))*PIXELS_IN_METERS);
        //batch.draw(chilaquilStand,getX(),getY(),getWidth(),getHeight());
        batch.draw(TextueRegionChilaquil,getX(),getY(),getWidth(),getHeight());
        //System.out.println("se esta dibujando");
    }
    public TextureRegion getFrame(float dt){
        currentState = getState();
        TextureRegion region;
        switch (currentState){
            case JUMPING:
                //region=chilaquilJump.getKeyFrame(stateTimer);
                region=texturechilaquilJumping;
                break;
            case RUNNING:
                region=chilaquilRun.getKeyFrame(stateTimer,true);
                break;
            case FALLING:
            case STANDING:
            default:region=texturechilaquilStand;
                break;
        }
        if((body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }

        //if mario is running right and the texture isnt facing right... flip it.
        else if((body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }
        stateTimer=currentState== previousState ? stateTimer + dt:0;
        previousState = currentState;
        return region;
    }
    public State getState(){
        if((body.getLinearVelocity().y > 0 && currentState == State.JUMPING) || (body.getLinearVelocity().y < 0 && previousState == State.JUMPING)) {
            return State.JUMPING;
        }
        else if(body.getLinearVelocity().y<0){
            //return State.FALLING;
            return State.RUNNING;
        }
        else if(body.getLinearVelocity().x !=0){
            //System.out.println("velociada en y="+body.getLinearVelocity().y+" velocidad en x="+body.getLinearVelocity().x);
            return State.RUNNING;
        }
        else{
            return State.STANDING;
        }
    }
    private void setRegion(TextureRegion region){
        TextueRegionChilaquil=region;
    }
    @Override
    public void act(float delta) {
        setRegion(getFrame(delta));
        //region.setRegion(110,0,260,105);
        if(seguirSaltando){
            seguirSaltando=false;
            saltar();
        }
        if(alive && !bloqueado){
            float velocidadY=body.getLinearVelocity().y;
            body.setLinearVelocity(Constants.SPEED_CHILAQUIL,velocidadY);
        }
        if(jumping){
            body.applyForceToCenter(0f,-IMPULSE_JUMP*1.15f,true);

        }
    }

    public void saltar() {
        if(!jumping && alive){
            jumping=true;
            Vector2 position=body.getPosition();
            body.applyLinearImpulse(0, IMPULSE_JUMP,position.x,position.y,true);
        }

    }

    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
}
