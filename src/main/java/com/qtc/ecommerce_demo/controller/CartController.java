package com.qtc.ecommerce_demo.controller;

import com.qtc.ecommerce_demo.dto.Result;
import com.qtc.ecommerce_demo.dto.CartDTO;
import com.qtc.ecommerce_demo.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

@Slf4j
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowCredentials = "false")
public class CartController {

    private final CartService cartService;

    /**
     * 获取购物车详情
     */
    @GetMapping("/list")
    public Result<CartDTO.Response> getCart(@RequestParam @NotNull(message = "用户ID不能为空") Long userId) {
        try {
            CartDTO.Response cartData = cartService.getCart(userId);
            return Result.success(cartData);
        } catch (Exception e) {
            log.error("获取购物车失败", e);
            return Result.error("获取购物车失败：" + e.getMessage());
        }
    }

    /**
     * 添加商品到购物车
     */
    @PostMapping("/add")
    public Result<String> addToCart(@RequestParam @NotNull(message = "用户ID不能为空") Long userId,
                                    @RequestBody CartDTO.CartItemRequest request) {
        try {
            if (request.getProductId() == null) {
                return Result.error(400, "商品ID不能为空");
            }
            if (request.getQuantity() == null || request.getQuantity() <= 0) {
                return Result.error(400, "商品数量必须大于0");
            }

            cartService.addToCart(userId, request.getProductId(), request.getQuantity());
            return Result.success("添加成功");
        } catch (Exception e) {
            log.error("添加购物车失败", e);
            return Result.error("添加失败：" + e.getMessage());
        }
    }

    /**
     * 更新购物车商品数量
     */
    @PutMapping("/update")
    public Result<String> updateCart(@RequestParam @NotNull(message = "用户ID不能为空") Long userId,
                                     @RequestBody CartDTO.CartItemRequest request) {
        try {
            if (request.getProductId() == null) {
                return Result.error(400, "商品ID不能为空");
            }
            if (request.getQuantity() == null || request.getQuantity() < 0) {
                return Result.error(400, "商品数量无效");
            }

            cartService.updateCart(userId, request.getProductId(), request.getQuantity());
            return Result.success("更新成功");
        } catch (Exception e) {
            log.error("更新购物车失败", e);
            return Result.error("更新失败：" + e.getMessage());
        }
    }

    /**
     * 删除购物车商品
     */
    @DeleteMapping("/delete")
    public Result<String> deleteItem(@RequestParam @NotNull(message = "用户ID不能为空") Long userId,
                                     @RequestParam @NotNull(message = "商品ID不能为空") Long productId) {
        try {
            cartService.deleteCartItem(userId, productId);
            return Result.success("删除成功");
        } catch (Exception e) {
            log.error("删除购物车商品失败", e);
            return Result.error("删除失败：" + e.getMessage());
        }
    }

    /**
     * 清空购物车
     */
    @DeleteMapping("/clear")
    public Result<String> clearCart(@RequestParam @NotNull(message = "用户ID不能为空") Long userId) {
        try {
            cartService.clearCart(userId);
            return Result.success("清空成功");
        } catch (Exception e) {
            log.error("清空购物车失败", e);
            return Result.error("清空失败：" + e.getMessage());
        }
    }

    /**
     * 选择/取消选择商品
     */
    @PutMapping("/select")
    public Result<String> selectItem(@RequestParam @NotNull(message = "用户ID不能为空") Long userId,
                                     @RequestBody CartDTO.SelectRequest request) {
        try {
            if (request.getProductId() == null) {
                return Result.error(400, "商品ID不能为空");
            }
            if (request.getSelected() == null) {
                return Result.error(400, "选择状态不能为空");
            }

            cartService.selectItem(userId, request.getProductId(), request.getSelected());
            return Result.success("操作成功");
        } catch (Exception e) {
            log.error("选择商品失败", e);
            return Result.error("操作失败：" + e.getMessage());
        }
    }

    /**
     * 全选/取消全选
     */
    @PutMapping("/select-all")
    public Result<String> selectAll(@RequestParam @NotNull(message = "用户ID不能为空") Long userId,
                                    @RequestParam @NotNull(message = "选择状态不能为空") Boolean selected) {
        try {
            cartService.selectAll(userId, selected);
            return Result.success(selected ? "全选成功" : "取消全选成功");
        } catch (Exception e) {
            log.error("全选操作失败", e);
            return Result.error("操作失败：" + e.getMessage());
        }
    }

    /**
     * 计算优惠
     */
    @PostMapping("/calculate")
    public Result<CartDTO.Response> calculate(@RequestParam @NotNull(message = "用户ID不能为空") Long userId,
                                              @RequestBody CartDTO.CalculateRequest request) {
        try {
            CartDTO.Response result = cartService.calculate(userId, request.getCouponId());
            return Result.success(result);
        } catch (Exception e) {
            log.error("计算优惠失败", e);
            return Result.error("计算失败：" + e.getMessage());
        }
    }

    /**
     * 获取购物车统计
     */
    @GetMapping("/summary")
    public Result<CartDTO.Summary> getSummary(@RequestParam @NotNull(message = "用户ID不能为空") Long userId) {
        try {
            CartDTO.Summary summary = cartService.getSummary(userId);
            return Result.success(summary);
        } catch (Exception e) {
            log.error("获取购物车统计失败", e);
            return Result.error("获取统计失败：" + e.getMessage());
        }
    }

    /**
     * 获取购物车商品数量
     */
    @GetMapping("/count")
    public Result<Integer> getCartCount(@RequestParam @NotNull(message = "用户ID不能为空") Long userId) {
        try {
            CartDTO.Summary summary = cartService.getSummary(userId);
            return Result.success(summary.getItemCount());
        } catch (Exception e) {
            log.error("获取购物车数量失败", e);
            return Result.error("获取数量失败：" + e.getMessage());
        }
    }

    /**
     * 批量选择商品
     */
    @PutMapping("/batch-select")
    public Result<String> batchSelect(@RequestParam @NotNull(message = "用户ID不能为空") Long userId,
                                      @RequestBody CartDTO.BatchSelectRequest request) {
        try {
            if (request.getProductIds() == null || request.getProductIds().isEmpty()) {
                return Result.error(400, "商品列表不能为空");
            }
            if (request.getSelected() == null) {
                return Result.error(400, "选择状态不能为空");
            }

            for (Long productId : request.getProductIds()) {
                cartService.selectItem(userId, productId, request.getSelected());
            }
            return Result.success("批量操作成功");
        } catch (Exception e) {
            log.error("批量选择失败", e);
            return Result.error("批量操作失败：" + e.getMessage());
        }
    }
}