document.addEventListener('DOMContentLoaded', () => {
    const resultsContainer = document.getElementById('results-container');
    const urlParams = new URLSearchParams(window.location.search);
    const skillsParam = urlParams.get('skills');
    const selectedSkills = skillsParam ? skillsParam.split(',') : [];

    // This is a simplified example. In a real app, you'd fetch this from a backend.
    const careerData = {
        'Software Engineer': ['problem-solving', 'programming', 'critical-thinking'],
        'Data Scientist': ['data-analysis', 'programming', 'machine-learning'],
        'UI/UX Designer': ['graphic-design', 'ui-ux', 'critical-thinking'],
    };

    let suggestionsHTML = '<ul>';
    let matchFound = false;

    for (const career in careerData) {
        const requiredSkills = careerData[career];
        const matchCount = selectedSkills.filter(skill => requiredSkills.includes(skill)).length;
        const matchPercentage = (matchCount / requiredSkills.length) * 100;

        if (matchPercentage > 30) {
            suggestionsHTML += `<li><h3>${career}</h3><p>Match: ${Math.round(matchPercentage)}%</p></li>`;
            matchFound = true;
        }
    }

    if (!matchFound) {
        suggestionsHTML += '<li>No strong career matches found. Try selecting more skills.</li>';
    }

    suggestionsHTML += '</ul>';
    resultsContainer.innerHTML = suggestionsHTML;
});