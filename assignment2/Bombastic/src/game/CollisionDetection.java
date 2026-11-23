package game;

import entities.Entity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CollisionDetection {

    private static final ExecutorService executor = Executors.newFixedThreadPool(3);

    // AABB kollisionstjek mellem to entities
    public static boolean aabb(Entity a, Entity b) {
        return a.getPositionX() + a.getWidth()  > b.getPositionX() &&
               b.getPositionX() + b.getWidth()  > a.getPositionX() &&
               a.getPositionY() + a.getHeight() > b.getPositionY() &&
               b.getPositionY() + b.getHeight() > a.getPositionY();
    }

    // Finder alle kollisionspar concurrent — interaktioner håndteres af kalderen
    public static List<CollisionPair> findCollisionsConcurrently(
            List<? extends Entity> as,
            List<? extends Entity> bs) throws InterruptedException {

        List<CollisionPair> results = Collections.synchronizedList(new ArrayList<>());
        List<Callable<Void>> tasks = new ArrayList<>();

        for (Entity a : as) {
            tasks.add(() -> {
                for (Entity b : bs)
                    if (aabb(a, b)) results.add(new CollisionPair(a, b));
                return null;
            });
        }

        executor.invokeAll(tasks);
        return results;
    }

    // Par af entities der kolliderer
    public record CollisionPair(Entity a, Entity b) {}
}
