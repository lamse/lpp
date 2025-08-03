package hey.lpp.util;

import hey.lpp.domain.product.UploadFile;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileStore {
    private final ServletContext servletContext;

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        String directoryPath = servletContext.getRealPath("/");
        log.info("directoryPath: {}", directoryPath);
        String targetDir = createDateDirectory(directoryPath + fileDir + File.separator + "product");
        return targetDir + File.separator + filename;
    }

    public List<UploadFile> storeFiles(List<MultipartFile> files) throws IOException {
        ArrayList<UploadFile> uploadFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue; // 파일이 비어있으면 건너뜀
            }
            UploadFile uploadFile = storeFile(file); // 저장할 파일 이름 생성
            uploadFiles.add(uploadFile);
        }

        return uploadFiles;
    }


    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null; // 파일이 비어있으면 null 반환
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFilename = createStoreFileName(originalFilename); // 저장할 파일 이름 생성
        String storeFilePath = getFullPath(storeFilename);
        multipartFile.transferTo(new File(storeFilePath)); // 파일 저장

        return new UploadFile(originalFilename, storeFilePath); // 업로드된 파일 정보 반환
    }

    private String createStoreFileName(String originalFilename) {
        String ext = FilenameUtil.getExtension(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    public static String createDateDirectory(String baseDir) {
        LocalDate currentDate = LocalDate.now();

        // Format the date components for directory names
        String year = String.valueOf(currentDate.getYear());
        String month = String.format("%02d", currentDate.getMonthValue());
        String day = String.format("%02d", currentDate.getDayOfMonth());


        Path targetDir = Paths.get(baseDir, year, month, day);

        try {
            // Create the directories, including any non-existent parent directories
            Files.createDirectories(targetDir);
        } catch (IOException e) {
            log.info("Failed to create directory {}", targetDir, e);
        }

        return targetDir.toString();
    }
}
