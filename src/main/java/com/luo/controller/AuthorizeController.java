package com.luo.controller;

import com.luo.dto.AccessTokenDTO;
import com.luo.dto.GitHubUser;
import com.luo.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;

    @Value("${github.client.id}")
    private String client_id;

    @Value("${github.client.secret}")
    private String client_secret;

    @Value("${github.client.redirectUri}")
    private String redirect_uri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state){
        //自动封装好对象的信息后转化为accessToken
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        System.out.println("生产的:"+accessToken);
        GitHubUser user = gitHubProvider.getGitHubUser(accessToken);
        //查看获取的github上的用户姓名
        System.out.println(user);
        return "index";
    }
}
