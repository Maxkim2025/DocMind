# 测试管理员登录
$loginData = @{
    username = "admin"
    password = "admin123"
}

try {
    $response = Invoke-RestMethod -Uri "http://localhost:8081/user/login" -Method POST -Headers @{"Content-Type"="application/json"} -Body ($loginData | ConvertTo-Json)
    
    Write-Host "管理员登录测试结果:"
    Write-Host "状态码: $($response.code)"
    Write-Host "消息: $($response.message)"
    Write-Host "角色: $($response.data.role)"
    Write-Host "Token: $($response.data.token)"
} catch {
    Write-Host "登录失败:"
    Write-Host "错误: $($_.Exception.Message)"
}
