# IssueTracker MVP - Requirements & Entities

## Project Name
**IssueTrackerServer** (Spring Boot backend) + **IssueTrackerClient** (React frontend)

---

## Project Summary
IssueTracker is a simple issue Reporting System.
Users can report issues, and admins (management) can view and resolve them.

### MVP Scope
- Backend: Spring Boot + JPA + H2 (or Postgres)
- Frontend: React
- Users: Tenant/Student (USER) and Admin (ADMIN)
- Authentication: Simple username/password login & register
- CRUD: Users can create/edit/delete their issues; Admins can update status
- Database: Minimal with two main entities (User, Issue)

---

## Entities

### 1. User
| Field | Type | Notes |
|-------|------|-------|
| id | Long | Primary Key, auto-generated |
| username | String | Unique, required |
| password | String | Required |
| email | String | Required |
| role | Enum/String | "USER" or "ADMIN" |

### 2. Issue
| Field | Type | Notes |
|-------|------|-------|
| id | Long | Primary Key, auto-generated |
| title | String | Short description of the issue |
| description | String | Detailed description |
| status | Enum/String | "PENDING" or "RESOLVED" |
| createdAt | LocalDateTime | Auto-set on creation |
| updatedAt | LocalDateTime | Auto-update on changes |
| reportedBy | User | Many-to-One relationship (issue reporter) |

---

## Relationships
- One **User** can report many **Issues**.
- Each **Issue** belongs to exactly one **User**.
- Admins do not report issues but can update the `status`.

---

## API Endpoints (Suggested)

### User Endpoints
- POST `/api/users/register` → Register new user
- POST `/api/users/login` → Login user
- GET `/api/users` → List all users (ADMIN only)
- DELETE `/api/users/{id}` → Delete a user (ADMIN only)

### Issue Endpoints
- GET `/api/issues` → List all issues (ADMIN) or own issues (USER)
- GET `/api/issues/{id}` → Get issue details
- POST `/api/issues` → Create new issue
- PUT `/api/issues/{id}` → Update issue (USER can edit own; ADMIN can update status)
- DELETE `/api/issues/{id}` → Delete issue (USER own only; ADMIN can delete any)

---

## Frontend Features
- Login/Register pages
- Dashboard showing user issues (table/list)
- Form to create/edit issue
- Admin dashboard to view all issues and update status
- Optional: logout functionality, basic styling, and status filter

---

## Suggested Tech Stack
- **Backend**: Spring Boot, Spring Web, Spring JPA, H2 or Postgres, Maven
- **Frontend**: React, Axios/Fetch for API calls, React Router (optional)
- **Version Control**: GitHub

---

## Optional Enhancements
- Logging (service layer)
- Validation for input fields
- Test suites for service layer
- Responsive frontend UI

