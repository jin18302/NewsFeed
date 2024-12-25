package NewsFeedProject.newsfeed;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.Set;

public class EnumValidator implements ConstraintValidator<EnumValid, Enum<?>> {

    private Set<String> acceptedValues = new HashSet<>();

    @Override
    public void initialize(EnumValid constraintAnnotation) {
        // 어노테이션에서 전달받은 enumClass 값을 가져옴
        Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClass();

        // Enum의 모든 값을 acceptedValues에 저장
        for (Enum<?> enumConstant : enumClass.getEnumConstants()) {
            acceptedValues.add(enumConstant.name().toLowerCase());
        }
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;  // null 값은 검증하지 않음
        }

        // Enum 값이 유효한지 확인 (소문자 비교)
        return acceptedValues.contains(value.name().toLowerCase());
    }
}
