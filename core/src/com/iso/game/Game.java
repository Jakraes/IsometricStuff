package com.iso.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.iso.game.Entities.EntityPlayer;
import com.iso.game.Map.Chunk;
import com.iso.game.Map.WorldMap;
import com.iso.game.Tiles.AbstractTile;
import com.iso.game.Tiles.TileAir;
import com.iso.game.Utility.Direction;
import com.iso.game.Utility.TextureManager;
import com.iso.game.Utility.Vector3;

public class Game extends ApplicationAdapter {
    SpriteBatch batch;
    int posX = 0, posY = 0;
    int zoom = 1;
    int radius = 2;
    int textureWidth = 16, textureHeight = 16;


    WorldMap map;
    EntityPlayer player;

    @Override
    public void create() {
        batch = new SpriteBatch();
        map = new WorldMap(0, 0.01f);

        boolean done = false;
        for (int y = 0; y < map.CHUNK_DEPTH; y++) {
            for (int x = 0; x < map.CHUNK_WIDTH; x++) {
                for (int z = map.CHUNK_HEIGHT - 1; z >= 0; z--) {
                    if (map.getChunk(0, 0).getTile(x, y, z).isSolid()) {
                        player = new EntityPlayer(new Vector3(x, y, z + 1));
                        done = true;
                        break;
                    }
                }
                if (done) break;
            }
            if (done) break;
        }

        Vector3 pos = player.getPosition();
        map.getChunk(0, 0).setTile(pos.x, pos.y, pos.z, player);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.792f, 0.902f, 0.961f, 1);

        posX = player.getPosition().x / map.CHUNK_WIDTH;
        posY = player.getPosition().y / map.CHUNK_DEPTH;

        System.out.println(posX + " " + posY);

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) player.move(Direction.FRONT, map);
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) player.move(Direction.BACK, map);
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) player.move(Direction.LEFT, map);
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) player.move(Direction.RIGHT, map);

        //double time = System.currentTimeMillis();
        batch.begin();

        Chunk[][] cs = new Chunk[radius * 2 + 1][radius * 2 + 1];

        for (int y = -radius; y <= radius; y++) {
            for (int x = -radius; x <= radius; x++) {
                cs[radius - y][radius - x] = map.getChunk(posX - x, posY - y);
            }
        }

        int playerX = player.getPosition().x;
        int playerY = player.getPosition().y;
        int playerZ = player.getPosition().z;

        for (int y = map.CHUNK_DEPTH * (radius * 2 + 1) - 1; y >= 0; y--) {
            for (int x = 0; x < map.CHUNK_WIDTH * (radius * 2 + 1); x++) {
                Chunk c = cs[y / 16][x / 16];
                for (int z = 0; z < map.CHUNK_HEIGHT; z++) {
                    int destX = ((x - playerX) + (y - playerY)) * (textureWidth * zoom) / 2;
                    int destY = ((y - playerY) - (x - playerX) + (z - playerZ)) * (textureHeight * zoom) / 4;

                    int tileX = x - (x / map.CHUNK_WIDTH) * map.CHUNK_WIDTH;
                    int tileY = y - (y / map.CHUNK_DEPTH) * map.CHUNK_DEPTH;
                    int tileZ = z - (z / map.CHUNK_HEIGHT) * map.CHUNK_HEIGHT;

                    AbstractTile t = c.getTile(tileX, tileY, tileZ);
                    if (t instanceof TileAir) continue;

                    batch.draw(t.getTexture(), destX, destY + 150, textureWidth * zoom, textureHeight * zoom);
                }
            }
        }

        batch.end();
        //System.out.println(System.currentTimeMillis() - time);
    }

    @Override
    public void dispose() {
        batch.dispose();
        TextureManager.dispose();
    }
}
