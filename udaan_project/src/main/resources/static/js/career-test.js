document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('career-test-form');

    form.addEventListener('submit', (event) => {
        // 1. Prevent the default form submission which reloads the page
        event.preventDefault();

        // 2. Get all checked skill checkboxes
        const checkedSkills = document.querySelectorAll('input[name="skill"]:checked');

        // 3. Extract their values and join them into a comma-separated string
        const skillValues = Array.from(checkedSkills).map(checkbox => checkbox.value);
        const skillsQueryString = skillValues.join(',');

        // 4. Redirect to the suggestion page with the skills as a URL parameter
        window.location.href = `/career-suggestion?skills=${skillsQueryString}`;
    });
});