package shop.yeblog.model.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


  @Query("select u from User u where u.username= :username")
  Optional<User> findByUsername(@Param("username") String username);

  @Query("select 1 from User u where u.id in :userIds")
  List<User> findByUserIds(@Param("userIds") List<Long> userIds);
  @Modifying
  @Query("update User u set u.password = :password, u.email = :email where u.id = :userId")
  void updateUserCredentials(@Param("password") String newPassword, @Param("email") String newEmail, @Param("userId") Long userId);


}
