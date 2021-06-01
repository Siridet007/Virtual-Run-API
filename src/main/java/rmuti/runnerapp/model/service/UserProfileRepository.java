package rmuti.runnerapp.model.service;

import org.springframework.data.jpa.repository.JpaRepository;
import rmuti.runnerapp.model.table.UserProfile;

import java.util.List;

public interface UserProfileRepository extends JpaRepository<UserProfile,Integer> {
    UserProfile findByUserNameAndPassWord(String userName,String passWord);
    UserProfile findByUserName(String userName);
    List<UserProfile> findByUserId(int userId);
    List<UserProfile> findByAu(String Au);
}
