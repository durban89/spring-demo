package com.gowhich.springdemo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.swing.*;

@Controller
public class SocialController {
    @GetMapping("social/facebook")
    public void facebook() {
        String appId = "xxx"; //promptForInput("输入App ID:");
        String appSecret = "xxx"; // promptForInput("输入App Secret:");
        String appToken = fetchApplicationAccessToken(appId, appSecret);
        AppDetail appDetail = fetchApplicationData(appId, appToken);
        System.out.println("\n 应用详情");
        System.out.println("=====================");
        System.out.println("ID:" + appDetail.getId());
        System.out.println("Name:" + appDetail.getName());
        System.out.println("Namespace: "+appDetail.getNamespace());
        System.out.println("Contact Email:"+appDetail.getContactEmail());
        System.out.println("Website Url:"+appDetail.getWebsiteUrl());
    }

    private static AppDetail fetchApplicationData(String appId, String appToken) {
        Facebook facebook = new FacebookTemplate(appToken);
        return facebook.restOperations().getForObject(
                "https://graph.facebook.com/{appId}?fields=name,namespace,contact_email,website_url",
                AppDetail.class,
                appId);
    }

    private static String fetchApplicationAccessToken(String appId, String appSecret) {
        OAuth2Operations oauth = new FacebookConnectionFactory(appId, appSecret).getOAuthOperations();
        return oauth.authenticateClient().getAccessToken();
    }

    private static String promptForInput(String promptText) {
        return JOptionPane.showInputDialog(promptText + " ");
    }

    private static final class AppDetail {
        private long id;
        private String name;
        private String namespace;

        @JsonProperty("contact_email")
        private String contactEmail;

        @JsonProperty("website_url")
        private String websiteUrl;

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getNamespace() {
            return namespace;
        }

        public String getContactEmail() {
            return contactEmail;
        }

        public String getWebsiteUrl() {
            return websiteUrl;
        }
    }

}
