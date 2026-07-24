package utils;

/**
 * Lớp tiện ích chứa các phương thức kiểm tra dữ liệu đầu vào.
 * Tất cả method đều là static — không cần khởi tạo đối tượng.
 * View gọi trực tiếp trước khi ủy quyền xử lý cho Controller.
 */
public class ValidationUtils {

    public static final int MIN_AGE = 18;
    public static final int MAX_AGE = 50;

    private ValidationUtils() {
    }

    /**
     * Kiểm tra các chuỗi đầu vào có bị rỗng (hoặc chỉ chứa khoảng trắng) không.
     * Dùng varargs để kiểm tra nhiều trường cùng lúc.
     *
     * @param values danh sách các chuỗi cần kiểm tra
     * @return true nếu TẤT CẢ đều không rỗng, false nếu có ít nhất 1 trường rỗng
     */
    public static boolean isNotEmpty(String... values) {
        for (String v : values) {
            if (v == null || v.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Kiểm tra chuỗi có parse được sang số nguyên không.
     */
    public static boolean isValidInt(String value) {
        try {
            Integer.parseInt(value.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Kiểm tra chuỗi có parse được sang số thực không.
     */
    public static boolean isValidDouble(String value) {
        try {
            Double.parseDouble(value.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Kiểm tra tuổi có nằm trong khoảng cho phép (18–50) không.
     */
    public static boolean isValidAge(int age) {
        return age >= MIN_AGE && age <= MAX_AGE;
    }

    /**
     * Kiểm tra một số có lớn hơn 0 không (dùng cho lương và số tiền điều chỉnh).
     */
    public static boolean isPositive(double value) {
        return value > 0;
    }
}
