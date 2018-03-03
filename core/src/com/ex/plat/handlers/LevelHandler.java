package com.ex.plat.handlers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class LevelHandler {

    public static final String LEVEL_DIR = "/levels/";
    public static final String LEVEL_LIST = "/levels/levels.txt";

    private TiledMap currentMap;
    private TiledMapRenderer mapRenderer;
    private World world;

    private HashMap<String, String> maps;
    HashMap<String, Array<Body>> mapBodies;

    public LevelHandler(World world) {

        this.world = world;

        maps = new HashMap<String, String>();

    }

    public void setMap(String name) {

        loadMap(maps.get(name));
        mapRenderer = new OrthogonalTiledMapRenderer(this.currentMap);

    }

    public void readMaps() {
        String line;
        try {
            Scanner sin = new Scanner(Gdx.files.internal(LEVEL_LIST).file());
            while (sin.hasNext()) {
                line = sin.nextLine();
                maps.put(line.split(".")[0], line);
            }

            sin.close();
        } catch (FileNotFoundException e) {
            Gdx.app.log("Error:", "Level File not Found");
        }
    }

    public void render(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    public void loadMap(String mapPath) {
        this.currentMap = new TmxMapLoader().load(LEVEL_DIR + mapPath);
        createBodies(currentMap, world);

    }

    private void createBodies(TiledMap map, World world) {

        if (mapBodies != null && !mapBodies.isEmpty()) {
            for (Array<Body> bodies : mapBodies.values()) {
                for (Body body : bodies) {
                    world.destroyBody(body);
                }
            }
        }

        mapBodies = new HashMap<String, Array<Body>>();

        MapLayers layers = map.getLayers();

        for (int i = 0; i < layers.size(); i++) {
            MapLayer layer = layers.get(i);
            if (layer.getProperties() != null && layer.getProperties().containsKey("Box2D")) {
                mapBodies.put(layer.getName(), MapBodyBuilder.buildShapes(map, world, layer.getName()));
            }
        }
    }



}
