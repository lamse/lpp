package hey.lpp.domain.product;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidFileValidator.class)
public @interface ValidFile {

    String message() default "Invalid File";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String[] allowFileDefines(); // 업로드 허용 파일들의 정의 array(여러 종류의 파일 타입을 허용할 수도 있기에 array)
    boolean required() default true; // 콘텐츠 수정 시 파일은 수정하지 않는 경우 파일 정보가 없기 때문에 validation 체크를 하지 않기 위한 값(false인 경우 체크x)
}
