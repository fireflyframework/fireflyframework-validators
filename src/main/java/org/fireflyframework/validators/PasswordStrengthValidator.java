/*
 * Copyright 2024-2026 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.fireflyframework.validators;

import org.fireflyframework.annotations.ValidPasswordStrength;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Validator for password strength.
 * 
 * This validator checks if a password is strong enough according to:
 * - Length constraints
 * - Character type constraints (uppercase, lowercase, digits, special characters)
 * - Common/weak password detection
 */
public class PasswordStrengthValidator implements ConstraintValidator<ValidPasswordStrength, String> {

    private int minLength;
    private int maxLength;
    private boolean requireUppercase;
    private boolean requireLowercase;
    private boolean requireDigit;
    private boolean requireSpecialChar;
    private int minCharTypes;
    private boolean rejectCommonPasswords;

    // Patterns for different character types
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile(".*[A-Z].*");
    private static final Pattern LOWERCASE_PATTERN = Pattern.compile(".*[a-z].*");
    private static final Pattern DIGIT_PATTERN = Pattern.compile(".*\\d.*");
    private static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile(".*[^a-zA-Z0-9].*");

    // Set of common/weak passwords that should be rejected
    private static final Set<String> COMMON_PASSWORDS = new HashSet<>(Arrays.asList(
        "password", "123456", "12345678", "qwerty", "abc123", "monkey", "1234567", "letmein",
        "trustno1", "dragon", "baseball", "111111", "iloveyou", "master", "sunshine", "ashley",
        "bailey", "passw0rd", "shadow", "123123", "654321", "superman", "qazwsx", "michael",
        "football", "welcome", "jesus", "ninja", "mustang", "password1", "123456789", "adobe123",
        "admin", "1234567890", "photoshop", "1234", "12345", "princess", "azerty", "000000",
        "access", "696969", "batman", "1qaz2wsx", "login", "qwertyuiop", "solo", "starwars",
        "121212", "flower", "hottie", "loveme", "zaq1zaq1", "hello", "freedom", "whatever",
        "666666", "!@#$%^&*", "charlie", "aa123456", "donald", "password123", "qwerty123",
        "secret", "admin123", "temp123", "test123", "123qwe", "zxcvbnm", "asdfghjkl", "pokemon",
        "jordan", "computer", "michelle", "jessica", "pepper", "1111", "zxcvbn", "asdfgh",
        "hunter", "silver", "minecraft", "purple", "jordan23", "ferrari", "cookie", "justin",
        "test", "hockey", "dallas", "chelsea", "summer", "internet", "soccer", "liverpool"
    ));

    @Override
    public void initialize(ValidPasswordStrength constraintAnnotation) {
        this.minLength = constraintAnnotation.minLength();
        this.maxLength = constraintAnnotation.maxLength();
        this.requireUppercase = constraintAnnotation.requireUppercase();
        this.requireLowercase = constraintAnnotation.requireLowercase();
        this.requireDigit = constraintAnnotation.requireDigit();
        this.requireSpecialChar = constraintAnnotation.requireSpecialChar();
        this.minCharTypes = constraintAnnotation.minCharTypes();
        this.rejectCommonPasswords = constraintAnnotation.rejectCommonPasswords();
    }

    /**
     * Validates if the provided password is strong enough.
     *
     * @param password the password to validate
     * @param context the constraint validator context
     * @return true if the password is strong enough, false otherwise
     */
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || password.isEmpty()) {
            return false;
        }

        // Check length constraints
        if (password.length() < minLength || password.length() > maxLength) {
            return false;
        }

        // Check if common passwords should be rejected
        if (rejectCommonPasswords && isCommonPassword(password)) {
            return false;
        }

        // Check character type constraints
        int charTypesCount = 0;

        boolean hasUppercase = UPPERCASE_PATTERN.matcher(password).find();
        boolean hasLowercase = LOWERCASE_PATTERN.matcher(password).find();
        boolean hasDigit = DIGIT_PATTERN.matcher(password).find();
        boolean hasSpecialChar = SPECIAL_CHAR_PATTERN.matcher(password).find();

        if (hasUppercase) charTypesCount++;
        if (hasLowercase) charTypesCount++;
        if (hasDigit) charTypesCount++;
        if (hasSpecialChar) charTypesCount++;

        // Check if the password has the required character types
        if (requireUppercase && !hasUppercase) {
            return false;
        }
        if (requireLowercase && !hasLowercase) {
            return false;
        }
        if (requireDigit && !hasDigit) {
            return false;
        }
        if (requireSpecialChar && !hasSpecialChar) {
            return false;
        }

        // Check if the password has the minimum number of character types
        return charTypesCount >= minCharTypes;
    }

    /**
     * Checks if a password is a common/weak password.
     *
     * @param password the password to check
     * @return true if the password is common/weak, false otherwise
     */
    public boolean isCommonPassword(String password) {
        if (password == null || password.isEmpty()) {
            return true;
        }

        // Check if it's in the common passwords set (case-insensitive)
        return COMMON_PASSWORDS.contains(password.toLowerCase());
    }

    /**
     * Calculates the strength score of a password (0-100).
     *
     * @param password the password to evaluate
     * @return the strength score (0-100) or -1 if invalid
     */
    public int calculateStrengthScore(String password) {
        if (password == null || password.isEmpty()) {
            return -1;
        }

        int score = 0;

        // Length score (up to 30 points)
        int lengthScore = Math.min(password.length() * 3, 30);
        score += lengthScore;

        // Character type score (up to 40 points)
        boolean hasUppercase = UPPERCASE_PATTERN.matcher(password).find();
        boolean hasLowercase = LOWERCASE_PATTERN.matcher(password).find();
        boolean hasDigit = DIGIT_PATTERN.matcher(password).find();
        boolean hasSpecialChar = SPECIAL_CHAR_PATTERN.matcher(password).find();

        if (hasUppercase) score += 10;
        if (hasLowercase) score += 10;
        if (hasDigit) score += 10;
        if (hasSpecialChar) score += 10;

        // Add bonus points for mixed character types (up to 10 points)
        int charTypesCount = 0;
        if (hasUppercase) charTypesCount++;
        if (hasLowercase) charTypesCount++;
        if (hasDigit) charTypesCount++;
        if (hasSpecialChar) charTypesCount++;

        score += (charTypesCount - 1) * 3; // 3 points for each additional character type beyond the first

        // Add bonus points for longer passwords (up to 10 points)
        if (password.length() > 12) {
            score += Math.min((password.length() - 12) * 2, 10);
        }

        // Deduct points for common passwords (up to 20 points)
        if (isCommonPassword(password)) {
            score -= 20;
        }

        // Ensure score is between 0 and 100
        return Math.max(0, Math.min(100, score));
    }

    /**
     * Gets the strength category of a password.
     *
     * @param password the password to evaluate
     * @return the strength category or null if invalid
     */
    public PasswordStrength getStrengthCategory(String password) {
        int score = calculateStrengthScore(password);

        if (score < 0) {
            return null;
        } else if (score < 20) {
            return PasswordStrength.VERY_WEAK;
        } else if (score < 40) {
            return PasswordStrength.WEAK;
        } else if (score < 60) {
            return PasswordStrength.MEDIUM;
        } else if (score < 80) {
            return PasswordStrength.STRONG;
        } else {
            return PasswordStrength.VERY_STRONG;
        }
    }

    /**
     * Generates a secure random password.
     *
     * @param length the length of the password to generate
     * @return a secure random password or null if the length is invalid
     */
    public String generateSecurePassword(int length) {
        if (length < minLength || length > maxLength) {
            return null;
        }

        java.security.SecureRandom random = new java.security.SecureRandom();
        StringBuilder password = new StringBuilder();

        // Character sets
        String uppercaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseChars = "abcdefghijklmnopqrstuvwxyz";
        String digitChars = "0123456789";
        String specialChars = "!@#$%^&*()-_=+[]{}|;:,.<>?";

        // Ensure at least one character from each required type
        if (requireUppercase) {
            password.append(uppercaseChars.charAt(random.nextInt(uppercaseChars.length())));
        }
        if (requireLowercase) {
            password.append(lowercaseChars.charAt(random.nextInt(lowercaseChars.length())));
        }
        if (requireDigit) {
            password.append(digitChars.charAt(random.nextInt(digitChars.length())));
        }
        if (requireSpecialChar) {
            password.append(specialChars.charAt(random.nextInt(specialChars.length())));
        }

        // Build the full character set based on requirements
        StringBuilder charSet = new StringBuilder();
        if (requireUppercase) charSet.append(uppercaseChars);
        if (requireLowercase) charSet.append(lowercaseChars);
        if (requireDigit) charSet.append(digitChars);
        if (requireSpecialChar) charSet.append(specialChars);

        // Fill the rest of the password with random characters
        for (int i = password.length(); i < length; i++) {
            password.append(charSet.charAt(random.nextInt(charSet.length())));
        }

        // Shuffle the password to avoid predictable patterns
        char[] passwordArray = password.toString().toCharArray();
        for (int i = 0; i < passwordArray.length; i++) {
            int j = random.nextInt(passwordArray.length);
            char temp = passwordArray[i];
            passwordArray[i] = passwordArray[j];
            passwordArray[j] = temp;
        }

        return new String(passwordArray);
    }

    /**
     * Password strength categories.
     */
    public enum PasswordStrength {
        VERY_WEAK,
        WEAK,
        MEDIUM,
        STRONG,
        VERY_STRONG
    }
}
