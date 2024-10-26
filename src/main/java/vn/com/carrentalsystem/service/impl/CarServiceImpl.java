package vn.com.carrentalsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import vn.com.carrentalsystem.dto.CarDTO;
import vn.com.carrentalsystem.dto.CarFromCityDTO;
import vn.com.carrentalsystem.entities.*;
import vn.com.carrentalsystem.enums.CarStatus;
import vn.com.carrentalsystem.repository.*;
import vn.com.carrentalsystem.service.CarService;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final DistrictRepository districtRepository;
    private final WardRepository wardRepository;
    private final ModelCarRepository modelCarRepository;
    private final BrandCarRepository brandCarRepository;
    private final CityRepository cityRepository;
    private final DocumentRepository documentRepository;
    private final ImageCarRepository imageCarRepository;
    private final AddressRepository addressRepository;
    private final AdditionalFunctionRepository additionalFunctionRepository;
    private final TermOfUseRepository termOfUseRepository;

    @Override
    public Car save(CarDTO carDTO, Userz userzCurrent) throws IOException {
        City city = cityRepository.findById(carDTO.getCity()).orElse(null);
        District district = districtRepository.findById(carDTO.getDistrict()).orElse(null);
        Ward ward = wardRepository.findById(carDTO.getWard()).orElse(null);
        BrandCar brandCar = brandCarRepository.findById(carDTO.getBrandName()).orElse(null);
        ModelCar modelCar = modelCarRepository.findById(carDTO.getModelCar()).orElse(null);

        // handle address
        Address address = Address.builder()
                .city(city.getCityName())
                .district(district.getDistrictName())
                .ward(ward.getWardName())
                .streetHomeNumber(carDTO.getStreetHomeNumber())
                .build();

        // handle addition function
        AdditionalFunction additionalFunction = AdditionalFunction.builder()
                .dvd(carDTO.getDvd() != null)
                .gps(carDTO.getGps() != null)
                .usb(carDTO.getUsb() != null)
                .camera(carDTO.getCamera() != null)
                .childSeat(carDTO.getChildSeat() != null)
                .childLock(carDTO.getChildLock() != null)
                .sunRoof(carDTO.getSunRoof() != null)
                .bluetooth(carDTO.getBluetooth() != null)
                .build();

        // handle term of use
        TermsOfUse termsOfUse = TermsOfUse.builder()
                .noFoodInCar(carDTO.getNoFoodInCar() != null)
                .noPet(carDTO.getNoPet() != null)
                .noSmoking(carDTO.getNoSmoking() != null)
                .other(carDTO.getOther() != null)
                .descriptionOfOther(carDTO.getSpecifyDescription())
                .build();

        // Start handle document
        Document document = saveDocumentCar(carDTO.getFileDocument());

        // Start handle image car
        ImageCar imageCar = saveImageCar(carDTO.getFileCarImage());

        // handle car
        Car car = Car.builder()
                .licensePlate(carDTO.getLicensePlate())
                .color(carDTO.getColor())
                .brand(brandCar.getBrandName())
                .model(modelCar.getModelName())
                .productionYear(carDTO.getProductionYear())
                .numberOfSeat(carDTO.getNoOfSeats())
                .transmissionType(carDTO.getTransmission())
                .fuelType(carDTO.getFuel())
                .document(document)
                .mileage(carDTO.getMileage())
                .fuelConsumption(carDTO.getFuelConsumption())
                .address(addressRepository.save(address))
                .carStatus(CarStatus.AVAILABLE)
                .description(carDTO.getDescription())
                .additionalFunction(additionalFunctionRepository.save(additionalFunction))
                .imageCar(imageCar)
                .basePrice(carDTO.getBasePrice())
                .deposit(carDTO.getDeposit())
                .termsOfUse(termOfUseRepository.save(termsOfUse))
                .userz(userzCurrent)
                .build();

        return carRepository.save(car);
    }

    public Document saveDocumentCar(MultipartFile[] multipartDocumentFiles) throws IOException {
        Document document = new Document();
        for(int i = 0; i < multipartDocumentFiles.length; i++) {
            String imageName = UUID.randomUUID() + "-" + StringUtils.cleanPath(multipartDocumentFiles[i].getOriginalFilename());
            //Lưu đường dẫn về Database
            if (i == 0) {
                document.setRegistration(imageName);
            }
            if (i == 1) {
                document.setCertificate(imageName);
            }
            if (i == 2) {
                document.setInsurance(imageName);
            }
        }

        //Lưu Document về Database
        Document documentDb = documentRepository.save(document);

        //Dường dẫn lưu file tính từ thư mục gốc project
        String uploadDocumentDir = "./src/main/resources/static/image/document/" + documentDb.getDocumentId();

        //Lưu file vào local
        for (int i = 0; i < multipartDocumentFiles.length; i++) {
            if (i == 0) {
                FileServiceImpl.saveFile(uploadDocumentDir, multipartDocumentFiles[i], documentDb.getRegistration());
            }
            if (i == 1) {
                FileServiceImpl.saveFile(uploadDocumentDir, multipartDocumentFiles[i], documentDb.getCertificate());
            }
            if (i == 2) {
                FileServiceImpl.saveFile(uploadDocumentDir, multipartDocumentFiles[i], documentDb.getInsurance());
            }
        }

        return documentDb;
    }

    public ImageCar saveImageCar(MultipartFile[] multipartImageCarFiles) throws IOException {
        ImageCar imageCar = new ImageCar();
        for(int i = 0; i < multipartImageCarFiles.length; i++) {
            String imageName = UUID.randomUUID() + "-" +StringUtils.cleanPath(multipartImageCarFiles[i].getOriginalFilename());
            //Lưu đường dẫn về Database
            if (i == 0) {
                imageCar.setUrlImageFront(imageName);
            }
            if (i == 1) {
                imageCar.setUrlImageBack(imageName);
            }
            if (i == 2) {
                imageCar.setUrlImageLeft(imageName);
            }
            if (i == 3) {
                imageCar.setUrlImageRight(imageName);
            }
        }

        //Lưu Image car về Database
        ImageCar imageCarDb = imageCarRepository.save(imageCar);

        //Dường dẫn lưu file tính từ thư mục gốc project
        String uploadImageCarDir = "./src/main/resources/static/image/image-car/" + imageCarDb.getImageCarId();

        //Lưu file vào local
        FileServiceImpl.saveFileInLocal(multipartImageCarFiles, uploadImageCarDir, imageCarDb);

        return imageCarDb;
    }

    @Override
    public List<Car> findByLicensePlate(String licensePlate) {
        return carRepository.findByLicensePlate(licensePlate);
    }

    @Override
    public List<CarFromCityDTO> countCarFromCity() {
        return carRepository.countCarFromCity();
    }

    @Override
    public Page<Car> getCarsByUserz(Userz userz, int page, int pageSize, String sort) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("carId").ascending());

        if ("lastestToNewest".equals(sort)) {
            pageable = PageRequest.of(page, pageSize, Sort.by("carId").descending());
        }

        return carRepository.findByUserz(userz, pageable);
    }

    @Override
    public Car findById(Long carId) {
        return carRepository.findById(carId).orElse(null);
    }

    @Override
    public void update(Long carId, CarDTO carDTO) throws IOException {
        Car carDB = carRepository.findById(carId).orElse(null);

        City city = cityRepository.findById(carDTO.getCity()).orElse(null);
        District district = districtRepository.findById(carDTO.getDistrict()).orElse(null);
        Ward ward = wardRepository.findById(carDTO.getWard()).orElse(null);

        carDB.setMileage(carDTO.getMileage());
        carDB.setCarStatus(carDTO.getCarStatus());
        carDB.setFuelConsumption(carDTO.getFuelConsumption());
        carDB.getAddress().setCity(city.getCityName());
        carDB.getAddress().setDistrict(district.getDistrictName());
        carDB.getAddress().setWard(ward.getWardName());
        carDB.getAddress().setStreetHomeNumber(carDTO.getStreetHomeNumber());
        carDB.setDescription(carDTO.getDescription());
        carDB.getAdditionalFunction().setBluetooth(carDTO.getBluetooth() != null);
        carDB.getAdditionalFunction().setCamera(carDTO.getCamera() != null);
        carDB.getAdditionalFunction().setDvd(carDTO.getDvd() != null);
        carDB.getAdditionalFunction().setGps(carDTO.getGps() != null);
        carDB.getAdditionalFunction().setUsb(carDTO.getUsb() != null);
        carDB.getAdditionalFunction().setChildLock(carDTO.getChildLock() != null);
        carDB.getAdditionalFunction().setChildSeat(carDTO.getChildSeat() != null);
        carDB.getAdditionalFunction().setSunRoof(carDTO.getSunRoof() != null);
        carDB.setBasePrice(carDTO.getBasePrice());
        carDB.setDeposit(carDTO.getDeposit());
        carDB.getTermsOfUse().setNoFoodInCar(carDTO.getNoFoodInCar() != null);
        carDB.getTermsOfUse().setNoPet(carDTO.getNoPet() != null);
        carDB.getTermsOfUse().setNoSmoking(carDTO.getNoSmoking() != null);
        carDB.getTermsOfUse().setOther(carDTO.getOther() != null);
        carDB.getTermsOfUse().setDescriptionOfOther(carDTO.getSpecifyDescription());

        // Handler image car, update and delete old image
        carDB.setImageCar(updateImageCar(carDTO.getFileCarImage(), carDB.getImageCar()));

        carRepository.save(carDB);

    }

    public ImageCar updateImageCar(MultipartFile[] multipartImageCarFiles, ImageCar imageCarDB) throws IOException {

        //Dường dẫn lưu file tính từ thư mục gốc project
        String uploadImageCarDir = "./src/main/resources/static/image/image-car/" + imageCarDB.getImageCarId();

        for(int i = 0; i < multipartImageCarFiles.length; i++) {

            if (!multipartImageCarFiles[i].isEmpty()) {
                String imageName = UUID.randomUUID() + "-" +StringUtils.cleanPath(multipartImageCarFiles[i].getOriginalFilename());
                //Lưu đường dẫn về Database
                if (i == 0){
                    // delete old image
                    File imageFile = new File(uploadImageCarDir + "/" +imageCarDB.getUrlImageFront());
                    FileSystemUtils.deleteRecursively(imageFile);

                    // update new image name
                    imageCarDB.setUrlImageFront(imageName);
                }
                if (i == 1){
                    // xoa file cu de update file moi
                    File imageFile = new File(uploadImageCarDir + "/" + imageCarDB.getUrlImageBack());
                    FileSystemUtils.deleteRecursively(imageFile);

                    // update ten file moi
                    imageCarDB.setUrlImageBack(imageName);
                }
                if (i == 2){
                    // xoa file cu de update file moi
                    File imageFile = new File(uploadImageCarDir + "/" + imageCarDB.getUrlImageLeft());
                    FileSystemUtils.deleteRecursively(imageFile);

                    // update ten file moi
                    imageCarDB.setUrlImageLeft(imageName);
                }
                if (i == 3){
                    // xoa file cu de update file moi
                    File imageFile = new File(uploadImageCarDir + "/" + imageCarDB.getUrlImageRight());
                    FileSystemUtils.deleteRecursively(imageFile);

                    // update ten file moi
                    imageCarDB.setUrlImageRight(imageName);
                }
            }

        }

        //update Image car về Database
        imageCarRepository.save(imageCarDB);

        //Lưu file vào local
        FileServiceImpl.saveFileInLocal(multipartImageCarFiles, uploadImageCarDir, imageCarDB);

        return imageCarDB;
    }

}
