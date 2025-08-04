package hey.lpp.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FilenameUtil {
    /**
     * 파일 이름에서 확장자를 추출합니다.
     *
     * @param filename 파일 이름
     * @return 확장자 (없으면 빈 문자열)
     */
    public static String getExtension(String filename) {
        if (filename == null || filename.isEmpty()) {
            return "";
        }
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == filename.length() - 1) {
            return ""; // 확장자가 없거나 점(.)이 마지막에 있는 경우
        }
        return filename.substring(lastDotIndex + 1).toLowerCase();
    }

    public static String getYearMonthDay() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return now.format(formatter);
    }
}
