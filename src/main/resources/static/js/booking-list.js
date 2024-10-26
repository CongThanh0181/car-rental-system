$(document).ready(function() {
    let sortElement = $('select[name="sort"]');
    // load first
    let sortSelectedValueCurrent = sortElement.val();
    let pageValueCurrent = $(".pagination .page-num.active a.page-link").text() - 1;
    renderListBookingWithPageAndSort(pageValueCurrent, sortSelectedValueCurrent);


    // handler click on pageNum
    $(".pagination .page-num").click(function (element) {
        let pageNode = element.target.closest('.page-num:not(.active)');

        if (pageNode) {
            let pageNodeActive = $(".pagination .page-num.active");
            pageNodeActive.removeClass('active');
            pageNode.classList.add('active');

            let aElement = pageNode.querySelector('a.page-link');
            let pageValue = aElement.textContent - 1;
            let sortSelectedValue = sortElement.val();
            renderListBookingWithPageAndSort(pageValue, sortSelectedValue);
        }
    })


    // handler change on sort select option
    sortElement.change(function () {
        let sortSelectedValue = sortElement.val();
        let pageValue = $(".pagination .page-num.active a.page-link").text() - 1;
        renderListBookingWithPageAndSort(pageValue, sortSelectedValue);

    })


    // handler click next page
    $(".pagination .page-next").click(function () {
        let pageNodeActive = $(".pagination .page-num.active");
        let nextSibling = pageNodeActive.next().not('.page-next');

        if (nextSibling.length !== 0) {
            pageNodeActive.removeClass('active');
            nextSibling.addClass('active');

            let sortSelectedValue = sortElement.val();
            let pageValue = $(".pagination .page-num.active a.page-link").text() - 1;
            renderListBookingWithPageAndSort(pageValue, sortSelectedValue);
        }

    });


    // handler click prev page
    $(".pagination .page-prev").click(function () {
        let pageNodeActive = $(".pagination .page-num.active");
        let nextSibling = pageNodeActive.prev().not('.page-prev');

        if (nextSibling.length !== 0) {
            pageNodeActive.removeClass('active');
            nextSibling.addClass('active');

            let sortSelectedValue = $('select[name="sort"]').val();
            let pageValue = $(".pagination .page-num.active a.page-link").text() - 1;
            renderListBookingWithPageAndSort(pageValue, sortSelectedValue);
        }
    });

})


function renderListBookingWithPageAndSort(page, sort) {

    let url = "/api/home-customer/booking-list?page=" + page + "&sort=" + sort;

    // Gửi yêu cầu HTTP đến server
    $.ajax({
        url: url,
        method: "GET",
        success: function (response) {
            let htmlString = getHtml(response);
            $(".booking-item").html(htmlString);

        },
        error: function (xhr, status, error) {
            // Todo xử lý lỗi
        }
    });

}


//Get html sau khi nhận response
function getHtml(response) {
    let htmlString = ``;
    response.forEach(function (booking) {
        htmlString = htmlString +
            `<div class="car-detail__preview">
                <div class="row">
                    <div class="preview--image col-6">
                        <div id="carouselIndicators${booking.carId}" class="carousel slide" data-ride="carousel">
                           <ol class="carousel-indicators">
                                <li data-target="#carouselIndicators${booking.bookingId}" data-slide-to="0" class="active"></li>
                                <li data-target="#carouselIndicators${booking.bookingId}" data-slide-to="1"></li>
                                <li data-target="#carouselIndicators${booking.bookingId}" data-slide-to="2"></li>
                                <li data-target="#carouselIndicators${booking.bookingId}" data-slide-to="3"></li>
                            </ol>
                            <div class="carousel-inner">
                                <div class="carousel-item active">
                                    <img src="/image/image-car/${booking.carId}/${booking.urlImageFront}" class="d-block w-100" alt="...">
                                </div>
                                <div class="carousel-item">
                                    <img src="/image/image-car/${booking.carId}/${booking.urlImageBack}" class="d-block w-100" alt="...">
                                </div>
                                <div class="carousel-item">
                                    <img src="/image/image-car/${booking.carId}/${booking.urlImageLeft}" class="d-block w-100" alt="...">
                                </div>
                                <div class="carousel-item">
                                    <img src="/image/image-car/${booking.carId}/${booking.urlImageRight}" class="d-block w-100" alt="...">
                                </div>
                            </div>
                           <button class="carousel-control-prev" type="button"
                                    data-target="#carouselIndicators${booking.bookingId}" data-slide="prev">
                                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                <span class="sr-only">Previous</span>
                            </button>
                            <button class="carousel-control-next" type="button"
                                    data-target="#carouselIndicators${booking.bookingId}" data-slide="next">
                                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                <span class="sr-only">Next</span>
                            </button>
                        </div>
                    </div>
                    <div class="preview--description col-4">
                        <h4 class="preview--description__title display-4 mb-3">${booking.brand} ${booking.model} ${booking.productionYear}</h4>
                        <div>
                            <ul>
                                <li class="mb-3"><span>From: </span> ${booking.startDateTime}</li> 
                                <li> <span>To: </span> ${booking.endDateTime}</li>  
                            </ul>
                        </div>
                        <div class="row mb-3">
                            <div class="col-4">
                                <span class="preview--description__lable">Number of days:</span>
                            </div>
                            <div class="col-8">
                                <span class="preview--description__info">${booking.numberOfDay}</span>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-4">
                                <span class="preview--description__lable">Price:</span>
                            </div>
                            <div class="col-8">
                                <span class="preview--description__info">${booking.basePrice}</span>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-4">
                                <span class="preview--description__lable">Total:</span>
                            </div>
                            <div class="col-8">
                                <span class="preview--description__info">${booking.basePrice * booking.numberOfDay}</span>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-4">
                                <span class="preview--description__lable">Deposit:</span>
                            </div>
                            <div class="col-8">
                                <span class="preview--description__info">${booking.deposit}</span>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-4">
                                <span class="preview--description__lable">Booking No:</span>
                            </div>
                            <div class="col-8">
                                <span class="preview--description__info">${booking.bookingNo}"></span>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-4">
                                <span class="preview--description__lable">Booking status:</span>
                            </div>
                            <div class="col-8">
                                <span class="preview--description__info text-${booking.bookingStatus === 'IN_PROGRESS' ? 'warning'
                                                                                : booking.bookingStatus === 'CONFIRMED' ? 'success'
                                                                                    : booking.bookingStatus === 'PENDING_DEPOSIT' ? 'danger'
                                                                                        : booking.bookingStatus === 'COMPLETED' ? 'primary'
                                                                                            : 'secondary'}">${booking.bookingStatus}</span>
                            </div>
                        </div>
                    </div>
                    <div class="preview--atc col-2">
                        <button class="btn btn-primary" href="@{/home-customer/car-detail(carId=${booking.carId})}">View Details</button>
                        <a class="btn btn-primary d-${(booking.bookingStatus === 'IN_PROGRESS') ? 'block' : 'none'}"
                                                href="/home-customer/return-car?bookingId=${booking.bookingId}"
                                                onclick="return confirm('${booking.notification}')">Return Car</a>
                        <a class="btn btn-secondary d-${(booking.bookingStatus === 'CONFIRMED') ? 'block' : 'none'}" 
                                                href="/home-customer/confirm-pick-up-booking?bookingId=${booking.bookingId}"
                                                onclick="return confirm('Are you sure you want to confirm pick up?')">Confirm Pick-Up</a>
                       <a class="btn btn-danger d-${(booking.bookingStatus === 'CONFIRMED' || booking.bookingStatus === 'PENDING_DEPOSIT') ? 'block' : 'none'}"
                                                href="/home-customer/cancel-booking?bookingId=${booking.bookingId}"
                                                onclick="return confirm('Are you sure you want to cancel booking?')">Cancel Booking</a>
                    </div>
                </div>
            </div>
            `;
    })

    return htmlString;
}

