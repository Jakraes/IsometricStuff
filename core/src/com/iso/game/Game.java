package com.iso.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.iso.game.Entities.EntityPlayer;
import com.iso.game.Map.WorldMap;
import com.iso.game.Tiles.AbstractTile;
import com.iso.game.Tiles.TileAir;
import com.iso.game.Utility.Direction;
import com.iso.game.Utility.TextureManager;
import com.iso.game.Utility.Vector3;

public class Game extends ApplicationAdapter {
    SpriteBatch batch;
    int zoom = 1;
    int radius = 3;
    int textureWidth = 16, textureHeight = 16;


    WorldMap map;
    EntityPlayer player;

    @Override
    public void create() {
        batch = new SpriteBatch();
        map = new WorldMap(10, 0.005f);

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

        Vector3 d = new Vector3(0, 0, 0);

        if (Gdx.input.isKeyPressed(Input.Keys.W)) d.add(Direction.UP.asVector());
        if (Gdx.input.isKeyPressed(Input.Keys.S)) d.add(Direction.DOWN.asVector());
        if (Gdx.input.isKeyPressed(Input.Keys.A)) d.add(Direction.LEFT.asVector());
        if (Gdx.input.isKeyPressed(Input.Keys.D)) d.add(Direction.RIGHT.asVector());

        player.move(d.toOne(), map);

        //double time = System.currentTimeMillis();
        batch.begin();

        for (int y = radius * map.CHUNK_DEPTH + 1; y >= -radius * map.CHUNK_DEPTH; y--) {
            for (int x = -radius * map.CHUNK_WIDTH; x <= radius * map.CHUNK_WIDTH + 1; x++) {
                for (int z = 0; z < map.CHUNK_HEIGHT; z++) {
                    int drawX = (x + y) * textureWidth * zoom / 2;
                    int drawY = (y - x + z) * textureHeight * zoom / 4;

                    if (drawX < -700 || drawX > 600) continue;
                    if (drawY < -400 || drawY > 600) continue;

                    AbstractTile t = map.getTile(player.getPosition().x + x, player.getPosition().y + y, z);
                    if (t instanceof TileAir) continue;

                    batch.draw(t.getTexture(), drawX + 600, drawY + 300, textureWidth * zoom, textureHeight * zoom);
                }
            }
        }

        /*
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

                    batch.draw(t.getTexture(), destX , destY + 150, textureWidth * zoom, textureHeight * zoom);
                }
            }
        }
        */

        batch.end();
        //System.out.println(System.currentTimeMillis() - time);
    }

    @Override
    public void dispose() {
        batch.dispose();
        TextureManager.dispose();
    }
}
