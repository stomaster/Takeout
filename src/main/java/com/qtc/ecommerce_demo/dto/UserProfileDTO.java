package com.qtc.ecommerce_demo.dto;

import com.qtc.ecommerce_demo.entity.UserCollection;
import lombok.Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class UserProfileDTO {
    private Long userId;
    private String username;
    private String email;
    private String avatarUrl;
    private String nickname;
    private String signature;
    private Integer viewCount = 0;
    private Integer collectCount = 0;
    private Integer purchaseCount = 0;
    private String grade;
    private String school;
    private String college;
    private String studentId;
    private String tags;
    private List<UserCollection> collections = new ArrayList<>();

    public List<String> getTagList() {
        if (tags == null || tags.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(tags.split(","));
    }
}