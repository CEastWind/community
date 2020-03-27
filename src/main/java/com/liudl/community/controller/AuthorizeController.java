package com.liudl.community.controller;

import com.liudl.community.dto.AccessTokenDTO;
import com.liudl.community.dto.GithubUser;
import com.liudl.community.model.User;
import com.liudl.community.provider.GithubProvider;
import com.liudl.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by TwistedFate on 2020/1/17 14:58
 */
@Controller
@Slf4j
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secrect}")
    private String clientSecrect;
    @Value("${github.redirect.url}")
    private String redirectUrl;

    @Autowired
    private UserService userService;

    @GetMapping("/goSignIn")
    public String goSignIn(Model model) {
        return "signIn";
    }

    @GetMapping("/goLogin")
    public String goLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam(name = "phoneNumber") String phoneNumber,
                        @RequestParam(name = "password") String password,
                        Model model,
                        HttpServletResponse response) {
        Pattern p = Pattern.compile("^[1]\\d{10}$");
        if (StringUtils.isBlank(phoneNumber) || !p.matcher(phoneNumber).matches()) {
            model.addAttribute("error", "请输入正确手机号");
            model.addAttribute("phoneNumber", phoneNumber);
            return "login";
        }
        if (StringUtils.isBlank(password) || password.trim().length() < 6) {
            model.addAttribute("error", "请输入6位以上密码");
            model.addAttribute("phoneNumber", phoneNumber);
            return "login";
        }
        User user = new User();
        user.setPhoneNumber(phoneNumber.trim());
        user.setPassword(password.trim());

        switch (userService.checkUserInfo(user)) {
            case "phomeNumberNotExists":
                model.addAttribute("error", "手机号未注册");
                return "login";
            case "passwordWrong":
                model.addAttribute("error", "密码错误");
                return "login";
            case "ok":
                String token = UUID.randomUUID().toString();
                response.addCookie(new Cookie("token", token));
                user.setToken(token);
                userService.loginCommonUser(user);
                return "redirect:/";
            default:
                model.addAttribute("error", "系统错误,请稍后重试");
                return "login";
        }
    }

    @PostMapping("/signIn")
    public String signIn(@RequestParam(name = "phoneNumber") String phoneNumber,
                         @RequestParam(name = "name") String name,
                         @RequestParam(name = "password") String password,
                         Model model) {

        Pattern p = Pattern.compile("^[1]\\d{10}$");
        if (StringUtils.isBlank(phoneNumber) || !p.matcher(phoneNumber).matches()) {
            model.addAttribute("error", "请输入正确手机号");
            model.addAttribute("phoneNumber", phoneNumber);
            return "signIn";
        }
        if (StringUtils.isBlank(name)) {
            model.addAttribute("error", "昵称不能为空");
            model.addAttribute("phoneNumber", phoneNumber);
            return "signIn";
        }
        if (StringUtils.isBlank(password) || password.trim().length() < 6) {
            model.addAttribute("error", "请输入6位以上密码");
            model.addAttribute("name", name);
            model.addAttribute("phoneNumber", phoneNumber);
            return "signIn";
        }

        User user = new User();
        user.setName(name.trim());
        user.setPhoneNumber(phoneNumber.trim());
        user.setPassword(password.trim());

        if (userService.createForSignIn(user)) {
            model.addAttribute("ok", "注册成功，点击“请登录”进行登录");
        } else {
            model.addAttribute("name", name);
            model.addAttribute("phoneNumber", phoneNumber);
            model.addAttribute("error", "该号码已被注册");
        }
        return "signIn";
    }

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecrect);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_url(redirectUrl);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null && githubUser.getId() != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userService.createOrUpdate(user);
            response.addCookie(new Cookie("token", token));
            return "redirect:/";
        } else {
            log.error("callback get github error,{}", githubUser);
            //登录失败，重新登录
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response,
                         HttpServletRequest request) {
        //移除session中的user
        request.getSession().removeAttribute("user");
        //删除cookie
        Cookie token = new Cookie("token", null);
        token.setMaxAge(0);
        response.addCookie(token);
        return "redirect:/";
    }
}
