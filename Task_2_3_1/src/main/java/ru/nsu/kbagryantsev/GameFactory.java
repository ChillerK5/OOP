package ru.nsu.kbagryantsev;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import static ru.nsu.kbagryantsev.EntityType.*;

public class GameFactory implements EntityFactory {
    @Spawns("Obstacle")
    public Entity newObstacle(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(OBSTACLE)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .collidable()
                .build();
    }

    @Spawns("SnakeHead")
    public Entity newSnakeHead(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(SNAKE_HEAD)
                .view(new Rectangle(16, 16, Color.DARKCYAN))
                .bbox(new HitBox(new Point2D(1, 1), BoundingShape.box(14, 14)))
                .with(new SnakeHeadComponent())
                .collidable()
                .build();
    }

    @Spawns("SnakeBody")
    public Entity newSnakeBody(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(SNAKE_BODY)
                .view(new Rectangle(16, 16, Color.DARKSLATEGRAY))
                .bbox(new HitBox(new Point2D(1, 1), BoundingShape.box(14, 14)))
                .collidable()
                .build();
    }
}
