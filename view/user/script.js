// 页面加载完成后执行
document.addEventListener('DOMContentLoaded', function() {
  // 1. 元素获取
  const passwordInput = document.getElementById('password');
  const confirmPasswordInput = document.getElementById('confirmPassword');
  const togglePwd = document.getElementById('togglePwd');
  const toggleConfirmPwd = document.getElementById('toggleConfirmPwd');
  const pwdError = document.getElementById('pwdError');
  const phoneInput = document.getElementById('phone');
  const phoneError = document.getElementById('phoneError');
  const submitBtn = document.getElementById('submitBtn');
  const agreementCheckbox = document.getElementById('agreement');

  // 2. 密码可见性切换函数
  function togglePasswordVisibility(input, toggle) {
    if (input.type === 'password') {
      input.type = 'text';
      toggle.src = 'eyeopen.png';
    } else {
      input.type = 'password';
      toggle.src = 'eyeclose.png';
    }
  }

  // 绑定密码可见性切换事件
  togglePwd.addEventListener('click', () => {
    togglePasswordVisibility(passwordInput, togglePwd);
  });

  toggleConfirmPwd.addEventListener('click', () => {
    togglePasswordVisibility(confirmPasswordInput, toggleConfirmPwd);
  });

  // 3. 密码一致性实时校验
  confirmPasswordInput.addEventListener('input', () => {
    if (confirmPasswordInput.value && passwordInput.value !== confirmPasswordInput.value) {
      pwdError.style.display = 'block';
    } else {
      pwdError.style.display = 'none';
    }
  });

  // 4. 手机号格式校验（11位纯数字）
  phoneInput.addEventListener('input', () => {
    const phone = phoneInput.value.trim();
    const phoneReg = /^[0-9]{11}$/;
    if (phone && !phoneReg.test(phone)) {
      phoneError.style.display = 'block';
    } else {
      phoneError.style.display = 'none';
    }
  });

  // 5. 注册提交逻辑
  submitBtn.addEventListener('click', () => {
    // 基础校验：协议勾选
    if (!agreementCheckbox.checked) {
      alert('请先同意服务条款和隐私政策');
      return;
    }

    // 手机号格式校验
    const phone = phoneInput.value.trim();
    const phoneReg = /^[0-9]{11}$/;
    if (!phoneReg.test(phone)) {
      phoneError.style.display = 'block';
      alert('手机号格式错误，请输入11位数字');
      phoneInput.focus();
      return;
    }

    // 密码一致性校验
    if (passwordInput.value !== confirmPasswordInput.value) {
      pwdError.style.display = 'block';
      alert('两次密码输入不一致');
      confirmPasswordInput.value = '';
      confirmPasswordInput.focus();
      return;
    }

    // 6. 模拟注册状态判断（使用localStorage存储用户信息）
    const isRegistered = localStorage.getItem(`user_${phone}`);
    if (isRegistered) {
      // 已注册：提示并跳转到个人主页
      alert('已经注册过一遍啦');
      // 实际项目中替换为真实的个人主页地址
      window.location.href = '/C:/Users/AW/Desktop/try2026/view/login/login.html';
    } else {
      // 未注册：保存信息并提示成功，跳转到主页
      const userInfo = {
        nickname: document.getElementById('nickname').value.trim(),
        phone: phone,
        registerTime: new Date().toLocaleString()
      };
      localStorage.setItem(`user_${phone}`, JSON.stringify(userInfo));
      alert('注册成功');
      // 实际项目中替换为真实的主页地址
      window.location.href = '/C:/Users/AW/Desktop/try2026/view/first view/view.html';
    }
  });
});