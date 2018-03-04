package com.ex.plat.handlers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import static com.ex.plat.Constants.PPM;
import static com.ex.plat.Constants.getBits;

public class LevelHandler {

    public static final String LEVEL_DIR = "levels/";
    public static final String LEVEL_LIST = "levels/levels.txt";

    private TiledMap currentMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private World world;

    private Vector2 playerStart;

    private HashMap<String, String> maps;
    HashMap<String, Array<Body>> mapBodies;

    public LevelHandler(World world) {

        this.world = world;
        maps = new HashMap<String, String>();
        mapBodies = new HashMap<String, Array<Body>>();
        readMaps();

    }

    public void setMap(String name) {

        b2dWorldDestroyer(world);
        loadMap(maps.get(name));
        mapRenderer = new OrthogonalTiledMapRenderer(this.currentMap);

    }

    public void readMaps() {
        String line;
        try {
            Scanner sin = new Scanner(Gdx.files.internal(LEVEL_LIST).file());
            while (sin.hasNext()) {
                line = sin.nextLine();
                maps.put(line.split("\\.")[0], line);
            }

            sin.close();
        } catch (FileNotFoundException e) {
            Gdx.app.log("Error", "Level File not Found");
        }
    }

    public void render(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    public void loadMap(String name) {
        this.currentMap = new TmxMapLoader().load(LEVEL_DIR + name);
        b2dWorldCreator(currentMap, world);

    }
    public void dispose() {
        b2dWorldDestroyer(world);
        currentMap.dispose();
        mapRenderer.dispose();
    }

    public TiledMap getMap() {
        return currentMap;
    }


    private void b2dWorldCreator(TiledMap map, World world) {

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Array<Body> bodies = new Array<Body>();
        short bits;

        //generate based on layers
        for (int i = 2; i<=5; i++) {
            bodies.clear();
            bits = getBits(i);
            for (MapObject object : map.getLayers().get(i).getObjects().getByType(RectangleMapObject.class)) {

                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                bdef.type = BodyDef.BodyType.StaticBody;
                bdef.position.set((rect.getX() + rect.getWidth() / 2) / PPM, (rect.getY() + rect.getHeight() / 2) / PPM);

                Body body = world.createBody(bdef);

                shape.setAsBox(rect.getWidth() / 2 / PPM, rect.getHeight() / 2 / PPM);
                fdef.shape = shape;
                fdef.filter.categoryBits = bits;
                body.createFixture(fdef);

                bodies.add(body);
            }

            mapBodies.put(map.getLayers().get(i).getName(), bodies);

        }

        Rectangle playerRect = ((RectangleMapObject) map.getLayers().get(8).getObjects().get(0)).getRectangle();
        playerStart = new Vector2(playerRect.getX() / 2, playerRect.getY() / 2);

        shape.dispose();

    }

    private void b2dWorldDestroyer(World world) {
        if (mapBodies != null && !mapBodies.isEmpty()) {
            for (Array<Body> bodies : mapBodies.values()) {
                for (Body body : bodies) {
                    world.destroyBody(body);
                }
            }
        }
    }

    public Vector2 getPlayerStart() {
        return playerStart;
    }
}
