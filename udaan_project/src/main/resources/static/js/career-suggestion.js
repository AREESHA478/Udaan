document.addEventListener('DOMContentLoaded', () => {
    // Enhanced data map with descriptions and personality traits
    const careerData = {
        'Software Engineer': {
            skills: ['problem-solving', 'programming', 'critical-thinking', 'teamwork'],
            description: 'Design and develop software applications, utilizing cutting-edge technologies to solve complex problems and create innovative solutions.',
            traits: ['Analytical', 'Logical', 'Detail-Oriented']
        },
        'Data Scientist': {
            skills: ['data-analysis', 'programming', 'machine-learning', 'critical-thinking', 'research'],
            description: 'Analyze large datasets to extract meaningful insights, using statistical methods and machine learning to predict trends and inform business decisions.',
            traits: ['Analytical', 'Curious', 'Methodical']
        },
        'UI/UX Designer': {
            skills: ['graphic-design', 'ui-ux', 'critical-thinking', 'teamwork', 'writing'],
            description: 'Create intuitive and visually appealing digital experiences, combining user creativity with user research and design principles.',
            traits: ['Creative', 'Empathetic', 'User-Focused']
        },
        'Product Manager': {
            skills: ['management', 'leadership', 'communication', 'problem-solving', 'data-analysis'],
            description: 'Lead product development from conception to launch, combining technical knowledge with business strategy to deliver user-centered products.',
            traits: ['Strategic', 'Leader', 'Communicator']
        },
        'Network Engineer': {
            skills: ['networking', 'problem-solving', 'programming'],
            description: 'Design, implement, and manage computer networks to ensure high availability and performance for organizations.',
            traits: ['Systematic', 'Problem-Solver', 'Technical']
        },
        'Digital Marketer': {
            skills: ['data-analysis', 'communication', 'writing', 'research'],
            description: 'Develop and execute online marketing campaigns using SEO, SEM, and social media to drive brand awareness and generate leads.',
            traits: ['Creative', 'Analytical', 'Adaptable']
        }
    };

    const skillCategories = {
        'Analytical': ['problem-solving', 'data-analysis', 'critical-thinking', 'research'],
        'Technical': ['programming', 'web-development', 'machine-learning', 'networking'],
        'Creative': ['graphic-design', 'writing', 'video-editing', 'ui-ux'],
        'Social': ['communication', 'teamwork'],
        'Leadership': ['leadership', 'management']
    };

    const urlParams = new URLSearchParams(window.location.search);
    const skillsParam = urlParams.get('skills');
    const selectedSkills = skillsParam ? skillsParam.split(',') : [];

    // --- 1. Calculate and Display Career Suggestions ---
    const scores = {};
    for (const career in careerData) {
        const requiredSkills = careerData[career].skills;
        let score = 0;
        requiredSkills.forEach(reqSkill => {
            if (selectedSkills.includes(reqSkill)) score++;
        });
        scores[career] = (score / requiredSkills.length) * 100;
    }

    const sortedCareers = Object.entries(scores).sort(([, a], [, b]) => b - a).filter(([, score]) => score > 20);
    const topCareers = sortedCareers.slice(0, 3);

    const careerGrid = document.getElementById('career-cards-grid');
    careerGrid.innerHTML = ''; // Clear loading text
    if (topCareers.length > 0) {
        topCareers.forEach(([career, score]) => {
            const cardHTML = `
                <div class="career-card card">
                    <h3>${career}</h3>
                    <div class="match-score">
                        <span>${Math.round(score)}%</span>
                        <div class="progress-bar"><div class="progress-fill" style="width: ${score}%;"></div></div>
                        <span class="score-label">Match Score</span>
                    </div>
                    <p>${careerData[career].description}</p>
                    <a href="#" class="view-path-btn">View Career Path <i class="fas fa-arrow-right"></i></a>
                </div>`;
            careerGrid.innerHTML += cardHTML;
        });
    } else {
        careerGrid.innerHTML = '<p>No strong career matches found. Please <a href="/career-test">try the test again</a> with more skills.</p>';
    }

    // --- 2. Display Personality Traits ---
    const traitsGrid = document.getElementById('traits-grid');
    const allTraits = new Set();
    topCareers.forEach(([career]) => {
        careerData[career].traits.forEach(trait => allTraits.add(trait));
    });
    allTraits.forEach(trait => {
        traitsGrid.innerHTML += `<span class="trait-tag">${trait}</span>`;
    });

    // --- 3. Display Skills Bar Chart ---
    const skillBars = document.getElementById('skill-bars');
    skillBars.innerHTML = ''; // Clear any previous content

    const categories = Object.keys(skillCategories);

    // 1. Add the bar structure to the DOM with height 0
    categories.forEach(category => {
        const barId = `bar-${category.toLowerCase()}`;
        skillBars.innerHTML += `
            <div class="bar-item">
                <div class="bar" id="${barId}"></div>
                <span>${category}</span>
            </div>`;
    });

    // 2. After a short delay, animate their height
    setTimeout(() => {
        categories.forEach(category => {
        const categorySkills = skillCategories[category];
        const matchCount = selectedSkills.filter(skill => categorySkills.includes(skill)).length;
        const heightPercent = (matchCount / categorySkills.length) * 100;
        const barId = `bar-${category.toLowerCase()}`;
            const barElement = document.getElementById(barId);
            if (barElement) {
                barElement.style.height = `${heightPercent}%`;
            }
        });
    }, 100);
});