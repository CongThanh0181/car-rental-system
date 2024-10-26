$(document).ready(function() {
    $("#btn-listYourCarToday").click(function() {
        $.ajax({
            url: "/home-carowner/add-car",
            type: "GET",
            success: function() {
                // Chuyển hướng trang khi yêu cầu thành công
                window.location.href = "/home-carowner/add-car";
            },
            error: function() {
                // Xử lý lỗi nếu cần
            }
        });
    });
});