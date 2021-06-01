package rmuti.runnerapp.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rmuti.runnerapp.config.Config;
import rmuti.runnerapp.model.service.UserProfileRepository;
import rmuti.runnerapp.model.table.UserProfile;
import rmuti.runnerapp.util.EncoderUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/user_profile")
public class UserProfileController {

    @Autowired
    private EncoderUtil encoderUtil;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @PostMapping("/save")
    public Object save(UserProfile userProfile) {
        APIResponse res = new APIResponse();
        UserProfile db = userProfileRepository.findByUserName(userProfile.getUserName());
        System.out.print(db);
        if (db == null) {
            userProfile.setPassWord(encoderUtil.passwordEncoder().encode(userProfile.getPassWord()));
            userProfileRepository.save(userProfile);
            res.setStatus(1);
            res.setMessage("ok");
            res.setData(userProfile);
            // res.dataReturn(0);
            res.setData(1);
        } else {
            res.setStatus(0);
            res.setMessage("Already has user");
            //res.dataReturn(1);
            res.setData(0);
        }

        System.out.print(res);
        return res;
    }

    @PostMapping("/login")
    public Object Login(@RequestParam String userName, @RequestParam String passWord) {
        APIResponse res = new APIResponse();
        UserProfile dbUserProfile = userProfileRepository.findByUserNameAndPassWord(userName, passWord);
        System.out.print(dbUserProfile);
        UserProfile db = userProfileRepository.findByUserName(userName);
        System.out.print(db);
        if (dbUserProfile == null) {
            res.setMessage("no user");
            res.setData(0);
            res.setStatus(0);
        } else {
            res.setMessage("Have user");
            res.setData(1);
            res.setStatus(1);
            res.setGetId(db.getUserId());
        }
        return res;
    }

    @PostMapping("/load")
    public Object load() {
        APIResponse res = new APIResponse();
        List<UserProfile> db = userProfileRepository.findAll();
        res.setData(db);
        return res;
        //return userProfileRepository.findAll();
    }
    @PostMapping("/show")
    public Object show(@RequestParam int userId){
        APIResponse res = new APIResponse();
        List<UserProfile> userProfiles = userProfileRepository.findByUserId(userId);
        res.setData(userProfiles);
        return res;
    }
    @PostMapping("/show_au")
    public Object showAu(String Au){
        APIResponse res = new APIResponse();
        List<UserProfile> _list = userProfileRepository.findByAu(Au);
        res.setData(_list);
        return res;
    }
    @PostMapping("/save_img")
    public Object saveImg(UserProfile userProfile,@RequestParam(value = "fileImg",required = false) MultipartFile fileImg){
        APIResponse res = new APIResponse();
        Random random = new Random();
        try{
            if(fileImg != null){
                char a = (char) (random.nextInt(26)+'a');
                userProfile.setImgProfile(String.valueOf(a)+".png");
                File fileToSave = new File(Config.imgPro+userProfile.getImgProfile());
                fileImg.transferTo(fileToSave);
                userProfile = userProfileRepository.save(userProfile);
                res.setData(userProfile);
                res.setStatus(1);
            }
        }catch (Exception e){
            res.setMessage("err");
            res.setStatus(0);
        }
        return res;
    }

    @PostMapping("/update_profile")
    public Object updateProfile (UserProfile userProfile,@RequestParam(value = "fileImg",required = false) MultipartFile fileImg){
        APIResponse res = new APIResponse();
        Random random = new Random();
        try{
            if(fileImg != null){
                Optional<UserProfile> userProfileDb = userProfileRepository.findById(userProfile.getUserId());
                File fileToDelete = new File(Config.imgPro+userProfileDb.get().getImgProfile());
                if(fileToDelete.delete())
                {
                    System.out.println("File deleted successfully");
                }
                else
                {
                    System.out.println("Failed to delete the file");
                }
                char a = (char) (random.nextInt(26)+'a');
                userProfile.setImgProfile(String.valueOf(a)+".png");
                File fileToSave = new File(Config.imgPro+userProfile.getImgProfile());
                fileImg.transferTo(fileToSave);
                userProfile = userProfileRepository.save(userProfile);
                res.setData(userProfile);
                res.setStatus(1);
            }else{
                Optional<UserProfile> userProfileDb = userProfileRepository.findById(userProfile.getUserId());
                userProfile.setImgProfile(userProfileDb.get().getImgProfile());
                userProfile = userProfileRepository.save(userProfile);
            }
        }catch (Exception e){
            res.setMessage("err");
            res.setStatus(0);
        }
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/image", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getResource (@RequestParam String imgProfile) throws Exception{
        try {
            InputStream in = new FileInputStream(Config.imgPro+imgProfile);
            var inImg =  IOUtils.toByteArray(in);
            in.close();
            return inImg;
        }catch (Exception e){
            return null;
        }
    }
    @PostMapping("/update")
    public Object update(UserProfile userProfile){
        try {
            userProfile = userProfileRepository.save(userProfile);
        }catch (Exception e){

        }
        return userProfile;
    }
}

