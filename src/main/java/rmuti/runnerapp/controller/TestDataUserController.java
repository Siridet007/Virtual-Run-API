package rmuti.runnerapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rmuti.runnerapp.model.service.TestDataUserRepository;
import rmuti.runnerapp.model.table.TestDataUser;

import java.util.List;

@RestController
@RequestMapping("/test_data")
public class TestDataUserController {
    @Autowired
    private TestDataUserRepository testDataUserRepository;
    @PostMapping("/save")
    public Object save(TestDataUser testDataUser){
        APIResponse res = new APIResponse();
        testDataUserRepository.save(testDataUser);
        res.setData(testDataUser);
        return res;
    }
    @PostMapping("/load")
    public Object load(@RequestParam String type){
        APIResponse res = new APIResponse();
        List<TestDataUser> list = testDataUserRepository.findByType(type);
        res.setData(list);
        return res;
    }
    @PostMapping("/show")
    public Object show(@RequestParam int userId,@RequestParam int id){
        List<TestDataUser> testDataUserList = testDataUserRepository.findByUserIdAndId(userId, id);
        return testDataUserList;
    }
    @PostMapping("/show_tid")
    public Object showTid(@RequestParam int tid){
        APIResponse res = new APIResponse();
        List<TestDataUser> db = testDataUserRepository.findByTid(tid);
        return db;
    }
}
