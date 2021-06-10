package rmuti.runnerapp.model.table;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "ranking")
public class Ranking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ranking_id")
    private int rankingId;
    @Column(name = "user_id")
    private int userId;
    @Column
    private int id;
    @Column
    private String name;
    @Column(name = "name_all")
    private String nameAll;
    @Column
    private String km;
    @Column
    private String time;
    @Column
    private String type;
    @Column(name = "img_ranking")
    private String imgRanking;
}
