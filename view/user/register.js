/**
 * 注册页面逻辑
 * 对接后端注册接口：POST /api/users/register
 */

document.addEventListener('DOMContentLoaded', function() {
    // 获取DOM元素
    const registerForm = document.getElementById('registerForm');
    const registerBtn = document.getElementById('registerBtn');
    const loading = document.getElementById('loading');
    const successMessage = document.getElementById('successMessage');
    const generalError = document.getElementById('generalError');

    // 输入字段
    const usernameInput = document.getElementById('username');
    const passwordInput = document.getElementById('password');
    const confirmPasswordInput = document.getElementById('confirmPassword');
    const emailInput = document.getElementById('email');
    const agreeTermsInput = document.getElementById('agreeTerms');

    // 错误提示元素
    const usernameError = document.getElementById('usernameError');
    const passwordError = document.getElementById('passwordError');
    const confirmError = document.getElementById('confirmError');
    const emailError = document.getElementById('emailError');
    const termsError = document.getElementById('termsError');
    const passwordStrength = document.getElementById('passwordStrength');

    // 表单状态
    let isSubmitting = false;

    // 验证规则
    const validationRules = {
        username: {
            regex: /^[a-zA-Z0-9_]{4,20}$/,
            message: '用户名必须为4-20位字母、数字或下划线'
        },
        password: {
            minLength: 6,
            maxLength: 20
        },
        email: {
            regex: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
            message: '请输入有效的邮箱地址'
        }
    };

    // 显示错误提示
    function showError(input, errorElement, message) {
        input.classList.remove('success');
        input.classList.add('error');
        errorElement.textContent = message;
        errorElement.classList.add('show');
    }

    // 清除错误提示
    function clearError(input, errorElement) {
        input.classList.remove('error');
        errorElement.textContent = '';
        errorElement.classList.remove('show');
    }

    // 显示成功状态
    function showSuccess(input) {
        input.classList.remove('error');
        input.classList.add('success');
    }

    // 验证用户名
    function validateUsername() {
        const username = usernameInput.value.trim();

        if (!username) {
            showError(usernameInput, usernameError, '用户名不能为空');
            return false;
        }

        if (!validationRules.username.regex.test(username)) {
            showError(usernameInput, usernameError, validationRules.username.message);
            return false;
        }

        clearError(usernameInput, usernameError);
        showSuccess(usernameInput);
        return true;
    }

    // 检查密码强度
    function checkPasswordStrength(password) {
        if (!password) {
            passwordStrength.classList.remove('show');
            return '';
        }

        let strength = '';
        let className = '';

        if (password.length < 6) {
            strength = '弱：密码至少6位';
            className = 'strength-weak';
        } else if (password.length < 8 || !/[A-Za-z]/.test(password) || !/\d/.test(password)) {
            strength = '中：建议包含字母和数字';
            className = 'strength-medium';
        } else {
            strength = '强：密码强度足够';
            className = 'strength-strong';
        }

        passwordStrength.textContent = strength;
        passwordStrength.className = 'password-strength ' + className;
        passwordStrength.classList.add('show');

        return strength;
    }

    // 验证密码
    function validatePassword() {
        const password = passwordInput.value;

        if (!password) {
            showError(passwordInput, passwordError, '密码不能为空');
            return false;
        }

        if (password.length < validationRules.password.minLength) {
            showError(passwordInput, passwordError, `密码至少需要${validationRules.password.minLength}位`);
            return false;
        }

        if (password.length > validationRules.password.maxLength) {
            showError(passwordInput, passwordError, `密码不能超过${validationRules.password.maxLength}位`);
            return false;
        }

        clearError(passwordInput, passwordError);
        showSuccess(passwordInput);
        checkPasswordStrength(password);
        return true;
    }

    // 验证确认密码
    function validateConfirmPassword() {
        const password = passwordInput.value;
        const confirmPassword = confirmPasswordInput.value;

        if (!confirmPassword) {
            showError(confirmPasswordInput, confirmError, '请再次输入密码');
            return false;
        }

        if (password !== confirmPassword) {
            showError(confirmPasswordInput, confirmError, '两次输入的密码不一致');
            return false;
        }

        clearError(confirmPasswordInput, confirmError);
        showSuccess(confirmPasswordInput);
        return true;
    }

    // 验证邮箱（可选）
    function validateEmail() {
        const email = emailInput.value.trim();

        if (!email) {
            clearError(emailInput, emailError);
            return true; // 邮箱可选
        }

        if (!validationRules.email.regex.test(email)) {
            showError(emailInput, emailError, validationRules.email.message);
            return false;
        }

        clearError(emailInput, emailError);
        showSuccess(emailInput);
        return true;
    }

    // 验证服务条款
    function validateTerms() {
        if (!agreeTermsInput.checked) {
            termsError.textContent = '请阅读并同意用户协议和隐私政策';
            termsError.classList.add('show');
            return false;
        }

        termsError.classList.remove('show');
        return true;
    }

    // 实时验证
    usernameInput.addEventListener('input', validateUsername);
    usernameInput.addEventListener('blur', validateUsername);

    passwordInput.addEventListener('input', function() {
        validatePassword();
        if (confirmPasswordInput.value) {
            validateConfirmPassword();
        }
    });
    passwordInput.addEventListener('blur', validatePassword);

    confirmPasswordInput.addEventListener('input', validateConfirmPassword);
    confirmPasswordInput.addEventListener('blur', validateConfirmPassword);

    emailInput.addEventListener('input', validateEmail);
    emailInput.addEventListener('blur', validateEmail);

    agreeTermsInput.addEventListener('change', function() {
        if (agreeTermsInput.checked) {
            termsError.classList.remove('show');
        }
    });

    // 显示加载状态
    function showLoading() {
        loading.classList.add('show');
        registerBtn.disabled = true;
        registerBtn.textContent = '注册中...';
        generalError.classList.remove('show');
        isSubmitting = true;
    }

    // 隐藏加载状态
    function hideLoading() {
        loading.classList.remove('show');
        registerBtn.disabled = false;
        registerBtn.textContent = '立即注册';
        isSubmitting = false;
    }

    // 显示成功消息
    function showSuccessMessage() {
        successMessage.classList.add('show');
        registerForm.reset();

        // 3秒后跳转到登录页
        setTimeout(() => {
            window.location.href = 'user.html';
        }, 2000);
    }

    // 显示通用错误
    function showGeneralError(message) {
        generalError.textContent = message;
        generalError.classList.add('show');
    }

    // 提交表单
    registerForm.addEventListener('submit', async function(event) {
        event.preventDefault();

        if (isSubmitting) {
            return;
        }

        // 验证所有字段
        const isUsernameValid = validateUsername();
        const isPasswordValid = validatePassword();
        const isConfirmValid = validateConfirmPassword();
        const isEmailValid = validateEmail();
        const isTermsValid = validateTerms();

        if (!isUsernameValid || !isPasswordValid || !isConfirmValid || !isEmailValid || !isTermsValid) {
            showGeneralError('请检查表单中的错误信息');
            return;
        }

        // 准备注册数据
        const userData = {
            username: usernameInput.value.trim(),
            password: passwordInput.value,
            email: emailInput.value.trim() || null
        };

        console.log('提交注册数据:', { ...userData, password: '***' });

        // 显示加载状态
        showLoading();

        try {
            // 调用API注册接口
            const response = await fetch('http://10.91.243.22:8081/api/users/register',{
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(userData)
            });

            const result = await response.json();

            if (response.ok && result.code === 200) {
                // 注册成功
                console.log('注册成功:', result);
                hideLoading();
                showSuccessMessage();
            } else {
                // 注册失败
                console.error('注册失败:', result);
                hideLoading();
                showGeneralError(result.message || '注册失败，请稍后重试');

                // 特殊处理用户名已存在的情况
                if (result.message && result.message.includes('用户名已存在')) {
                    showError(usernameInput, usernameError, '用户名已存在，请更换用户名');
                }
            }
        } catch (error) {
            console.error('注册请求失败:', error);
            hideLoading();
            showGeneralError('网络错误，请检查网络连接后重试');
        }
    });
});

// 模态框相关函数
function showTerms() {
    const modal = document.getElementById('modalOverlay');
    const title = document.getElementById('modalTitle');
    const body = document.getElementById('modalBody');

    title.textContent = '用户协议';
    body.innerHTML = `
        <h3>欢迎使用奶茶外卖平台</h3>
        <p>本协议是您与奶茶外卖平台（以下简称"本平台"）之间关于使用本平台服务的法律协议。请您仔细阅读以下条款：</p>
        
        <h3>1. 服务说明</h3>
        <p>本平台提供奶茶饮品的外卖订购、配送及相关服务。您理解并同意，本平台提供的服务可能根据实际情况进行调整。</p>
        
        <h3>2. 用户注册</h3>
        <p>2.1 您需要注册账号才能使用本平台服务。<br>
        2.2 您应提供真实、准确、完整的注册信息。<br>
        2.3 您对账号和密码的安全负全部责任。</p>
        
        <h3>3. 订单与支付</h3>
        <p>3.1 订单提交后不可随意取消。<br>
        3.2 付款后订单立即生效。<br>
        3.3 价格如有变动，以实际支付价格为准。</p>
        
        <h3>4. 配送服务</h3>
        <p>4.1 配送时间可能因天气、交通等因素延迟。<br>
        4.2 请确保配送地址准确无误。<br>
        4.3 如遇特殊原因，平台可能调整配送范围。</p>
        
        <h3>5. 责任限制</h3>
        <p>5.1 本平台不保证服务不中断或完全无错误。<br>
        5.2 因不可抗力导致的服务中断，平台不承担责任。</p>
        
        <h3>6. 协议修改</h3>
        <p>本平台有权随时修改本协议。修改后的协议一经公布即有效。</p>
        
        <p style="margin-top: 20px; color: #888; font-size: 12px;">
            更新日期：2024年1月<br>
            如您继续使用本平台服务，即表示您已阅读并同意本协议。
        </p>
    `;

    modal.classList.add('show');
}

function showPrivacy() {
    const modal = document.getElementById('modalOverlay');
    const title = document.getElementById('modalTitle');
    const body = document.getElementById('modalBody');

    title.textContent = '隐私政策';
    body.innerHTML = `
        <h3>隐私保护政策</h3>
        <p>本政策说明了我们如何收集、使用、存储和保护您的个人信息。请您仔细阅读：</p>
        
        <h3>1. 信息收集</h3>
        <p>1.1 注册信息：用户名、密码、邮箱等。<br>
        1.2 订单信息：收货地址、联系方式、订单记录等。<br>
        1.3 设备信息：IP地址、设备类型、操作系统等。</p>
        
        <h3>2. 信息使用</h3>
        <p>2.1 为您提供和优化服务。<br>
        2.2 处理订单和配送。<br>
        2.3 发送重要通知。<br>
        2.4 提升用户体验。</p>
        
        <h3>3. 信息保护</h3>
        <p>3.1 采用安全技术保护个人信息。<br>
        3.2 建立访问控制制度。<br>
        3.3 定期进行安全检查。</p>
        
        <h3>4. 信息共享</h3>
        <p>4.1 不会向第三方出售个人信息。<br>
        4.2 仅在必要时与配送方共享必要信息。<br>
        4.3 法律要求时可能披露信息。</p>
        
        <h3>5. Cookie使用</h3>
        <p>5.1 使用Cookie提升服务质量。<br>
        5.2 可设置浏览器拒绝Cookie。</p>
        
        <h3>6. 您的权利</h3>
        <p>6.1 查阅、复制个人信息。<br>
        6.2 更正、补充个人信息。<br>
        6.3 删除个人信息。<br>
        6.4 撤回同意。</p>
        
        <h3>7. 未成年人保护</h3>
        <p>7.1 不满18岁需监护人同意。<br>
        7.2 如发现未成年人注册，将删除账号。</p>
        
        <p style="margin-top: 20px; color: #888; font-size: 12px;">
            更新日期：2024年1月<br>
            如有疑问，请联系客服：service@bubbletea.com
        </p>
    `;

    modal.classList.add('show');
}

function closeModal() {
    document.getElementById('modalOverlay').classList.remove('show');
}

// 点击模态框背景关闭
document.getElementById('modalOverlay').addEventListener('click', function(event) {
    if (event.target === this) {
        closeModal();
    }
});

// ESC键关闭模态框
document.addEventListener('keydown', function(event) {
    if (event.key === 'Escape') {
        closeModal();
    }
});