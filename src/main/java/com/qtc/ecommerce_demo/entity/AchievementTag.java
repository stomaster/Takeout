package com.qtc.ecommerce_demo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AchievementTag {
    private Integer id;
    private String tagCode;  // 标签代码，如: LATE_NIGHT_FOODIE
    private String tagName;  // 标签显示名称，如: #深夜食神
    private String description;
    private String ruleType;  // 规则类型
    private String ruleConfig;  // JSON格式的规则配置
    private String iconUrl;  // 图标URL
    private LocalDateTime createTime;
}
/*

 */