$(document).ready(function () {

    let cityElement = document.querySelector('select[name="city"]');
    let districtElement = document.querySelector('select[name="district"]');
    let wardElement = document.querySelector('select[name="ward"]');
    let passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;


    // add rule for validate plugin
    $.validator.addMethod("passwordRegex", function(value, element) {
        return this.optional(element) || passwordRegex.test(value);
    }, "Invalid password format.");


    // Validate security form
    $("#securityForm").validate({
        rules: {
            oldPassword: {
                required:true,
            },
            newPassword: {
                required:true,
                passwordRegex: true
            },
            confirmPassword: {
                required:true,
                equalTo: "#newPassword"
            }
        },
        messages: {
            oldPassword: {
                required: "Old password must be not empty!",
            },
            newPassword: {
                required:"Confirm password must be not empty!",
                passwordRegex: "Password must be at least 8 characters long and contain at least one uppercase letter, " +
                    "one lowercase letter, and one digit"
            },
            confirmPassword: {
                required: "Confirm password must be not empty!",
                equalTo: "Confirm password must match password!"
            }
        },
        submitHandler: function(form) {
            form.submit();
        }
    });


    // Validation personal information form
    $("#personalInformationForm").validate({
        rules: {
            fullName: {
                required: true,
            },
            phone: {
                required: true,
            },
            nationalIdNo: {
                required: true,
            },
            city: {
                required: true,
            },
            district: {
                required: true,
            },
            ward: {
                required: true,
            },
            streetHomeNumber: {
                required: true,
            },
            dateOfBirth: {
                required: true,
            }
        },
        messages: {
            fullName: {
                required: "Full name must be not empty!",
            },
            phone: {
                required: "Phone must be not empty!",
            },
            nationalIdNo: {
                required: "National Id no must be not empty!",
            },
            city: {
                required: "City must be not empty!",
            },
            district: {
                required: "District must be not empty!",
            },
            ward: {
                required: "Ward must be not empty!",
            },
            streetHomeNumber: {
                required: "Street, home number must be not empty!",
            },
            dateOfBirth: {
                required: "Date of birth must be not empty!",
            }
        },
        submitHandler: function(form) {
            // Gửi form nếu hợp lệ
            form.submit();
        }
    });


    // Validate driving license. Đang chưa đồng bộ được với jQuery Validation Plugin
    $('#savePersonalInformation').click(function (event) {
        let imgElement = document.getElementById("thumbnailFileDrivingLicense");
        let srcAttribute = imgElement.getAttribute("src");

        if (!srcAttribute) {
            $('#drivingLicenseErr').text("Driving license must be not empty!");
            event.preventDefault();
        } else {
            $('#drivingLicenseErr').text("");
        }
    });


    // Handler click select city và render district
    cityElement.addEventListener("click", renderDistrict);
    cityElement.addEventListener("change", renderDistrict);
    function renderDistrict () {

        // Reset lại lựa chọn của district, ward mỗi lần change city
        districtElement.innerHTML = '<option value="" disabled selected hidden>District</option>';
        wardElement.innerHTML = '<option value="" disabled selected hidden>Ward</option>';

        let cityElementValue = this.value;
        if (cityElementValue) {
            $.ajax({
                url: '/api/districts',
                method: 'GET',
                contentType: 'application/json',
                data: {
                    cityId: cityElementValue
                },
                success: function (response) {
                    // Xử lý chuỗi JSON nhận được từ server
                    //render district
                    $.each(response, function (index, district) {
                        let option = document.createElement('option');
                        option.value = district.districtId;
                        option.textContent = district.districtName;
                        districtElement.appendChild(option);
                    });
                },
                error: function (xhr, status, error) {
                    // Todo xử lý lỗi
                }
            });
        }

    }


    // Handler click select district và render ward
    districtElement.addEventListener("click", renderWard);
    districtElement.addEventListener("change", renderWard);
    function renderWard() {

        // Reset lại lựa chọn của ward mỗi lần change district
        wardElement.innerHTML = '<option value="" disabled selected hidden>Ward</option>';

        let districtElementValue = this.value;

        if (districtElementValue) {
            $.ajax({
                url: '/api/wards',
                method: 'GET',
                contentType: 'application/json',
                data: {
                    districtId: districtElementValue
                },
                success: function (response) {
                    // Xử lý chuỗi JSON nhận được từ server
                    //render district
                    $.each(response, function (index, ward) {
                        let option = document.createElement('option');
                        option.value = ward.wardId;
                        option.textContent = ward.wardName;
                        wardElement.appendChild(option);
                    });
                },
                error: function(xhr, status, error) {
                    // Todo xử lý lỗi
                }
            });
        }

    }


    // Handler change password
    $('#saveSecurity').click(function () {
        if ($("#securityForm").valid()) {
            changePassword();
        }
    })
    function changePassword() {
        var oldPassword = document.getElementById("oldPassword").value;
        var newPassword = document.getElementById("newPassword").value;
        var confirmPassword = document.getElementById("confirmPassword").value;

        // Gửi yêu cầu AJAX đến máy chủ
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/change-password", true);
        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var response = JSON.parse(xhr.responseText);
                var messageDiv = document.getElementById("changePasswordResponse");
                messageDiv.innerHTML = response.message;
                messageDiv.classList.add("alert-danger");
                if(response.message === "Password change success")
                {
                    messageDiv.classList.remove("alert-danger")
                    messageDiv.classList.add("alert-success");
                }
                messageDiv.style.display = "block";
            }
        };

        var data = JSON.stringify({oldPassword: oldPassword, newPassword: newPassword, confirmPassword: confirmPassword});
        xhr.send(data);
    }


    // Handle listen to events add license image
    $("input[name='fileLicenseImg']").change(function () {
        file = this.files[0];
        reader = new FileReader();

        if (file && file.type.match('image.*')) {
            // xoá error
            document.querySelector(".fileLicenseImgErr").innerText="";

            // Đọc và hiển thị thumbnail, preview
            reader.onload = function (e) {
                $('#thumbnailFileDrivingLicense').attr('src', e.target.result);
            }
            reader.readAsDataURL(file);
        }
    })


// Validation
    $("#personalInformationForm").validate({
        rules: {
            fullName: {
                required: true,
            },
            phone: {
                required: true,
            },
            nationalIdNo: {
                required: true,
            },
            city: {
                required: true,
            },
            district: {
                required: true,
            },
            ward: {
                required: true,
            },
            streetHomeNumber: {
                required: true,
            },
            dateOfBirth: {
                required: true,
            },

        },
        messages: {
            fullName: {
                required: "Full name must be not empty!",
            },
            phone: {
                required: "Phone must be not empty!",
            },
            nationalIdNo: {
                required: "National Id no must be not empty!",
            },
            city: {
                required: "City must be not empty!",
            },
            district: {
                required: "District must be not empty!",
            },
            ward: {
                required: "Ward must be not empty!",
            },
            streetHomeNumber: {
                required: "Street, home number must be not empty!",
            },
            dateOfBirth: {
                required: "Date of birth must be not empty!",
            },
        },
        submitHandler: function(form) {
            // Gửi form nếu hợp lệ
            form.submit();
        }
    });

})