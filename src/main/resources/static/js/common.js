$(function() {
    $('#openTechStack').on('click', function() {
        $('#techStackModal').removeClass('hidden');
    });
    $('#closeTechStack').on('click', function() {
        $('#techStackModal').addClass('hidden');
    });
});