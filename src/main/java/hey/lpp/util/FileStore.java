package hey.lpp.util;

import hey.lpp.domain.product.UploadFile;
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

    @Value("${file.dir}")
    private String fileDir;
    private final String yearMonthDay;

    public FileStore() {
        yearMonthDay = FilenameUtil.getYearMonthDay();
    }

    public String getFullPath(String filename) {
        String rootPath = System.getProperty("user.dir");
        log.info("directoryPath: {}", rootPath);
        String staticDir = "/src/main/resources/static";
        String targetDir = createDateDirectory(rootPath + staticDir + fileDir + File.separator
                + "product" + File.separator + yearMonthDay);
        return targetDir + File.separator + filename;
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
        storeFilePath = fileDir + "product/" + yearMonthDay + "/" + storeFilename; // web 경로로 변경

        return new UploadFile(originalFilename, storeFilePath); // 업로드된 파일 정보 반환
    }

    private String createStoreFileName(String originalFilename) {
        String ext = FilenameUtil.getExtension(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    public static String createDateDirectory(String baseDir) {
        Path targetDir = Paths.get(baseDir);
        try {
            // Create the directories, including any non-existent parent directories
            Files.createDirectories(targetDir);
        } catch (IOException e) {
            log.info("Failed to create directory {}", targetDir, e);
        }

        return targetDir.toString();
    }
}
