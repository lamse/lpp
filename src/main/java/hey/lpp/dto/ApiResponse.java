package hey.lpp.dto;

public record ApiResponse<T>(String status, T data, Object errors) {

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("success", data, null);
    }

    public static ApiResponse<?> error(Object errors) {
        return new ApiResponse<>("error", null, errors);
    }
}
