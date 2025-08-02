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
                    context.buildConstraintViolationWithTemplate("파일(" + fileName + ")의 용량이 0 byte입니다.").addConstraintViolation();
                    result = false;
                    continue;
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                context.buildConstraintViolationWithTemplate("파일(" + fileName + ")의 용량 확인 중 에러가 발생했습니다.").addConstraintViolation();
                result = false;
                continue;
            }

            final String[] allowExtArray = annotation.allowFileDefines();
            final String fileExt = FilenameUtil.getExtension(fileName);

            //파일명의 허용 확장자 검사
            if (!ArrayUtils.contains(allowExtArray, fileExt.toLowerCase())) {
                String sb = "허용되지 않는 확장자(" + fileExt + ")의 파일이 포함되어 있습니다. 다음 확장자들만 허용됩니다." +
                        " [" + Arrays.toString(allowExtArray)  + "]";
                context.buildConstraintViolationWithTemplate(sb).addConstraintViolation();
                result = false;
            }
        }

        return result;
    }
}
