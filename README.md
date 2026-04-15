
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
| GET | api/transports | Cell 3 |  |
| GET | api/branch/{branchId}/transports | private | ALL |
| GET | api/branch/{branchId}/transports/{transportId} | private | ALL |
| POST | api/branch/{branchId}/transports | private | MANAGER/ADMIN |
| PUT | api/branch/{branchId}/transports/{transportId} | private | MANAGER/ADMIN |
| DELETE | api/branch/{branchId}/transports/{transportId} | private | MANAGER/ADMIN |
| GET | api/branches | private | ALL |
| GET | api/branches/{branchId} | private | ALL |
| POST | api/branches | private | MANAGER/ADMIN |
| PUT | api/branches/{branchId} | private | MANAGER/ADMIN |
| DELETE | api/branches/{branchId} | private | ADMIN |
| GET | api/types | private | ALL |
| GET | api/types/{typeId} | private | ALL |
| POST | api/types | private | MANAGER/ADMIN |
| PUT | api/types/{typeId} | private | MANAGER/ADMIN |
| DELETE | api/types/{typeId} | private | ADMIN |

