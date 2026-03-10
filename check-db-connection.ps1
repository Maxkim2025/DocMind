# 检查数据库连接和用户信息

# 测试环境信息
$baseUrl = "http://localhost:8081"

# 测试无需认证的接口
Write-Host "测试无需认证的接口:"
try {
    $response = Invoke-WebRequest -Uri "$baseUrl/hello" -Method GET -Headers @{"Accept"="application/json"} -UseBasicParsing
    Write-Host "状态码: $($response.StatusCode)"
    Write-Host "响应: $($response.Content)"
} catch {
    Write-Host "测试失败:"
    Write-Host "错误: $($_.Exception.Message)"
}

# 测试注册新用户
Write-Host "\n测试注册新用户:"
try {
    $userData = @{
        username = "newadmin"
        password = "admin123"
        email = "newadmin@example.com"
        phone = "13800138003"
        position = "Admin"
        gender = "male"
        role = "ADMIN"
    }
    $response = Invoke-WebRequest -Uri "$baseUrl/user" -Method POST -Headers @{"Content-Type"="application/json"} -Body ($userData | ConvertTo-Json) -UseBasicParsing
    Write-Host "状态码: $($response.StatusCode)"
    Write-Host "响应: $($response.Content)"
} catch {
    Write-Host "测试失败:"
    Write-Host "错误: $($_.Exception.Message)"
}

# 测试登录新用户
Write-Host "\n测试登录新用户:"
try {
    $loginData = @{
        username = "newadmin"
        password = "admin123"
    }
    $response = Invoke-WebRequest -Uri "$baseUrl/user/login" -Method POST -Headers @{"Content-Type"="application/json"} -Body ($loginData | ConvertTo-Json) -UseBasicParsing
    Write-Host "状态码: $($response.StatusCode)"
    Write-Host "响应: $($response.Content)"
} catch {
    Write-Host "测试失败:"
    Write-Host "错误: $($_.Exception.Message)"
}
