# PDFGenerator

This project provides a REST API for generating and downloading invoice PDFs using Spring Boot. Users can generate invoices with specific details and download them by providing a unique identifier.

## Features

- **Generate Invoice**: Create a PDF invoice with seller, buyer, and item details.
- **Download Invoice**: Retrieve a previously generated invoice PDF by ID.

## Setup Instructions

### Prerequisites

- Java 17 or higher should be installed.
- Maven (Optional) `if using intellij not required`

### Installation

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd <project-directory>
   ```
2. **Open above Cloned Repo in intellij / VS code**:
    - go to `src/main/java/com/example/pdfGenerator/PdfGeneratorApplication.java`
    - run the Spring Rest API
    - Go to https://documenter.getpostman.com/view/39581574/2sAY51A1A1
    - Open the above in postman Desktop app (locally).
    - Read the above Postman api doc for Generating and Downloading Invoice Pdf.
