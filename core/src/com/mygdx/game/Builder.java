package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public interface Builder {
	Builder sprite(Sprite sprite);
    Builder vidas(int vidas);
    Builder speed(float speed);
    Builder position(Vector2 position);
    Builder velocity(Vector2 velocity);
    
    Builder collisionRectangle(Rectangle collisionRectangle);
    Builder reset();
}