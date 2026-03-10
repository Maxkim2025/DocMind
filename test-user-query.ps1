# 测试用户查询

# 测试环境信息
$baseUrl = "http://localhost:8081"

# 测试查询管理员用户
Write-Host "测试查询管理员用户:"
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/user/query?username=admin" -Method GET -Headers @{"Accept"="application/json"}
    
    Write-Host "查询成功!"
    Write-Host "状态码: $($response.code)"
    Write-Host "用户信息: $($response.data | ConvertTo-Json -Depth 3)"
    
} catch {
    Write-Host "查询失败:"
    Write-Host "错误: $($_.Exception.Message)"
}
