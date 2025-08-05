function changeImage(src) {
    document.getElementById('mainImage').src = src;
}

$(function() {
    $('.openChat').on('click', function() {
        $('#chatModal').removeClass('hidden');
    });
    $('#closeChat').on('click', function() {
        $('#chatModal').addClass('hidden');
    });
});

