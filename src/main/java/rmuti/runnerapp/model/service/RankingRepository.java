package rmuti.runnerapp.model.service;

import org.springframework.data.jpa.repository.JpaRepository;
import rmuti.runnerapp.model.table.Ranking;

import java.util.List;

public interface RankingRepository extends JpaRepository<Ranking,Integer> {
    List<Ranking> findByType(String Type);
    List<Ranking> findByUserId(int UserId);
    List<Ranking> findByIdAndUserId(int Id,int UserId);
}
