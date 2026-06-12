# 🔐 Endpoint Permissions (RBAC)

Access to the API is restricted based on user roles embedded in the JWT token.

| Controller | Endpoint | Method | Required Role |
| :--- | :--- | :--- | :--- |
| **Auth** | `/api/auth/register` | `POST` | Public |
| **Auth** | `/api/auth/login` | `POST` | Public |
| **User** | `/api/user/create` | `POST` | `ROLE_ADMIN` |
| **User** | `/api/user/read/all` | `GET` | `ROLE_ADMIN` |
| **User** | `/api/user/id/{userId}` | `GET` | `ROLE_ADMIN` |
| **User** | `/api/user/read/me` | `GET` | Authenticated (Self) |
| **User** | `/api/user/update` | `PATCH` | Authenticated (Self) |
| **User** | `/api/user/delete/{userId}` | `DELETE` | `ROLE_ADMIN` |
| **User** | `/api/user/delete/my/user` | `DELETE` | Authenticated (Self) |
| **Roles** | `/api/roles/promote/admin/{userId}` | `PATCH` | `ROLE_ADMIN` |
| **Roles** | `/api/roles/demote/admin/{userId}` | `DELETE` | `ROLE_ADMIN` |

---

### 📝 Implementation Notes

* **Prefixes:** All endpoints follow the `/api` base prefix as defined in the RestControllers.
* **"Self" Logic:** Endpoints such as `/read/me`, `/update`, and `/delete/my/user` leverage the authenticated security context, allowing users to manage their own data regardless of specific roles.
* **Hierarchy:** Operations that modify other user accounts or elevate privileges (`promote`, `demote`, `delete/{userId}`) are strictly restricted to `ROLE_ADMIN`.

---

[🔙 Return to 📄 API Documentation (Swagger/OpenAPI)](/docs/api-doc.md)
