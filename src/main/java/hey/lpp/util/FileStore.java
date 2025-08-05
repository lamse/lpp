package hey.lpp.util;

import hey.lpp.domain.product.UploadFile;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class FileStore {

    @Value("${upload.image.dir}")
    private String fileDir;
    private final String yearMonthDay;

    public FileStore() {
        yearMonthDay = FilenameUtil.getYearMonthDay();
    }

    public String getFullPath(String filename) {
        String rootPath = System.getProperty("user.dir");
        return rootPath + fileDir + filename;
    }

    @PostConstruct
    public void createSaveDirectory() {
        String rootPath = System.getProperty("user.dir");
        log.info("directoryPath: {}", rootPath);
        Path targetDir = Paths.get(rootPath + fileDir + yearMonthDay);
        try {
            // Create the directories, including any non-existent parent directories
            log.info("Creating directory: {}", targetDir);
            Files.createDirectories(targetDir);
        } catch (IOException e) {
            log.info("Failed to create directory {}", targetDir, e);
        }
    }

    public List<UploadFile> storeFiles(List<MultipartFile> files) throws IOException {
        ArrayList<UploadFile> uploadFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue; // 파일이 비어있으면 건너뜀
            }
            uploadFiles.add(storeFile(file));
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

        return new UploadFile(originalFilename, storeFilename.replace("/", "_")); // 업로드된 파일 정보 반환
    }

    private String createStoreFileName(String originalFilename) {
        String ext = FilenameUtil.getExtension(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return yearMonthDay + "/" + uuid + "." + ext;
    }
}
