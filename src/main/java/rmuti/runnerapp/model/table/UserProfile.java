package rmuti.runnerapp.model.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;


@Data
@Entity(name = "user_profile")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "user_name")
    private String userName;

    @JsonIgnore
    @Column(name = "pass_word")
    private String passWord;

    @Column
    private String au;

    @Column
    private String name;

    @Column
    private String tel;

    @Column(name = "img_profile")
    private String imgProfile;
}