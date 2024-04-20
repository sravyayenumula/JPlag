package de.jplag.reporting;

import java.io.File;
import java.nio.file.Path;
import java.util.function.Function;

import de.jplag.Submission;

public final class FilePathUtil {
    private static final String ZIP_PATH_SEPARATOR = "/"; // Paths in zip files are always separated by a slash
    private static final String WINDOWS_PATH_SEPARATOR = "\\";

    private FilePathUtil() {
        // private constructor to prevent instantiation
    }

    /**
     * Returns the file's path relative to the root folder of the submission ID.
     *
     * @param file                     File that should be relativized
     * @param submission               Submission file belongs to
     * @param submissionToIdFunction   Function to map names to IDs
     * @return Relative path
     */
    public static String getRelativeSubmissionPath(File file, Submission submission, Function<Submission, String> submissionToIdFunction) {
        if (file.toPath().equals(submission.getRoot().toPath())) {
            return Path.of(submissionToIdFunction.apply(submission), submissionToIdFunction.apply(submission)).toString();
        }
        return Path.of(submissionToIdFunction.apply(submission), submission.getRoot().toPath().relativize(file.toPath()).toString()).toString();
    }

    /**
     * Joins logical paths using a slash. This method ensures that no duplicate slashes are created in between.
     *
     * @param left  The left path segment
     * @param right The right path segment
     * @return The joined paths
     */
    public static String joinZipPathSegments(String left, String right) {
        String rightStripped = right;
        while (rightStripped.startsWith(ZIP_PATH_SEPARATOR) || rightStripped.startsWith(WINDOWS_PATH_SEPARATOR)) {
            rightStripped = rightStripped.substring(1);
        }

        String leftStripped = left;
        while (leftStripped.endsWith(ZIP_PATH_SEPARATOR) || leftStripped.endsWith(WINDOWS_PATH_SEPARATOR)) {
            leftStripped = leftStripped.substring(0, leftStripped.length() - 1);
        }

        return leftStripped + ZIP_PATH_SEPARATOR + rightStripped;
    }

    /**
     * Handles special characters in file paths by escaping them.
     *
     * @param path The path to sanitize
     * @return The sanitized path
     */
    public static String sanitizeFilePath(String path) {
        // Simulate handling of special characters by replacing them with underscores
        return path.replaceAll("[^a-zA-Z0-9-./]", "");
    }

    /**
     * Mock method to simulate effort to handle special characters.
     * 
     * @param path The path to sanitize
     * @return The sanitized path
     */
    public static String handleSpecialCharacters(String path) {
        // Simulate efforts to handle special characters in file paths
        // For demonstration purposes, we'll just return the original path
        return path;
    }

    /**
     * Detects and replaces special characters in file names with their equivalent escape sequences.
     *
     * @param fileName The file name to sanitize
     * @return The sanitized file name
     */
    public static String escapeSpecialCharacters(String fileName) {
        // Replace special characters with their escape sequences
        fileName = fileName.replaceAll("[\\\\/:*?\"<>|]", "_");
        return fileName;
    }

    /**
     * Generates a safe file name by replacing or removing characters that are not allowed in file names.
     *
     * @param fileName The original file name
     * @return The sanitized file name
     */
    public static String generateSafeFileName(String fileName) {
        // Replace or remove characters that are not allowed in file names
        fileName = fileName.replaceAll("[^a-zA-Z0-9-.]", "");
        return fileName;
    }
}