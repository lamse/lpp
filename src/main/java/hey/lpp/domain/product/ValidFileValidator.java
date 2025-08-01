package hey.lpp.domain.product;

import hey.lpp.util.FilenameUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.ArrayUtils;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class ValidFileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

    private ValidFile annotation;

    @Override
    public void initialize(ValidFile constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {

        if (!annotation.required()) {
            if (multipartFile.isEmpty()) {
                return true;
            }
        }

        if (multipartFile.isEmpty()) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("업로드 대상 파일이 없습니다.").addConstraintViolation();
            return false;
        }

        final String fileName = multipartFile.getOriginalFilename();
        if (!StringUtils.hasText(fileName)) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("업로드 요청한 파일명이 존재하지 않습니다.").addConstraintViolation();
            return false;
        }

        try {
            int targetByte = multipartFile.getBytes().length;
            if (targetByte == 0) {
                constraintValidatorContext.buildConstraintViolationWithTemplate("파일의 용량이 0 byte입니다.").addConstraintViolation();
                return false;
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            constraintValidatorContext.buildConstraintViolationWithTemplate("파일의 용량 확인 중 에러가 발생했습니다.").addConstraintViolation();
            return false;
        }


        final String[] allowExtArray = annotation.allowFileDefines();
        final String fileExt = FilenameUtil.getExtension(fileName);

        //파일명의 허용 확장자 검사
        if (!ArrayUtils.contains(allowExtArray, fileExt.toLowerCase())) {
            String sb = "허용되지 않는 확장자의 파일입니다. 다음 확장자들만 허용됩니다." +
                    ": " + Arrays.toString(allowExtArray);
            constraintValidatorContext.buildConstraintViolationWithTemplate(sb).addConstraintViolation();

            return false;
        }

        return true;
    }
}
