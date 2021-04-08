package rmuti.runnerapp.model.service;

import org.springframework.data.jpa.repository.JpaRepository;
import rmuti.runnerapp.model.table.TestDataUser;

import java.util.List;

public interface TestDataUserRepository extends JpaRepository<TestDataUser,Integer> {
    List<TestDataUser> findByType(String type);
    List<TestDataUser> findByUserIdAndType(int userId,String type);
    List<TestDataUser> findByUserIdAndId(int userId,int Id);
    List<TestDataUser> findByTid(int Tid);
}
