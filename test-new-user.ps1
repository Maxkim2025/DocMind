# 测试创建新用户并登录

# 测试环境信息
$baseUrl = "http://localhost:8081"

# 测试创建新用户
Write-Host "测试创建新用户:"
try {
    $userData = @{
        username = "testuser"
        password = "test123"
        email = "test@example.com"
        phone = "13800138002"
        position = "Test User"
        gender = "male"
        role = "USER"
    }
    $response = Invoke-WebRequest -Uri "$baseUrl/user" -Method POST -Headers @{"Content-Type"="application/json"} -Body ($userData | ConvertTo-Json) -UseBasicParsing
    
    Write-Host "创建成功!"
    Write-Host "状态码: $($response.StatusCode)"
    Write-Host "响应: $($response.Content)"
    
    # 测试登录
    Write-Host "\n测试登录:"
    $loginData = @{
        username = "testuser"
        password = "test123"
    }
    $loginResponse = Invoke-WebRequest -Uri "$baseUrl/user/login" -Method POST -Headers @{"Content-Type"="application/json"} -Body ($loginData | ConvertTo-Json) -UseBasicParsing
    
    Write-Host "登录成功!"
    Write-Host "状态码: $($loginResponse.StatusCode)"
    Write-Host "响应: $($loginResponse.Content)"
    
} catch {
    Write-Host "测试失败:"
    Write-Host "错误: $($_.Exception.Message)"
    
    # 尝试获取响应内容
    try {
        $reader = New-Object System.IO.StreamReader($_.Exception.Response.GetResponseStream())
        $responseBody = $reader.ReadToEnd()
        $reader.Close()
        Write-Host "响应内容: $responseBody"
    } catch {
        Write-Host "无法获取响应内容: $($_.Exception.Message)"
    }
}
