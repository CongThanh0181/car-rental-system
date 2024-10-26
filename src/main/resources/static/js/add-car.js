$(document).ready(function () {

  // Selector
  const tabs = document.querySelectorAll('.nav-link');
  let brandNameElement = document.querySelector('select[name="brandName"]');
  let modelCarElement = document.querySelector('select[name="modelCar"]');
  let licensePlateElement = document.querySelector('input[name="licensePlate"]');
  let colorElement = document.querySelector('select[name="color"]');
  let productionYearElement = document.querySelector('select[name="productionYear"]');
  let noOfSeatsElement = document.querySelector('select[name="noOfSeats"]');
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


  // Handle document step1, listen to events add document image
  $("input[name='fileDocument']").each(function (index) {
    $(this).change(function () {
      showThumbFileDocument(this, index);
    });
  });


  // Handle Image step2, listen to events add car image
  $("input[name='fileCarImage']").each(function (index) {
    $(this).change(function () {
      showThumbFileCarImage(this, index);
    });
  });


  // listen to events click button Next step1
  document.getElementById("nextButton").addEventListener("click", function() {
    let checkValid = true;

    // Validate inputs
      // Remove spaces before, after and between
    let licensePlateValue = licensePlateElement.value.replace(/\s+/g, ' ').trim();
    if(licensePlateValue === null || licensePlateValue === ""){
      checkValid = false;
      document.getElementById("licensePlateErr").innerText="License plate must be not empty!";
    }

    let colorValue = colorElement.value;
    if(colorValue === null || colorValue === ""){
      checkValid = false;
      document.getElementById("colorErr").innerText="Color must be not empty!";
    }

    let brandNameValue = brandNameElement.value;
    if(brandNameValue === null || brandNameValue === ""){
      checkValid = false;
      document.getElementById("brandNameErr").innerText="Brand name must be not empty!";
    }

    let productionYearValue = productionYearElement.value;
    if(productionYearValue === null || productionYearValue === ""){
      checkValid = false;
      document.getElementById("productionYearErr").innerText="Production Year of car must be not empty!";
    }

    let modelCarValue = modelCarElement.value;
    if(modelCarValue === null || modelCarValue === ""){
      checkValid = false;
      document.getElementById("modelCarErr").innerText="Model car must be not empty!";
    }

    let noOfSeatsValue = noOfSeatsElement.value;
    if(noOfSeatsValue === null || noOfSeatsValue === ""){
      checkValid = false;
      document.getElementById("noOfSeatsErr").innerText="No. of seats must be not empty!";
    }

    let transmissionValue = $('input[name="transmission"]:checked').val();
    if(transmissionValue === undefined){
      checkValid = false;
      document.getElementById("transmissionErr").innerText="Transmission must be not empty!";
    }

    let fuelValue = $('input[name="fuel"]:checked').val();
    if(fuelValue === undefined){
      checkValid = false;
      document.getElementById("fuelErr").innerText="Fuel must be not empty!";
    }

    let fileRegistrationValue = $('#fileRegistration')[0];
    if (fileRegistrationValue.files.length === 0) {
      checkValid = false;
      document.getElementById("registrationErr").innerText="Registration must be not empty!";
    }

    let fileCertificateValue = $('#fileCertificate')[0];
    if (fileCertificateValue.files.length === 0) {
      checkValid = false;
      document.getElementById("certificateErr").innerText="Certificate must be not empty!";
    }

    let fileInsuranceValue = $('#fileInsurance')[0];
    if (fileInsuranceValue.files.length === 0) {
      checkValid = false;
      document.getElementById("insuranceErr").innerText="Insurance must be not empty!";
    }

    if(checkValid){
      // Check License Plate is unique (Chưa biết xử lý bất đồng bộ nên làm tạm thế này)
      $.ajax({
        url: '/api/home-carowner/license-plate',
        method: 'GET',
        contentType: 'application/json',
        data: {
          licensePlate: licensePlateValue
        },
        success: function(response) {
          if (response === 'fail') {
            document.getElementById("licensePlateErr").innerText="License plate existed!";
          } else {
            nextTab();
          }
        },
        error: function(xhr, status, error) {
          // Todo xử lý lỗi
        }
      });
    }

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
      document.getElementById("mileageErr").innerText="Mileage must be number!";
    } else if (mileageValue < 0) {
      checkValid = false;
      document.getElementById("mileageErr").innerText="Mileage cannot be negative!";
    }

    let fuelConsumptionValue = fuelConsumptionElement.value.replace(/\s+/g, ' ').trim();
    if(!decimalRegex.test(fuelConsumptionValue) && fuelConsumptionValue){
      checkValid = false;
      document.getElementById("fuelConsumptionErr").innerText="Fuel consumption must be number!";
    } else if (fuelConsumptionValue < 0) {
      checkValid = false;
      document.getElementById("fuelConsumptionErr").innerText="Fuel consumption cannot be negative!";
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

    let fileFrontImageValue = $('#fileFrontImage')[0];
    if (fileFrontImageValue.files.length === 0) {
      checkValid = false;
      document.getElementById("frontImageErr").innerText="Front image must be not empty!";
    }

    let fileBackImageValue = $('#fileBackImage')[0];
    if (fileBackImageValue.files.length === 0) {
      checkValid = false;
      document.getElementById("backImageErr").innerText="Back image must be not empty!";
    }

    let fileLeftImageValue = $('#fileLeftImage')[0];
    if (fileLeftImageValue.files.length === 0) {
      checkValid = false;
      document.getElementById("leftImageErr").innerText="Left image must be not empty!";
    }

    let fileRightImageValue = $('#fileRightImage')[0];
    if (fileRightImageValue.files.length === 0) {
      checkValid = false;
      document.getElementById("rightImageErr").innerText="Right image must be not empty!";
    }

    if(checkValid){
      nextTab();
    }
  });


  // Listen events to click button Next step3
  document.getElementById("nextButton3").addEventListener("click", function() {
    let checkValid = true;

    let basePriceValue = basePriceElement.value;
    if(basePriceValue === null || basePriceValue === ""){
      checkValid = false;
      document.getElementById("basePriceErr").innerText="Base price must be not empty!";
    } else if(!decimalRegex.test(basePriceValue)){
      checkValid = false;
      document.getElementById("basePriceErr").innerText="Base price must be number!";
    } else if (basePriceValue < 0) {
      checkValid = false;
      document.getElementById("basePriceErr").innerText="Base price cannot be negative!";
    }

    let depositValue = depositElement.value;
    if(!decimalRegex.test(depositValue) && depositValue){
      checkValid = false;
      document.getElementById("depositErr").innerText="Deposit must be number!";
    } else if (depositValue < 0) {
      checkValid = false;
      document.getElementById("depositErr").innerText="Deposit cannot be negative!";
    }

    // prepare for step 4
    let fullCarName = brandNameElement.options[brandNameElement.selectedIndex].text;
    fullCarName = fullCarName + " " + modelCarElement.options[modelCarElement.selectedIndex].text;
    fullCarName = fullCarName + " " + productionYearElement.options[productionYearElement.selectedIndex].text;

    let fullAddress = streetHomeNumberElement.value;
    fullAddress = fullAddress + ", " + wardElement.options[wardElement.selectedIndex].text;
    fullAddress = fullAddress + ", " + districtElement.options[districtElement.selectedIndex].text;
    fullAddress = fullAddress + ", " + cityElement.options[cityElement.selectedIndex].text;

    $('#previewCarName').text(fullCarName);
    $('#previewAddress').text(fullAddress);
    $('#previewNoOfRides').text(mileageElement.value);
    $('#previewPrice').text(basePriceValue);

    if(checkValid){
      nextTab();
    }

  });


  // Start Validate all inputs
  licensePlateElement.addEventListener("keydown", function (){
    document.getElementById("licensePlateErr").innerText="";
  });

  colorElement.addEventListener("change", function (){
    document.getElementById("colorErr").innerText="";
  });

  brandNameElement.addEventListener("change", function (){
    document.getElementById("brandNameErr").innerText="";
  });

  modelCarElement.addEventListener("change", function (){
    document.getElementById("modelCarErr").innerText="";
  });

  productionYearElement.addEventListener("change", function (){
    document.getElementById("productionYearErr").innerText="";
  });

  noOfSeatsElement.addEventListener("change", function (){
    document.getElementById("noOfSeatsErr").innerText="";
  });

  document.getElementById('automatic').addEventListener("change", function (){
    document.getElementById("transmissionErr").innerText="";
  });

  document.getElementById('manual').addEventListener("change", function (){
    document.getElementById("transmissionErr").innerText="";
  });

  document.getElementById('gasoline').addEventListener("change", function (){
    document.getElementById("fuelErr").innerText="";
  });

  document.getElementById('diesel').addEventListener("change", function (){
    document.getElementById("fuelErr").innerText="";
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
  // End Validate all inputs


  // Lắng nghe sự kiện khi chọn select BrandCar và render model car
  brandNameElement.addEventListener('change', function() {

    // Reset lại lựa chọn của model car mỗi lần change branch name
    modelCarElement.innerHTML = '<option value="" disabled selected hidden>Select your model car name</option>';

    let brandNameElementValue = this.value;

    $.ajax({
      url: '/api/model-cars',
      method: 'GET',
      contentType: 'application/json',
      data: {
        brandId: brandNameElementValue
      },
      success: function(response) {
        // Xử lý chuỗi JSON nhận được từ server
        //render model car
        $.each(response, function(index, model) {
          let option = document.createElement('option');
          option.value = model.modelCarId;
          option.textContent = model.modelName;
          modelCarElement.appendChild(option);
        });
      },
      error: function(xhr, status, error) {
        // Todo xử lý lỗi
      }
    });

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

  // Handle textarea if user checkbox other
  let otherElement  = document.getElementById("other");
  let specifyDescriptionElement = document.getElementById("specifyDescription");

  otherElement.addEventListener("change", function() {
    specifyDescriptionElement.disabled = !otherElement.checked;
  });


// Các function tự tạo thêm

  // Function show thumb document
  function showThumbFileDocument(fileInput, index) {
    file = fileInput.files[0];
    reader = new FileReader();

    if (file && file.type.match('image.*')) {
      // xoá error
      document.querySelector(".fileDocumentErr"+index).innerText="";

      // Đọc và hiển thị thumbnail
      reader.onload = function (e) {
        $('#thumbnailFileDocument' + index).attr('src', e.target.result);
      }
      reader.readAsDataURL(file);
    }

  }

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