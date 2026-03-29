document.addEventListener('DOMContentLoaded', function () {
    // 1. 获取所有商品数据
    const productCards = document.querySelectorAll('.product-card');
    const productList = Array.from(productCards).map(card => ({
        name: card.dataset.name,
        element: card
    }));

    const searchInput = document.getElementById('searchInput');
    const searchResults = document.getElementById('searchResults');

    // 2. 实时搜索联想
    searchInput.addEventListener('input', function () {
        const keyword = this.value.trim().toLowerCase();
        if (!keyword) {
            searchResults.style.display = 'none';
            return;
        }

        // 匹配商品
        const matchedProducts = productList.filter(item =>
            item.name.toLowerCase().includes(keyword)
        );

        if (matchedProducts.length === 0) {
            searchResults.innerHTML = '<div class="no-result">未找到相关奶茶</div>';
            searchResults.style.display = 'block';
            return;
        }

        // 渲染联想结果
        searchResults.innerHTML = matchedProducts.map(item => {
            const reg = new RegExp(`(${keyword})`, 'gi');
            const highlightedName = item.name.replace(reg, '<span class="highlight">$1</span>');
            return `<div class="result-item" data-name="${item.name}">${highlightedName}</div>`;
        }).join('');

        searchResults.style.display = 'block';
    });

    // 3. 点击搜索结果 → 打开对应商品弹窗
    searchResults.addEventListener('click', function (e) {
        const resultItem = e.target.closest('.result-item');
        if (!resultItem) return;

        const targetName = resultItem.dataset.name;
        const targetProduct = productList.find(p => p.name === targetName);

        if (targetProduct) {
            // 模拟点击商品卡片 → 自动打开购买页
            targetProduct.element.click();
            // 关闭搜索下拉框
            searchResults.style.display = 'none';
            searchInput.value = targetName;
        }
    });

    // 4. 点击空白处关闭搜索框
    document.addEventListener('click', (e) => {
        if (!searchInput.contains(e.target) && !searchResults.contains(e.target)) {
            searchResults.style.display = 'none';
        }
    });
});