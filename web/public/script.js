document.addEventListener('DOMContentLoaded', () => {
    // Particle generation
    const particlesContainer = document.getElementById('particles');
    const particleCount = 50;

    for (let i = 0; i < particleCount; i++) {
        const particle = document.createElement('div');
        particle.classList.add('particle');

        const size = Math.random() * 5 + 2;
        particle.style.width = `${size}px`;
        particle.style.height = `${size}px`;

        particle.style.left = `${Math.random() * 100}%`;
        particle.style.top = `${Math.random() * 100}%`;

        particle.style.animationDuration = `${Math.random() * 20 + 10}s`;
        particle.style.animationDelay = `${Math.random() * 5}s`;

        particlesContainer.appendChild(particle);
    }

    // Form handling
    const form = document.getElementById('registerForm');
    const messageDiv = document.getElementById('message');
    const submitBtn = form.querySelector('.btn-submit');

    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const username = document.getElementById('username').value.trim();
        const password = document.getElementById('password').value.trim();

        if (username.length < 4) {
            showMessage('Tài khoản phải có ít nhất 4 ký tự.', 'error');
            return;
        }

        // Disable button during request
        submitBtn.disabled = true;
        submitBtn.textContent = 'Đang xử lý...';
        messageDiv.style.display = 'none';

        try {
            const response = await fetch('http://localhost:3000/api/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ username, password })
            });

            const data = await response.json();

            if (data.success) {
                showMessage(data.message, 'success');
                form.reset();
            } else {
                showMessage(data.message, 'error');
            }
        } catch (error) {
            console.error('Error:', error);
            showMessage('Có lỗi xảy ra, vui lòng thử lại sau.', 'error');
        } finally {
            submitBtn.disabled = false;
            submitBtn.textContent = 'Đăng Ký Ngay';
        }
    });

    function showMessage(text, type) {
        messageDiv.textContent = text;
        messageDiv.className = `message ${type}`;
        messageDiv.style.display = 'block';
    }
});
