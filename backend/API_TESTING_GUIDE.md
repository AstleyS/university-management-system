## API Testing Guide

This directory contains organized HTTP request files for testing the University Management System API with role-based access control.

### File Organization

- **auth.http** - Authentication endpoints (login/register)
- **courses.http** - CourseRoute management endpoints
- **students.http** - Student management endpoints
- **professors.http** - Professor management endpoints
- **enrollments.http** - Enrollment management endpoints
- **course-instructors.http** - CourseRoute instructor (professor-course mapping) endpoints

### How to Use Tokens

**IMPORTANT: Use @ symbol (not quotes) for token variables**

#### Token Setup

1. At the top of each `.http` file, you'll see:
```
@admin_token = 
@professor_token = 
@student_token = 
```

2. **Get your tokens:**
   - Open `auth.http`
   - Run the login requests for each role
   - Copy the token from the response (without quotes)

3. **Paste tokens:**
   - Go back to the same file where you need the token
   - Find the token setup section at the top
   - Paste the token value after the `=` sign:
   ```
   @admin_token = eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
   @professor_token = eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
   @student_token = eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
   ```

4. **Use in requests:**
   ```
   Authorization: Bearer @admin_token
   ```
   
   ✅ **CORRECT** - Use @ symbol
   ❌ **WRONG** - Don't use quotes or curly braces

### Installation

1. **Install REST Client Extension** (VS Code)
   - Open Extensions (Ctrl+Shift+X)
   - Search for "REST Client"
   - Install by Huachao Zheng

2. **Run Requests**
   - Click "Send Request" above any request
   - View response in the right panel

### Testing Role-Based Access Control (RBAC)

Each file contains examples of:
- ✅ **ALLOWED** requests - User has permission
- ❌ **DENIED** requests - User lacks permission (403 Forbidden)

#### Access Levels Summary

| Endpoint | Admin | Professor | Student |
|----------|-------|-----------|---------|
| **Courses** | CRUD | Read | Read |
| **Students** | CRUD | Read | Read Own |
| **Professors** | CRUD | Read | Read |
| **Enrollments** | CRUD | Read + Grade | Read Own |
| **CourseRoute Instructors** | CRUD | Read | Read |

### Key Endpoint Examples

#### Authentication
```
POST /api/auth/register
POST /api/auth/login
```

#### Courses (courses.http)
```
GET    /api/courses              (All roles)
GET    /api/courses/{id}         (All roles)
POST   /api/courses              (Admin only)
PUT    /api/courses/{id}         (Admin only)
DELETE /api/courses/{id}         (Admin only)
```

#### Enrollments (enrollments.http)
```
GET    /api/enrollments                    (Admin, Professor)
GET    /api/enrollments/{id}               (All roles)
GET    /api/enrollments/student/{id}       (Student own, Professor read, Admin read)
POST   /api/enrollments                    (Admin only)
PUT    /api/enrollments/{id}               (Admin only)
PATCH  /api/enrollments/{id}/grade         (Admin, Professor)
DELETE /api/enrollments/{id}               (Admin only)
```

#### CourseRoute Instructors (course-instructors.http)
```
GET    /api/course-instructors                        (Admin, Professor)
GET    /api/course-instructors/{id}                   (All roles)
GET    /api/course-instructors/professor/{id}/courses (All roles)
POST   /api/course-instructors                        (Admin only)
PUT    /api/course-instructors/{id}                   (Admin only)
DELETE /api/course-instructors/{id}                   (Admin only)
```

### Testing Workflow

1. **Start Backend Server**
   ```bash
   cd backend
   ./gradlew bootRun
   ```

2. **Setup Test Users** (auth.http)
   - Run: Register admin, student1, professor1
   - Run: Login for each role
   - Copy all 3 tokens

3. **Add Tokens to Files**
   - Open any `.http` file (e.g., courses.http)
   - Find the token setup at the top
   - Paste all 3 tokens

4. **Test by Role**
   - Run ALLOWED requests (should succeed)
   - Run DENIED requests (should get 403 Forbidden)

5. **Verify Permissions**
   - Confirm RBAC is working correctly
   - Check error messages for denied requests

### Common Issues

**Issue:** "401 Unauthorized" or "Invalid token"
- **Solution:** Make sure token is copied correctly without quotes
- Check: `Authorization: Bearer @token_name` (use @ not {})

**Issue:** "403 Forbidden" on ALLOWED endpoint
- **Solution:** User role might not have permission
- Check access levels table above
- Verify you're using correct token

**Issue:** Token variable not recognized
- **Solution:** Make sure token setup is at the top of the file
- Format should be: `@variable_name = token_value`
- No quotes around the token

### Notes

- Server runs on `http://localhost:8080`
- All requests return JSON responses
- Failed requests include error details
- Tokens expire after configured duration (check security config)
- Each file has its own token setup section for convenience
