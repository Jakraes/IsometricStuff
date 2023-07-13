package com.iso.game.Entities;

import com.badlogic.gdx.math.Vector3;
import com.iso.game.Direction;
import com.iso.game.Map.AbstractMap;
import com.iso.game.Tiles.AbstractTile;
import com.iso.game.Tiles.TileAir;

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

    // TODO NEEDS POLISHING, A LOT OF IT
    public void move(Direction direction, AbstractMap map) {
        Vector3 newPos = new Vector3(position).add(direction.asVector());

        map.setTile((int) position.x, (int) position.y, (int) position.z, new TileAir());

        AbstractTile nextTile = map.getTile((int) newPos.x, (int) newPos.y, (int) newPos.z);
        if (nextTile.isSolid()) {
            nextTile = map.getTile((int) newPos.x, (int) newPos.y, (int) newPos.z + 1);
            if (!nextTile.isSolid()) setPosition((int) newPos.x, (int) newPos.y, (int) newPos.z + 1);
        } else {
            nextTile = map.getTile((int) newPos.x, (int) newPos.y, (int) newPos.z - 1);
            if (nextTile.isSolid()) setPosition(newPos);
            else {
                nextTile = map.getTile((int) newPos.x, (int) newPos.y, (int) newPos.z - 2);
                if (nextTile.isSolid()) setPosition((int) newPos.x, (int) newPos.y, (int) newPos.z - 1);
            }
        }

        map.setTile((int) position.x, (int) position.y, (int) position.z, this);
    }
}
