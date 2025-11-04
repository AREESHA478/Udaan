// auth-script.js

// 1. Modal open/close functions
function openModal(defaultTab = 'signin') { // Default to 'signin' if no tab is specified
    document.getElementById('auth-modal').classList.add('open');
    showTab(defaultTab); // Show the specified tab
}

function closeModal() {
    document.getElementById('auth-modal').classList.remove('open');
}

// 2. Tab switching logic
function showTab(tabName) {
    // Hide all forms and reset active state
    document.querySelectorAll('.auth-form').forEach(form => {
        form.classList.remove('active');
    });
    document.querySelectorAll('.auth-tab-btn').forEach(btn => {
        btn.classList.remove('active');
    });

    // Show the desired form and set the active tab
    document.getElementById(tabName + '-form').classList.add('active');
    
    // Update the tab button state (if the tab button exists)
    const tabButton = document.getElementById('tab-' + tabName);
    if (tabButton) {
        tabButton.classList.add('active');
    }
    
    // Optional: Reset scroll position if modal content is long
    const modalContent = document.querySelector('.auth-modal-content');
    if (modalContent) {
        modalContent.scrollTop = 0;
    }
}

// 3. Initial state setting
document.addEventListener('DOMContentLoaded', () => {
    // Close modal when clicking outside
    const modalOverlay = document.getElementById('auth-modal');
    modalOverlay.addEventListener('click', (event) => {
        if (event.target === modalOverlay) closeModal();
    });

    // Open modal only if the login icon is present (i.e., user is not authenticated)
    const loginIcon = document.querySelector('.user-profile .fa-user-circle');
    if (loginIcon) {
        loginIcon.parentElement.addEventListener('click', () => {
            openModal('signin'); // Show the sign-in tab by default on click
        });
    }

    // Check for login error on page load and open the modal
    if (window.location.search.includes('error')) {
        openModal('signin');
    }

});