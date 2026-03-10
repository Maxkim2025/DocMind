# 检查管理员密码格式

# 测试环境信息
$baseUrl = "http://localhost:8081"

# 测试登录，获取详细错误信息
Write-Host "测试登录并获取详细错误信息:"
try {
    $loginData = @{
        username = "admin"
        password = "admin123"
    }
    $response = Invoke-WebRequest -Uri "$baseUrl/user/login" -Method POST -Headers @{"Content-Type"="application/json"} -Body ($loginData | ConvertTo-Json)
    
    Write-Host "登录成功!"
    Write-Host "状态码: $($response.StatusCode)"
    Write-Host "响应: $($response.Content)"
    
} catch {
    Write-Host "登录失败:"
    Write-Host "状态码: $($_.Exception.Response.StatusCode)"
    Write-Host "错误: $($_.Exception.Message)"
    Write-Host "响应: $($_.Exception.Response | Get-Member)"
    
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
