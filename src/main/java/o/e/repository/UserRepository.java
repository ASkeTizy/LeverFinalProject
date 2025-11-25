package o.e.repository;

import o.e.entity.Comment;
import o.e.entity.Roles;
import o.e.entity.SellerInformationDTO;
import o.e.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // find by email
    Optional<User> findByEmail(String email);

    // custom query for users with comments
    @Query("SELECT DISTINCT new o.e.entity.SellerInformationDTO(u.id, u.firstName, u.lastName, u.email) " +
            "FROM User u JOIN Comment c ON u.id = c.authorId")
    List<SellerInformationDTO> findUsersWithComments();

    // update password

}
