package ru.nsu.kbagryantsev;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.onKey;
import static com.almasb.fxgl.dsl.FXGLForKtKt.onKeyDown;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.text.TextLevelLoader;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import javafx.scene.input.KeyCode;
import static ru.nsu.kbagryantsev.EntityType.OBSTACLE;
import static ru.nsu.kbagryantsev.EntityType.SNAKE_HEAD;

public class SnakeGame extends GameApplication {
    public static final int BLOCK_SIZE = 20;
    public static final int MAP_SIZE = 100;

    private final GameFactory gameFactory = new GameFactory();
    private Entity player;
    private AStarGrid grid;
    public AStarGrid getGrid() {return grid;}

    /**
     * @param settings game settings
     */
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Snake-line Miami");
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setTicksPerSecond(30);
    }

    @Override
    protected void initInput() {
        onKeyDown(KeyCode.UP, () -> {
            player.getComponentOptional(SnakeHeadComponent.class).ifPresent(SnakeHeadComponent::moveUp);
            return null;
        });
        onKeyDown(KeyCode.LEFT, () -> {
            player.getComponentOptional(SnakeHeadComponent.class).ifPresent(SnakeHeadComponent::moveLeft);
            return null;
        });
        onKeyDown(KeyCode.DOWN, () -> {
            player.getComponentOptional(SnakeHeadComponent.class).ifPresent(SnakeHeadComponent::moveDown);
            return null;
        });
        onKeyDown(KeyCode.RIGHT, () -> {
            player.getComponentOptional(SnakeHeadComponent.class).ifPresent(SnakeHeadComponent::moveRight);
            return null;
        });

        onKey(KeyCode.F, () -> player.call("grow"));
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(gameFactory);

        Level level = getAssetLoader().loadLevel("level1.txt",
                new TextLevelLoader(BLOCK_SIZE, BLOCK_SIZE, '0'));
        getGameWorld().setLevel(level);

        AStarGrid grid = AStarGrid.fromWorld(getGameWorld(), MAP_SIZE, MAP_SIZE, BLOCK_SIZE, BLOCK_SIZE, (type) -> {
            if (type == OBSTACLE) {
                return CellState.NOT_WALKABLE;
            }
            return CellState.WALKABLE;
        });

        player = getGameWorld().getSingleton(SNAKE_HEAD);

        set("grid", grid);

        Viewport viewport = getGameScene().getViewport();
        viewport.setBounds(0, 0, 2000, 2000);
        viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
    }

    @Override
    protected void initPhysics() {
        FXGL.onCollisionCollectible(EntityType.SNAKE_HEAD, EntityType.FOOD, food -> {
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
