package vn.com.carrentalsystem.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.com.carrentalsystem.entities.ImageCar;
import vn.com.carrentalsystem.service.FileService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {

    public static void saveFile(String uploadDir, MultipartFile multipartFile, String fileName) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {

            Path filePath = uploadPath.resolve(fileName);

            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            //Todo
        }

    }

    public static void saveFileInLocal(MultipartFile[] multipartImageCarFiles, String uploadImageCarDir, ImageCar imageCarDb) throws IOException {

        for (int i = 0; i < multipartImageCarFiles.length; i++) {
            if (!multipartImageCarFiles[i].isEmpty()) {
                if (i == 0) {
                    FileServiceImpl.saveFile(uploadImageCarDir, multipartImageCarFiles[i], imageCarDb.getUrlImageFront());
                }
                if (i == 1) {
                    FileServiceImpl.saveFile(uploadImageCarDir, multipartImageCarFiles[i], imageCarDb.getUrlImageBack());
                }
                if (i == 2) {
                    FileServiceImpl.saveFile(uploadImageCarDir, multipartImageCarFiles[i], imageCarDb.getUrlImageLeft());
                }
                if (i == 3) {
                    FileServiceImpl.saveFile(uploadImageCarDir, multipartImageCarFiles[i], imageCarDb.getUrlImageRight());
                }
            }
        }
    }

}
