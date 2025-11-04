// Theme Toggle Functionality
document.addEventListener('DOMContentLoaded', () => {
    const themeToggleBtn = document.getElementById('theme-toggle-btn');
    const body = document.body;
    const icon = themeToggleBtn.querySelector('i');

    // Function to apply the theme
    const applyTheme = (theme) => {
        body.setAttribute('data-theme', theme);
        if (theme === 'dark') {
            icon.classList.remove('fa-moon');
            icon.classList.add('fa-sun');
        } else {
            icon.classList.remove('fa-sun');
            icon.classList.add('fa-moon');
        }
        localStorage.setItem('theme', theme);
    };

    // Check for saved theme in local storage
    const savedTheme = localStorage.getItem('theme') || 'light';
    applyTheme(savedTheme);

    // Event listener for the toggle button
    themeToggleBtn.addEventListener('click', () => {
        const currentTheme = body.getAttribute('data-theme');
        const newTheme = currentTheme === 'light' ? 'dark' : 'light';
        applyTheme(newTheme);
    });
});