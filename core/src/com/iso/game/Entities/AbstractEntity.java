package com.iso.game.Entities;

import com.iso.game.Map.AbstractMap;
import com.iso.game.Tiles.AbstractTile;
import com.iso.game.Tiles.TileAir;
import com.iso.game.Utility.Vector3;

public abstract class AbstractEntity extends AbstractTile {
    Vector3 position;

    public AbstractEntity(int index, Vector3 position) {
        super(index, true);
        this.position = position;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public void setPosition(int x, int y, int z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }

    // TODO needs some polishing I think
    public void move(Vector3 direction, AbstractMap map) {
        Vector3 newPos = new Vector3(position).add(direction);

        map.setTile(position.x, position.y, position.z, new TileAir());

        AbstractTile nextTile = map.getTile(newPos.x, newPos.y, newPos.z);
        if (nextTile.isSolid()) {
            nextTile = map.getTile(newPos.x, newPos.y, newPos.z + 1);
            if (!nextTile.isSolid()) setPosition(newPos.x, newPos.y, newPos.z + 1);
        } else {
            nextTile = map.getTile(newPos.x, newPos.y, newPos.z - 1);
            if (nextTile.isSolid()) setPosition(newPos);
            else {
                nextTile = map.getTile(newPos.x, newPos.y, newPos.z - 2);
                if (nextTile.isSolid()) setPosition(newPos.x, newPos.y, newPos.z - 1);
            }
        }

        map.setTile(position.x, position.y, position.z, this);
    }
}
