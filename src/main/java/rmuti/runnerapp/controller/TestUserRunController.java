package rmuti.runnerapp.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rmuti.runnerapp.config.Config;
import rmuti.runnerapp.model.service.TestAllRepository;
import rmuti.runnerapp.model.service.TestUserRunRepository;
import rmuti.runnerapp.model.service.UserProfileRepository;
import rmuti.runnerapp.model.table.TestAll;
import rmuti.runnerapp.model.table.TestUserRun;
import rmuti.runnerapp.model.table.UserProfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/test_run")
public class TestUserRunController {
    @Autowired
    private TestUserRunRepository testUserRunRepository;
    @Autowired
    private TestAllRepository testAllRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @PostMapping("/save_run")
    public Object saveRun(TestUserRun testUserRun){
        APIResponse res = new APIResponse();
        System.out.println(testUserRun);
        TestUserRun db = testUserRunRepository.findByIdAndUserId(testUserRun.getId(),testUserRun.getUserId());
        System.out.println(db);
        System.out.println("ffff");
        if(db == null){
            testUserRunRepository.save(testUserRun);
            res.setData(testUserRun);
            res.setStatus(1);
        }else {
            res.setStatus(0);
            res.setMessage("Already has user");
            System.out.println("has user");
        }

        return res;
    }
    @PostMapping("/check")
    public Object check(@RequestParam int id,@RequestParam int userId){
        APIResponse res = new APIResponse();
        TestUserRun testUserRun = testUserRunRepository.findByIdAndUserId(id, userId);
        if(testUserRun == null){
            res.setStatus(1);
        }else {
            res.setStatus(0);
        }
        return res;
    }
    @PostMapping("/load")
    public Object load(@RequestParam int id,@RequestParam int userId){
        APIResponse res = new APIResponse();
        List<TestUserRun> userRunList = testUserRunRepository.findByidAndUserId(id,userId);
        res.setData(userRunList);
        return res;
    }
    @PostMapping("/show_run")
    public Object showRun(@RequestParam int userId){
        APIResponse res = new APIResponse();
        List<TestUserRun> dbRun = testUserRunRepository.findByUserId(userId);
        res.setData(dbRun);
        return  res;
    }
    @PostMapping("/test_show")
    public Object testShow(@RequestParam int userId){
        APIResponse res = new APIResponse();
        List<TestUserRun> testUserRuns = testUserRunRepository.findByUserId(userId);
        List<Integer> value = new ArrayList<Integer>();

        for(var i=0;i< testUserRuns.size();i++){
            var sum = testUserRuns.get(i);
            var aid = sum.getId();
            value.add(aid);
        }
        System.out.println(value);
        List<TestAll> retAllRun = new ArrayList<>();
        for(var i=0;i< value.size();i++) {
            var sum = value.get(i);
            System.out.println(sum);
            List<TestAll> allRuns = testAllRepository.findByid(sum);
            System.out.println(allRuns);
            var aa = allRuns.get(0);
            retAllRun.add(aa);
        }
        res.setData(retAllRun);
        return  res;
    }
    @PostMapping("/show_data")
    public Object showData(@RequestParam int id){
        APIResponse res = new APIResponse();
        List<TestUserRun> testAllList = testUserRunRepository.findByid(id);
        List<Integer> value = new ArrayList<>();
        System.out.println(testAllList);
        for(var i=0;i<testAllList.size();i++){
            var sum = testAllList.get(i);
            System.out.println(sum);
            var user = sum.getUserId();
            System.out.println(user);
            value.add(user);
        }
        List<UserProfile> userProfileList = new ArrayList<>();
        for(var i=0;i<value.size();i++){
            var sum = value.get(i);
            List<UserProfile> userProfiles = userProfileRepository.findByUserId(sum);
            var aa = userProfiles.get(0);
            userProfileList.add(aa);
        }

        res.setData(userProfileList);
        return res;
    }

    @PostMapping("/update")
    public Object update(TestUserRun testUserRun, @RequestParam(value = "fileImg",required = false) MultipartFile fileImg){
        APIResponse res = new APIResponse();
        Random random = new Random();
        try{
            if(fileImg != null){
                char a = (char) (random.nextInt(26)+'a');
                testUserRun.setImgSlip(String.valueOf(a)+".png");
                File fileToSave = new File(Config.imgSlip+testUserRun.getImgSlip());
                fileImg.transferTo(fileToSave);
                testUserRun = testUserRunRepository.save(testUserRun);
                res.setData(testUserRun);
                res.setStatus(1);
            }
        }catch (Exception e){
            res.setMessage("err");
            res.setStatus(0);
        }
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/image", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getResource (@RequestParam String imgSlip) throws Exception{
        try {
            InputStream in = new FileInputStream(Config.imgSlip+imgSlip);
            var inImg =  IOUtils.toByteArray(in);
            in.close();
            return inImg;
        }catch (Exception e){
            return null;
        }
    }
    @PostMapping("/update_save")
    public Object updateSave(TestUserRun testUserRun){
        APIResponse res = new APIResponse();
        try{
            testUserRun = testUserRunRepository.save(testUserRun);
            res.setStatus(1);
        }catch (Exception e){
            res.setStatus(0);
        }
        return testUserRun;
    }
}
