package com.fandroide.chilaquil.game.box2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.fandroide.chilaquil.game.BaseScreen;
import com.fandroide.chilaquil.game.MainGame;

/**
 * Created by usuario on 25/05/17.
 */
public class Box2DScreen extends BaseScreen {
    public Box2DScreen(MainGame game) {
        super(game);
    }
    private World world;
    private Box2DDebugRenderer renderer;
    private OrthographicCamera camera;
    private Body bodyChilaquil,bodySuelo,bodyComida;
    private Fixture fixtureChilaquil,fixtureSuelo,fixtureComida;
    private boolean chilaquilSaltando,debeSaltar=false,chilaquilVivo=true;

    @Override
    public void show() {
        world = new World(new Vector2(0,-10),true);
        renderer=new Box2DDebugRenderer();
        camera=new OrthographicCamera(16,9);
        camera.translate(3,2);
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA=contact.getFixtureA(),fixtureB=contact.getFixtureB();
                if((fixtureA.getUserData().equals("chilaquil") && fixtureB.getUserData().equals("suelo"))||
                   (fixtureB.getUserData().equals("chilaquil") && fixtureA.getUserData().equals("suelo"))){
                    if(Gdx.input.isTouched()){
                        debeSaltar=true;
                    }
                    chilaquilSaltando=false;
                }
            }

            @Override
            public void endContact(Contact contact) {
                Fixture fixtureA=contact.getFixtureA(),fixtureB=contact.getFixtureB();
                if((fixtureA.getUserData().equals("chilaquil") && fixtureB.getUserData().equals("suelo"))||
                        (fixtureB.getUserData().equals("chilaquil") && fixtureA.getUserData().equals("suelo"))){
                    System.out.println("esta saltando");
                    chilaquilSaltando=true;
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
        BodyDef chilaquilDef=createChilaquilDef();
        bodyChilaquil=world.createBody(chilaquilDef);
        PolygonShape chilaquilShape=new PolygonShape();
        chilaquilShape.setAsBox(0.5f,0.5f);
        fixtureChilaquil=bodyChilaquil.createFixture(chilaquilShape,1);
        chilaquilShape.dispose();
        /////////////////////////////////////////////////////
        BodyDef sueloDef=createSuelo();
        bodySuelo=world.createBody(sueloDef);
        PolygonShape sueloShape=new PolygonShape();
        sueloShape.setAsBox(500,1);
        fixtureSuelo=bodySuelo.createFixture(sueloShape,1);
        sueloShape.dispose();
        ///////////////////////////////////////////////7
        BodyDef comidaDef=createComida(8);
        bodyComida=world.createBody(comidaDef);
        PolygonShape comidaShape=new PolygonShape();
        comidaShape.setAsBox(0.5f,0.2f);
        fixtureComida=bodyComida.createFixture(comidaShape,1);
        comidaShape.dispose();
        fixtureChilaquil.setUserData("chilaquil");
        fixtureSuelo.setUserData("suelo");
        fixtureComida.setUserData("comida");
    }

    private BodyDef createComida(float x) {
        BodyDef def=new BodyDef();
        def.position.set(x,0.6f);
        def.type= BodyDef.BodyType.StaticBody;
        return def;
    }

    private BodyDef createSuelo() {
        BodyDef def=new BodyDef();
        def.position.set(0,-1);
        def.type= BodyDef.BodyType.StaticBody;
        return def;
    }

    private BodyDef createChilaquilDef() {
        BodyDef def=new BodyDef();
        def.position.set(1,1);
        def.type=BodyDef.BodyType.DynamicBody;
        return def;
    }

    private void Saltar(){
        Vector2 position=bodyChilaquil.getPosition();
        bodyChilaquil.applyLinearImpulse(0,5,position.x,position.y,true);
    }

    @Override
    public void dispose() {
        bodyComida.destroyFixture(fixtureComida);
        bodyChilaquil.destroyFixture(fixtureChilaquil);
        bodySuelo.destroyFixture(fixtureSuelo);
        world.destroyBody(bodyChilaquil);
        world.destroyBody(bodySuelo);
        world.destroyBody(bodyComida);
        world.dispose();
        renderer.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(debeSaltar){
            debeSaltar=false;
            Saltar();
        }
        if(Gdx.input.justTouched() && !chilaquilSaltando){
            debeSaltar=true;
        }
        float velocidadY=bodyChilaquil.getLinearVelocity().y;
        bodyChilaquil.setLinearVelocity(4,velocidadY);
        world.step(delta,6,2);
        camera.update();
        renderer.render(world,camera.combined);
    }
}
