package com.qtc.ecommerce_demo.controller;

import com.qtc.ecommerce_demo.dto.Result;
import com.qtc.ecommerce_demo.entity.UserCollection;
import com.qtc.ecommerce_demo.service.UserCollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/collections")  // ✅ 修正：添加 /collections
@RequiredArgsConstructor
public class UserCollectionController {

    private final UserCollectionService userCollectionService;

    /**
     * 添加收藏
     * POST /api/users/{userId}/collections
     */
    @PostMapping
    public Result<Boolean> addCollection(@PathVariable Long userId,
                                         @RequestParam Long productId) {
        try {
            boolean success = userCollectionService.addCollection(userId, productId);
            if (success) {
                return Result.success(true);
            } else {
                return Result.error("已收藏过该商品");
            }
        } catch (Exception e) {
            return Result.error("收藏失败: " + e.getMessage());
        }
    }

    /**
     * 获取收藏列表
     * GET /api/users/{userId}/collections
     */
    @GetMapping
    public Result<List<UserCollection>> getCollections(@PathVariable Long userId) {
        try {
            List<UserCollection> collections = userCollectionService.getUserCollections(userId);
            return Result.success(collections);
        } catch (Exception e) {
            return Result.error("获取收藏列表失败: " + e.getMessage());
        }
    }

    /**
     * 取消收藏
     * DELETE /api/users/{userId}/collections/{productId}
     */
    @DeleteMapping("/{productId}")
    public Result<Boolean> removeCollection(@PathVariable Long userId,
                                            @PathVariable Long productId) {
        try {
            boolean success = userCollectionService.removeCollection(userId, productId);
            return Result.success(success);
        } catch (Exception e) {
            return Result.error("取消收藏失败: " + e.getMessage());
        }
    }

    /**
     * 检查是否已收藏
     * GET /api/users/{userId}/collections/check?productId={productId}
     */
    @GetMapping("/check")
    public Result<Boolean> checkCollected(@PathVariable Long userId,
                                          @RequestParam Long productId) {
        try {
            boolean isCollected = userCollectionService.isCollected(userId, productId);
            return Result.success(isCollected);
        } catch (Exception e) {
            return Result.error("检查失败: " + e.getMessage());
        }
    }
}