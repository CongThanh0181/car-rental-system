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

    let url = "/api/home-carowner/car-list?page="+ page +"&sort=" + sort;

    // Gửi yêu cầu HTTP đến server
    $.ajax({
        url: url,
        method: "GET",
        success: function(response) {
            let htmlString = getHtml(response);
            $(".car-item").html(htmlString);

        },
        error: function(xhr, status, error) {
            // Todo xử lý lỗi
        }
    });

}

//Get html sau khi nhận response
function getHtml(response) {
    let htmlString = ``;
    response.forEach(function (car) {
        htmlString = htmlString +
            `<div class="car-detail__preview">
                <div class="row">
                    <div class="preview--image col-6">
                        <div id="carouselIndicators${car.carId}" class="carousel slide" data-ride="carousel">
                            <ol class="carousel-indicators">
                                <li data-target="#carouselIndicators${car.carId}" data-slide-to="0" class="active"></li>
                                <li data-target="#carouselIndicators${car.carId}" data-slide-to="1"></li>
                                <li data-target="#carouselIndicators${car.carId}" data-slide-to="2"></li>
                                <li data-target="#carouselIndicators${car.carId}" data-slide-to="3"></li>
                            </ol>
                            <div class="carousel-inner">
                                <div class="carousel-item active">
                                    <img src="/image/image-car/${car.carId}/${car.urlImageFront}" class="d-block w-100" alt="...">
                                </div>
                                <div class="carousel-item">
                                    <img src="/image/image-car/${car.carId}/${car.urlImageBack}" class="d-block w-100" alt="...">
                                </div>
                                <div class="carousel-item">
                                    <img src="/image/image-car/${car.carId}/${car.urlImageLeft}" class="d-block w-100" alt="...">
                                </div>
                                <div class="carousel-item">
                                    <img src="/image/image-car/${car.carId}/${car.urlImageRight}" class="d-block w-100" alt="...">
                                </div>
                            </div>
                            <button class="carousel-control-prev" type="button"
                                    data-target="#carouselIndicators${car.carId}" data-slide="prev">
                                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                <span class="sr-only">Previous</span>
                            </button>
                            <button class="carousel-control-next" type="button"
                                    data-target="#carouselIndicators${car.carId}" data-slide="next">
                                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                <span class="sr-only">Next</span>
                            </button>
                        </div>
                    </div>
                    <div class="preview--description col-4">
                        <h4 class="preview--description__title display-4 mb-5">${car.brand} ${car.model} ${car.productionYear}</h4>
                        <div class="row mb-3">
                            <div class="col-4">
                                <span class="preview--description__lable">Ratings:</span>
                            </div>
                            <div class="col-8 preview--description__info">
                                <i class="fa-regular fa-star"></i>
                                <i class="fa-regular fa-star"></i>
                                <i class="fa-regular fa-star"></i>
                                <i class="fa-regular fa-star"></i>
                                <i class="fa-regular fa-star"></i>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-4">
                                
                            </div>
                            <div class="col-8">
                                <span>(No ratings yet)</span>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-4">
                                <span class="preview--description__lable">No. of rides:</span>
                            </div>
                            <div class="col-8">
                                <span class="preview--description__info">${car.mileage}</span>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-4">
                                <span class="preview--description__lable">Price:</span>
                            </div>
                            <div class="col-8">
                                <span class="preview--description__info">
                                    <span>${car.basePrice}</span>
                                    k/day
                                </span>
                            </div>
                        </div> 
                        <div class="row mb-3">
                            <div class="col-4">
                                <span class="preview--description__lable">Locations:</span>
                            </div>
                            <div class="col-8">
                                <span class="preview--description__info">${car.district}, ${car.city}</span>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-4">
                                <span class="preview--description__lable">Status:</span>
                            </div>
                            <div class="col-8">
                                <span class="preview--description__info text-${car.carStatus === 'Available' ? 'success' : 'danger'}">${car.carStatus}</span>
                            </div>
                        </div>
                    </div>
                    <div class="preview--atc col-2">
                        <a href="/home-carowner/edit-car-detail?carId=${car.carId}" class="btn btn-primary">View Details</a>
                        <a href="#" class="btn btn-primary">Confirm Deposit</a>
                    </div>
                </div>
            </div>
            `;
    })

    return htmlString;
}