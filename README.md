# Firefly Common Validators

[![CI](https://github.com/fireflyframework/fireflyframework-validators/actions/workflows/ci.yml/badge.svg)](https://github.com/fireflyframework/fireflyframework-validators/actions/workflows/ci.yml)
A comprehensive library providing robust validators for enterprise and mission-critical applications, part of the Firefly platform.

## Overview

The Firefly Validators library provides a set of high-quality validation utilities specifically designed for enterprise applications. These validators ensure data integrity and compliance with international standards, helping you build robust and reliable financial software.

## Features

This library contains validators for the following enterprise data validation formats:

- **IBAN Validator**: Validates International Bank Account Numbers according to ISO 13616 standard
- **BIC Validator**: Validates Bank Identifier Codes (SWIFT codes) according to ISO 9362 standard
- **Credit Card Validator**: Validates credit card numbers for various card types using the Luhn algorithm
- **Currency Code Validator**: Validates ISO 4217 currency codes with special support for European currencies
- **Sort Code Validator**: Validates UK bank sort codes with formatting and parsing capabilities
- **Account Number Validator**: Validates bank account numbers for various countries with country-specific rules

Each validator provides comprehensive validation logic, helpful utility methods, and clear error reporting.

## Requirements

- Java 11 or higher
- Maven 3.6 or higher (for building)

## Installation

Add the following dependency to your Maven `pom.xml`:

```xml
<dependency>
    <groupId>org.fireflyframework</groupId>
    <artifactId>fireflyframework-validators</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

For Gradle projects, add:

```groovy
implementation 'org.fireflyframework:fireflyframework-validators:1.0.0-SNAPSHOT'
```

## Usage

### IBAN Validator

The IBAN (International Bank Account Number) validator checks if an IBAN is valid according to the ISO 13616 standard, including country-specific length validation, character set validation, and check digit verification.

```java
IbanValidator validator = new IbanValidator();

// Validate an IBAN
boolean isValid = validator.isValid("DE89 3704 0044 0532 0130 00");
// Returns: true

// Invalid IBAN examples
boolean isTooShort = validator.isValid("DE89 3704 0044 0532 0130"); // Too short
boolean hasInvalidCountry = validator.isValid("US12 1234 5678 9012 3456"); // Invalid country code
```

### BIC Validator

The BIC (Bank Identifier Code) validator, also known as SWIFT code validator, checks if a BIC is valid according to the ISO 9362 standard. It validates the format and provides methods to extract different parts of the BIC.

```java
BicValidator validator = new BicValidator();

// Validate a BIC
boolean isValid = validator.isValid("DEUTDEFF");
// Returns: true

// Get parts of a BIC
String bankCode = validator.getBankCode("DEUTDEFF");     // Returns: "DEUT"
String countryCode = validator.getCountryCode("DEUTDEFF"); // Returns: "DE"
String locationCode = validator.getLocationCode("DEUTDEFF"); // Returns: "FF"
String branchCode = validator.getBranchCode("DEUTDEFF");  // Returns: "XXX" (default)
```

### Credit Card Validator

The Credit Card validator checks if a credit card number is valid using the Luhn algorithm and card-specific patterns. It can identify the card type and mask the card number for display purposes.

```java
CreditCardValidator validator = new CreditCardValidator();

// Validate a credit card number
boolean isValid = validator.isValid("4111 1111 1111 1111");
// Returns: true

// Get the card type
CreditCardValidator.CardType cardType = validator.getCardType("4111 1111 1111 1111");
// Returns: CardType.VISA

// Mask a card number for display
String masked = validator.maskCardNumber("4111 1111 1111 1111");
// Returns: "************1111"
```

Supported card types:
- Visa
- MasterCard
- American Express
- Discover
- Diners Club
- JCB

### Currency Code Validator

The Currency Code validator checks if a currency code is valid according to the ISO 4217 standard. It provides special methods for checking Euro and European currencies.

```java
CurrencyCodeValidator validator = new CurrencyCodeValidator();

// Validate a currency code
boolean isValid = validator.isValid("EUR");
// Returns: true

// Check if it's the Euro currency
boolean isEuro = validator.isEuroCurrency("EUR");
// Returns: true

// Check if it's a European currency
boolean isEuropean = validator.isEuropeanCurrency("GBP");
// Returns: true

// Invalid currency code examples
boolean isTooLong = validator.isValid("EURO"); // Too long
boolean isTooShort = validator.isValid("EU");   // Too short
boolean isNonExistent = validator.isValid("ABC");  // Non-existent code
```

### Sort Code Validator

The Sort Code validator checks if a UK bank sort code is valid. It provides methods to normalize, format, and extract parts of the sort code.

```java
SortCodeValidator validator = new SortCodeValidator();

// Validate a sort code
boolean isValid = validator.isValid("12-34-56");
// Returns: true

// Normalize a sort code (remove separators)
String normalized = validator.normalize("12-34-56");
// Returns: "123456"

// Format a sort code with hyphens
String formatted = validator.format("123456");
// Returns: "12-34-56"

// Get parts of a sort code
String bankId = validator.getBankIdentifier("12-34-56");     // Returns: "12"
String branchId = validator.getBranchIdentifier("12-34-56"); // Returns: "3456"
```

### Account Number Validator

The Account Number validator checks if a bank account number is valid for a specific country. It has special support for UK account numbers and bank accounts.

```java
AccountNumberValidator validator = new AccountNumberValidator();

// Validate an account number for a specific country
boolean isValid = validator.isValid("12345678", "GB");
// Returns: true

// Validate a UK account number
boolean isValidUk = validator.isValidUkAccountNumber("12345678");
// Returns: true

// Validate a UK bank account (sort code + account number)
boolean isValidAccount = validator.isValidUkBankAccount("12-34-56", "12345678");
// Returns: true

// Format a UK bank account
String formatted = validator.formatUkBankAccount("12-34-56", "12345678");
// Returns: "12-34-56 12345678"
```

Supported countries include:
- GB (United Kingdom): 8 digits
- FR (France): 10 digits
- DE (Germany): 10 digits
- ES (Spain): 10 digits
- IT (Italy): 12 digits
- NL (Netherlands): 10 digits
- BE (Belgium): 12 digits
- CH (Switzerland): 4-11 digits

## Standards Compliance

This library is designed to comply with the following international standards:

- **ISO 13616**: International Bank Account Number (IBAN)
- **ISO 9362**: Business Identifier Code (BIC)
- **ISO/IEC 7812**: Identification cards - Identification of issuers
- **ISO 4217**: Currency codes
- **UK Financial Standards**: Sort codes and account numbers

## Dependencies

- **Apache Commons Validator (1.7)**: Used for some validation utilities
- **JUnit 5 (5.9.2)**: Used for testing (test scope only)
- **AssertJ (3.24.2)**: Used for fluent assertions in tests (test scope only)

## Contributing

Contributions to the Firefly Validators library are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

Please ensure your code follows the existing style and includes appropriate tests.
