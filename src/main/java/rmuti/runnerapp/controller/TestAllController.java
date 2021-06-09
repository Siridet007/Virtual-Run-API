package rmuti.runnerapp.controller;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rmuti.runnerapp.config.Config;
import rmuti.runnerapp.model.service.NotificationService;
import rmuti.runnerapp.model.service.TestAllRepository;
import rmuti.runnerapp.model.service.UserProfileRepository;
import rmuti.runnerapp.model.table.TestAll;
import rmuti.runnerapp.model.table.UserProfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Log4j2
@RestController
@RequestMapping("/test_all")
public class TestAllController {
    @Autowired
    private TestAllRepository testAllRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @PostMapping("/save")
    public Object save(TestAll testAll) {
        APIResponse res = new APIResponse();
        testAllRepository.save(testAll);
        res.setData(testAll);
        return res;
    }

    @PostMapping("/load")
    public Object load() {
        APIResponse res = new APIResponse();
        List<TestAll> db = testAllRepository.findAll();
        List<TestAll> stringList = new ArrayList<>();
        Collections.sort(db, new Comparator<TestAll>() {
            @Override
            public int compare(TestAll o1, TestAll o2) {
                String A = new String();
                String B = new String();

                A = o1.getDateStart();
                B = o2.getDateStart();

                return A.compareTo(B);
            }
        });
        for(int i = 0; i < db.size(); i++){
            stringList.add(db.get(i));
        }

        res.setData(db);
        return res;
    }

    @PostMapping("/show")
    public Object show(@RequestParam String type) {
        List<TestAll> _list = testAllRepository.findAllByType(type);
        Collections.sort(_list, new Comparator<TestAll>() {
            @Override
            public int compare(TestAll o1, TestAll o2) {
                String A = new String();
                String B = new String();

                A = o1.getDateStart();
                B = o2.getDateStart();

                return A.compareTo(B);
            }
        });
        return _list;
    }

    @PostMapping("/show_all")
    public Object showAll(@RequestParam int userId) {
        List<TestAll> _list = testAllRepository.findByUserId(userId);
        Collections.sort(_list, new Comparator<TestAll>() {
            @Override
            public int compare(TestAll o1, TestAll o2) {
                String A = new String();
                String B = new String();

                A = o1.getDateStart();
                B = o2.getDateStart();

                return A.compareTo(B);
            }
        });
        return _list;
    }

    @PostMapping("/show_list")
    public Object showList(@RequestParam String type, int userId) {
        List<TestAll> _list = testAllRepository.findByTypeAndUserId(type, userId);
        return _list;
    }

    @PostMapping("/show_id")
    public Object showID(@RequestParam int id) {
        List<TestAll> db = testAllRepository.findByid(id);
        return db;
    }

    @PostMapping("/update")
    public Object update(TestAll testAll, @RequestParam(value = "fileImg", required = false) MultipartFile fileImg) {
        APIResponse res = new APIResponse();
        Random random = new Random();
        try {
            if (fileImg != null) {
                char a = (char) (random.nextInt(26) + 'a');
                testAll.setImgAll(String.valueOf(a) + ".png");
                File fileToSave = new File(Config.imgAll + testAll.getImgAll());
                fileImg.transferTo(fileToSave);
                testAll = testAllRepository.save(testAll);
                res.setData(testAll);
                res.setStatus(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setMessage("err");
            res.setStatus(0);
        }
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/image", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getResource(@RequestParam String imgAll) throws Exception {
        try {
            InputStream in = new FileInputStream(Config.imgAll + imgAll);
            var inImg = IOUtils.toByteArray(in);
            in.close();
            return inImg;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/update_list")
    public Object updateList(TestAll testAll, @RequestParam(value = "fileImg", required = false) MultipartFile fileImg) {
        log.info("start update list...");
        APIResponse res = new APIResponse();
        Random random = new Random();
        try {
            if (fileImg != null) {
                Optional<TestAll> testAllDb = testAllRepository.findById(testAll.getId());
                File fileDelete = new File(Config.imgAll + testAllDb.get().getImgAll());
                if (fileDelete.delete()) {
                    log.info("File deleted successfully");
                } else {
                    log.info("Failed to delete the file");
                }
                char a = (char) (random.nextInt(26) + 'a');
                testAll.setImgAll(String.valueOf(a) + ".png");
                File fileToSave = new File(Config.imgAll + testAll.getImgAll());
                fileImg.transferTo(fileToSave);
                testAll = testAllRepository.save(testAll);
                res.setData(testAll);
                res.setStatus(1);
            } else {
                Optional<TestAll> testAllDb = testAllRepository.findById(testAll.getId());
                testAll.setImgAll(testAllDb.get().getImgAll());
                testAll = testAllRepository.save(testAll);
            }
            log.info("end update list...");
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            res.setMessage("err");
            res.setStatus(0);
        }
        return res;
    }

    @PostMapping("/remove")
    public Object remove(@RequestParam int id, @RequestParam int userId) {
        APIResponse res = new APIResponse();
        String img = null;
        List<TestAll> testAll = testAllRepository.findByIdAndUserId(id, userId);
        for (var i = 0; i < testAll.size(); i++) {
            var sum = testAll.get(i);
            System.out.println(sum);
            img = sum.getImgAll();
            System.out.println(img);
        }
        System.out.println(img);
        System.out.println(testAll);
        try {
            File fileToDelete = new File(Config.imgAll + img);
            Files.delete(Path.of(String.valueOf(fileToDelete)));
            testAllRepository.deleteById(id);
            res.setStatus(0);
            res.setMessage("Remove List success!");
        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(1);
        }
        return res;
    }

}
