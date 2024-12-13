# Vehicle Rental Management System

The **Vehicle Rental Management System** is a JavaFX-based application designed to facilitate the management of vehicle rentals. It provides administrators with CRUD functionality for vehicles, users, and rental records, ensuring efficient data handling and accurate rental tracking. The system integrates PostgreSQL for robust database management and follows the MVC architecture for modularity and scalability.

---

## Team Members List

**Jumanazarova Akylai**
- Sole contributor

### Roles:
- System Design and Development
- JavaFX GUI creation
- Backend implementation with PostgreSQL
- Database schema design
- Documentation and testing

---

## Requirements

### Database Connection
The system is connected to a PostgreSQL database to store and manage records for vehicles, users, and rentals. The database supports efficient querying and updates.

### JavaFX Design
The application is developed using JavaFX for its user interface. The design is modern, user-friendly, and responsive to ensure a smooth user experience for administrators and customers.

---

## Features

### Vehicle Management
- **Add Vehicles**: Add new vehicles to the system, specifying attributes such as type, model, brand, and rental cost.
- **Edit Vehicles**: Update vehicle details when needed.
- **View Vehicles**: Display a list of all vehicles, including availability and other key information.
- **Delete Vehicles**: Remove vehicles no longer in service.

### User Management
- **Add Users**: Register new users by providing personal details such as name, contact information, and role.
- **Edit Users**: Update user details as necessary.
- **View Users**: List all registered users.
- **Delete Users**: Remove user records when required.

### Rental Management
- **Add Rentals**: Record new rental transactions, linking users and vehicles.
- **Edit Rentals**: Modify rental records if necessary.
- **View Rentals**: Display detailed information about active and completed rentals.
- **Delete Rentals**: Remove erroneous or completed rental records.

### Authentication
- **Secure Login**: Role-based login system to differentiate between admin and user access.
- **Error Handling**: Notifications for failed login attempts.

### Data Visualization
- **Comprehensive ListView**: A unified view displaying vehicles, users, and rental data.
- **Reports and Analytics**: Summarize data like vehicle utilization, user activity, and rental durations.

---

## Purpose and Goals
The system is designed to:
- Automate the management of vehicle rentals.
- Provide a secure and efficient database-backed application.
- Offer a user-friendly interface for all interactions.
- Enhance operational efficiency through streamlined workflows.

---

## Key Features

### CRUD Operations
- Manage vehicles, users, and rentals with intuitive UI components and secure backend operations.

### Rental Tracking
- Keep detailed records of rentals, including user and vehicle details.

### Role-Based Authentication
- Ensure secure access with distinct permissions for administrators and users.

### Scalability
- Designed to handle up to 1000 records efficiently.

### Data Security
- Input validation and hashed passwords for enhanced security.

---

## Database Design

### Tables
1. **Users**:
   - `user_id`, `username`, `password`, `email`, `phone_number`, `role`.
2. **Vehicles**:
   - `vehicle_id`, `type_name`, `brand`, `model`, `rental_price`, `availability`.
3. **Rentals**:
   - `rental_id`, `user_id`, `vehicle_id`, `start_date`, `end_date`, `total_cost`.

### Database Connection Details
- **URL**: `jdbc:postgresql://localhost:5432/vehicle`
- **User**: `postgres`
- **Password**: `akylai95`

---

## Functional Requirements
- **Secure Authentication**: Login system for admins and users.
- **CRUD Operations**: Create, read, update, and delete data for vehicles, users, and rentals.
- **Error Handling**: Notify users of invalid input or system issues.
- **Responsive UI**: Ensure all operations complete within 2 seconds.

---

## Technical Requirements
- **Database**: PostgreSQL (version 13 or higher).
- **JavaFX**: For building the user interface.
- **Java Libraries**:
  - `java.sql` for database operations.
  - `java.util` for collections and utilities.
  - `java.time.LocalDate` for date handling.
  
![](https://github.com/user-attachments/assets/fdbb502f-1a7d-4a3b-9fac-45c2caf8c719).

---

## Interface Specification

### Management Screens
#### Vehicles, Users, and Rentals
- Table: Displays corresponding data.
- Buttons: Add, Edit, Delete.

---

## Custom Scenarios

### Adding a Vehicle
- **Precondition**: Navigate to the Vehicle Management tab.
- **Main Flow**: Enter vehicle details (ID, type, license plate, price per day).

Click "Add" to save the new vehicle to the database
- **Postcondition**:  The new vehicle appears in the Vehicle Management table.

### Renting a Vehicle
- **Precondition**:  Ensure both user and vehicle records exist in the system.
- **Main Flow**: Select user and vehicle, specify rental period, and confirm.
- **Postcondition**:The rental details are saved and displayed in the Rental Management table.


---

## Dependencies
- **PostgreSQL**: For database management.
- **JavaFX**: For graphical user interface.
- **Java Libraries**: For essential operations.

---

## UML Diagram
![image](https://github.com/user-attachments/assets/79a7af16-427c-4109-944c-f5bca3321d74).

---

## Presentation
*Click here to view the project presentation.*
![](https://www.canva.com/design/DAGYyxLgDDA/rgm2W3aWmMR8Y4PdjKIdcQ/edit?utm_content=DAGYyxLgDDA&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton).




