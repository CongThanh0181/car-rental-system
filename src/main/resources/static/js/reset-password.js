$(document).ready(function () {
    let passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;

    $.validator.addMethod("passwordRegex", function(value, element) {
        return this.optional(element) || passwordRegex.test(value);
    }, "Invalid password format.");

    $('#resetPass').validate({
        rules: {
            password: {
                required: true,
                passwordRegex: true
            },
            'confirm-password': {
                required: true,
                equalTo: "#confirm-password"
            },
        },
        messages: {
            password: {
                required: "New password must be not empty!",
                passwordRegex: "New password must be at least 8 characters long and contain at least one uppercase letter, " +
                    "one lowercase letter, and one digit!"
            },
            'confirm-password': {
                required: "Confirm password must be not empty!",
                equalTo: "Confirm password must match new password!"
            }
        },
        submitHandler: function(form) {
            // Gửi form nếu hợp lệ
            form.submit();
        }
    })

});
