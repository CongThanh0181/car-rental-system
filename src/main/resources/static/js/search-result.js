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
// $(document).ready(function () {
//     let sortElement = $('select[name="sort"]');
//     // load first
//     let sortSelectedValueCurrent = sortElement.val();
//     let pageValueCurrent = $(".pagination .page-num.active a.page-link").text() - 1;
//     renderListBookingWithPageAndSort(pageValueCurrent, sortSelectedValueCurrent);
//
//
//     // handler click on pageNum
//     $(".pagination .page-num").click(function (element) {
//         let pageNode = element.target.closest('.page-num:not(.active)');
//
//         if (pageNode) {
//             let pageNodeActive = $(".pagination .page-num.active");
//             pageNodeActive.removeClass('active');
//             pageNode.classList.add('active');
//
//             let aElement = pageNode.querySelector('a.page-link');
//             let pageValue = aElement.textContent - 1;
//             let sortSelectedValue = sortElement.val();
//             renderListBookingWithPageAndSort(pageValue, sortSelectedValue);
//         }
//     })
//
//
//     // handler change on sort select option
//     sortElement.change(function () {
//         let sortSelectedValue = sortElement.val();
//         let pageValue = $(".pagination .page-num.active a.page-link").text() - 1;
//         renderListBookingWithPageAndSort(pageValue, sortSelectedValue);
//
//     })
//
//
//     // handler click next page
//     $(".pagination .page-next").click(function () {
//         let pageNodeActive = $(".pagination .page-num.active");
//         let nextSibling = pageNodeActive.next().not('.page-next');
//
//         if (nextSibling.length !== 0) {
//             pageNodeActive.removeClass('active');
//             nextSibling.addClass('active');
//
//             let sortSelectedValue = sortElement.val();
//             let pageValue = $(".pagination .page-num.active a.page-link").text() - 1;
//             renderListBookingWithPageAndSort(pageValue, sortSelectedValue);
//         }
//
//     });
//
//
//     // handler click prev page
//     $(".pagination .page-prev").click(function () {
//         let pageNodeActive = $(".pagination .page-num.active");
//         let nextSibling = pageNodeActive.prev().not('.page-prev');
//
//         if (nextSibling.length !== 0) {
//             pageNodeActive.removeClass('active');
//             nextSibling.addClass('active');
//
//             let sortSelectedValue = $('select[name="sort"]').val();
//             let pageValue = $(".pagination .page-num.active a.page-link").text() - 1;
//             renderListBookingWithPageAndSort(pageValue, sortSelectedValue);
//         }
//     });
//
// })
//
//
//
//
// function renderListBookingWithPageAndSort(page, sort) {
//
//     console.log(sort)
//     console.log(page)
//     let url = "/api/home-customer/search-result?page=" + page + "&sort=" + sort;
//
//     // Gửi yêu cầu HTTP đến server
//     $.ajax({
//         url: url,
//         method: "GET",
//         success: function (response) {
//
//             console.log("Response from server:", response);
//             let htmlString = getHtml(response);
//             $(".search-car-item").html(htmlString);
//         },
//         error: function (xhr, status, error) {
//
//             console.log(error)
//             // Todo xử lý lỗi
//         }
//     });
// }
//
//
//
// //Get html sau khi nhận response
// function getHtml(response) {
//     let htmlString = ``;
//     response.forEach(function (car) {
//         htmlString = htmlString +
//             `            <tr>
//                             <td>${car.carId}</td>
//                             <td>${car.modelCar}</td>
//                             <td class="text-center"><img
//                                                          style="max-width: 230px; max-height: 200px" alt="...">
//                             </td>
//                             <td>${car.rate}</td>
//                             <td>${car.noOfRides}</td>
//                             <td>${car.basePrice} <span> /day</span></td>
//                             <td>${car.location}</td>
//                             <td>${car.carStatus}</td>
//                             <td class="d-flex flex-column">
//                                 <a class="btn btn-primary mb-3"
//                                         href="@{/home-customer/book-car(carId=${car.carId})}">
//                                         Rent now</a>
//                                 <a class="btn btn-primary"
//                                         href="@{/home-customer/car-detail(carId=${car.carId})}">
//                                         View detail</a>
//                             </td>
//                         </tr>
//             `;
//     })
//     return htmlString;
// }

