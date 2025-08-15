$(function() {
    $('#openTechStack').on('mouseover', function() {
        $('#techStackModal').removeClass('opacity-0 pointer-events-none');
        $('#techStackModal').addClass('opacity-100');
    });
    $('#closeTechStack').on('click', function() {
        $('#techStackModal').removeClass('opacity-100');
        $('#techStackModal').addClass('opacity-0 pointer-events-none');
    });

    $(document).on('click', function(event) {
        if (!$(event.target).closest('#techStackModal .bg-white').length && !$(event.target).is('#openTechStack')) {
            $('#techStackModal').removeClass('opacity-100');
            $('#techStackModal').addClass('opacity-0 pointer-events-none');
        }
    });

    const currentSearchParams = new URLSearchParams(window.location.search);
    const paginationLinks = document.querySelectorAll(".pagination a[href]");

    paginationLinks.forEach(link => {
        const linkUrl = new URL(link.href);
        const linkParams = linkUrl.searchParams;

        currentSearchParams.forEach((value, key) => {
            if (!linkParams.has(key)) {
                linkParams.set(key, value);
            }
        });
        link.href = linkUrl.pathname + '?' + linkParams.toString();
    });
});