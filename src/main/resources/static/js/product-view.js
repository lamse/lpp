function changeImage(src) {
    document.getElementById('mainImage').src = src;
}

$(function() {
    $('.openChat').on('click', function() {
        getChatList($(this).data('id'));
    });
    $('#closeChat').on('click', function() {
        $('#chatModal').addClass('hidden');
    });
});

function getChatList(id) {
    $.ajax({
        url: '/product/offer/chat/' + id,
        type: 'GET',
        success: function(data) {
            $('#chatList').replaceWith(data);
            $('#chatModal').removeClass('hidden');
        },
        error: function() {
            alert('Failed to load chat list.');
        }
    });
}

function addChat() {
    const form = $('#commentForm');

    $.ajax({
        url: form.attr('action'),
        type: 'POST',
        data: form.serialize(),
        success: function(data) {
            $('#chatList').replaceWith(data);
            $('#chatModal').removeClass('hidden');
        },
        error: function(response) {
            if (response.status === 401) {
                alert('You must be logged in to add chat.');
                window.location.href = '/login?redirectURL=' + encodeURIComponent(window.location.href);
                return;
            }
            alert('Failed to add chat.');
        }
    });
    return false;
}

function deleteChat(id) {
    $.ajax({
        url: '/product/offer/chat/' + id + '/delete',
        type: 'DELETE',
        success: function(response) {
            if ( response > 0) {
                $('#chat_' + id).remove();
            } else {
                alert('This comment is either unauthorized or does not exist.');
            }

        },
        error: function(response) {if (response.status === 401) {
            alert('You must be logged in to delete chat.');
            return;
        }
            alert('Failed to delete chat.');
        }
    });
}