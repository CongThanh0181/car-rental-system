// Get the modals
var modal1 = document.getElementById("myModal1");
var modal2 = document.getElementById("myModal2");

// Get the buttons that open the modals
var btn1 = document.getElementById("openModalBtn1");
var btn2 = document.getElementById("openModalBtn2");

// Get the <span> elements that close the modals
var span1 = document.getElementsByClassName("close")[0];
var span2 = document.getElementsByClassName("close")[1];

// When the user clicks the button, open the modal 1
btn1.onclick = function () {
    modal1.style.display = "block";
}

// When the user clicks on <span> (x), close the modal 1
span1.onclick = function () {
    modal1.style.display = "none";
    window.location.reload();
}

// When the user clicks the button, open the modal 2
btn2.onclick = function () {
    modal2.style.display = "block";
}

// When the user clicks on <span> (x), close the modal 2
span2.onclick = function () {
    modal2.style.display = "none";
    window.location.reload();
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function (event) {
    if (event.target === modal1 || event.target === modal2) {
        event.target.style.display = "none";
        return window.location.reload();
    }
}

    function formatCurrency(input) {
    // Lấy giá trị nhập vào và loại bỏ các ký tự không phải số
    let value = input.value.replace(/[^0-9.]/g, '');

    // Thêm dấu phẩy giữa các hàng đơn vị
        value = value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");

    // Định dạng số tiền theo định dạng tiền tệ
    input.value =  value + 'VND';
}
// Function to submit form
document.getElementById('myForm').onsubmit = function(event ) {
    event.preventDefault()
    var balance = document.getElementById("amountWallet").innerHTML.replace(/[^0-9.]/g, '')
    console.log(balance)
    var withdrawMoney = document.getElementById("withdrawNumber").value.replace(/[^0-9.]/g, '')
    console.log(withdrawMoney)
    // Get form data
    var formData = new FormData(document.getElementById('myForm'));
    // Send form data using fetch API
    fetch('/withdrawForm', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (response.ok) {
                if(balance - withdrawMoney >= 0){
                alert('Withdraw successfully!');
                document.getElementById('myModal').style.display = "none"; // Close modal after successful submission
                    }
                else {
                    alert('Failed to withdraw!');
                }
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
};

document.getElementById('topUpForm').onsubmit = function(event) {
    event.preventDefault();

    // Lấy số dư từ phần tử có id là "amountWallet"
    var balance = parseFloat(document.getElementById("amountWallet").innerText);

    // Lấy số tiền muốn nạp từ phần tử có id là "topUpInput"
    var topUpMoney = parseFloat(document.getElementById("topUpInput").value);

    console.log(balance);
    console.log(topUpMoney);

    // Kiểm tra xem các giá trị đã được lấy đúng không
    // Nếu số dư và số tiền nạp là NaN (Not a Number), có thể có lỗi xảy ra trong việc lấy giá trị từ các phần tử
    if (isNaN(balance) || isNaN(topUpMoney)) {
        console.error("Error: Unable to get balance or top-up money.");
        return;
    }

    // Thực hiện các bước tiếp theo như gửi dữ liệu bằng fetch API
    var formDataTopUp = new FormData(document.getElementById('topUpForm'));
    fetch('/topUpForm', {
        method: 'POST',
        body: formDataTopUp
    })
        .then(response => {
            if (response.ok) {
                alert('Top Up successfully!');
                document.getElementById('myModal').style.display = "none"; // Close modal after successful submission
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
};

// Hàm này chuyển đổi một số thành chuỗi và thêm dấu phẩy giữa các hàng đơn vị
function addCommas(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

// Hàm này được gọi khi trang đã tải xong để thay đổi văn bản trong phần tử p
window.onload = function() {
    let number = document.getElementById("formattedNumber").innerHTML
    document.getElementById("formattedNumber").innerHTML = addCommas(number)
}
