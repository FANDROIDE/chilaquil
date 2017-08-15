package com.fandroide.chilaquil.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fandroide.chilaquil.game.nivel.nivel1;

public class MainGame extends Game {
	private AssetManager manager;

	public AssetManager getManager(){
		return manager;
	}

	@Override
	public void create() {
		manager = new AssetManager();//////////cargando las texturas que se usaran en el juego
		manager.load("actores/chilaquil1.png",Texture.class);
		manager.load("actores/policia.png",Texture.class);
		manager.load("actores/chilaquilTransparente.png",Texture.class);
		manager.load("actores/comidaChilaquil.jpg",Texture.class);
		manager.load("actores/piso.jpg",Texture.class);
		manager.load("actores/floor.png",Texture.class);
		manager.load("actores/overfloor.png",Texture.class);
		manager.load("actores/basurero.png",Texture.class);
		manager.load("actores/portal.png",Texture.class);
		manager.load("audio/stinger.ogg", Music.class);
		manager.load("audio/jump.ogg", Sound.class);
		manager.finishLoading();
		setScreen(new nivel1(this));
	}
}
