// 全局状态
let cartCount = 0
let cartItems = []
let currentProduct = null

// 优惠券（支持 10元、5元 各每周领1次）
let coupons = []
const COUPON_WEEK_LIMIT = 1

// DOM
const couponBtns = document.querySelectorAll('.coupon-btn')
const couponGetBtn = document.querySelector('.coupon-btn.get')
const couponSuccessModal = document.querySelector('.coupon-success-modal')
const overlay = document.querySelector('.overlay')

const productCards = document.querySelectorAll('.product-card')
const productModal = document.querySelector('.product-modal')
const payModal = document.querySelector('.pay-modal')
const cartModal = document.querySelector('.cart-modal')
const couponRecordModal = document.querySelector('.coupon-record-modal')

const addToCartBtn = document.querySelector('.add-to-cart')
const payNowBtn = document.querySelector('.pay-now')
const cartIcon = document.querySelector('.cart')
const cartCountEl = document.querySelector('.cart-count')
const cartItemsEl = document.querySelector('.cart-items')
const cartTotalEl = document.getElementById('cart-total')
const checkoutBtn = document.querySelector('.checkout-btn')

const cartCouponEntry = document.querySelector('.cart-coupon-entry')
const couponCountEl = document.getElementById('coupon-count')
const couponRecordItemsEl = document.querySelector('.coupon-record-items')
const useCouponCheckbox = document.getElementById('use-coupon')
const availableCouponEl = document.getElementById('available-coupon')

// 初始化
init()
function init() {
    initCouponButtons()
    bindEvents()
    updateCouponCount()
    renderCartList()
}

// ==============================
// 优惠券核心（支持 10元 + 5元）
// ==============================
function initCouponButtons() {
    function getWeekStart() {
        let now = new Date()
        let day = now.getDay() || 7
        let monday = new Date(now.setDate(now.getDate() - day + 1))
        monday.setHours(0,0,0,0)
        return monday
    }

    function hasGot10() {
        let mon = getWeekStart()
        return coupons.some(c => c.value === 10 && new Date(c.getTime) >= mon && c.isValid)
    }
    function hasGot5() {
        let mon = getWeekStart()
        return coupons.some(c => c.value === 5 && new Date(c.getTime) >= mon && c.isValid)
    }

    // 初始禁用已领
    if (hasGot10()) couponBtns[0].classList.add('disabled')
    if (hasGot5()) couponBtns[1].classList.add('disabled')
    if (hasGot10() && hasGot5()) {
        couponGetBtn.classList.add('disabled')
        couponGetBtn.textContent = '明天再领，下周再来吧'
    }

    // 领取按钮（核心修复！）
    couponGetBtn.onclick = function () {
        let got10 = hasGot10()
        let got5 = hasGot5()

        if (got10 && got5) {
            alert('本周优惠券已全部领完，下周再来吧！')
            return
        }

        let addValue = got10 ? 5 : 10

        coupons.push({
            value: addValue,
            getTime: new Date(),
            isValid: true
        })

        // 提示
        couponSuccessModal.style.display = 'block'
        overlay.style.display = 'block'
        setTimeout(() => {
            couponSuccessModal.style.display = 'none'
            overlay.style.display = 'none'
        }, 3000)

        // 禁用对应按钮
        if (addValue === 10) couponBtns[0].classList.add('disabled')
        if (addValue === 5) couponBtns[1].classList.add('disabled')

        // 全部领完则禁用领取按钮
        if (hasGot10() && hasGot5()) {
            couponGetBtn.classList.add('disabled')
            couponGetBtn.textContent = '明天再领，下周再来吧'
        }

        updateCouponCount()
        renderCartList()
    }
}

// 更新可用优惠券数量
function updateCouponCount() {
    let cnt = coupons.filter(c => c.isValid).length
    couponCountEl.textContent = cnt
    availableCouponEl.textContent = cnt
    useCouponCheckbox.disabled = cnt === 0
    useCouponCheckbox.checked = cnt > 0
}

// 获取当前最优优惠券
function getBestCoupon() {
    let valid = coupons.filter(c => c.isValid)
    if (valid.length === 0) return null
    return valid.reduce((max, c) => c.value > max.value ? c : max, valid[0])
}

// ==============================
// 购物车渲染
// ==============================
function renderCartList() {
    cartItemsEl.innerHTML = ''
    if (cartItems.length === 0) {
        document.querySelector('.cart-empty').style.display = 'block'
        document.querySelector('.cart-list').style.display = 'none'
        cartTotalEl.textContent = '0.00'
        return
    }

    document.querySelector('.cart-empty').style.display = 'none'
    document.querySelector('.cart-list').style.display = 'block'

    let best = getBestCoupon()
    let total = 0

    cartItems.forEach((item, idx) => {
        let subtotal = item.price * item.count
        let real = best ? Math.max(0, subtotal - best.value) : subtotal
        total += real

        let el = document.createElement('div')
        el.className = 'cart-item'
        el.innerHTML = `
            <div class="col col-index">${idx+1}</div>
            <div class="col col-img"><img src="${item.img}" class="cart-item-img"></div>
            <div class="col col-name">${item.name}</div>
            <div class="col col-price">¥${item.price.toFixed(2)}</div>
            <div class="col col-count">
                <div class="count-controls">
                    <button class="count-btn minus" data-id="${item.id}">-</button>
                    <input type="number" class="count-input" value="${item.count}" min="1" data-id="${item.id}">
                    <button class="count-btn plus" data-id="${item.id}">+</button>
                </div>
            </div>
            <div class="col col-subtotal">
                ${best ? `<span style=text-decoration:line-through;color:#666;font-size:12px>¥${subtotal.toFixed(2)}</span><br>` : ''}
                ¥${real.toFixed(2)}
            </div>
        `
        cartItemsEl.appendChild(el)
    })

    cartTotalEl.textContent = total.toFixed(2)
    bindCartCountEvents()
}

// 购物车数量加减
function bindCartCountEvents() {
    document.querySelectorAll('.minus').forEach(btn => {
        btn.onclick = () => {
            let id = btn.dataset.id
            let item = cartItems.find(i => i.id === id)
            if (item.count <= 1) return
            item.count--
            cartCount--
            cartCountEl.textContent = cartCount
            renderCartList()
        }
    })
    document.querySelectorAll('.plus').forEach(btn => {
        btn.onclick = () => {
            let id = btn.dataset.id
            let item = cartItems.find(i => i.id === id)
            item.count++
            cartCount++
            cartCountEl.textContent = cartCount
            renderCartList()
        }
    })
    document.querySelectorAll('.count-input').forEach(input => {
        input.onchange = () => {
            let id = input.dataset.id
            let item = cartItems.find(i => i.id === id)
            let val = Math.max(1, parseInt(input.value) || 1)
            cartCount = cartCount - item.count + val
            item.count = val
            cartCountEl.textContent = cartCount
            renderCartList()
        }
    })
}

// ==============================
// 优惠券记录弹窗
// ==============================
function renderCouponRecord() {
    couponRecordItemsEl.innerHTML = ''
    if (coupons.length === 0) {
        document.querySelector('.coupon-record-empty').style.display = 'block'
        document.querySelector('.coupon-record-list').style.display = 'none'
        return
    }
    document.querySelector('.coupon-record-empty').style.display = 'none'
    document.querySelector('.coupon-record-list').style.display = 'block'

    coupons.forEach(c => {
        let el = document.createElement('div')
        el.className = 'coupon-record-item'
        el.innerHTML = `
            <div class="record-col record-col-type">满减券</div>
            <div class="record-col record-col-value">¥${c.value}</div>
            <div class="record-col record-col-time">${new Date(c.getTime).toLocaleString()}</div>
            <div class="record-col record-col-status ${c.isValid ? 'status-valid' : 'status-invalid'}">
                ${c.isValid ? '有效' : '已使用'}
            </div>
        `
        couponRecordItemsEl.appendChild(el)
    })
}

// ==============================
// 全部事件绑定
// ==============================
function bindEvents() {
    // 商品点击
    productCards.forEach(card => {
        card.onclick = function () {
            currentProduct = {
                id: this.dataset.id,
                name: this.dataset.name,
                price: +this.dataset.price,
                desc: this.dataset.desc,
                img: this.dataset.img
            }
            document.getElementById('product-detail-img').src = currentProduct.img
            document.getElementById('product-detail-name').innerText = currentProduct.name
            document.getElementById('product-detail-desc').innerText = currentProduct.desc

            let ori = document.getElementById('original-price')
            let dis = document.getElementById('discount-price')
            let best = getBestCoupon()

            if (best) {
                ori.innerText = '¥' + currentProduct.price
                ori.style.display = 'inline'
                dis.innerText = '¥' + Math.max(0, currentProduct.price - best.value).toFixed(2)
            } else {
                ori.style.display = 'none'
                dis.innerText = '¥' + currentProduct.price
            }
            productModal.style.display = 'block'
            overlay.style.display = 'block'
        }
    })

    // 加入购物车
    addToCartBtn.onclick = function () {
        if (!currentProduct) return
        let exist = cartItems.find(i => i.id === currentProduct.id)
        if (exist) {
            exist.count++
        } else {
            cartItems.push({ ...currentProduct, count: 1 })
        }
        cartCount++
        cartCountEl.textContent = cartCount
        productModal.style.display = 'none'
        overlay.style.display = 'none'
        renderCartList()
        alert('已加入购物车')
    }

    // 立即支付
    payNowBtn.onclick = function () {
        productModal.style.display = 'none'
        payModal.style.display = 'block'
        updateCouponCount()
    }

    // 购物车打开
    cartIcon.onclick = function () {
        cartModal.style.display = 'block'
        overlay.style.display = 'block'
        renderCartList()
    }

    // 优惠券记录
    cartCouponEntry.onclick = function () {
        couponRecordModal.style.display = 'block'
        overlay.style.display = 'block'
        renderCouponRecord()
    }

    // 结算
    checkoutBtn.onclick = function () {
        if (cartItems.length === 0) {
            alert('购物车为空！')
            return
        }
        cartModal.style.display = 'none'
        payModal.style.display = 'block'
        updateCouponCount()
    }

    // 支付方式
   document.querySelectorAll('.pay-method').forEach(m => {
    m.onclick = function () {
        let best = getBestCoupon()
        let paySuccess = false
        
        // 1. 使用优惠券（如有）
        if (useCouponCheckbox.checked && best) {
            best.isValid = false
            updateCouponCount()
            alert('优惠券已使用，抵扣 ¥' + best.value)
        }

        // 2. 结算后处理购物车（两种方案可选，选一种即可）
        // ========== 方案1：结算后清空购物车（推荐，符合真实场景） ==========
        cartItems = [] // 清空购物车商品列表
        cartCount = 0  // 重置购物车总数
        cartCountEl.textContent = cartCount // 更新顶部购物车数字
        
        // ========== 方案2：结算后减少指定数量（比如只减1件，按需启用） ==========
        // // 遍历购物车，每件商品数量减1，数量为0则移除
        // cartItems = cartItems.map(item => {
        //     item.count -= 1
        //     cartCount -= 1
        //     return item
        // }).filter(item => item.count > 0) // 过滤掉数量为0的商品
        // cartCountEl.textContent = cartCount // 更新顶部数字

        // 3. 重新渲染购物车 + 提示支付成功
        renderCartList()
        paySuccess = true
        
        if (paySuccess) {
            alert('支付成功！购物车已更新')
            payModal.style.display = 'none'
            overlay.style.display = 'none'
        }
    }
})

// 结算按钮（新增：空购物车提示优化）
checkoutBtn.onclick = function () {
    if (cartItems.length === 0) {
        alert('购物车为空，无法结算！')
        return
    }
    cartModal.style.display = 'none'
    payModal.style.display = 'block'
    updateCouponCount()
}

    // 关闭弹窗
    document.querySelectorAll('.close-modal').forEach(btn => {
        btn.onclick = function () {
            productModal.style.display = 'none'
            payModal.style.display = 'none'
            couponSuccessModal.style.display = 'none'
            cartModal.style.display = 'none'
            couponRecordModal.style.display = 'none'
            overlay.style.display = 'none'
        }
    })

    // 遮罩关闭
    overlay.onclick = function () {
        productModal.style.display = 'none'
        payModal.style.display = 'none'
        couponSuccessModal.style.display = 'none'
        cartModal.style.display = 'none'
        couponRecordModal.style.display = 'none'
        overlay.style.display = 'none'
    }
    // 更新可用优惠券数量（修改这个函数）
function updateCouponCount() {
    let cnt = coupons.filter(c => c.isValid).length
    // 1. 更新原有优惠券入口数量
    couponCountEl.textContent = cnt
    availableCouponEl.textContent = cnt
    // 2. 新增：更新购物车显眼位置的优惠券数量
    document.getElementById('cart-coupon-num').textContent = cnt
    
    useCouponCheckbox.disabled = cnt === 0
    useCouponCheckbox.checked = cnt > 0
}
}