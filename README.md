
| HTTP METHOD | Endpoint | Acesses | Role |
| --------|--------|--------|------|
| POST | auth/users/register | Cell 3 |  |
| GET | auth/users/verify | Cell 3 |  |
| POST | auth/users/login | Cell 3 |  |
| POST | auth/users/change-password | Cell 3 |  |
| POST | auth/users/forget-password | Cell 3 |  |
| POST | auth/users/reset-password | Cell 3 |  |
| POST | auth/users/inactivate | Cell 3 |  |
| POST | auth/users/reactivate | Cell 3 |  |
| POST | auth/users/update-role | Cell 3 |  |
| POST | auth/users/update-profile | Cell 3 |  |
| POST | auth/users/update-profile-picture | Cell 3 |  |
| GET | api/branch/{branchId}/transports | Cell 3 |  |
| GET | api/transports/{transportId} | private | ALL |
| GET | api/branch/{branchId}/transports/{transportId} | private | ALL |
| POST | api/branch/{branchId}/transports | private | MANAGER/ADMIN |
| GET | api/branch/{branchId}/transports | private | MANAGER/ADMIN |
| PUT | api/branch/{branchId}/transports/{transportId} | private | MANAGER/ADMIN |
| DELETE | api/branch/{branchId}/transports/{transportId} | private | MANAGER/ADMIN |
