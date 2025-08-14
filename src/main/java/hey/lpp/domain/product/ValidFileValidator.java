package hey.lpp.domain.product;

import hey.lpp.util.FileStore;
import hey.lpp.util.FilenameUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.ArrayUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ValidFileValidator implements ConstraintValidator<ValidFile, List<MultipartFile>> {
    private final FileStore fileStore;
    private ValidFile annotation;

    @Override
    public void initialize(ValidFile constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext context) {
        boolean result = true;
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                if (!annotation.required()) {
                    return true;
                }
                //context.buildConstraintViolationWithTemplate("업로드 대상 파일이 없습니다.").addConstraintViolation();
                return false;
            }

            final String fileName = file.getOriginalFilename();
//            if (!StringUtils.hasText(fileName)) {
//                continue; // 파일명이 비어있으면 건너뜀
//            }

            try {
                int targetByte = file.getBytes().length;
                if (targetByte == 0) {
                    context.buildConstraintViolationWithTemplate("The size of file(" + fileName + ")is 0 bytes.").addConstraintViolation();
                    result = false;
                    continue;
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                context.buildConstraintViolationWithTemplate("An error occurred while checking the capacity of file.(" + fileName + ")").addConstraintViolation();
                result = false;
                continue;
            }

            final String[] allowExtArray = annotation.allowFileDefines();
            final String fileExt = FilenameUtil.getExtension(fileName);

            //파일명의 허용 확장자 검사
            if (!ArrayUtils.contains(allowExtArray, fileExt.toLowerCase())) {
                String sb = "Contains files with an extension that is not allowed(" + fileExt + "). Only the following extensions are allowed" +
                        " [" + Arrays.toString(allowExtArray)  + "]";
                context.buildConstraintViolationWithTemplate(sb).addConstraintViolation();
                result = false;
            }
        }

        return result;
    }
}
