package com.sam.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.sam.managers.EntityManager;

public class Entity extends Sprite {
	
	public String 					name;
	public TiledMap					map;
	public Boolean 					move;
	public Texture					texture;
	public Vector2					pos;
	public TiledMapTileLayer.Cell	cell;
	public MapLayer					layer1;
	public TiledMapTileLayer 		layer2;
	public TiledMapTile				tile;
	public EntityManager			entityManager;
	
	
	public Entity(String title, TiledMap mip, boolean moveable, Texture tex, float x, float y, EntityManager entman){
		super(tex);
		this.setPosition(x, y);
		name = title;
		map = mip;
		move = moveable;
		layer1 = map.getLayers().get(2);
		layer2 = (TiledMapTileLayer) layer1;
		cell = layer2.getCell((int)(getX()/16), (int)(getY()/16));
		if(cell != null){
			tile = cell.getTile();
		}
		entityManager = entman;
		entityManager.add(this);
	}
	
	public TiledMapTileLayer.Cell getUp(){
		return layer2.getCell((int) getX()/16, (int)getY()/16+1);
	}
	public TiledMapTileLayer.Cell getDown(){
		return layer2.getCell((int) getX()/16, (int)getY()/16-1);
	}
	public TiledMapTileLayer.Cell getRight(){
		return layer2.getCell((int) getX()/16+1, (int)getY()/16);
	}
	public TiledMapTileLayer.Cell getLeft(){
		return layer2.getCell((int) getX()/16-1, (int)getY()/16);
	}
	
	public boolean isObjectUp(){
		if(getUp() != null){
			if(getUp().getTile().getProperties().containsKey("wall")){
				return true;
			}
		}else if(getUpEntity() != null){
			return true;
		}
		return false;
	}
	
	public boolean isObjectDown(){
		if(getDown() != null){
			if(getDown().getTile().getProperties().containsKey("wall")){
				return true;
			}
		}else if(getDownEntity() != null){
			return true;
		}
		return false;
	}
	
	public boolean isObjectRight(){
		if(getRight() != null){
			if(getRight().getTile().getProperties().containsKey("wall")){
				return true;
			}
		}else if(getRightEntity() != null){
			return true;
		}
		return false;
	}
	
	public boolean isObjectLeft(){
		if(getLeft() != null){
			if(getLeft().getTile().getProperties().containsKey("wall")){
				return true;
			}
		}else if(getLeftEntity() != null){
			return true;
		}
		return false;
	}
	
	
	public Entity getUpEntity(){
		for(int i = 0; i < entityManager.entityList.size(); i++){
			if(entityManager.entityList.get(i).getX() == this.getX() && entityManager.entityList.get(i).getY() == this.getY() + 16){
				return entityManager.entityList.get(i);
			}
		}
		return null;
	}
	
	public Entity getDownEntity(){
		for(int i = 0; i < entityManager.entityList.size(); i++){
			if(entityManager.entityList.get(i).getX() == this.getX() && entityManager.entityList.get(i).getY() == this.getY() - 16){
				return entityManager.entityList.get(i);
			}
		}
		return null;
	}
	
	public Entity getRightEntity(){
		for(int i = 0; i < entityManager.entityList.size(); i++){
			if(entityManager.entityList.get(i).getX() == this.getX() + 16 && entityManager.entityList.get(i).getY() == this.getY()){
				return entityManager.entityList.get(i);
			}
		}
		return null;
	}
	
	public Entity getLeftEntity(){
		for(int i = 0; i < entityManager.entityList.size(); i++){
			if(entityManager.entityList.get(i).getX() == this.getX() - 16 && entityManager.entityList.get(i).getY() == this.getY()){
				return entityManager.entityList.get(i);
			}
		}
		return null;
	}
}