
<table>
  <tr>
    <th>HTTP METHOD</th>
    <th>Endpoint</th>
    <th>Accesses</th>
    <th>Role</th>
  </tr>
  <tr><td colspan="4" align="center"><strong>🧑🏻‍🚒 User & Profile Endpoints</strong></td></tr>
  <tr><td>POST</td><td>auth/users/register</td><td>puplic</td><td></td></tr>
  <tr><td>GET</td><td>auth/users/verify</td><td>puplic</td><td></td></tr>
  <tr><td>POST</td><td>auth/users/login</td><td>puplic</td><td></td></tr>
  <tr><td>POST</td><td>auth/users/change-password</td><td>private</td><td></td></tr>
  <tr><td>POST</td><td>auth/users/forget-password</td><td>puplic</td><td></td></tr>
  <tr><td>POST</td><td>auth/users/reset-password</td><td>puplic</td><td></td></tr>
  <tr><td>POST</td><td>auth/users/inactivate</td><td>private</td><td></td></tr>
  <tr><td>POST</td><td>auth/users/reactivate</td><td>private</td><td></td></tr>
  <tr><td>POST</td><td>auth/users/update-role</td><td>private</td><td></td></tr>
  <tr><td>POST</td><td>auth/users/update-profile</td><td>private</td><td></td></tr>
  <tr><td>POST</td><td>auth/users/update-profile-picture</td><td>private</td><td></td></tr>

  <tr><td colspan="4" align="center"><strong>🚒 Transport Endpoints</strong></td></tr>
  <tr><td>GET</td><td>api/transports</td><td>private</td><td></td></tr>
  <tr><td>GET</td><td>api/branch/{branchId}/transports</td><td>private</td><td>ALL</td></tr>
  <tr><td>GET</td><td>api/branch/{branchId}/transports/{transportId}</td><td>private</td><td>ALL</td></tr>
  <tr><td>POST</td><td>api/branch/{branchId}/transports</td><td>private</td><td>MANAGER/ADMIN</td></tr>
  <tr><td>PUT</td><td>api/branch/{branchId}/transports/{transportId}</td><td>private</td><td>MANAGER/ADMIN</td></tr>
  <tr><td>DELETE</td><td>api/branch/{branchId}/transports/{transportId}</td><td>private</td><td>MANAGER/ADMIN</td></tr>
  
  <tr><td colspan="4" align="center"><strong>🏢 Branch Endpoints</strong></td></tr>
  <tr><td>GET</td><td>api/branches</td><td>private</td><td>ALL</td></tr>
  <tr><td>GET</td><td>api/branches/{branchId}</td><td>private</td><td>ALL</td></tr>
  <tr><td>POST</td><td>api/branches</td><td>private</td><td>MANAGER/ADMIN</td></tr>
  <tr><td>PUT</td><td>api/branches/{branchId}</td><td>private</td><td>MANAGER/ADMIN</td></tr>
  <tr><td>DELETE</td><td>api/branches/{branchId}</td><td>private</td><td>ADMIN</td></tr>
  
  <tr><td colspan="4" align="center"><strong>🧯 Type Endpoints</strong></td></tr>
  <tr><td>GET</td><td>api/types</td><td>private</td><td>ALL</td></tr>
  <tr><td>GET</td><td>api/types/{typeId}</td><td>private</td><td>ALL</td></tr>
  <tr><td>POST</td><td>api/types</td><td>private</td><td>MANAGER/ADMIN</td></tr>
  <tr><td>PUT</td><td>api/types/{typeId}</td><td>private</td><td>MANAGER/ADMIN</td></tr>
  <tr><td>DELETE</td><td>api/types/{typeId}</td><td>private</td><td>ADMIN</td></tr>

  <tr><td colspan="4" align="center"><strong>☎️ contact Type</strong></td></tr>
  <tr><td>GET</td><td>api/contact-types</td><td>private</td><td>ALL</td></tr>
  <tr><td>GET</td><td>api/contact-types/{contactTypeId}</td><td>private</td><td>ALL</td></tr>
  <tr><td>POST</td><td>api/contact-types</td><td>private</td><td>MANAGER/ADMIN</td></tr>
  <tr><td>PUT</td><td>api/contact-types/{contactTypeId}</td><td>private</td><td>MANAGER/ADMIN</td></tr>
  <tr><td>DELETE</td><td>api/contact-types/{contactTypeId}</td><td>private</td><td>ADMIN</td></tr>
</table>



