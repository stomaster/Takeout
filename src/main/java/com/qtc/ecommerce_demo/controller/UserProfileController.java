package com.qtc.ecommerce_demo.controller;

import com.qtc.ecommerce_demo.dto.Result;
import com.qtc.ecommerce_demo.entity.Product;
import com.qtc.ecommerce_demo.mapper.ProductMapper;
import com.qtc.ecommerce_demo.dto.*;
import com.qtc.ecommerce_demo.entity.UserCollection;
import com.qtc.ecommerce_demo.mapper.UserProfileMapper;
import com.qtc.ecommerce_demo.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@RestController
@RequestMapping("/api/users/{userId}")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final UserProfileMapper userProfileMapper;
    /**
     * 获取用户完整信息
     * GET http://localhost:8081/api/users/1/profile
     */
    @GetMapping("/profile")
    public Result<UserProfileDTO> getUserProfile(@PathVariable Long userId) {
        try {
            // 增加浏览量
            userProfileService.incrementViewCount(userId);
            // 获取用户信息
            UserProfileDTO data = userProfileService.getUserProfile(userId);
            return Result.success(data);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 更新用户信息
     * PUT http://localhost:8081/api/users/1/profile
     * Content-Type: application/json
     * {
     *   "nickname": "小明",
     *   "avatarUrl": "/avatar.jpg",
     *   "signature": "热爱编程",
     *   "grade": "大三",
     *   "school": "清华大学",
     *   "college": "计算机学院",
     *   "studentId": "20230101",
     *   "tags": "编程,游戏,音乐"
     * }
     */
    @PutMapping("/profile")
    public Result<Boolean> updateUserProfile(@PathVariable Long userId,
                                             @RequestBody UserProfileVO userProfileVO) {
        try {
            // 手动验证昵称（先简单验证，后续可完善）
            if (userProfileVO.getNickname() == null || userProfileVO.getNickname().trim().isEmpty()) {
                return Result.error("昵称不能为空");
            }
            if (userProfileVO.getNickname().length() > 20) {
                return Result.error("昵称不能超过20个字符");
            }

            boolean success = userProfileService.saveOrUpdateUserProfile(userId, userProfileVO);
            return Result.success(success);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    @PostMapping("/avatar")
    public Result<String> uploadAvatar(@PathVariable Long userId,
                                       @RequestParam("file") MultipartFile file) {
        try {
            // 1. 验证文件
            if (file.isEmpty()) {
                return Result.error("请选择头像图片");
            }

            // 2. 验证文件类型
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return Result.error("只能上传图片文件（jpg, png, gif等）");
            }

            // 3. 验证文件大小（限制2MB）
            if (file.getSize() > 2 * 1024 * 1024) {
                return Result.error("头像大小不能超过2MB");
            }

            // 4. 生成唯一文件名
            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            String fileName = "avatar_" + userId + "_" + UUID.randomUUID().toString() + fileExtension;

            // 5. 创建保存目录
            String uploadDir = "src/main/resources/static/uploads/avatars/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 6. 保存文件
            String filePath = uploadDir + fileName;
            file.transferTo(new File(filePath));

            // 7. 构建访问URL
            String avatarUrl = "/uploads/avatars/" + fileName;

            // 8. 更新用户头像URL
            int result = userProfileMapper.updateAvatar(userId, avatarUrl);

            if (result > 0) {
                return Result.success(avatarUrl);
            } else {
                return Result.error("更新头像失败");
            }

        } catch (IOException e) {
            return Result.error("头像上传失败: " + e.getMessage());
        } catch (Exception e) {
            return Result.error("上传失败: " + e.getMessage());
        }
    }
    /**
     * 获取用户收藏列表
     * GET http://localhost:8081/api/users/1/collections
     */


    /**
     * 添加收藏
     * POST http://localhost:8081/api/users/1/collections
     * Content-Type: application/json
     * {
     *   "productId": 123,
     *   "productImage": "/product/123.jpg"
     * }
     */
    /**
     * 取消收藏
     * DELETE http://localhost:8081/api/users/1/collections/123
     */

}