package o.e.repository;

import o.e.entity.GameObject;
import o.e.entity.SellerInformationDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameObjectRepository extends JpaRepository<GameObject,Long> {
    @Query("SELECT new o.e.entity.SellerInformationDTO(u.id, u.firstName, u.lastName, u.email) " +
            "FROM GameObject g " +
            "JOIN Comment c ON c.authorId = g.userId " +
            "JOIN User u ON u.id = c.authorId " +
            "WHERE g.gameId = :gameId AND c.rate BETWEEN :startRate AND :endRate")
    List<SellerInformationDTO> findUsersByGameAndRate(@Param("gameId") Integer gameId,
                                                      @Param("startRate") Integer startRate,
                                                      @Param("endRate") Integer endRate);
}
