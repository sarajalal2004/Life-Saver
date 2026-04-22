# A Firefighter Case Prioritization API

## 📌 Description
The Fire Case Prioritization API is a backend system built to help firefighters and emergency response teams manage and respond to fire incidents more effectively.  

The system collects fire case data and automatically caculate priority levels, estimated time needed and suggested branch to handle the case. This allows emergency teams to focus on the most critical situations first, improving response time and potentially saving lives and property.

---

## 🛠️ Tools & Technologies

- Programming Language: Java  
- Framework: Spring Boot  
- Database: MySQL  
- API Testing: Postman  
- Version Control: Git & GitHub  

---

## 🧠 Approach

The project was designed as a RESTful API using Spring Boot, focusing on clean architecture and scalability. The application follows a layered structure, separating concerns into controllers, services, and repositories. This made the code easier to maintain and extend.

To handle prioritization, a custom sorting logic was implemented with help of third party TomTom API. 
These attributes are evaluated and combined into a priority score, which determines the order in which cases are handled.

---

## 🚧 Challenges & Unsolved Problems

One of the main challenges was designing an effective priority algorithm that balances multiple factors without overcomplicating the system. Deciding how much weight each factor should have required careful consideration.

Unsolved or future improvements:
- Implementing UI authentication and role-based access for different users
- Hadling transfers between branches
- Allowing calcuating of suggested Transport to use

---

## 👥 User Stories

This system is designed for:
- Firefighters who need to quickly identify high-priority incidents
- Emergency management teams monitoring multiple cases

User Stories: https://github.com/your-username/fire-priority-api/wiki/user-stories

---

## 🗺️ ERD Diagram

The system includes entities such as FireCase, Location, and ResponseUnit, with relationships defining how incidents are tracked and managed.

ERD diagram: https://github.com/your-username/fire-priority-api/wiki/erd-diagram

---

## 📅 Planning & Development Process

The project was broken down into phases:
1. Requirement analysis and user story definition  
2. Database design and ERD creation  
3. API development (CRUD + prioritization logic)  
4. Testing and debugging  
5. Documentation  

Trello: https://github.com/your-username/fire-priority-api/projects

---

## ⚙️ Installation Instructions

Follow these steps to run the project locally:

```bash
# Clone the repository
git clone https://github.com/your-username/fire-priority-api.git


# Configure database in application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/fire_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```
---

## 🌐 API Endpoints

<table>
  <tr>
    <th>HTTP METHOD</th>
    <th>Endpoint</th>
    <th>Access</th>
    <th>Role</th>
  </tr>

  <!-- ================= USER ================= -->
  <tr><td colspan="4" align="center"><strong>🧑🏻‍🚒 User & Profile Endpoints</strong></td></tr>
  <tr><td>POST</td><td>/auth/users/register</td><td>Public</td><td>-</td></tr>
  <tr><td>GET</td><td>/auth/users/verify?token={token}</td><td>Public</td><td>-</td></tr>
  <tr><td>POST</td><td>/auth/users/login</td><td>Public</td><td>-</td></tr>
  <tr><td>POST</td><td>/auth/users/change-password</td><td>Private</td><td>ALL</td></tr>
  <tr><td>POST</td><td>/auth/users/forget-password</td><td>Public</td><td>-</td></tr>
  <tr><td>POST</td><td>/auth/users/reset-password?token={token}&newPassword={newPassword}</td><td>Public</td><td>-</td></tr>
  <tr><td>POST</td><td>/auth/users/inactivate</td><td>Private</td><td>ADMIN</td></tr>
  <tr><td>POST</td><td>/auth/users/reactivate</td><td>Private</td><td>ADMIN</td></tr>
  <tr><td>POST</td><td>/auth/users/update-role</td><td>Private</td><td>ADMIN</td></tr>
  <tr><td>PUT</td><td>/auth/users/update-profile?email={email}</td><td>Private</td><td>ALL</td></tr>
  <tr><td>POST</td><td>/auth/users/update-profile-picture</td><td>Private</td><td>ALL</td></tr>

  <!-- ================= TRANSPORT ================= -->
  <tr><td colspan="4" align="center"><strong>🚒 Transport Endpoints</strong></td></tr>
  <tr><td>GET</td><td>/api/transports</td><td>Private</td><td>ALL</td></tr>
  <tr><td>GET</td><td>/api/branches/{branchId}/transports</td><td>Private</td><td>ALL</td></tr>
  <tr><td>GET</td><td>/api/branches/{branchId}/transports/{transportId}</td><td>Private</td><td>ALL</td></tr>
  <tr><td>POST</td><td>/api/branches/{branchId}/transports</td><td>Private</td><td>MANAGER / ADMIN</td></tr>
  <tr><td>PUT</td><td>/api/branches/{branchId}/transports/{transportId}</td><td>Private</td><td>MANAGER / ADMIN</td></tr>
  <tr><td>DELETE</td><td>/api/branches/{branchId}/transports/{transportId}</td><td>Private</td><td>MANAGER / ADMIN</td></tr>

  <!-- ================= BRANCH ================= -->
  <tr><td colspan="4" align="center"><strong>🏢 Branch Endpoints</strong></td></tr>
  <tr><td>GET</td><td>/api/branches</td><td>Private</td><td>ALL</td></tr>
  <tr><td>GET</td><td>/api/branches/{branchId}</td><td>Private</td><td>ALL</td></tr>
  <tr><td>POST</td><td>/api/branches</td><td>Private</td><td>MANAGER / ADMIN</td></tr>
  <tr><td>PUT</td><td>/api/branches/{branchId}</td><td>Private</td><td>MANAGER / ADMIN</td></tr>
  <tr><td>DELETE</td><td>/api/branches/{branchId}</td><td>Private</td><td>ADMIN</td></tr>

  <!-- ================= TYPE ================= -->
  <tr><td colspan="4" align="center"><strong>🧯 Type Endpoints</strong></td></tr>
  <tr><td>GET</td><td>/api/types</td><td>Private</td><td>ALL</td></tr>
  <tr><td>GET</td><td>/api/types/{typeId}</td><td>Private</td><td>ALL</td></tr>
  <tr><td>POST</td><td>/api/types</td><td>Private</td><td>MANAGER / ADMIN</td></tr>
  <tr><td>PUT</td><td>/api/types/{typeId}</td><td>Private</td><td>MANAGER / ADMIN</td></tr>
  <tr><td>DELETE</td><td>/api/types/{typeId}</td><td>Private</td><td>ADMIN</td></tr>

  <!-- ================= CONTACT TYPE ================= -->
  <tr><td colspan="4" align="center"><strong>☎️ Contact Type Endpoints</strong></td></tr>
  <tr><td>GET</td><td>/api/contact-types</td><td>Private</td><td>ALL</td></tr>
  <tr><td>GET</td><td>/api/contact-types/{contactTypeId}</td><td>Private</td><td>ALL</td></tr>
  <tr><td>POST</td><td>/api/contact-types</td><td>Private</td><td>MANAGER / ADMIN</td></tr>
  <tr><td>PUT</td><td>/api/contact-types/{contactTypeId}</td><td>Private</td><td>MANAGER / ADMIN</td></tr>
  <tr><td>DELETE</td><td>/api/contact-types/{contactTypeId}</td><td>Private</td><td>ADMIN</td></tr>

  <!-- ================= CASE ================= -->
  <tr><td colspan="4" align="center"><strong>🔥 Case Endpoints</strong></td></tr>
  <tr><td>GET</td><td>/api/cases</td><td>Private</td><td>ALL</td></tr>
  <tr><td>GET</td><td>/api/cases/{caseId}</td><td>Private</td><td>ALL</td></tr>
  <tr><td>POST</td><td>/api/types/{typeId}/cases</td><td>Private</td><td>MANAGER / ADMIN</td></tr>
  <tr><td>PUT</td><td>/api/types/{typeId}/cases/{caseId}</td><td>Private</td><td>MANAGER / ADMIN</td></tr>
  <tr><td>DELETE</td><td>/api/types/{typeId}/cases/{caseId}</td><td>Private</td><td>ADMIN</td></tr>
  <tr><td>GET</td><td>/api/cases/{caseId}/start</td><td>Private</td><td>FIREFIGHTER / MANAGER</td></tr>
  <tr><td>GET</td><td>/api/cases/{caseId}/complete</td><td>Private</td><td>FIREFIGHTER / MANAGER</td></tr>

  <!-- ================= CONTACT ================= -->
  <tr><td colspan="4" align="center"><strong>📞 Contact Endpoints</strong></td></tr>
  <tr><td>GET</td><td>/api/contacts</td><td>Private</td><td>ALL</td></tr>
  <tr><td>GET</td><td>/api/cases/{caseId}/contacts</td><td>Private</td><td>ALL</td></tr>
  <tr><td>GET</td><td>/api/cases/{caseId}/contacts/{contactId}</td><td>Private</td><td>ALL</td></tr>
  <tr><td>POST</td><td>/api/cases/{caseId}/contactType/{contactTypeId}/contacts</td><td>Private</td><td>MANAGER / ADMIN</td></tr>
  <tr><td>PUT</td><td>/api/cases/{caseId}/contactType/{contactTypeId}/contacts/{contactId}</td><td>Private</td><td>MANAGER / ADMIN</td></tr>
  <tr><td>DELETE</td><td>/api/cases/{caseId}/contactType/{contactTypeId}/contacts/{contactId}</td><td>Private</td><td>ADMIN</td></tr>

  <!-- ================= HISTORY ================= -->
  <tr><td colspan="4" align="center"><strong>📜 History Group Endpoints</strong></td></tr>
  <tr><td>GET</td><td>/api/history-groups</td><td>Private</td><td>ALL</td></tr>
  <tr><td>GET</td><td>/api/cases/{caseId}/history-groups</td><td>Private</td><td>ALL</td></tr>
  <tr><td>GET</td><td>/api/cases/{caseId}/history-groups/{historyGroupId}</td><td>Private</td><td>ALL</td></tr>
  <tr><td>POST</td><td>/api/cases/{caseId}/branches/{branchId}/users/{userId}/history-groups</td><td>Private</td><td>MANAGER / ADMIN</td></tr>
  <tr><td>PUT</td><td>/api/history-groups/{historyGroupId}</td><td>Private</td><td>MANAGER / ADMIN</td></tr>
  <tr><td>DELETE</td><td>/api/cases/{caseId}/branches/{branchId}/users/{userId}/history-groups/{historyGroupId}</td><td>Private</td><td>ADMIN</td></tr>

</table>



