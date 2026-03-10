# 测试用户注册

# 测试环境信息
$baseUrl = "http://localhost:8081"

# 测试注册管理员用户
Write-Host "测试注册管理员用户:"
try {
    $userData = @{
        username = "admin"
        password = "admin123"
        email = "admin@example.com"
        phone = "13800138000"
        position = "Admin"
        gender = "male"
        role = "ADMIN"
    }
    $response = Invoke-RestMethod -Uri "$baseUrl/user" -Method POST -Headers @{"Content-Type"="application/json"} -Body ($userData | ConvertTo-Json)
    
    Write-Host "注册成功!"
    Write-Host "状态码: $($response.code)"
    Write-Host "用户信息: $($response.data | ConvertTo-Json -Depth 3)"
    
} catch {
    Write-Host "注册失败:"
    Write-Host "错误: $($_.Exception.Message)"
}
