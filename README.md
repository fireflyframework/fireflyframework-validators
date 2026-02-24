# Firefly Framework - Validators

[![CI](https://github.com/fireflyframework/fireflyframework-validators/actions/workflows/ci.yml/badge.svg)](https://github.com/fireflyframework/fireflyframework-validators/actions/workflows/ci.yml)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-21%2B-orange.svg)](https://openjdk.org)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green.svg)](https://spring.io/projects/spring-boot)

> Financial and domain-specific Bean Validation validators for enterprise Java applications.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Quick Start](#quick-start)
- [Configuration](#configuration)
- [Documentation](#documentation)
- [Contributing](#contributing)
- [License](#license)

## Overview

Firefly Framework Validators provides a comprehensive set of Jakarta Bean Validation constraint annotations and their corresponding validators, purpose-built for financial services and enterprise applications.

Each validator is implemented as a custom constraint annotation paired with a `ConstraintValidator` implementation, making them composable with standard Bean Validation workflows. The library covers account numbers, IBAN, BIC/SWIFT, credit cards, CVV, currency codes, phone numbers, tax IDs, national IDs, interest rates, amounts, dates, PIN codes, sort codes, and password strength.

## Features

- `@ValidAccountNumber` - Account number format validation
- `@ValidIban` - IBAN (International Bank Account Number) validation
- `@ValidBic` - BIC/SWIFT code validation
- `@ValidCreditCard` - Credit card number validation (Luhn algorithm)
- `@ValidCVV` - Card verification value validation
- `@ValidCurrencyCode` - ISO 4217 currency code validation
- `@ValidPhoneNumber` - International phone number validation
- `@ValidTaxId` - Tax identification number validation
- `@ValidNationalId` - National identity document validation
- `@ValidAmount` - Monetary amount validation
- `@ValidInterestRate` - Interest rate range validation
- `@ValidDate` / `@ValidDateTime` - Date and datetime format validation
- `@ValidPIN` - Personal identification number validation
- `@ValidSortCode` - Bank sort code validation
- `@ValidPasswordStrength` - Password complexity validation

## Requirements

- Java 21+
- Spring Boot 3.x
- Maven 3.9+

## Installation

```xml
<dependency>
    <groupId>org.fireflyframework</groupId>
    <artifactId>fireflyframework-validators</artifactId>
    <version>26.02.07</version>
</dependency>
```

## Quick Start

```java
import org.fireflyframework.annotations.ValidIban;
import org.fireflyframework.annotations.ValidAmount;

public class TransferRequest {

    @ValidIban
    private String recipientIban;

    @ValidAmount
    private BigDecimal amount;

    @ValidCurrencyCode
    private String currency;
}
```

## Configuration

No configuration is required. Validators are automatically discovered by the Jakarta Bean Validation provider.

## Documentation

No additional documentation available for this project.

## Contributing

Contributions are welcome. Please read the [CONTRIBUTING.md](CONTRIBUTING.md) guide for details on our code of conduct, development process, and how to submit pull requests.

## License

Copyright 2024-2026 Firefly Software Solutions Inc.

Licensed under the Apache License, Version 2.0. See [LICENSE](LICENSE) for details.
