package rmuti.runnerapp.model.service;

import org.springframework.data.jpa.repository.JpaRepository;
import rmuti.runnerapp.model.table.TestAll;

import java.util.List;

public interface TestAllRepository extends JpaRepository<TestAll,Integer> {
    List<TestAll> findAllByType(String type);

    List<TestAll> findByid(int id);

    List<TestAll> findByIdAndUserId(int id,int UserId);

    List<TestAll> findByTypeAndUserId(String type,int UserId);

    List<TestAll> findByUserId(int UserId);
}
