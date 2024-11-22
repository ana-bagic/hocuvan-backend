package fer.proinz.hocuvan.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fer.proinz.hocuvan.domain.Visitor;

import java.util.List;

/**
 * Interface which communicates with the database and creates entity Visitor in
 * database.
 * 
 * @author Nina
 *
 */
@Repository
public interface VisitorRepo extends JpaRepository<Visitor, Long> {

    int countByUsername(String username);

    Visitor findByUsername(String username);

    List<Visitor> findAllByUsername(String username);

    @Query("SELECT v FROM Visitor v WHERE v.username = :username AND v.password = :password")
    Visitor validateLogin(@Param ("username") String username,@Param ("password") String password);

    @Modifying
    @Query("update Visitor v set v.lastName = :newLastName where v.id = :id")
    void updateLastName(@Param(value = "id") Long id, @Param(value = "newLastName") String newLastName);

    @Modifying
    @Query("update Visitor v set v.firstName = :newFirstName where v.id = :id")
    void updateFirstName(@Param(value = "id") Long id, @Param(value = "newFirstName") String newFirstName);

    @Modifying
    @Query("update Visitor v set v.username = :newUsername where v.id = :id")
    void updateUsername(@Param(value = "id") Long id, @Param(value = "newUsername") String newUsername);

    @Modifying
    @Query("update Visitor v set v.password = :newPassword where v.id = :id")
    void updatePassword(@Param(value = "id") Long id, @Param(value = "newPassword") String newPassword);

    @Modifying
    @Query("update Visitor v set v.email = :newEmail where v.id = :id")
    void updateEmail(@Param(value = "id") Long id, @Param(value = "newEmail") String newEmail);

    @Modifying
    @Query("update Visitor v set v.phoneNumber = :newPhoneNumber where v.id = :id")
    void updatePhoneNumber(@Param(value = "id") Long id, @Param(value = "newPhoneNumber") String newPhoneNumber);



    boolean existsByUsernameAndIdNot(String username, Long id);

    boolean existsByEmailAndIdNot(String email, Long id);
}
