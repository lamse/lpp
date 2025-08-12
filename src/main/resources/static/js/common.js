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
});