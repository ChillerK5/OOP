package ru.nsu.kbagryantsev;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.pathfinding.CellMoveComponent;
import com.almasb.fxgl.pathfinding.astar.AStarCell;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import java.util.ArrayList;
import java.util.List;
import static ru.nsu.kbagryantsev.SnakeGame.BLOCK_SIZE;
import static ru.nsu.kbagryantsev.SnakeHeadComponent.MoveDirection.*;

@Required(AStarMoveComponent.class)
public class SnakeHeadComponent extends Component {
    enum MoveDirection {
        UP, RIGHT, DOWN, LEFT, STOP
    }
    private MoveDirection direction = RIGHT;
    private final List<Entity> bodyParts = new ArrayList<>();
    private final List<AStarMoveComponent> bodyPartsAStarMoveComponents = new ArrayList<>();
    private final List<CellMoveComponent> bodyPartsCellMoveComponents = new ArrayList<>();
    private CellMoveComponent moveComponent;
    private AStarMoveComponent aStarMoveComponent;

    public void moveUp() {
        direction = UP;
    }

    public void moveDown() {
        direction = DOWN;
    }

    public void moveLeft() {
        direction = LEFT;
    }

    public void moveRight() {
        direction = RIGHT;
    }

    @Override
    public void onUpdate(double tpf) {
        int x = moveComponent.getCellX();
        int y = moveComponent.getCellY();
        entity.setProperty("previousX", x);
        entity.setProperty("previousY", y);

        if (aStarMoveComponent.isMoving())
            return;

        switch (direction) {
            case UP -> {
                if (aStarMoveComponent.getGrid().getUp(x, y).filter(c -> c.getState().isWalkable()).isPresent())
                    aStarMoveComponent.moveToUpCell();
            }
            case RIGHT -> {
                if (aStarMoveComponent.getGrid().getRight(x, y).filter(c -> c.getState().isWalkable()).isPresent())
                    aStarMoveComponent.moveToRightCell();
            }
            case DOWN -> {
                if (aStarMoveComponent.getGrid().getDown(x, y).filter(c -> c.getState().isWalkable()).isPresent())
                    aStarMoveComponent.moveToDownCell();
            }
            case LEFT -> {
                if (aStarMoveComponent.getGrid().getLeft(x, y).filter(c -> c.getState().isWalkable()).isPresent())
                    aStarMoveComponent.moveToLeftCell();
            }
        }

        for (int i = 1; i < bodyParts.size(); i++) {
            Entity currentBodyPart = bodyParts.get(i);
            Entity previousBodyPart = bodyParts.get(i - 1);
            currentBodyPart.setProperty("previousX", currentBodyPart.call("getCellX"));
            currentBodyPart.setProperty("previousY", currentBodyPart.call("getCellY"));
            currentBodyPart.call("moveToCell",
                    previousBodyPart.getProperties().getInt("previousX"),
                    previousBodyPart.getProperties().getInt("previousY"));
        }
    }
    @Override
    public void onAdded() {
        bodyParts.add(entity);

        entity.setProperty("previousX", moveComponent.getCellX());
        entity.setProperty("previousY", moveComponent.getCellY());
    }

    private void checkWorldBounds() {

    }

    private void die() {
        bodyParts.forEach(Entity::removeFromWorld);
        bodyParts.clear();
    }

    public void grow() {
        Entity lastBodyPart = bodyParts.get(bodyParts.size() - 1);
        AStarMoveComponent lastBodyPartAStar = bodyPartsAStarMoveComponents.get(bodyPartsAStarMoveComponents.size() - 1);

        int previousX = lastBodyPart.getObject("previousX");
        int previousY = lastBodyPart.getObject("previousY");

        Entity body = spawn("B", new SpawnData(previousX * BLOCK_SIZE, previousY * BLOCK_SIZE));

        bodyParts.add(body);
    }
}
