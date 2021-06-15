package rmuti.runnerapp.model.table;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "test_all")
public class TestAll {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "name_all")
    private String nameAll;
    @Column
    private String distance;
    @Column
    private String type;
    @Column(name = "date_start")
    private String dateStart;
    @Column(name = "date_end")
    private String dateEnd;
    @Column(name = "img_all")
    private String imgAll;
    @Column(name = "user_id")
    private int userId;
    @Column
    private String price;
    @Column
    private String accessories;
    @Column
    private String active;
    @Column(name = "create_date")
    private Date createDate = new Date();

    //เปลี่ยน string เป็น date
}
