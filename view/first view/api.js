// ====================================================================
// 前端API接口文件 - 对接Java后端
// 文件位置：view/first view/api.js
// 使用方法：在HTML中引入 <script src="api.js"></script>
// ============================================
 
// 配置
const API_CONFIG = {
    BASE_URL: 'http://10.91.243.22:8081/api/users/register',
    TIMEOUT: 10000,
    UPLOAD_TIMEOUT: 30000
};
/*
BASE_URL: ''
含义：API请求的基础URL

当前值：空字符串（''）

作用：表示API请求会使用相对路径

实际效果：

当调用API时，会自动使用当前页面的协议、域名和端口

例如，如果页面是 http://localhost:8080/view.html，API请求会自动发往 http://localhost:8080

如果是 BASE_URL: 'http://localhost:8081'，API请求会发往 http://localhost:8081

2. TIMEOUT: 10000
含义：普通API请求的超时时间

单位：毫秒（10000ms = 10秒）

作用：设置普通API请求（如获取商品、登录等）在10秒内无响应就自动取消

适用场景：大部分GET、POST请求

3. UPLOAD_TIMEOUT: 30000
含义：文件上传的超时时间

单位：毫秒（30000ms = 30秒）

作用：设置文件上传（如图片、头像等）在30秒内无响应就自动取消

适用场景：上传商品图片、用户头像等文件操作

为什么更长：文件上传通常需要更多时间，所以设置了更长的超时
*/

// 全局状态存储
let currentUser = null;  //当前登录用户的信息
let authToken = ''; //用户的认证令牌

// ============================================
// 工具函数
// ============================================

/**
 * 从本地存储加载用户信息
 */
/*
硬编码的特点
优点：

简单直接：不需要额外定义变量

一目了然：看到代码就知道存储的键名是什么

快速开发：适合原型开发或小项目
*/
function loadUserFromStorage() {  // API.utils.loadUserFromStorage();  // ← 这里被调用,自动加载当前用户的信息
    try {
        const userStr = localStorage.getItem('bubble_user');//bubble_user:用户对象的JSON字符串
        //// 实际存储的JSON字符串格式
//'{"userId": 123, "username": "张三", "email": "zhangsan@example.com", "avatar": "avatar_url.jpg"}'
//localStorage是浏览器本地存储库，不是后端数据库
        const token = localStorage.getItem('bubble_token');

        if (userStr && token) {
            currentUser = JSON.parse(userStr);
            authToken = token;
            return true;
        }
    } catch (error) {
        console.error('加载用户信息失败:', error);
        clearUserData();
    }
    return false;
}

/**
 * 保存用户信息到本地存储
 */
function saveUserToStorage(user, token) {
    try {
        localStorage.setItem('bubble_user', JSON.stringify(user)); //localStorage里存的是字符串，user是对象
        localStorage.setItem('bubble_token', token);
        currentUser = user;
        authToken = token;
    } catch (error) {
        console.error('保存用户信息失败:', error);
    }
}

/**
 * 清除用户信息
 */
function clearUserData() {
    localStorage.removeItem('bubble_user');
    localStorage.removeItem('bubble_token');
    currentUser = null;
    authToken = '';
}

/**
 * 获取请求头
 * 

请求头是HTTP请求的一部分，用于向服务器传递额外的信息。它是键值对（key-value pairs）的集合，提供关于请求的元数据。
 */
function getAuthHeaders(contentType = 'application/json') {//application/json表示：这是需要按照JSON规范解析的应用程序数据
    const headers = {};

    if (contentType) {
        headers['Content-Type'] = contentType;
    }

    if (authToken) {
        headers['Authorization'] = `Bearer ${authToken}`;
    }

    return headers;
}

/**
 * 处理API响应
 * . await是什么意思？

await的意思是 等待，它也是一个关键字，只能在 async函数内部使用。

功能：await后面通常跟一个 Promise 对象。它会“暂停”当前 async函数的执行，直到它后面的 Promise 完成（兑现或拒绝），并返回 Promise 成功的结果。如果 Promise 失败，await会抛出错误。

作用：它让异步代码的写法看起来和同步代码一样直观，避免了层层嵌套的回调函数（即“回调地狱”）。

异步读取 (await response.text()或 await response.json())：

response.text()和 response.json()这两个方法本身返回的都是 Promise 对象。

response.text()​ 的“读取”过程：从网络接收的响应数据流可能是分块到达的。这个方法需要等待所有数据块传输完毕，然后将它们拼接成一整个字符串。这个过程是耗时的，如果让程序“干等”（同步），用户界面会“卡住”。

response.json()​ 的“解析”过程：首先它也要像 text()一样等待数据读取完成，得到字符串后，它还要对这个字符串进行 JSON 语法解析，将其转换成 JavaScript 对象。这个解析过程对于大型数据也可能耗费可观的时间。

所以，这两个操作都被设计成异步的。当程序执行到 await response.json()时，它不会“死等”在这里。它会说：“好吧，解析工作你先做着，我（主线程）先去处理别的任务（比如响应用户点击、渲染动画）。等你解析完了，再回来通知我，并把结果给我。”
 */
async function handleResponse(response) {
    if (!response.ok) {
        if (response.status === 401) {//未授权认证
            clearUserData();//清楚当前用户信息
            if (!window.location.pathname.includes('user.html')) {//当前路径是否包含user.html
                window.location.href = '../user/user.html';//没有的话跳转
            }
            throw new Error('登录已过期，请重新登录');
        }

        const errorText = await response.text();
        throw new Error(`HTTP ${response.status}: ${errorText || response.statusText}`);
    }

    const contentType = response.headers.get('content-type');
    if (contentType && contentType.includes('application/json')) {
        return await response.json();
    } else {
        return await response.text();
    }
}

/**
 * 通用API请求函数
 */
async function apiRequest(endpoint, options = {}) {
    const controller = new AbortController();
    const timeoutId = setTimeout(() => controller.abort(), options.timeout || API_CONFIG.TIMEOUT);

    try {
        const url = `${API_CONFIG.BASE_URL}${endpoint}`;
        const defaultOptions = {
            method: 'GET',
            headers: getAuthHeaders(),
            signal: controller.signal
        };

        const mergedOptions = { ...defaultOptions, ...options };

        const response = await fetch(url, mergedOptions);
        return await handleResponse(response);
    } catch (error) {
        if (error.name === 'AbortError') {
            throw new Error('请求超时，请检查网络连接');
        }
        console.error('API请求失败:', error);
        throw error;
    } finally {
        clearTimeout(timeoutId);
    }
}

// ============================================
// 用户资料相关API
// ============================================

const userProfileApi = {
    /**
     * 获取用户完整信息
     * GET /api/users/{userId}/profile
     */
    async getUserProfile(userId) {
        try {
            const response = await apiRequest(`/api/users/${userId}/profile`);
            return response;
        } catch (error) {
            console.error('获取用户资料失败:', error);
            return {
                code: 500,
                message: error.message,
                data: null
            };
        }
    },

    /**
     * 更新用户信息
     * PUT /api/users/{userId}/profile
     */
    async updateUserProfile(userId, profileData) {
        try {
            const response = await apiRequest(`/api/users/${userId}/profile`, {
                method: 'PUT',
                body: JSON.stringify(profileData)
            });
            return response;
        } catch (error) {
            console.error('更新用户资料失败:', error);
            return {
                code: 500,
                message: error.message,
                data: false
            };
        }
    },

    /**
     * 上传用户头像
     * POST /api/users/{userId}/avatar
     */
    async uploadAvatar(userId, file) {
        try {
            const formData = new FormData();
            formData.append('file', file);

            const response = await fetch(`${API_CONFIG.BASE_URL}/api/users/${userId}/avatar`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${authToken}`
                },
                body: formData,
                timeout: API_CONFIG.UPLOAD_TIMEOUT
            });

            return await handleResponse(response);
        } catch (error) {
            console.error('上传头像失败:', error);
            return {
                code: 500,
                message: error.message,
                data: null
            };
        }
    }
};

// ============================================
// 商品相关API
// ============================================

const productApi = {
    /**
     * 获取所有商品列表
     * GET /api/products
     */
    async getProducts() {
        try {
            const response = await apiRequest('/api/products');
            return response;
        } catch (error) {
            console.error('获取商品列表失败:', error);
            return {
                code: 500,
                message: error.message,
                data: []
            };
        }
    },

    /**
     * 获取商品详情
     * GET /api/products/{id}
     */
    async getProductDetail(id) {
        try {
            const response = await apiRequest(`/api/products/${id}`);
            return response;
        } catch (error) {
            console.error('获取商品详情失败:', error);
            return {
                code: 500,
                message: error.message,
                data: null
            };
        }
    },

    /**
     * 搜索商品
     * GET /api/products/search?keyword={keyword}
     */
    async searchProducts(keyword) {
        try {
            const response = await apiRequest(`/api/products/search?keyword=${encodeURIComponent(keyword)}`);
            return response;
        } catch (error) {
            console.error('搜索商品失败:', error);
            return {
                code: 500,
                message: error.message,
                data: []
            };
        }
    },

    /**
     * 根据分类获取商品
     * GET /api/products/category/{category}
     */
    async getProductsByCategory(category) {
        try {
            const response = await apiRequest(`/api/products/category/${encodeURIComponent(category)}`);
            return response;
        } catch (error) {
            console.error('获取分类商品失败:', error);
            return {
                code: 500,
                message: error.message,
                data: []
            };
        }
    },

    /**
     * 获取热门商品
     * GET /api/products/hot?limit={limit}
     */
    async getHotProducts(limit = 10) {
        try {
            const response = await apiRequest(`/api/products/hot?limit=${limit}`);
            return response;
        } catch (error) {
            console.error('获取热门商品失败:', error);
            return {
                code: 500,
                message: error.message,
                data: []
            };
        }
    },

    /**
     * 分页查询商品
     * GET /api/products/page?page={page}&size={size}
     */
    async getProductsByPage(page = 1, size = 10) {
        try {
            const response = await apiRequest(`/api/products/page?page=${page}&size=${size}`);
            return response;
        } catch (error) {
            console.error('分页查询失败:', error);
            return {
                code: 500,
                message: error.message,
                data: []
            };
        }
    },

    /**
     * 高级查询商品
     * POST /api/products/query
     */
    async queryProducts(queryParams) {
        try {
            const response = await apiRequest('/api/products/query', {
                method: 'POST',
                body: JSON.stringify(queryParams)
            });
            return response;
        } catch (error) {
            console.error('高级查询失败:', error);
            return {
                code: 500,
                message: error.message,
                data: []
            };
        }
    },

    /**
     * 上传商品图片
     * POST /api/products/{id}/upload-image
     */
    async uploadProductImage(productId, file) {
        try {
            const formData = new FormData();
            formData.append('file', file);

            const response = await fetch(`${API_CONFIG.BASE_URL}/api/products/${productId}/upload-image`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${authToken}`
                },
                body: formData,
                timeout: API_CONFIG.UPLOAD_TIMEOUT
            });

            return await handleResponse(response);
        } catch (error) {
            console.error('上传商品图片失败:', error);
            return {
                code: 500,
                message: error.message,
                data: null
            };
        }
    }
};

// ============================================
// 收藏相关API
// ============================================

const collectionApi = {
    /**
     * 获取用户收藏列表
     * GET /api/users/{userId}/collections
     */
    async getCollections(userId) {
        try {
            const response = await apiRequest(`/api/users/${userId}/collections`);
            return response;
        } catch (error) {
            console.error('获取收藏列表失败:', error);
            return {
                code: 500,
                message: error.message,
                data: []
            };
        }
    },

    /**
     * 添加收藏
     * POST /api/users/{userId}/collections?productId={productId}
     */
    async addCollection(userId, productId) {
        try {
            const response = await apiRequest(`/api/users/${userId}/collections?productId=${productId}`, {
                method: 'POST'
            });
            return response;
        } catch (error) {
            console.error('添加收藏失败:', error);
            return {
                code: 500,
                message: error.message,
                data: false
            };
        }
    },

    /**
     * 取消收藏
     * DELETE /api/users/{userId}/collections/{productId}
     */
    async removeCollection(userId, productId) {
        try {
            const response = await apiRequest(`/api/users/${userId}/collections/${productId}`, {
                method: 'DELETE'
            });
            return response;
        } catch (error) {
            console.error('取消收藏失败:', error);
            return {
                code: 500,
                message: error.message,
                data: false
            };
        }
    },

    /**
     * 检查是否已收藏
     * GET /api/users/{userId}/collections/check?productId={productId}
     */
    async checkCollected(userId, productId) {
        try {
            const response = await apiRequest(`/api/users/${userId}/collections/check?productId=${productId}`);
            return response;
        } catch (error) {
            console.error('检查收藏状态失败:', error);
            return {
                code: 500,
                message: error.message,
                data: false
            };
        }
    }
};

// ============================================
// 优惠券相关API
// ============================================

const couponApi = {
    /**
     * 获取优惠券列表
     * GET /api/coupon/list?userId={userId}
     */
    async getCouponList(userId) {
        try {
            const response = await apiRequest(`/api/coupon/list?userId=${userId}`);
            return response;
        } catch (error) {
            console.error('获取优惠券列表失败:', error);
            return {
                code: 500,
                message: error.message,
                data: []
            };
        }
    },

    /**
     * 领取优惠券
     * POST /api/coupon/receive?userId={userId}&couponId={couponId}
     */
    async receiveCoupon(userId, couponId) {
        try {
            const response = await apiRequest(`/api/coupon/receive?userId=${userId}&couponId=${couponId}`, {
                method: 'POST'
            });
            return response;
        } catch (error) {
            console.error('领取优惠券失败:', error);
            return {
                code: 500,
                message: error.message,
                data: false
            };
        }
    },

    /**
     * 获取可用优惠券
     * GET /api/coupon/available?userId={userId}&orderAmount={amount}
     */
    async getAvailableCoupons(userId, orderAmount) {
        try {
            const response = await apiRequest(`/api/coupon/available?userId=${userId}&orderAmount=${orderAmount}`);
            return response;
        } catch (error) {
            console.error('获取可用优惠券失败:', error);
            return {
                code: 500,
                message: error.message,
                data: []
            };
        }
    },

    /**
     * 使用优惠券
     * POST /api/coupon/use?userCouponId={id}&orderId={orderId}
     */
    async useCoupon(userCouponId, orderId) {
        try {
            const response = await apiRequest(`/api/coupon/use?userCouponId=${userCouponId}&orderId=${orderId}`, {
                method: 'POST'
            });
            return response;
        } catch (error) {
            console.error('使用优惠券失败:', error);
            return {
                code: 500,
                message: error.message,
                data: false
            };
        }
    }
};

// ============================================
// 购物车相关API
// ============================================

const cartApi = {
    /**
     * 获取购物车详情
     * GET /api/cart/list?userId={userId}
     */
    async getCart(userId) {
        try {
            const response = await apiRequest(`/api/cart/list?userId=${userId}`);
            return response;
        } catch (error) {
            console.error('获取购物车失败:', error);
            return {
                code: 500,
                message: error.message,
                data: null
            };
        }
    },

    /**
     * 添加到购物车
     * POST /api/cart/add?userId={userId}
     */
    async addToCart(userId, productId, quantity) {
        try {
            const response = await apiRequest(`/api/cart/add?userId=${userId}`, {
                method: 'POST',
                body: JSON.stringify({
                    productId: productId,
                    quantity: quantity
                })
            });
            return response;
        } catch (error) {
            console.error('添加购物车失败:', error);
            return {
                code: 500,
                message: error.message,
                data: false
            };
        }
    },

    /**
     * 更新购物车商品数量
     * PUT /api/cart/update?userId={userId}
     */
    async updateCart(userId, productId, quantity) {
        try {
            const response = await apiRequest(`/api/cart/update?userId=${userId}`, {
                method: 'PUT',
                body: JSON.stringify({
                    productId: productId,
                    quantity: quantity
                })
            });
            return response;
        } catch (error) {
            console.error('更新购物车失败:', error);
            return {
                code: 500,
                message: error.message,
                data: false
            };
        }
    },

    /**
     * 删除购物车商品
     * DELETE /api/cart/delete?userId={userId}&productId={productId}
     */
    async deleteCartItem(userId, productId) {
        try {
            const response = await apiRequest(`/api/cart/delete?userId=${userId}&productId=${productId}`, {
                method: 'DELETE'
            });
            return response;
        } catch (error) {
            console.error('删除购物车商品失败:', error);
            return {
                code: 500,
                message: error.message,
                data: false
            };
        }
    },

    /**
     * 清空购物车
     * DELETE /api/cart/clear?userId={userId}
     */
    async clearCart(userId) {
        try {
            const response = await apiRequest(`/api/cart/clear?userId=${userId}`, {
                method: 'DELETE'
            });
            return response;
        } catch (error) {
            console.error('清空购物车失败:', error);
            return {
                code: 500,
                message: error.message,
                data: false
            };
        }
    },

    /**
     * 选择/取消选择商品
     * PUT /api/cart/select?userId={userId}
     */
    async selectItem(userId, productId, selected) {
        try {
            const response = await apiRequest(`/api/cart/select?userId=${userId}`, {
                method: 'PUT',
                body: JSON.stringify({
                    productId: productId,
                    selected: selected
                })
            });
            return response;
        } catch (error) {
            console.error('选择商品失败:', error);
            return {
                code: 500,
                message: error.message,
                data: false
            };
        }
    },

    /**
     * 全选/取消全选
     * PUT /api/cart/select-all?userId={userId}&selected={selected}
     */
    async selectAll(userId, selected) {
        try {
            const response = await apiRequest(`/api/cart/select-all?userId=${userId}&selected=${selected}`, {
                method: 'PUT'
            });
            return response;
        } catch (error) {
            console.error('全选操作失败:', error);
            return {
                code: 500,
                message: error.message,
                data: false
            };
        }
    },

    /**
     * 计算优惠
     * POST /api/cart/calculate?userId={userId}
     */
    async calculateCart(userId, couponId) {
        try {
            const response = await apiRequest(`/api/cart/calculate?userId=${userId}`, {
                method: 'POST',
                body: JSON.stringify({
                    couponId: couponId
                })
            });
            return response;
        } catch (error) {
            console.error('计算优惠失败:', error);
            return {
                code: 500,
                message: error.message,
                data: null
            };
        }
    },

    /**
     * 获取购物车统计
     * GET /api/cart/summary?userId={userId}
     */
    async getCartSummary(userId) {
        try {
            const response = await apiRequest(`/api/cart/summary?userId=${userId}`);
            return response;
        } catch (error) {
            console.error('获取购物车统计失败:', error);
            return {
                code: 500,
                message: error.message,
                data: null
            };
        }
    },

    /**
     * 获取购物车商品数量
     * GET /api/cart/count?userId={userId}
     */
    async getCartCount(userId) {
        try {
            const response = await apiRequest(`/api/cart/count?userId=${userId}`);
            return response;
        } catch (error) {
            console.error('获取购物车数量失败:', error);
            return {
                code: 500,
                message: error.message,
                data: 0
            };
        }
    }
};

// ============================================
// 全局导出
// ============================================

// 全局API对象
const API = {
    // 用户相关
    user: userProfileApi,

    // 商品相关
    product: productApi,

    // 收藏相关
    collection: collectionApi,

    // 优惠券相关
    coupon: couponApi,

    // 购物车相关
    cart: cartApi,

    // 工具函数
    utils: {
        loadUserFromStorage,
        saveUserToStorage,
        clearUserData,
        getAuthHeaders
    },

    // 当前用户信息
    getCurrentUser: () => currentUser,
    getAuthToken: () => authToken,

    // 设置用户
    setCurrentUser: (user, token) => {
        saveUserToStorage(user, token);
    },

    // 检查登录状态
    isLoggedIn: () => {
        return currentUser !== null && authToken !== '';
    }
};

// 页面加载时自动加载用户信息
if (typeof window !== 'undefined') {
    window.addEventListener('DOMContentLoaded', () => {
        API.utils.loadUserFromStorage();
    });
}

// 全局导出
if (typeof window !== 'undefined') {
    window.API = API;
}

// 模块化导出
if (typeof module !== 'undefined' && module.exports) {
    module.exports = API;
}