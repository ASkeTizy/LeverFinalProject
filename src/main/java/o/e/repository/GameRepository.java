package o.e.repository;

import o.e.entity.Game;
import o.e.entity.GameObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game,Long> {
    Game findByName(String name);
    void deleteById(Long objectId);

}
