function editItem(itemName, id) {
    event.stopImmediatePropagation();
    window.open("/" + itemName + "/" + id + "?form", "_self");
}

function newItem(itemName) {
    editItem(itemName, "");
}

function deleteItem(itemName, id) {
    event.stopPropagation();
    var row = event.target.parentElement.parentElement;
    $.ajax({
        url: "/" + itemName + "/" + id,
        type: "DELETE",
        success: function (result) {
            row.remove();
        }
    });
}

function fillJobDescription(element) {
    var description = document.getElementById("description");
    if(description && !description.value) {
        var value = element.options[element.selectedIndex].innerHTML;
        description.value = value.replace(/^[a-z]|[A-Z]/g, function(v, i) {
            return i === 0 ? v.toUpperCase() : " " + v.toLowerCase();
        });
    }
}

// (function ($) {
//     $('.spinner .btn:first-of-type').on('click', function () {
//         $('.spinner input').val(parseInt($('.spinner input').val(), 10) + 1);
//     });
//     $('.spinner .btn:last-of-type').on('click', function () {
//         $('.spinner input').val(parseInt($('.spinner input').val(), 10) - 1);
//     });
// })(jQuery);
