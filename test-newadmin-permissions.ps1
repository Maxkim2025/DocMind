# 测试新管理员用户的权限

# 测试环境信息
$baseUrl = "http://localhost:8081"

# 使用之前获取的token
$token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJuZXdhZG1pbjpBRE1JTiIsImF1dGhvcml0aWVzIjoiQURNSU4iLCJpYXQiOjE3NzMwNjc0NzMsImV4cCI6MTc3MzE1Mzg3M30.28oTS98FKaqtMMqTbDV3V5yk6UKI3H8BWuesgQ1LqV0"

# 测试访问管理员接口
Write-Host "测试访问管理员接口 (/user/admin):"
try {
    $response = Invoke-WebRequest -Uri "$baseUrl/user/admin" -Method GET -Headers @{"Accept"="application/json"; "Authorization"="Bearer $token"} -UseBasicParsing
    Write-Host "状态码: $($response.StatusCode)"
    Write-Host "响应: $($response.Content)"
} catch {
    Write-Host "测试失败:"
    Write-Host "状态码: $($_.Exception.Response.StatusCode)"
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

# 测试访问普通用户接口
Write-Host "\n测试访问普通用户接口 (/user/user):"
try {
    $response = Invoke-WebRequest -Uri "$baseUrl/user/user" -Method GET -Headers @{"Accept"="application/json"; "Authorization"="Bearer $token"} -UseBasicParsing
    Write-Host "状态码: $($response.StatusCode)"
    Write-Host "响应: $($response.Content)"
} catch {
    Write-Host "测试失败:"
    Write-Host "状态码: $($_.Exception.Response.StatusCode)"
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

# 测试访问用户列表接口
Write-Host "\n测试访问用户列表接口 (/user):"
try {
    $response = Invoke-WebRequest -Uri "$baseUrl/user" -Method GET -Headers @{"Accept"="application/json"; "Authorization"="Bearer $token"} -UseBasicParsing
    Write-Host "状态码: $($response.StatusCode)"
    Write-Host "响应: $($response.Content)"
} catch {
    Write-Host "测试失败:"
    Write-Host "状态码: $($_.Exception.Response.StatusCode)"
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
