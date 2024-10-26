$(document).ready(function () {

  // Selector
  const tabs = document.querySelectorAll('.nav-link');
  let isDriverElement = document.querySelector('#isDriver');
  let driverNameElement = document.querySelector('input[name="driverName"]');
  let driverPhoneElement = document.querySelector('input[name="driverPhone"]');
  let driverNationalIdNoElement = document.querySelector('input[name="driverNationalIdNo"]');
  let driverHouseNumberStreetElement = document.querySelector('input[name="driverHouseNumberStreet"]');
  let driverEmailElement = document.querySelector('input[name="driverEmail"]');
  let driverCityElement = document.querySelector('select[name="driverCity"]');
  let driverDistrictElement = document.querySelector('select[name="driverDistrict"]');
  let driverWardElement = document.querySelector('select[name="driverWard"]');
  let driverDateOfBirthElement = document.querySelector('input[name="driverDateOfBirth"]');
  let driverDrivingLicenseElement = document.querySelector('input[name="driverDrivingLicense"]');


  // Handler event user check is driver
  isDriverElement.addEventListener("change", function () {
    if (isDriverElement.checked) {
      driverNameElement.disabled = false;
      driverPhoneElement.disabled = false;
      driverNationalIdNoElement.disabled = false;
      driverHouseNumberStreetElement.disabled = false;
      driverEmailElement.disabled = false;
      driverCityElement.disabled = false;
      driverDistrictElement.disabled = false;
      driverWardElement.disabled = false;
      driverDateOfBirthElement.disabled = false;
      driverDrivingLicenseElement.disabled = false;

    } else {
      driverNameElement.disabled = true;
      driverPhoneElement.disabled = true;
      driverNationalIdNoElement.disabled = true;
      driverHouseNumberStreetElement.disabled = true;
      driverEmailElement.disabled=true;
      driverCityElement.disabled = true;
      driverDistrictElement.disabled = true;
      driverWardElement.disabled = true;
      driverDateOfBirthElement.disabled = true;
      driverDrivingLicenseElement.disabled =true;

    }

  });


  $("#bookCarForm").validate({
    rules: {
      driverName: {
        required: true,
      },
      driverPhone: {
        required: true,
      },
      driverNationalIdNo: {
        required: true,
      },
      driverCity: {
        required: true,
      },
      driverDistrict: {
        required: true,
      },
      driverWard: {
        required: true,
      },
      driverHouseNumberStreet: {
        required: true,
      },
      driverDateOfBirth: {
        required: true,
      },
      driverEmail: {
        required: true,
        email: true
      },
      driverDrivingLicense: {
        required: true,
      },
      paymentMethod: {
        required: true,
      }
    },
    messages: {
      driverName: {
        required: "Full name must be not empty!",
      },
      driverPhone: {
        required: "Phone must be not empty!",
      },
      driverNationalIdNo: {
        required: "National Id no must be not empty!",
      },
      driverCity: {
        required: "City must be not empty!",
      },
      driverDistrict: {
        required: "District must be not empty!",
      },
      driverWard: {
        required: "Ward must be not empty!",
      },
      driverHouseNumberStreet: {
        required: "House number and street must be not empty!",
      },
      driverDateOfBirth: {
        required: "Date of birth must be not empty!",
      },
      driverEmail: {
        required: "Email must be not empty!",
        email: "Incorrect email format!"
      },
      driverDrivingLicense: {
        required: "Driving license must be not empty!",
      },
      paymentMethod: {
        required: "Payment Method must be not empty!",
      },
    },
    errorPlacement: function(error, element) {
      if (element.attr("name") === "paymentMethod") {
        error.appendTo($(".paymentMethodErr")); // Hiển thị thông báo lỗi tại .roleErr do mình custom
      } else {
        error.insertAfter(element); // Hiển thị thông báo mặc định của plugin validate
      }
    },

  });


  // Handler click next button step 1
  $('#nextButton').click(function () {
    if ($("#bookCarForm").valid()) {
      nextTab();
    }

  })


  // Handler click confirm button step 2
  $('#confirmPayment').click(function (event) {
    event.preventDefault();

    if ($("#bookCarForm").valid()) {

      let formData = new FormData();
      formData.append('driverName', driverNameElement.value);
      formData.append('driverPhone', driverPhoneElement.value);
      formData.append('driverNationalIdNo', driverNationalIdNoElement.value);
      formData.append('driverEmail', driverEmailElement.value);
      formData.append('driverDateOfBirth', driverDateOfBirthElement.value);
      if (driverDrivingLicenseElement.files[0] !== undefined) {
        formData.append('driverDrivingLicense', driverDrivingLicenseElement.files[0]);
      }
      formData.append('driverCity', driverCityElement.value);
      formData.append('driverDistrict', driverDistrictElement.value);
      formData.append('driverWard', driverWardElement.value);
      formData.append('driverHouseNumberStreet', driverHouseNumberStreetElement.value);
      formData.append('paymentMethod', $("input[name='paymentMethod']:checked").val());
      formData.append('carId', $("#hiddenCarId").val());
      formData.append('pickUpDate', $("#hiddenPickUpDate").val());
      formData.append('pickUpTime', $("#hiddenPickUpTime").val());
      formData.append('dropOffDate', $("#hiddenDropOffDate").val());
      formData.append('dropOffTime', $("#hiddenDropOffTime").val());
      formData.append('totalAmount', $("#hiddenTotalAmount").val());

      console.log(formData)

      $.ajax({
        url: '/api/book-car/save',
        method: 'POST',
        contentType: false,
        processData: false,
        data: formData,
        success: function(response) {
          console.log(response);
          console.log(response.car.brand);
          $('#brandModelYearOfCar').text(response.car.brand + " " + response.car.model + " " + response.car.productionYear);
          $('#pickUp').text(response.startDateTime);
          $('#dropOff').text(response.endDateTime);
          $('#bookingNo').text(response.bookingNo);
        },
        error: function(xhr, status, error) {
          // Todo xử lý lỗi
        }
      });

      nextTab();
    }

  })



  // Lắng nghe sự kiện khi chọn select city và render district
  driverCityElement.addEventListener("change", function () {

    // Reset lại lựa chọn của district, ward mỗi lần change city
    driverDistrictElement.innerHTML = '<option value="" disabled selected hidden>District</option>';
    driverWardElement.innerHTML = '<option value="" disabled selected hidden>Ward</option>';

    let driverCityElementValue = this.value;

    $.ajax({
      url: '/api/districts',
      method: 'GET',
      contentType: 'application/json',
      data: {
        cityId: driverCityElementValue
      },
      success: function(response) {
        // Xử lý chuỗi JSON nhận được từ server
        //render district
        $.each(response, function(index, district) {
          let option = document.createElement('option');
          option.value = district.districtId;
          option.textContent = district.districtName;
          driverDistrictElement.appendChild(option);
        });
      },
      error: function(xhr, status, error) {
        // Todo xử lý lỗi
      }
    });

  });


  // Lắng nghe sự kiện khi chọn select district và render ward
  driverDistrictElement.addEventListener("change", function () {

    // Reset lại lựa chọn của ward mỗi lần change district
    driverWardElement.innerHTML = '<option value="" disabled selected hidden>Ward</option>';

    let driverDistrictElementValue = this.value;

    $.ajax({
      url: '/api/wards',
      method: 'GET',
      contentType: 'application/json',
      data: {
        districtId: driverDistrictElementValue
      },
      success: function(response) {
        // Xử lý chuỗi JSON nhận được từ server
        //render district
        $.each(response, function(index, ward) {
          let option = document.createElement('option');
          option.value = ward.wardId;
          option.textContent = ward.wardName;
          driverWardElement.appendChild(option);
        });
      },
      error: function(xhr, status, error) {
        // Todo xử lý lỗi
      }
    });

  });


  // Handler change driving license of driver
  $("input[name='driverDrivingLicense']").change(function () {
    let file = this.files[0];
    let reader = new FileReader();

    if (file && file.type.match('image.*')) {
      // Đọc và hiển thị thumbnail, preview
      reader.onload = function (e) {
        $('#thumbnailDrivingLicenseDriver').attr('src', e.target.result);
      }
      reader.readAsDataURL(file);
    } else {
      $('#thumbnailDrivingLicenseDriver').removeAttr('src');
    }
  })


  // Function next tab
  function nextTab() {

    const currentTab = document.querySelector('.nav-link.active');
    const currentIndex = Array.from(tabs).indexOf(currentTab);

    currentTab.classList.remove("active");

    let nextIndex = currentIndex + 1;

    tabs[nextIndex].classList.add("active", "show");

    document.querySelector(currentTab.getAttribute("href")).classList.remove("active", "show");
    document.querySelector(tabs[nextIndex].getAttribute("href")).classList.add("active", "show");

  }

});