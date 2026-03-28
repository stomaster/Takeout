package com.qtc.ecommerce_demo.controller;

import com.qtc.ecommerce_demo.dto.ProductQueryDTO;
import com.qtc.ecommerce_demo.dto.Result;
import com.qtc.ecommerce_demo.entity.Product;
import com.qtc.ecommerce_demo.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductMapper productMapper;

    /**
     * 获取所有在售商品列表
     * GET /api/products
     */
    @GetMapping
    public Result<List<Product>> getProducts() {
        try {
            List<Product> products = productMapper.selectAllAvailable();
            return Result.success(products);
        } catch (Exception e) {
            return Result.error("获取商品列表失败: " + e.getMessage());
        }
    }

    /**
     * 获取商品详情
     * GET /api/products/{id}
     */
    @GetMapping("/{id}")
    public Result<Product> getProduct(@PathVariable Long id) {
        try {
            Product product = productMapper.selectById(id);
            if (product == null) {
                return Result.error("商品不存在");
            }
            return Result.success(product);
        } catch (Exception e) {
            return Result.error("获取商品详情失败: " + e.getMessage());
        }
    }

    /**
     * 添加新商品
     * POST /api/products
     */
    @PostMapping
    public Result<Long> addProduct(@RequestBody Product product) {
        try {
            // 验证必要字段
            if (product.getName() == null || product.getName().trim().isEmpty()) {
                return Result.error("商品名称不能为空");
            }
            if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                return Result.error("商品价格必须大于0");
            }

            // 设置默认值
            if (product.getStock() == null) {
                product.setStock(0);
            }
            if (product.getStatus() == null) {
                product.setStatus(1); // 默认上架
            }
            if (product.getCategory() == null) {
                product.setCategory("未分类");
            }

            // 设置时间
            product.setCreateTime(LocalDateTime.now());
            product.setUpdateTime(LocalDateTime.now());

            int result = productMapper.insert(product);
            if (result > 0) {
                return Result.success(product.getId());
            } else {
                return Result.error("添加商品失败");
            }
        } catch (Exception e) {
            return Result.error("添加商品失败: " + e.getMessage());
        }
    }

    /**
     * 更新商品信息
     * PUT /api/products/{id}
     */
    @PutMapping("/{id}")
    public Result<Boolean> updateProduct(@PathVariable Long id,
                                         @RequestBody Product product) {
        try {
            // 检查商品是否存在
            Product existingProduct = productMapper.selectById(id);
            if (existingProduct == null) {
                return Result.error("商品不存在");
            }

            // 设置ID
            product.setId(id);
            // 保留原创建时间
            product.setCreateTime(existingProduct.getCreateTime());
            // 更新修改时间
            product.setUpdateTime(LocalDateTime.now());

            int result = productMapper.update(product);
            return Result.success(result > 0);
        } catch (Exception e) {
            return Result.error("更新商品失败: " + e.getMessage());
        }
    }

    /**
     * 删除商品（逻辑删除，改为下架状态）
     * DELETE /api/products/{id}
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteProduct(@PathVariable Long id) {
        try {
            // 检查商品是否存在
            Product existingProduct = productMapper.selectById(id);
            if (existingProduct == null) {
                return Result.error("商品不存在");
            }

            // 逻辑删除：将状态改为0（下架）
            existingProduct.setStatus(0);
            existingProduct.setUpdateTime(LocalDateTime.now());

            int result = productMapper.update(existingProduct);
            return Result.success(result > 0);
        } catch (Exception e) {
            return Result.error("删除商品失败: " + e.getMessage());
        }
    }

    /**
     * 搜索商品
     * GET /api/products/search?keyword={keyword}
     */
    @GetMapping("/search")
    public Result<List<Product>> searchProducts(@RequestParam String keyword) {
        try {
            List<Product> products = productMapper.search("%" + keyword + "%");
            return Result.success(products);
        } catch (Exception e) {
            return Result.error("搜索商品失败: " + e.getMessage());
        }
    }

    /**
     * 根据分类获取商品
     * GET /api/products/category/{category}
     */
    @GetMapping("/category/{category}")
    public Result<List<Product>> getProductsByCategory(@PathVariable String category) {
        try {
            List<Product> products = productMapper.selectByCategory(category);
            return Result.success(products);
        } catch (Exception e) {
            return Result.error("获取分类商品失败: " + e.getMessage());
        }
    }

    /**
     * 分页查询商品
     * GET /api/products/page?page={page}&size={size}
     */
    @GetMapping("/page")
    public Result<List<Product>> getProductsByPage(@RequestParam(defaultValue = "1") Integer page,
                                                   @RequestParam(defaultValue = "10") Integer size) {
        try {
            int offset = (page - 1) * size;
            List<Product> products = productMapper.selectByPage(offset, size);
            return Result.success(products);
        } catch (Exception e) {
            return Result.error("分页查询失败: " + e.getMessage());
        }
    }

    /**
     * 获取商品总数
     * GET /api/products/count
     */
    @GetMapping("/count")
    public Result<Integer> getProductCount() {
        try {
            int count = productMapper.countAvailable();
            return Result.success(count);
        } catch (Exception e) {
            return Result.error("获取商品总数失败: " + e.getMessage());
        }
    }

    /**
     * 查询热门商品
     * GET /api/products/hot?limit={limit}
     */
    @GetMapping("/hot")
    public Result<List<Product>> getHotProducts(@RequestParam(defaultValue = "10") Integer limit) {
        try {
            List<Product> products = productMapper.selectHotProducts(limit);
            return Result.success(products);
        } catch (Exception e) {
            return Result.error("获取热门商品失败: " + e.getMessage());
        }
    }

    /**
     * 增加商品库存
     * PUT /api/products/{id}/increase-stock?quantity={quantity}
     */
    @PutMapping("/{id}/increase-stock")
    public Result<Boolean> increaseStock(@PathVariable Long id,
                                         @RequestParam Integer quantity) {
        try {
            if (quantity <= 0) {
                return Result.error("增加数量必须大于0");
            }

            int result = productMapper.increaseStock(id, quantity);
            return Result.success(result > 0);
        } catch (Exception e) {
            return Result.error("增加库存失败: " + e.getMessage());
        }
    }

    /**
     * 减少商品库存
     * PUT /api/products/{id}/reduce-stock?quantity={quantity}
     */
    @PutMapping("/{id}/reduce-stock")
    public Result<Boolean> reduceStock(@PathVariable Long id,
                                       @RequestParam Integer quantity) {
        try {
            if (quantity <= 0) {
                return Result.error("减少数量必须大于0");
            }

            int result = productMapper.reduceStock(id, quantity);
            return Result.success(result > 0);
        } catch (Exception e) {
            return Result.error("减少库存失败: " + e.getMessage());
        }
    }

    /**
     * 查询商品库存
     * GET /api/products/{id}/stock
     */
    @GetMapping("/{id}/stock")
    public Result<Integer> getStock(@PathVariable Long id) {
        try {
            Integer stock = productMapper.getStock(id);
            if (stock == null) {
                return Result.error("商品不存在");
            }
            return Result.success(stock);
        } catch (Exception e) {
            return Result.error("查询库存失败: " + e.getMessage());
        }
    }

    /**
     * 修改商品状态
     * PUT /api/products/{id}/status?status={status}
     */
    @PutMapping("/{id}/status")
    public Result<Boolean> updateStatus(@PathVariable Long id,
                                        @RequestParam Integer status) {
        try {
            if (status != 0 && status != 1) {
                return Result.error("状态值必须为0(下架)或1(上架)");
            }

            int result = productMapper.updateStatus(id, status);
            return Result.success(result > 0);
        } catch (Exception e) {
            return Result.error("修改状态失败: " + e.getMessage());
        }
    }

    /**
     * 批量查询商品
     * GET /api/products/batch?ids=1,2,3
     */
    @GetMapping("/batch")
    public Result<List<Product>> getProductsByIds(@RequestParam List<Long> ids) {
        try {
            if (ids == null || ids.isEmpty()) {
                return Result.success(List.of());
            }

            List<Product> products = productMapper.selectByIds(ids);
            return Result.success(products);
        } catch (Exception e) {
            return Result.error("批量查询失败: " + e.getMessage());
        }
    }

    /**
     * 高级查询（支持多个条件）
     * POST /api/products/query
     */
    @PostMapping("/query")
    public Result<List<Product>> queryProducts(@RequestBody ProductQueryDTO queryDTO) {
        try {
            // 这里可以使用更复杂的查询逻辑
            // 暂时先返回所有商品，在实际项目中可以按条件过滤
            List<Product> allProducts = productMapper.selectAllAvailable();

            // 按条件过滤
            List<Product> filteredProducts = allProducts.stream()
                    .filter(p -> queryDTO.getKeyword() == null ||
                            p.getName().contains(queryDTO.getKeyword()) ||
                            (p.getDescription() != null && p.getDescription().contains(queryDTO.getKeyword())))
                    .filter(p -> queryDTO.getCategory() == null || queryDTO.getCategory().equals(p.getCategory()))
                    .filter(p -> queryDTO.getMinPrice() == null || p.getPrice().compareTo(queryDTO.getMinPrice()) >= 0)
                    .filter(p -> queryDTO.getMaxPrice() == null || p.getPrice().compareTo(queryDTO.getMaxPrice()) <= 0)
                    .toList();

            return Result.success(filteredProducts);
        } catch (Exception e) {
            return Result.error("高级查询失败: " + e.getMessage());
        }
    }

    /**
     * 上传商品图片
     * POST /api/products/{id}/upload-image
     */
    @PostMapping("/{id}/upload-image")
    public Result<String> uploadProductImage(@PathVariable Long id,
                                             @RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return Result.error("文件不能为空");
            }

            // 检查文件类型
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return Result.error("只能上传图片文件");
            }

            // 生成文件名
            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            String filename = UUID.randomUUID() + fileExtension;
            String uploadDir = "src/main/resources/static/uploads/products/";

            // 创建目录
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 保存文件
            String filePath = uploadDir + filename;
            file.transferTo(new File(filePath));

            // 更新商品图片URL
            Product product = new Product();
            product.setId(id);
            product.setImageUrl("/uploads/products/" + filename);
            product.setUpdateTime(LocalDateTime.now());
            productMapper.update(product);

            return Result.success("/uploads/products/" + filename);
        } catch (IOException e) {
            return Result.error("上传图片失败: " + e.getMessage());
        } catch (Exception e) {
            return Result.error("上传失败: " + e.getMessage());
        }
    }
}