package com.sam.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.sam.entities.characters.Character;
import com.sam.entities.characters.pcs.PlayerCharacter;
import com.sam.managers.CameraManager;
import com.sam.managers.loaders.NpcLoader;
import com.sam.managers.loaders.PassagewayLoader;
import com.sam.managers.loaders.PcLoader;
import com.sam.managers.EntityManager;
import com.sam.managers.LevelManager;
import com.sam.managers.PassagewayManager;
import com.sam.managers.PcManager;

public class LevelBase {
	
	public String 			mapfile;
	public LevelManager 	levelManager;
	
	public TiledMap 		map;
	public PcManager		pcman;
	public Texture 			tex2;
	public PlayerCharacter 	guy;
	public Character		girl;
	public int 				WIDTH, HEIGHT;
	public OrthographicCamera cam;
	public CameraManager	camman;
	public OrthogonalTiledMapRenderer 	renderer;
	public PcLoader			pcLoad;
	public EntityManager	entman;
	public NpcLoader		npcLoad;
	public PassagewayLoader passload;
	public PassagewayManager	passman;

	public LevelBase(String mapname, LevelManager lm){
		mapfile = mapname;
		levelManager = lm;
		levelManager.addLevel(this);
		map = new TmxMapLoader().load(mapfile);
	}
	
	public void init() {

		pcman = new PcManager();
		entman = new EntityManager();
		passman = new PassagewayManager();
		
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		pcLoad = new PcLoader("pcs.xml", pcman, entman, map);
		pcLoad.load();
		
		npcLoad = new NpcLoader("npcs.xml", entman, map);
		npcLoad.load();
		
		passload = new PassagewayLoader("Passageways.xml", passman, map, levelManager, entman);
		passload.load();
		
		cam = new OrthographicCamera(WIDTH, HEIGHT); 
		
		camman = new CameraManager(cam, map);
		camman.itit();
		camman.target(pcman.pcList.get(0));
		
		renderer = new OrthogonalTiledMapRenderer(map, 1f);
	}
	
	public void update() {		
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		GL20 gl = Gdx.graphics.getGL20();

        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gl.glViewport(0, 0, WIDTH, HEIGHT);
           
        //camman.target(guy);
        camman.update();
        
        for(int i = 0; i < entman.entityList.size(); i++){
        	entman.entityList.get(i).update();
        }
        
        pcman.pcList.get(0).move();
	}
	
	public void draw() {
		renderer.setView(cam);
		renderer.render();
		
		renderer.getSpriteBatch().begin();
		for(int i = 0; i < entman.entityList.size(); i++){
        	entman.entityList.get(i).draw(renderer.getSpriteBatch());
        }
		for(int i = 0; i < pcman.pcList.size(); i++){
        	pcman.pcList.get(i).draw(renderer.getSpriteBatch());
        }
		renderer.getSpriteBatch().end();
	}
	
	public void dispose() {
		entman.entityList.clear();
		passman.passlist.clear();
	}
}
