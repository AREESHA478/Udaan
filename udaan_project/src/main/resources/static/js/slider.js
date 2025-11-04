document.addEventListener('DOMContentLoaded', () => {
    // --- Hero Slider ---
    const heroSlides = document.querySelectorAll('.hero-section .slide');
    let currentHeroSlide = 0;

    function showHeroSlide(index) {
        heroSlides.forEach((slide, i) => {
            slide.classList.remove('active-slide');
            if (i === index) {
                slide.classList.add('active-slide');
            }
        });
    }

    function nextHeroSlide() {
        currentHeroSlide = (currentHeroSlide + 1) % heroSlides.length;
        showHeroSlide(currentHeroSlide);
    }

    if (heroSlides.length > 0) {
        setInterval(nextHeroSlide, 5000); // Change slide every 5 seconds
    }

    // --- Testimonial Slider ---
    const testimonialSlider = document.getElementById('testimonial-slider');
    const prevBtn = document.getElementById('prev-testimonial');
    const nextBtn = document.getElementById('next-testimonial');
    const testimonialSlides = document.querySelectorAll('.testimonial-card');
    let currentTestimonialIndex = 0;

    function updateSliderPosition() {
        testimonialSlider.style.transform = `translateX(-${currentTestimonialIndex * 100}%)`;
    }

    if (prevBtn && nextBtn && testimonialSlider) {
        nextBtn.addEventListener('click', () => {
            currentTestimonialIndex = (currentTestimonialIndex + 1) % testimonialSlides.length;
            updateSliderPosition();
        });

        prevBtn.addEventListener('click', () => {
            currentTestimonialIndex = (currentTestimonialIndex - 1 + testimonialSlides.length) % testimonialSlides.length;
            updateSliderPosition();
        });
    }
});