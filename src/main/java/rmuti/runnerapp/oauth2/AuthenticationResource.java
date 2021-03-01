package rmuti.runnerapp.oauth2;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rmuti.runnerapp.model.beans.LoginBean;
import rmuti.runnerapp.model.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
@RestController
public class AuthenticationResource {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @PostMapping(path = "/authorize")
    public ResponseEntity<Object> authenticate(@RequestParam String username, @RequestParam String password) {
        Optional<Map<String, Object>> optUser = userService.login(new LoginBean(username, password));
        if (optUser.isPresent()) {
            return ResponseEntity.ok().body(optUser.get());
        } else {
            return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(path = "/token_check")
    public ResponseEntity<Object> validationToken(@RequestParam String token) {
        boolean validate = tokenService.validateToken(token);
        if (validate) {
            Map<String, Boolean> ret = new HashMap<>();
            ret.put("active", true);
            return ResponseEntity.ok().body(ret);
        } else {
            return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
        }
    }

}
