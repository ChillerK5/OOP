package ru.nsu.kbagryantsev;

import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.dsl.FXGL;
import static com.almasb.fxgl.dsl.FXGL.geto;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.pathfinding.CellMoveComponent;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import static ru.nsu.kbagryantsev.SnakeGame.BLOCK_SIZE;

public class GameFactory implements EntityFactory {

    @Spawns("H")
    public Entity newSnakeHead(SpawnData spawnData) {
        return FXGL.entityBuilder(spawnData)
                .type(EntityType.SNAKE_HEAD)
                .viewWithBBox(new Rectangle(BLOCK_SIZE, BLOCK_SIZE, Color.AQUA))
                .with(new CellMoveComponent(BLOCK_SIZE, BLOCK_SIZE, 1000))
                .with(new AStarMoveComponent(new LazyValue<>(() -> geto("grid"))))
                .with(new SnakeHeadComponent())
                .collidable()
                .build();
    }

    @Spawns("B")
    public Entity newSnakeBody(SpawnData spawnData) {
        return FXGL.entityBuilder(spawnData)
                .type(EntityType.SNAKE_BODY)
                .viewWithBBox(new Rectangle(BLOCK_SIZE, BLOCK_SIZE, Color.AQUA))
                .with(new CellMoveComponent(BLOCK_SIZE, BLOCK_SIZE, 1000))
                .with(new AStarMoveComponent(new LazyValue<>(() -> geto("grid"))))
                .collidable()
                .build();
    }

    @Spawns("F")
    public Entity newFood(SpawnData spawnData) {
        return FXGL.entityBuilder(spawnData)
                .type(EntityType.FOOD)
                .viewWithBBox(new Circle(10, 10, 10, Color.GREEN))
                .collidable()
                .build();
    }
}
