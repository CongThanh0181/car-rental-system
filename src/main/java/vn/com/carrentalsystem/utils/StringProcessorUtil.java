package vn.com.carrentalsystem.utils;

public class StringProcessorUtil {

    public static String processFullName(String fullName) {

        // Remove excess space and convert the first letter to uppercase
        String[] nameParts = fullName.trim().split("\\s+");
        StringBuilder processedName = new StringBuilder();

        for (String namePart : nameParts) {
            if (!namePart.isEmpty()) {
                String processedPart = namePart.substring(0, 1).toUpperCase() + namePart.substring(1).toLowerCase();
                processedName.append(processedPart).append(" ");
            }
        }

        // Remove excess space in last word
        return processedName.toString().trim();
    }
}
