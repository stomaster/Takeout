package com.qtc.ecommerce_demo.mapper;

import com.qtc.ecommerce_demo.entity.UserProfile;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserProfileMapper {
    @Update("UPDATE user_profile SET avatar_url = #{avatarUrl} WHERE user_id = #{userId}")
    int updateAvatar(@Param("userId") Long userId, @Param("avatarUrl") String avatarUrl);

    @Select("SELECT * FROM user_profile WHERE user_id = #{userId}")
    UserProfile selectByUserId(Long userId);

    @Insert("INSERT INTO user_profile (user_id, nickname, avatar_url, signature, " +
            "grade, school, college, student_id, tags) " +
            "VALUES (#{userId}, #{nickname}, #{avatarUrl}, #{signature}, " +
            "#{grade}, #{school}, #{college}, #{studentId}, #{tags})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(UserProfile userProfile);

    /*
    String sql = "第一部分" + "第二部分" + "第三部分";
    // 编译后等同于：
    String sql = "第一部分第二部分第三部分";
     */

    @Update("UPDATE user_profile SET " +
            "avatar_url = #{avatarUrl}, " +
            "nickname = #{nickname}, " +
            "signature = #{signature}, " +
            "grade = #{grade}, " +
            "school = #{school}, " +
            "college = #{college}, " +
            "student_id = #{studentId}, " +
            "tags = #{tags} " +
            "WHERE user_id = #{userId}")
    int update(UserProfile userProfile);

    @Update("UPDATE user_profile SET view_count = view_count + 1 WHERE user_id = #{userId}")
    int incrementViewCount(Long userId);

    @Update("UPDATE user_profile SET collect_count = collect_count + 1 WHERE user_id = #{userId}")
    int incrementCollectCount(Long userId);

    // ✅ 新增：减少收藏数（确保不为负数）
    @Update("UPDATE user_profile SET collect_count = " +
            "CASE WHEN collect_count > 0 THEN collect_count - 1 ELSE 0 END " +
            "WHERE user_id = #{userId}")
    int decrementCollectCount(Long userId);
    /*
    UPDATE user_profile
SET collect_count =
    CASE
        WHEN collect_count > 0 THEN collect_count - 1
        ELSE 0
    END
WHERE user_id = #{userId}
     */

    @Update("UPDATE user_profile SET purchase_count = purchase_count + 1 WHERE user_id = #{userId}")
    int incrementPurchaseCount(Long userId);
}









