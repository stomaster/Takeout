package com.qtc.ecommerce_demo.dto;

import lombok.Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class UserProfileVO {
    // 只保留字段，不要任何验证注解
    private String nickname;
    private String avatarUrl;
    private String signature;
    private String grade;
    private String school;
    private String college;
    private String studentId;
    private String tags;

    public List<String> getTagList() {
        if (tags == null || tags.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(tags.split(","));
    }

    public void setTagList(List<String> tagList) {
        if (tagList == null || tagList.isEmpty()) {
            this.tags = null;
        } else {
            this.tags = String.join(",", tagList);
        }
    }
}