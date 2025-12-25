# Ala-Too Student Complaint Resolution System (SCRS)

A comprehensive web-based complaint management system designed for universities to handle student complaints efficiently with voting and rating functionality.

## 🎯 Features

### Core Functionality
- **User Authentication & Authorization**: JWT-based authentication with role-based access control
- **Complaint Management**: Create, track, and manage complaints through their lifecycle
- **Voting & Rating System**: Students can vote and rate complaint resolutions
- **File Attachments**: Upload and manage supporting documents and images
- **Real-time Status Updates**: Track complaint progress from submission to resolution
- **Multi-role Support**: Students, Staff, and Administrators with different permissions

### User Roles
- **Students**: Submit complaints, vote on resolutions, confirm completion
- **Staff**: Review, assign, and update complaint status
- **Administrators**: Full system access, user management, system configuration

### Complaint Lifecycle
1. **NEW**: Complaint submitted by student
2. **ASSIGNED**: Complaint assigned to appropriate staff member
3. **IN_PROGRESS**: Staff member is working on the complaint
4. **RESOLUTION_ANNOUNCED**: Solution provided, awaiting student confirmation
5. **CONFIRMED_BY_STUDENT**: Student confirms resolution is satisfactory
6. **CLOSED**: Complaint officially closed

## 🏗️ System Architecture

### Backend (Java Spring Boot)
```
src/main/java/com/alaToo/scrs/
├── config/           # Security and application configuration
├── controller/       # REST API controllers
├── dto/             # Data Transfer Objects
├── entity/          # JPA entities
├── repository/      # Data access layer
├── security/        # JWT security components
├── service/         # Business logic layer
└── util/            # Utility classes
```

### Frontend
```
src/main/resources/static/
├── index.html       # Main application interface
├── css/
│   └── style.css   # Comprehensive styling
└── js/
    └── app.js      # Frontend application logic
```

## 🚀 Getting Started

### Prerequisites
- Java 11 or higher
- Maven 3.6+
- H2 Database (included)

### Installation & Running

1. **Clone the repository**
```bash
git clone <repository-url>
cd scrs
```

2. **Build and run the application**
```bash
mvn spring-boot:run
```

3. **Access the application**
- Open your browser and go to: `http://localhost:8080`
- The application will automatically create and initialize the database

### Default Login Credentials

**Administrator:**
- Username: `admin`
- Password: `admin123`

**Staff Member:**
- Username: `staff1`
- Password: `staff123`

**Student:**
- Username: `student1`
- Password: `student123`

## 📊 API Documentation

### Authentication Endpoints
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration
- `GET /api/auth/user` - Get current user info

### Complaint Endpoints
- `GET /api/complaints` - Get user's complaints
- `POST /api/complaints` - Create new complaint
- `GET /api/complaints/{id}` - Get specific complaint
- `PUT /api/complaints/{id}` - Update complaint
- `PUT /api/complaints/{id}/status` - Update complaint status
- `PUT /api/complaints/{id}/confirm` - Confirm resolution
- `GET /api/complaints/new` - Get new complaints (staff only)

### Voting Endpoints
- `POST /api/votes/{complaintId}` - Submit vote
- `DELETE /api/votes/{complaintId}` - Remove vote
- `GET /api/votes/{complaintId}` - Get votes for complaint
- `GET /api/votes/{complaintId}/stats` - Get voting statistics

### File Management Endpoints
- `POST /api/files/upload/{complaintId}` - Upload file
- `GET /api/files/{fileId}` - Download file
- `GET /api/files/complaint/{complaintId}` - Get complaint files
- `DELETE /api/files/{fileId}` - Delete file

## 🗃️ Database Schema

### Core Entities

**Users**
- Personal information and credentials
- Role-based access control
- Student ID for verification

**Departments**
- Academic, Facility, Administration, IT categories
- Contact information and management

**Complaints**
- Complete complaint lifecycle tracking
- File attachments support
- Voting and rating integration

**Complaint Votes**
- 1-5 star rating system
- User-vote relationship
- Aggregate statistics

**Complaint Files**
- File storage metadata
- Support for images, PDFs, documents
- Security validation

## 🎨 User Interface Features

### Modern Design
- Responsive layout for all devices
- Gradient backgrounds and glass-morphism effects
- Interactive components with hover effects
- Toast notifications for user feedback

### Navigation
- Mobile-friendly hamburger menu
- Role-based menu visibility
- Quick access to key functions

### Forms
- Real-time validation
- Multi-step forms for complex operations
- File upload with drag-and-drop support
- Department auto-population based on type

### Dashboard
- Real-time statistics
- Visual complaint status indicators
- Quick action buttons
- Filtering and search capabilities

## 🔒 Security Features

### Authentication & Authorization
- JWT token-based authentication
- Role-based access control (RBAC)
- Secure password hashing with BCrypt
- CORS configuration for web access

### Data Protection
- Input validation and sanitization
- SQL injection prevention
- XSS protection
- File upload security restrictions

### API Security
- Protected endpoints with JWT
- Request rate limiting
- Error handling without information leakage
- Secure file serving

## 🛠️ Technical Implementation

### Backend Technologies
- **Spring Boot 2.7+**: Application framework
- **Spring Security**: Authentication and authorization
- **Spring Data JPA**: Database operations
- **H2 Database**: Embedded database for development
- **JWT**: Token-based authentication
- **Maven**: Dependency management

### Frontend Technologies
- **HTML5**: Semantic markup
- **CSS3**: Modern styling with flexbox/grid
- **Vanilla JavaScript**: Client-side functionality
- **Font Awesome**: Icon library
- **Responsive Design**: Mobile-first approach

### Architecture Patterns
- **MVC Pattern**: Clear separation of concerns
- **Repository Pattern**: Data access abstraction
- **Service Layer**: Business logic encapsulation
- **DTO Pattern**: Data transfer optimization

## 📈 Performance Features

### Optimization
- Lazy loading for large datasets
- Efficient database queries with JPA
- Caching strategies for frequently accessed data
- Optimized file serving

### Scalability
- Stateless authentication
- Horizontal scaling ready
- Database connection pooling
- Memory-efficient file handling

## 🔧 Configuration

### Application Properties
Key configurations in `application.properties`:
- Database connection settings
- JWT token configuration
- File upload limits
- CORS origins

### Environment Variables
- `JWT_SECRET`: Secret key for JWT signing
- `JWT_EXPIRATION`: Token expiration time
- `UPLOAD_DIR`: File upload directory

## 🧪 Testing

### Manual Testing Checklist
- [ ] User registration and login
- [ ] Complaint creation and management
- [ ] File upload and download
- [ ] Voting and rating system
- [ ] Status updates by staff
- [ ] Resolution confirmation by students
- [ ] Role-based access control
- [ ] Mobile responsiveness

### Test Scenarios
1. **Student Journey**: Register → Login → Submit Complaint → Vote on Resolution
2. **Staff Journey**: Login → Review Complaints → Update Status → Announce Resolution
3. **Admin Journey**: Login → Manage Users → System Configuration → Generate Reports

## 🚀 Deployment

### Production Considerations
1. **Database**: Switch to PostgreSQL or MySQL
2. **File Storage**: Use cloud storage (AWS S3, etc.)
3. **Security**: Update JWT secrets and HTTPS configuration
4. **Monitoring**: Add logging and monitoring tools
5. **Backup**: Implement regular database backups

### Docker Deployment
```dockerfile
FROM openjdk:11-jre-slim
COPY target/scrs.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 📝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🤝 Support

For support and questions:
- Create an issue in the repository
- Contact the development team
- Check the documentation

## 🎯 Future Enhancements

### Planned Features
- [ ] Email notifications for status changes
- [ ] Advanced reporting and analytics
- [ ] Mobile application (React Native)
- [ ] Integration with university systems
- [ ] AI-powered complaint categorization
- [ ] Real-time chat support
- [ ] Multi-language support
- [ ] Advanced search and filtering

### Technical Improvements
- [ ] Unit and integration tests
- [ ] Performance monitoring
- [ ] API documentation (Swagger)
- [ ] CI/CD pipeline
- [ ] Microservices architecture
- [ ] GraphQL API support

---

**Built with ❤️ for Ala-Too International University**

*Empowering students to voice their concerns and building a better campus together.*
# Student-Complaint-Resolution-System-SCRS
