package nh.demo.plantify.shared.text;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class TextUtils {

    public static boolean hasText(String str) {
        // from: org.springframework.util.StringUtils.hasText(java.lang.String)
        return (str != null && !str.isBlank());
    }

    public static Collection<String> getWords(String str) {
        if (str == null) {
            return List.of();
        }

        String text = str
            .replaceAll("[.,;:!?()\"'-]", " ")
            .trim()
            .replaceAll("\\s+", " ");

        return Arrays.asList(text.split(" "));
    }

}
