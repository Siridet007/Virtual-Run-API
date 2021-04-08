package rmuti.runnerapp.model.table;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "test_data")
public class TestDataUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int did;
    @Column(name = "user_id")
    private int userId;
    @Column
    private int id;
    @Column
    private int tid;
    @Column
    private String km;
    @Column
    private String time;
    @Column
    private String type;
    @Column(name = "date_now")
    private String dateNow;
}
