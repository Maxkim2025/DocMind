# 测试管理员登录和权限

# 测试环境信息
$baseUrl = "http://localhost:8081"

# 测试管理员登录
Write-Host "测试管理员登录:"
try {
    $loginData = @{
        username = "admin"
        password = "admin123"
    }
    $response = Invoke-RestMethod -Uri "$baseUrl/user/login" -Method POST -Headers @{"Content-Type"="application/json"} -Body ($loginData | ConvertTo-Json)
    
    Write-Host "登录成功!"
    Write-Host "状态码: $($response.code)"
    Write-Host "消息: $($response.message)"
    Write-Host "角色: $($response.data.role)"
    Write-Host "Token: $($response.data.token)"
    
    # 保存Token用于后续测试
    $adminToken = $response.data.token
    
    # 测试管理员访问管理员接口
    Write-Host "\n测试管理员访问管理员接口:"
    $adminResponse = Invoke-RestMethod -Uri "$baseUrl/user/admin" -Method GET -Headers @{"Accept"="application/json"; "Authorization"="Bearer $adminToken"}
    Write-Host "访问成功!"
    Write-Host "响应: $($adminResponse | ConvertTo-Json -Depth 3)"
    
    # 测试管理员访问普通用户接口
    Write-Host "\n测试管理员访问普通用户接口:"
    $userResponse = Invoke-RestMethod -Uri "$baseUrl/user/user" -Method GET -Headers @{"Accept"="application/json"; "Authorization"="Bearer $adminToken"}
    Write-Host "访问成功!"
    Write-Host "响应: $($userResponse | ConvertTo-Json -Depth 3)"
    
} catch {
    Write-Host "测试失败:"
    Write-Host "错误: $($_.Exception.Message)"
}
