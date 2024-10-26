function validateDateTime() {
    const pickupDateTime = new Date(pickupDateTimeInput.value);
    const returnDateTime = new Date(returnDateTimeInput.value);

    if (pickupDateTime >= returnDateTime) {
        alert("Pick-up date and time must be before drop-off date and time.");
        pickupDateTimeInput.value = "";
    }
}

// Lấy các phần tử input
const pickupDateTimeInput = document.getElementById("date-pick-up");
const returnDateTimeInput = document.getElementById("date-drop-off");

// Sự kiện kiểm tra khi thay đổi giá trị ngày và giờ
pickupDateTimeInput.addEventListener("change", function() {
    validateDateTime();
});

returnDateTimeInput.addEventListener("change", function() {
    validateDateTime();
});