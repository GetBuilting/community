package com.luo.provider;

import com.alibaba.fastjson.JSON;
import com.luo.dto.AccessTokenDTO;
import com.luo.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 实例化工具AccessToken
 */
@Component
public class GitHubProvider {

    //获取AccessToken的令牌，自动封装的
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        //头类型
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        //转换为JSON的字符串类型
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            //返回的是这个查询一下格式,必须是一个accessToken的码
            String string = response.body().string();
            //使用分割字符串的方法获取token的码值
            String token = string.split("&")[0].split("=")[1];
            System.out.println(token);
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        //没有返回是空
        return null;
    }

    //获取用户信息
    public GitHubUser getGitHubUser(String accessionToken){
        //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
        //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
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization","token"+accessionToken)
                .build();
       try {
           Response response = client.newCall(request).execute();
           String string = response.body().string();
           System.out.println("对象"+string);
           //将string转化为对象
           GitHubUser gitHubUser = JSON.parseObject(string, GitHubUser.class);
           return gitHubUser;
       }catch (IOException e){
            e.printStackTrace();
       }
       return null;
    }


}
