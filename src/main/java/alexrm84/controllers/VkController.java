package alexrm84.controllers;

import alexrm84.entities.User;
import alexrm84.services.UserService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/vk")
public class VkController {
    private RestTemplate restTemplate;
    private UserService userService;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Value("${vk.clientId}")
    private String clientId;

    @Value("${vk.clientSecret}")
    private String clientSecret;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code, HttpSession session) throws Exception {
        String requestUrl = "https://oauth.vk.com/access_token?client_id=" + clientId + "&client_secret=" + clientSecret + "&redirect_uri=http://localhost:8190/app/vk/callback&code=" + code;
        ResponseEntity<String> accessTokenEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, null, String.class);
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(accessTokenEntity.getBody()).getAsJsonObject();
        String accessToken = jsonObject.get("access_token").getAsString();

        String userIds = jsonObject.get("user_id").toString();
        System.out.println(userIds);
        String getNameRequest = "https://api.vk.com/method/users.get?user_ids=" + userIds + "&access_token=" + accessToken + "&v=5.103";
        ResponseEntity<String> userInfoEntity = restTemplate.exchange(getNameRequest, HttpMethod.GET, null, String.class);
        System.out.println(userInfoEntity.getBody());

        session.setAttribute("accessToken", accessToken);
        JsonObject jsonUserInfo = parser.parse(userInfoEntity.getBody()).getAsJsonObject().get("response").getAsJsonArray().get(0).getAsJsonObject();
        userService.save(jsonUserInfo);
        return "redirect:/shop";
    }

}
