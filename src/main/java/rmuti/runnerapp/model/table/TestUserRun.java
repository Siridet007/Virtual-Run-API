package rmuti.runnerapp.model.table;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "test_run")
public class TestUserRun {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int rid;
    @Column(name = "user_id")
    private int userId;
    @Column
    private int id;
    @Column
    private String size;
    @Column(name = "create_date")
    private Date createDate = new Date();
    @Column
    private String status;
    @Column(name = "img_slip")
    private String imgSlip;
    @Column
    private String accessories;
}
