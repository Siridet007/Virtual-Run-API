package rmuti.runnerapp.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rmuti.runnerapp.config.Config;
import rmuti.runnerapp.model.service.RankingRepository;
import rmuti.runnerapp.model.table.Ranking;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping("/ranking")
public class RankingController {
    @Autowired
    private RankingRepository rankingRepository;
    @PostMapping("/save")
    public Object save(Ranking ranking){
        APIResponse res = new APIResponse();
        rankingRepository.save(ranking);
        res.setData(ranking);
        return res;
    }
    @PostMapping("/load")
    public Object load(){
        APIResponse res = new APIResponse();
        List<Ranking> db = rankingRepository.findAll();
        res.setData(db);
        res.setMessage("ggg");
        return res;
    }
    @PostMapping("/show")
    public Object show(@RequestParam int UserId){
        APIResponse res = new APIResponse();
        List<Ranking> db = rankingRepository.findByUserId(UserId);
        res.setData(db);
        return res;
    }
    @PostMapping("/show_type")
    public Object showType(@RequestParam String type){
        APIResponse res = new APIResponse();
        List<Ranking> db = rankingRepository.findByType(type);

        System.out.println("My Sort"+db);

        List<Ranking> stringList = new ArrayList<>();
        Collections.sort(db, new Comparator<Ranking>() {
            @Override
            public int compare(Ranking o1, Ranking o2) {
                String valA = new String();
                String valB = new String();

                    valA = o1.getNameAll();
                    valB = o2.getNameAll();

                int sum1 = valA.compareTo(valB);

                if(sum1 != 0){
                    return sum1;
                }

                    valA = (String) o1.getTime();
                    valB = (String) o2.getTime();

                return valA.compareTo(valB);
            }
        });
        for(int i = 0; i < db.size(); i++){
            stringList.add(db.get(i));
        }
        System.out.println(stringList);
        res.setData(db);
        return res;
    }
    @PostMapping("/show_id")
    public Object showId(@RequestParam int UserId){
        List<Ranking> db =rankingRepository.findByUserId(UserId);
        return db;
    }
    @PostMapping("/update")
    public Object update(Ranking ranking, @RequestParam(value = "fileImg",required = false)MultipartFile fileImg){
        APIResponse res = new APIResponse();
        Random random = new Random();
        try{
            if(fileImg != null){
                char a = (char) (random.nextInt(26)+'a');
                ranking.setImgRanking(String.valueOf(a)+".png");
                File fileToSave = new File(Config.imgRank+ranking.getImgRanking());
                fileImg.transferTo(fileToSave);
                ranking = rankingRepository.save(ranking);
                res.setData(ranking);
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
    public byte[] getResource (@RequestParam String imgProfile) throws Exception{
        try {
            InputStream in = new FileInputStream(Config.imgRank+imgProfile);
            var inImg =  IOUtils.toByteArray(in);
            in.close();
            return inImg;
        }catch (Exception e){
            return null;
        }
    }
}