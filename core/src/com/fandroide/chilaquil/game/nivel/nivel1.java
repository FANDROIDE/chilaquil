package com.fandroide.chilaquil.game.nivel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.fandroide.chilaquil.game.BaseScreen;
import com.fandroide.chilaquil.game.MainGame;
import com.fandroide.chilaquil.game.entities.FloorEntity;
import com.fandroide.chilaquil.game.entities.basureroEntity;
import com.fandroide.chilaquil.game.entities.chilaquilEntity;
import com.fandroide.chilaquil.game.entities.comidaEntity;
import com.fandroide.chilaquil.game.entities.pisoEntity;
import com.fandroide.chilaquil.game.entities.policiaEntity;
import com.fandroide.chilaquil.game.entities.portalEntity;

import java.util.ArrayList;
import java.util.List;

import static com.fandroide.chilaquil.game.Constants.*;

/**
 * Created by usuario on 25/05/17.
 */
public class nivel1 extends BaseScreen {
    private Stage stage;
    private World world;
    private chilaquilEntity chilaquil;
    private List<FloorEntity> floorList = new ArrayList<FloorEntity>();
    private comidaEntity comida;
    private basureroEntity basurero;
    private portalEntity portal;
    private Sound soundJump;
    private Music musica;
    private float renderX,renderY;
    //private Box2DDebugRenderer renderer;
    //private OrthographicCamera camera;
    public nivel1(MainGame game) {
        super(game);
        soundJump=game.getManager().get("audio/jump.ogg");
        musica=game.getManager().get("audio/stinger.ogg");
        stage=new Stage(new FitViewport(640,360));
        world=new World(new Vector2(0,-10),true);
        world.setContactListener(new ContactListener() {

            private boolean areCollided(Contact contact, Object userA, Object userB){
                return (contact.getFixtureA().getUserData().equals(userA) && contact.getFixtureB().getUserData().equals(userB)) ||
                        (contact.getFixtureA().getUserData().equals(userB) && contact.getFixtureB().getUserData().equals(userA));
            }
            @Override
            public void beginContact(Contact contact) {
                if(areCollided(contact,"chilaquil","piso")){
                    chilaquil.setJumping(false);
                    if(Gdx.input.isTouched()){
                        soundJump.play();
                        chilaquil.setSeguirSaltando(true);
                    }
                }
                if(areCollided(contact,"chilaquil","policia")){
                    chilaquil.setAlive(false);
                }
                if(areCollided(contact,"chilaquil","pared")){
                    chilaquil.setBloqueado(true);
                }
                if(areCollided(contact,"chilaquil","comida")){
                    //chilaquil.setBloqueado(true);
                    comida.setAlive(false);
                }

            }

            @Override
            public void endContact(Contact contact) {
                if(areCollided(contact,"chilaquil","pared")){
                    chilaquil.setBloqueado(false);
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
        renderX=0f;
        renderY=0f;
    }

    @Override
    public void show() {
        //renderer=new Box2DDebugRenderer();
        //camera=new OrthographicCamera(Gdx.graphics.getWidth()/Constants.PIXELS_IN_METERS,Gdx.graphics.getHeight()/Constants.PIXELS_IN_METERS);
        ////AQUI SE CARGAN LOS ACTORES (SE CONSTRUYE EL NIVEL)
        Texture textureChilaquil=game.getManager().get("actores/chilaquilTransparente.png");
        Texture textureComida=game.getManager().get("actores/comidaChilaquil.jpg");
        Texture textureFloor=game.getManager().get("actores/floor.png");
        Texture textureOverFloor=game.getManager().get("actores/overfloor.png");
        Texture textureBasurero=game.getManager().get("actores/basurero.png");
        Texture texturePortal=game.getManager().get("actores/portal.png");
        chilaquil=new chilaquilEntity(world,textureChilaquil,new Vector2(1,2));
        comida=new comidaEntity(world,textureComida,new Vector2(6,1));
        //piso=new pisoEntity(world,texturePiso,new Vector2(0,0));
        //escalon1=new FloorEntity(world,textureFloor,textureOverFloor,0,100,0.5f);
        //piso=new FloorEntity(world,textureFloor,textureOverFloor,30,20,3.5f);
        floorList.add(new FloorEntity(world,textureFloor,textureOverFloor,0,100,0.5f));
        floorList.add(new FloorEntity(world,textureFloor,textureOverFloor,30,20,3.5f));
        floorList.add(new FloorEntity(world,textureFloor,textureOverFloor,10,5,1.5f));
        portal= new portalEntity(world,texturePortal,new Vector2(8,1));

        ///basureros
        basurero=new basureroEntity(world,textureBasurero,new Vector2(60,1));
        stage.addActor(chilaquil);
        stage.addActor(comida);
        stage.addActor(basurero);
        stage.addActor(portal);
        //stage.addActor(piso);
        //stage.addActor(escalon1);
        for (FloorEntity floor : floorList)
            stage.addActor(floor);
        musica.setVolume(0.05f);
        musica.play();
    }

    @Override
    public void hide() {
        chilaquil.detach();
        chilaquil.remove();
        comida.detach();
        comida.remove();
        basurero.detach();
        basurero.remove();
        portal.detach();
        portal.remove();
        /*piso.detach();
        piso.remove();
        escalon1.detach();
        escalon1.remove();*/
        for (FloorEntity floor : floorList)
            floor.detach();
        floorList.clear();
        //renderer.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0.74902f,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(chilaquil.getX()>100 && chilaquil.isAlive() && !chilaquil.isBloqueado()){
            stage.getCamera().translate(SPEED_CHILAQUIL*delta* PIXELS_IN_METERS,0,0);
            //camera.translate(0.05f,0);
        }
        if(Gdx.input.justTouched() && !chilaquil.isJumping() && chilaquil.isAlive()){
            soundJump.play();
            chilaquil.saltar();
        }
        renderX=Gdx.input.getAccelerometerX();
        renderY=Gdx.input.getAccelerometerY();
        stage.act();
        world.step(delta,6,2);
        stage.draw();
        //camera.update();

        //renderer.render(world,camera.combined);
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }
}