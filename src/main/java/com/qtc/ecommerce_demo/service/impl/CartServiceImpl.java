package com.qtc.ecommerce_demo.service.impl;

import com.qtc.ecommerce_demo.dto.CartDTO;
import com.qtc.ecommerce_demo.entity.Cart;
import com.qtc.ecommerce_demo.entity.Product;
import com.qtc.ecommerce_demo.entity.Coupon;
import com.qtc.ecommerce_demo.mapper.CartMapper;
import com.qtc.ecommerce_demo.mapper.ProductMapper;
import com.qtc.ecommerce_demo.mapper.CouponMapper;
import com.qtc.ecommerce_demo.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartMapper cartMapper;
    private final ProductMapper productMapper;
    private final CouponMapper couponMapper;

    @Override
    public CartDTO.Response getCart(Long userId) {
        // 1. 查询购物车数据
        List<Cart> cartItems = cartMapper.selectCartByUserId(userId);

        // 2. 查询商品信息
        List<Long> productIds = cartItems.stream()
                .map(Cart::getProductId)
                .collect(Collectors.toList());

        List<Product> products = productMapper.batchSelectByIds(productIds);
        Map<Long, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        // 3. 构建购物车项列表
        List<CartDTO.Item> items = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        Integer totalQuantity = 0;
        Integer selectedCount = 0;

        for (Cart cartItem : cartItems) {
            Product product = productMap.get(cartItem.getProductId());
            if (product == null) {
                continue;
            }

            CartDTO.Item item = new CartDTO.Item();
            BeanUtils.copyProperties(cartItem, item);

            // 设置商品信息
            item.setProductName(product.getName());
            item.setImageUrl(product.getImageUrl());
            item.setPrice(product.getPrice());
            item.setStatus(product.getStatus());
            item.setStock(product.getStock());

            // 计算小计
            if (cartItem.getQuantity() != null && product.getPrice() != null) {
                BigDecimal subtotal = product.getPrice().multiply(
                        BigDecimal.valueOf(cartItem.getQuantity())
                );
                item.setSubtotal(subtotal);

                // 统计选中的商品
                if (Boolean.TRUE.equals(cartItem.getSelected())) {
                    totalAmount = totalAmount.add(subtotal);
                    totalQuantity += cartItem.getQuantity();
                    selectedCount++;
                }
            }

            // 如果商品下架，自动取消选中
            if (product.getStatus() != null && product.getStatus() == 0) {
                item.setSelected(false);
                cartMapper.updateSelected(userId, cartItem.getProductId(), false);
            }

            items.add(item);
        }

        // 4. 查询可用优惠券
        List<CartDTO.Coupon> coupons = getAvailableCoupons(userId, totalAmount);

        // 5. 自动选择最优优惠券
        Long selectedCouponId = selectBestCoupon(coupons, totalAmount);
        Map<String, BigDecimal> discountInfo = calculateDiscount(totalAmount, selectedCouponId, coupons);

        // 6. 构建响应
        CartDTO.Response response = new CartDTO.Response();
        response.setItems(items);
        response.setCoupons(coupons);
        response.setTotalAmount(totalAmount);
        response.setDiscountAmount(discountInfo.get("discountAmount"));
        response.setFinalAmount(discountInfo.get("finalAmount"));
        response.setTotalQuantity(totalQuantity);
        response.setItemCount(items.size());
        response.setSelectedCouponId(selectedCouponId);

        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addToCart(Long userId, Long productId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new RuntimeException("商品数量必须大于0");
        }

        // 1. 验证商品
        Product product = productMapper.selectById(productId);
        if (product == null) {
            throw new RuntimeException("商品不存在");
        }
        if (product.getStatus() != null && product.getStatus() == 0) {
            throw new RuntimeException("商品已下架");
        }

        // 2. 验证库存
        Integer stock = product.getStock();
        if (stock == null || stock < quantity) {
            throw new RuntimeException("商品库存不足");
        }

        // 3. 检查是否已在购物车
        Cart existingCart = cartMapper.selectByUserIdAndProductId(userId, productId);

        if (existingCart != null) {
            // 更新数量
            int newQuantity = existingCart.getQuantity() + quantity;
            if (stock < newQuantity) {
                throw new RuntimeException("库存不足，最多可添加：" + stock + " 件");
            }

            existingCart.setQuantity(newQuantity);
            existingCart.calculateTotal();
            existingCart.setUpdateTime(LocalDateTime.now());
            cartMapper.updateCart(existingCart);
        } else {
            // 新增
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setProductId(productId);
            cart.setProductName(product.getName());
            cart.setProductPrice(product.getPrice());
            cart.setProductImage(product.getImageUrl());
            cart.setQuantity(quantity);
            cart.setSelected(true);
            cart.calculateTotal();
            cart.setCreateTime(LocalDateTime.now());
            cart.setUpdateTime(LocalDateTime.now());

            cartMapper.insertCart(cart);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCart(Long userId, Long productId, Integer quantity) {
        if (quantity == null || quantity < 0) {
            throw new RuntimeException("商品数量不能为负数");
        }

        // 1. 验证商品
        Product product = productMapper.selectById(productId);
        if (product == null) {
            throw new RuntimeException("商品不存在");
        }

        // 2. 获取购物车项
        Cart cart = cartMapper.selectByUserIdAndProductId(userId, productId);
        if (cart == null) {
            throw new RuntimeException("购物车中不存在该商品");
        }

        if (quantity == 0) {
            // 删除
            cartMapper.deleteCart(cart.getCartId());
            return;
        }

        // 3. 验证库存
        Integer stock = product.getStock();
        if (stock == null || stock < quantity) {
            throw new RuntimeException("库存不足，当前库存：" + stock);
        }

        // 4. 更新数量
        cart.setQuantity(quantity);
        cart.calculateTotal();
        cart.setUpdateTime(LocalDateTime.now());
        cartMapper.updateCart(cart);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCartItem(Long userId, Long productId) {
        cartMapper.deleteByUserIdAndProductId(userId, productId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clearCart(Long userId) {
        cartMapper.clearCartByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void selectItem(Long userId, Long productId, Boolean selected) {
        cartMapper.updateSelected(userId, productId, selected);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void selectAll(Long userId, Boolean selected) {
        cartMapper.updateAllSelected(userId, selected);
    }

    @Override
    public CartDTO.Response calculate(Long userId, Long couponId) {
        // 1. 计算选中商品总金额
        List<Cart> selectedCarts = cartMapper.selectSelectedByUserId(userId);
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (Cart cart : selectedCarts) {
            Product product = productMapper.selectById(cart.getProductId());
            if (product != null && product.getStatus() != null && product.getStatus() == 1) {
                if (product.getPrice() != null && cart.getQuantity() != null) {
                    totalAmount = totalAmount.add(
                            product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity()))
                    );
                }
            }
        }

        // 2. 获取可用优惠券
        List<CartDTO.Coupon> coupons = getAvailableCoupons(userId, totalAmount);

        // 3. 计算优惠
        Long selectedCoupon = (couponId != null) ? couponId : selectBestCoupon(coupons, totalAmount);
        Map<String, BigDecimal> discountInfo = calculateDiscount(totalAmount, selectedCoupon, coupons);

        // 4. 返回结果
        CartDTO.Response response = new CartDTO.Response();
        response.setTotalAmount(totalAmount);
        response.setDiscountAmount(discountInfo.get("discountAmount"));
        response.setFinalAmount(discountInfo.get("finalAmount"));
        response.setSelectedCouponId(selectedCoupon);
        response.setCoupons(coupons);

        return response;
    }

    @Override
    public CartDTO.Summary getSummary(Long userId) {
        CartDTO.Summary summary = new CartDTO.Summary();

        Integer itemCount = cartMapper.countCartItems(userId);
        BigDecimal totalAmount = cartMapper.sumSelectedAmount(userId);

        summary.setItemCount(itemCount != null ? itemCount : 0);
        summary.setTotalAmount(totalAmount != null ? totalAmount : BigDecimal.ZERO);

        // 统计选中商品数量
        List<Cart> selectedItems = cartMapper.selectSelectedByUserId(userId);
        int selectedCount = selectedItems.stream()
                .mapToInt(Cart::getQuantity)
                .sum();
        summary.setSelectedCount(selectedCount);

        return summary;
    }

    // 私有辅助方法
    private List<CartDTO.Coupon> getAvailableCoupons(Long userId, BigDecimal totalAmount) {
        try {
            List<Coupon> coupons = couponMapper.selectAvailableCoupons(userId, totalAmount.doubleValue());

            return coupons.stream().map(coupon -> {
                CartDTO.Coupon vo = new CartDTO.Coupon();
                vo.setCouponId(coupon.getId());
                vo.setName(coupon.getName());
                vo.setDescription(coupon.getDescription());
                vo.setType(coupon.getType());
                vo.setAmount(coupon.getAmount());
                vo.setMinAmount(coupon.getMinAmount());
                vo.setAvailable(true);
                return vo;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("获取优惠券失败", e);
            return new ArrayList<>();
        }
    }

    private Long selectBestCoupon(List<CartDTO.Coupon> coupons, BigDecimal totalAmount) {
        if (coupons == null || coupons.isEmpty() || totalAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return null;
        }

        return coupons.stream()
                .filter(CartDTO.Coupon::getAvailable)
                .max(Comparator.comparing(CartDTO.Coupon::getAmount))
                .map(CartDTO.Coupon::getCouponId)
                .orElse(null);
    }

    private Map<String, BigDecimal> calculateDiscount(BigDecimal totalAmount, Long couponId,
                                                      List<CartDTO.Coupon> coupons) {
        Map<String, BigDecimal> result = new HashMap<>();
        result.put("discountAmount", BigDecimal.ZERO);
        result.put("finalAmount", totalAmount);

        if (couponId == null || coupons == null || coupons.isEmpty()) {
            return result;
        }

        Optional<CartDTO.Coupon> couponOpt = coupons.stream()
                .filter(c -> c.getCouponId() != null && c.getCouponId().equals(couponId))
                .findFirst();

        if (couponOpt.isPresent()) {
            CartDTO.Coupon coupon = couponOpt.get();
            BigDecimal discount = coupon.getAmount();
            BigDecimal finalAmount = totalAmount.subtract(discount);

            // 优惠后金额不能小于0
            if (finalAmount.compareTo(BigDecimal.ZERO) < 0) {
                finalAmount = BigDecimal.ZERO;
            }

            result.put("discountAmount", discount);
            result.put("finalAmount", finalAmount);
        }

        return result;
    }
}