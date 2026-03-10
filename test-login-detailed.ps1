# 详细测试登录功能

# 测试环境信息
$baseUrl = "http://localhost:8081"

# 测试登录
Write-Host "测试登录:"

# 准备登录数据
$loginData = @{
    username = "admin"
    password = "admin123"
}

# 转换为JSON
$jsonData = $loginData | ConvertTo-Json

# 执行登录请求
try {
    $response = Invoke-WebRequest -Uri "$baseUrl/user/login" -Method POST -Headers @{"Content-Type"="application/json"} -Body $jsonData -UseBasicParsing
    
    Write-Host "登录成功!"
    Write-Host "状态码: $($response.StatusCode)"
    Write-Host "响应: $($response.Content)"
    
    # 解析响应
    $responseObj = $response.Content | ConvertFrom-Json
    
    if ($responseObj.code -eq 200) {
        Write-Host "登录成功!"
        Write-Host "角色: $($responseObj.data.role)"
        Write-Host "Token: $($responseObj.data.token)"
        
        # 保存Token用于后续测试
        $adminToken = $responseObj.data.token
        
        # 测试管理员访问管理员接口
        Write-Host "\n测试管理员访问管理员接口:"
        $adminResponse = Invoke-WebRequest -Uri "$baseUrl/user/admin" -Method GET -Headers @{"Accept"="application/json"; "Authorization"="Bearer $adminToken"} -UseBasicParsing
        Write-Host "状态码: $($adminResponse.StatusCode)"
        Write-Host "响应: $($adminResponse.Content)"
        
        # 测试管理员访问普通用户接口
        Write-Host "\n测试管理员访问普通用户接口:"
        $userResponse = Invoke-WebRequest -Uri "$baseUrl/user/user" -Method GET -Headers @{"Accept"="application/json"; "Authorization"="Bearer $adminToken"} -UseBasicParsing
        Write-Host "状态码: $($userResponse.StatusCode)"
        Write-Host "响应: $($userResponse.Content)"
    } else {
        Write-Host "登录失败:"
        Write-Host "错误码: $($responseObj.code)"
        Write-Host "错误消息: $($responseObj.message)"
    }
    
} catch {
    Write-Host "登录失败:"
    Write-Host "状态码: $($_.Exception.Response.StatusCode)"
    Write-Host "错误: $($_.Exception.Message)"
    
    # 尝试获取响应内容
    try {
        $reader = New-Object System.IO.StreamReader($_.Exception.Response.GetResponseStream())
        $responseBody = $reader.ReadToEnd()
        $reader.Close()
        Write-Host "响应内容: $responseBody"
        
        # 解析响应
        $responseObj = $responseBody | ConvertFrom-Json
        Write-Host "错误码: $($responseObj.code)"
        Write-Host "错误消息: $($responseObj.message)"
    } catch {
        Write-Host "无法获取响应内容: $($_.Exception.Message)"
    }
}
