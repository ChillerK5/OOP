package ru.nsu.kbagryantsev;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import static com.almasb.fxgl.dsl.FXGL.*;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.ui.FontFactory;
import com.almasb.fxgl.ui.FontType;
import java.util.Map;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import static ru.nsu.kbagryantsev.EntityType.*;

public class SnakeGame extends GameApplication {
    public static final int BLOCK_SIZE = 16;
    public static final int MAP_SIZE = 100;
    private final GameFactory gameFactory = new GameFactory();
    private Entity player;

    /**
     * @param settings game settings
     */
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Snake-line Miami");
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.getCSSList().add("background.css");
        settings.setTicksPerSecond(15);
    }

    @Override
    protected void initInput() {
        onKeyDown(KeyCode.UP, () -> player.getComponentOptional(SnakeHeadComponent.class).ifPresent(SnakeHeadComponent::up));
        onKeyDown(KeyCode.LEFT, () -> player.getComponentOptional(SnakeHeadComponent.class).ifPresent(SnakeHeadComponent::left));
        onKeyDown(KeyCode.DOWN, () -> player.getComponentOptional(SnakeHeadComponent.class).ifPresent(SnakeHeadComponent::down));
        onKeyDown(KeyCode.RIGHT, () -> player.getComponentOptional(SnakeHeadComponent.class).ifPresent(SnakeHeadComponent::right));

        onKey(KeyCode.F, () -> player.call("grow"));
    }

    @Override
    protected void initGame() {
        // Background color
        getGameScene().getRoot().getStyleClass().add("root");

        // Entity factory
        getGameWorld().addEntityFactory(gameFactory);

        setLevelFromMap("tmx/level0.tmx");

        player = spawn("SnakeHead", 16, 16);
        set("score", 0);

//        AStarGrid grid = AStarGrid.fromWorld(getGameWorld(), MAP_SIZE, MAP_SIZE, BLOCK_SIZE, BLOCK_SIZE, (type) -> {
//            if (type == OBSTACLE) {
//                return CellState.NOT_WALKABLE;
//            }
//            return CellState.WALKABLE;
//        });
//        set("grid", grid);

        Viewport viewport = getGameScene().getViewport();
        viewport.setZoom(1.4);
        viewport.setBounds(-100, -100, BLOCK_SIZE * MAP_SIZE + 100, BLOCK_SIZE * MAP_SIZE + 100);
        viewport.bindToEntity(player, (double) getAppWidth() / 2, (double) getAppHeight() / 2);
    }

    @Override
    protected void initUI() {
        Font discoDuck = getAssetLoader().loadFont("discoduckv2i.ttf").newFont(64);

        Text scoreLabel = new Text(24, 64, null);
        scoreLabel.setFont(discoDuck);
        scoreLabel.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, new Stop(0, Color.AQUA), new Stop(1, Color.WHEAT)));
        animationBuilder(getGameScene())
                .duration(Duration.seconds(2.0))
                .repeatInfinitely()
                .autoReverse(true)
                .rotate(scoreLabel)
                .from(-3)
                .to(3)
                .buildAndPlay();
        animationBuilder()
                .duration(Duration.seconds(2.0))
                .repeatInfinitely()
                .autoReverse(true)
                .scale(scoreLabel)
                .from(new Point2D(1, 1))
                .to(new Point2D(1.1, 1.1))
                .buildAndPlay();
        scoreLabel.textProperty().bind(getip("score").asString("Score %d"));

        getGameScene().addUINodes(scoreLabel);
    }

    @Override
    protected void initPhysics() {
        FXGL.onCollision(SNAKE_HEAD, OBSTACLE, (snake, obstacle) -> snake.call("die"));
        FXGL.onCollision(SNAKE_HEAD, SNAKE_BODY, (head, body) -> head.call("die"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
