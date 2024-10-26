$(document).ready(function () {
    let passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;

    $.validator.addMethod("passwordRegex", function(value, element) {
        return this.optional(element) || passwordRegex.test(value);
    }, "Invalid password format.");


    // Validate login form
    $("#login-form").validate({
        rules: {
            email: {
                required:true,
                email: true
            },
            password: {
                required:true,
            }
        },
        messages: {
            email: {
                required: "Email must be not empty!",
                email: "Incorrect email format!"
            },
            password: {
                required: "Password must be not empty!",
            }
        },
        submitHandler: function(form) {
            form.submit();
        }

    });


    // Validate sign form
    $("#signup-form").validate({
        rules: {
            name: {
                required: true,
            },
            email: {
                required: true,
                email: true
            },
            phone: {
                required: true,
            },
            password: {
                required: true,
                passwordRegex: true
            },
            confirmPassword: {
                required: true,
                equalTo: "#signUpPassword"
            },
            role: {
                required: true
            },
            agree: {
                required: true
            }
        },
        messages: {
            name: {
                required: "Name must be not empty!",
            },
            email: {
                required: "Email must be not empty!",
                email: "Incorrect email format!"
            },
            phone: {
                required: "Phone must be not empty!",
            },
            password: {
                required: "Password must be not empty!",
                passwordRegex: "Password must be at least 8 characters long and contain at least one uppercase letter, " +
                    "one lowercase letter, and one digit!"
            },
            confirmPassword: {
                required: "Confirm password must be not empty!",
                equalTo: "Confirm password must match password!"
            },
            role: "Please select a role!",
            agree: "Please agree to the Terms and Conditions!"
        },
        errorPlacement: function(error, element) {
            if (element.attr("name") === "role") {
                error.appendTo($(".roleErr")); // Hiển thị thông báo lỗi tại .roleErr do mình custom
            } else if (element.attr("name") === "agree") {
                error.appendTo($(".agreeErr")); // Hiển thị thông báo lỗi tại .agreeErr do mình custom
            } else {
                error.insertAfter(element); // Hiển thị thông báo mặc định của plugin validate
            }
        },
        submitHandler: function(form) {
            // Gửi form nếu hợp lệ
            form.submit();
        }
    });


});