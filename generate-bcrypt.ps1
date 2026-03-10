# 生成BCrypt加密密码
Add-Type -AssemblyName System.Web

# 生成BCrypt加密的密码
$password = "admin123"
$bcryptPassword = [System.Web.Security.FormsAuthentication]::HashPasswordForStoringInConfigFile($password, "BCRYPT")

Write-Host "原始密码: $password"
Write-Host "BCrypt加密密码: $bcryptPassword"

# 生成SQL语句
Write-Host "\nSQL更新语句:"
Write-Host "UPDATE \"user\" SET password = '$bcryptPassword' WHERE username = 'admin';"
