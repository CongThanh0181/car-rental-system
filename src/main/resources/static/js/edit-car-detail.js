$(document).ready(function () {

    // Selector
    const tabs = document.querySelectorAll('.nav-link');
    let mileageElement = document.querySelector('input[name="mileage"]');
    let cityElement = document.querySelector('select[name="city"]');
    let districtElement = document.querySelector('select[name="district"]');
    let wardElement = document.querySelector('select[name="ward"]');
    let streetHomeNumberElement = document.querySelector('input[name="streetHomeNumber"]');
    let basePriceElement = document.querySelector('input[name="basePrice"]');
    let fuelConsumptionElement = document.querySelector('input[name="fuelConsumption"]');
    let depositElement = document.querySelector('input[name="deposit"]');

    // Regex
    const numberRegex = /^[0-9]+$/;
    const decimalRegex = /^\d+(\.\d+)?$/;


    // Handle Image step2, listen to events add car image
    $("input[name='fileCarImage']").each(function (index) {
        $(this).change(function () {
            showThumbFileCarImage(this, index);
        });
    });


    // Listen events to click button Next step1
    document.getElementById("nextButton1").addEventListener("click", function() {
        nextTab();
    });


    // Listen events to click button Next step2
    document.getElementById("nextButton2").addEventListener("click", function() {
        let checkValid = true;

        let mileageValue = mileageElement.value.replace(/\s+/g, ' ').trim();
        if(mileageValue === null || mileageValue === ""){
            checkValid = false;
            document.getElementById("mileageErr").innerText="Mileage must be not empty!";
        } else if(!numberRegex.test(mileageValue)){
            checkValid = false;
            document.getElementById("mileageErr").innerText="Mileage must be number";
        }

        let fuelConsumptionValue = fuelConsumptionElement.value.replace(/\s+/g, ' ').trim();
        if(!decimalRegex.test(fuelConsumptionValue) && fuelConsumptionValue){
            checkValid = false;
            document.getElementById("fuelConsumptionErr").innerText="Fuel consumption must be number";
        }

        let cityValue = cityElement.value;
        if(cityValue === null || cityValue === ""){
            checkValid = false;
            document.getElementById("cityErr").innerText="City must be not empty!";
        }

        let districtValue = districtElement.value;
        if(districtValue === null || districtValue === ""){
            checkValid = false;
            document.getElementById("districtErr").innerText="District must be not empty!";
        }

        let wardValue = wardElement.value;
        if(wardValue === null || wardValue === ""){
            checkValid = false;
            document.getElementById("wardErr").innerText="Ward must be not empty!";
        }

        let streetHomeNumberValue = streetHomeNumberElement.value.replace(/\s+/g, ' ').trim();
        if(streetHomeNumberValue === null || streetHomeNumberValue === ""){
            checkValid = false;
            document.getElementById("streetHomeNumberErr").innerText="Number house and street must be not empty!";
        }

        if(checkValid){
            nextTab();
        }
    });


    // Listen events to click button submit step3
    document.getElementById("updateCarForm").addEventListener("submit", function(event) {
        event.preventDefault();
        let checkValid = true;

        let basePriceValue = basePriceElement.value;
        if(basePriceValue === null || basePriceValue === ""){
            checkValid = false;
            document.getElementById("basePriceErr").innerText="Base price must be not empty!";
        } else if(!decimalRegex.test(basePriceValue)){
            checkValid = false;
            document.getElementById("basePriceErr").innerText="Base price must be number";
        }

        let depositValue = depositElement.value;
        if(!decimalRegex.test(depositValue) && depositValue){
            checkValid = false;
            document.getElementById("depositErr").innerText="Deposit must be number";
        }

        if(checkValid){
            document.getElementById("updateCarForm").submit();
        }

    });


    mileageElement.addEventListener("keydown", function (){
        document.getElementById("mileageErr").innerText="";
    });

    fuelConsumptionElement.addEventListener("keydown", function (){
        document.getElementById("fuelConsumptionErr").innerText="";
    });

    cityElement.addEventListener("change", function (){
        document.getElementById("cityErr").innerText="";
    });

    districtElement.addEventListener("change", function (){
        document.getElementById("districtErr").innerText="";
    });

    wardElement.addEventListener("change", function (){
        document.getElementById("wardErr").innerText="";
    });

    streetHomeNumberElement.addEventListener("keydown", function (){
        document.getElementById("streetHomeNumberErr").innerText="";
    });

    basePriceElement.addEventListener("keydown", function (){
        document.getElementById("basePriceErr").innerText="";
    });

    depositElement.addEventListener("keydown", function (){
        document.getElementById("depositErr").innerText="";
    });


// Lắng nghe sự kiện khi chọn select city và render district
    cityElement.addEventListener("click", renderDistrict);
    cityElement.addEventListener("change", renderDistrict);

    function renderDistrict () {

        // Reset lại lựa chọn của district, ward mỗi lần change city
        districtElement.innerHTML = '<option value="" disabled selected hidden>District</option>';
        wardElement.innerHTML = '<option value="" disabled selected hidden>Ward</option>';

        let cityElementValue = this.value;

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

// Lắng nghe sự kiện khi chọn select district và render ward
    districtElement.addEventListener("click", renderWard);
    districtElement.addEventListener("change", renderWard);

    function renderWard() {

        // Reset lại lựa chọn của ward mỗi lần change district
        wardElement.innerHTML = '<option value="" disabled selected hidden>Ward</option>';

        let districtElementValue = this.value;

        $.ajax({
            url: '/api/wards',
            method: 'GET',
            contentType: 'application/json',
            data: {
                districtId: districtElementValue
            },
            success: function(response) {
                // Xử lý chuỗi JSON nhận được từ server
                //render district
                $.each(response, function(index, ward) {
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


    // Handle textarea if user checkbox other
    let otherElement  = document.getElementById("other");
    let specifyDescriptionElement = document.getElementById("specifyDescription");

    otherElement.addEventListener("change", function() {
        if (otherElement.checked) {
            specifyDescriptionElement.disabled = false;
        } else {
            specifyDescriptionElement.disabled = true;
        }
    });


    // Lắng nghe sự kiện khi chọn select city và render district
    cityElement.addEventListener("change", function () {

        // Reset lại lựa chọn của district, ward mỗi lần change city
        districtElement.innerHTML = '<option value="" disabled selected hidden>District</option>';
        wardElement.innerHTML = '<option value="" disabled selected hidden>Ward</option>';

        let cityElementValue = this.value;

        $.ajax({
            url: '/api/districts',
            method: 'GET',
            contentType: 'application/json',
            data: {
                cityId: cityElementValue
            },
            success: function(response) {
                // Xử lý chuỗi JSON nhận được từ server
                //render district
                $.each(response, function(index, district) {
                    let option = document.createElement('option');
                    option.value = district.districtId;
                    option.textContent = district.districtName;
                    districtElement.appendChild(option);
                });
            },
            error: function(xhr, status, error) {
                // Todo xử lý lỗi
            }
        });

    });


    // Lắng nghe sự kiện khi chọn select district và render ward
    districtElement.addEventListener("change", function () {

        // Reset lại lựa chọn của ward mỗi lần change district
        wardElement.innerHTML = '<option value="" disabled selected hidden>Ward</option>';

        let districtElementValue = this.value;

        $.ajax({
            url: '/api/wards',
            method: 'GET',
            contentType: 'application/json',
            data: {
                districtId: districtElementValue
            },
            success: function(response) {
                // Xử lý chuỗi JSON nhận được từ server
                //render district
                $.each(response, function(index, ward) {
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

    });


    // Function show thumb car image
    function showThumbFileCarImage(fileInput, index) {
        file = fileInput.files[0];
        reader = new FileReader();

        if (file && file.type.match('image.*')) {
            // xoá error
            document.querySelector(".fileCarImageErr"+index).innerText="";

            // Đọc và hiển thị thumbnail, preview
            reader.onload = function (e) {
                $('#thumbnailFileCarImage' + index).attr('src', e.target.result);
                $('#previewFileCarImage' + index).attr('src', e.target.result);
            }
            reader.readAsDataURL(file);
        }

    }

    // Function next tab
    function nextTab() {
        // Tìm tab hiện tại đang active
        const currentTab = document.querySelector('.nav-link.active');

        // Tìm vị trí của tab hiện tại trong danh sách các tabs
        const currentIndex = Array.from(tabs).indexOf(currentTab);

        // Gỡ bỏ lớp active khỏi tab hiện tại
        currentTab.classList.remove("active");

        // Xác định vị trí của tab tiếp theo
        let nextIndex = currentIndex + 1;

        // Thêm lớp active vào tab tiếp theo
        tabs[nextIndex].classList.add("active", "show");

        // Ẩn nội dung của tab hiện tại
        document.querySelector(currentTab.getAttribute("href")).classList.remove("active", "show");

        // Hiển thị nội dung của tab tiếp theo
        document.querySelector(tabs[nextIndex].getAttribute("href")).classList.add("active", "show");

    }
});





