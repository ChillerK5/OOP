package ru.nsu.kbagryantsev;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import static ru.nsu.kbagryantsev.SnakeGame.BLOCK_SIZE;

public class SnakeHeadComponent extends Component {
    private Point2D direction = new Point2D(1, 0);

    private final List<Entity> bodyParts = new ArrayList<>();

    @Override
    public void onAdded() {
        bodyParts.add(entity);

        entity.setProperty("previousPosition", entity.getPosition());
    }

    @Override
    public void onUpdate(double tpf) {
        entity.setProperty("previousPosition", entity.getPosition());
        entity.translate(direction.multiply(BLOCK_SIZE));

        for (int i = 1; i < bodyParts.size(); i++) {
            var previousPart = bodyParts.get(i - 1);
            var part = bodyParts.get(i);

            Point2D previousPosition = previousPart.getObject("previousPosition");

            part.setProperty("previousPosition", part.getPosition());
            part.setPosition(previousPosition);
        }
    }

    public void die() {
        bodyParts.forEach(Entity::removeFromWorld);
        bodyParts.clear();
    }

    public void up() {
        if (!direction.equals(new Point2D(0, 1))) {
            direction = new Point2D(0, -1);
        }
    }

    public void down() {
        if (!direction.equals(new Point2D(0, -1))) {
            direction = new Point2D(0, 1);
        }
    }

    public void left() {
        if (!direction.equals(new Point2D(1, 0))) {
            direction = new Point2D(-1, 0);
        }
    }

    public void right() {
        if (!direction.equals(new Point2D(-1, 0))) {
            direction = new Point2D(1, 0);
        }
    }

    public void grow() {
        Entity lastBodyPart = bodyParts.get(bodyParts.size() - 1);

        Point2D previousPosition = lastBodyPart.getObject("previousPosition");

        Entity body = spawn("SnakeBody", previousPosition);
        inc("score", 1);

        bodyParts.add(body);
    }
}
