# RBAC权限测试脚本

# 测试环境信息
$baseUrl = "http://localhost:8081"

Write-Host "=== RBAC权限控制测试开始 ==="

# 1. 测试无需认证的接口
Write-Host "\n1. 测试无需认证的接口:"
Write-Host "测试 /hello 接口:"
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/hello" -Method GET -Headers @{"Accept"="application/json"} -ErrorAction Stop
    Write-Host "状态: 成功"
    Write-Host "响应: $($response | ConvertTo-Json -Depth 3)"
} catch {
    Write-Host "状态: 失败"
    Write-Host "错误: $($_.Exception.Message)"
}

# 2. 创建测试用户
Write-Host "\n2. 创建测试用户:"

# 创建管理员用户
Write-Host "创建管理员用户:"
try {
    $adminUser = @{
        username = "admin"
        password = "123456"
        email = "admin@example.com"
        phone = "13800138000"
        position = "Admin"
        gender = "male"
        role = "ADMIN"
    }
    $response = Invoke-RestMethod -Uri "$baseUrl/user" -Method POST -Headers @{"Content-Type"="application/json"} -Body ($adminUser | ConvertTo-Json) -ErrorAction Stop
    Write-Host "状态: 成功"
    Write-Host "响应: $($response | ConvertTo-Json -Depth 3)"
} catch {
    Write-Host "状态: 失败"
    Write-Host "错误: $($_.Exception.Message)"
}

# 创建普通用户
Write-Host "\n创建普通用户:"
try {
    $userUser = @{
        username = "user"
        password = "123456"
        email = "user@example.com"
        phone = "13800138001"
        position = "User"
        gender = "female"
        role = "USER"
    }
    $response = Invoke-RestMethod -Uri "$baseUrl/user" -Method POST -Headers @{"Content-Type"="application/json"} -Body ($userUser | ConvertTo-Json) -ErrorAction Stop
    Write-Host "状态: 成功"
    Write-Host "响应: $($response | ConvertTo-Json -Depth 3)"
} catch {
    Write-Host "状态: 失败"
    Write-Host "错误: $($_.Exception.Message)"
}

# 3. 测试登录功能
Write-Host "\n3. 测试登录功能:"

# 管理员登录
Write-Host "管理员登录:"
try {
    $loginData = @{
        username = "admin"
        password = "123456"
    }
    $response = Invoke-RestMethod -Uri "$baseUrl/user/login" -Method POST -Headers @{"Content-Type"="application/json"} -Body ($loginData | ConvertTo-Json) -ErrorAction Stop
    $adminToken = $response.data.token
    $adminRole = $response.data.role
    Write-Host "状态: 成功"
    Write-Host "角色: $adminRole"
    Write-Host "Token: $adminToken"
} catch {
    Write-Host "状态: 失败"
    Write-Host "错误: $($_.Exception.Message)"
}

# 普通用户登录
Write-Host "\n普通用户登录:"
try {
    $loginData = @{
        username = "user"
        password = "123456"
    }
    $response = Invoke-RestMethod -Uri "$baseUrl/user/login" -Method POST -Headers @{"Content-Type"="application/json"} -Body ($loginData | ConvertTo-Json) -ErrorAction Stop
    $userToken = $response.data.token
    $userRole = $response.data.role
    Write-Host "状态: 成功"
    Write-Host "角色: $userRole"
    Write-Host "Token: $userToken"
} catch {
    Write-Host "状态: 失败"
    Write-Host "错误: $($_.Exception.Message)"
}

# 4. 测试权限控制
Write-Host "\n4. 测试权限控制:"

# 管理员访问管理员接口
Write-Host "管理员访问管理员接口 (/user/admin):"
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/user/admin" -Method GET -Headers @{"Accept"="application/json"; "Authorization"="Bearer $adminToken"} -ErrorAction Stop
    Write-Host "状态: 成功"
    Write-Host "响应: $($response | ConvertTo-Json -Depth 3)"
} catch {
    Write-Host "状态: 失败"
    Write-Host "错误: $($_.Exception.Message)"
}

# 管理员访问普通用户接口
Write-Host "\n管理员访问普通用户接口 (/user/user):"
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/user/user" -Method GET -Headers @{"Accept"="application/json"; "Authorization"="Bearer $adminToken"} -ErrorAction Stop
    Write-Host "状态: 成功"
    Write-Host "响应: $($response | ConvertTo-Json -Depth 3)"
} catch {
    Write-Host "状态: 失败"
    Write-Host "错误: $($_.Exception.Message)"
}

# 普通用户访问管理员接口
Write-Host "\n普通用户访问管理员接口 (/user/admin):"
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/user/admin" -Method GET -Headers @{"Accept"="application/json"; "Authorization"="Bearer $userToken"} -ErrorAction Stop
    Write-Host "状态: 成功"
    Write-Host "响应: $($response | ConvertTo-Json -Depth 3)"
} catch {
    Write-Host "状态: 失败"
    Write-Host "错误: $($_.Exception.Message)"
}

# 普通用户访问普通用户接口
Write-Host "\n普通用户访问普通用户接口 (/user/user):"
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/user/user" -Method GET -Headers @{"Accept"="application/json"; "Authorization"="Bearer $userToken"} -ErrorAction Stop
    Write-Host "状态: 成功"
    Write-Host "响应: $($response | ConvertTo-Json -Depth 3)"
} catch {
    Write-Host "状态: 失败"
    Write-Host "错误: $($_.Exception.Message)"
}

# 5. 测试未授权访问
Write-Host "\n5. 测试未授权访问:"

# 无令牌访问受保护接口
Write-Host "无令牌访问受保护接口 (/user):"
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/user" -Method GET -Headers @{"Accept"="application/json"} -ErrorAction Stop
    Write-Host "状态: 成功"
    Write-Host "响应: $($response | ConvertTo-Json -Depth 3)"
} catch {
    Write-Host "状态: 失败"
    Write-Host "错误: $($_.Exception.Message)"
}

# 6. 测试错误情况
Write-Host "\n6. 测试错误情况:"

# 错误用户名登录
Write-Host "错误用户名登录:"
try {
    $loginData = @{
        username = "wronguser"
        password = "123456"
    }
    $response = Invoke-RestMethod -Uri "$baseUrl/user/login" -Method POST -Headers @{"Content-Type"="application/json"} -Body ($loginData | ConvertTo-Json) -ErrorAction Stop
    Write-Host "状态: 成功"
    Write-Host "响应: $($response | ConvertTo-Json -Depth 3)"
} catch {
    Write-Host "状态: 失败"
    Write-Host "错误: $($_.Exception.Message)"
}

# 错误密码登录
Write-Host "\n错误密码登录:"
try {
    $loginData = @{
        username = "admin"
        password = "wrongpassword"
    }
    $response = Invoke-RestMethod -Uri "$baseUrl/user/login" -Method POST -Headers @{"Content-Type"="application/json"} -Body ($loginData | ConvertTo-Json) -ErrorAction Stop
    Write-Host "状态: 成功"
    Write-Host "响应: $($response | ConvertTo-Json -Depth 3)"
} catch {
    Write-Host "状态: 失败"
    Write-Host "错误: $($_.Exception.Message)"
}

# 空用户名登录
Write-Host "\n空用户名登录:"
try {
    $loginData = @{
        username = ""
        password = "123456"
    }
    $response = Invoke-RestMethod -Uri "$baseUrl/user/login" -Method POST -Headers @{"Content-Type"="application/json"} -Body ($loginData | ConvertTo-Json) -ErrorAction Stop
    Write-Host "状态: 成功"
    Write-Host "响应: $($response | ConvertTo-Json -Depth 3)"
} catch {
    Write-Host "状态: 失败"
    Write-Host "错误: $($_.Exception.Message)"
}

# 空密码登录
Write-Host "\n空密码登录:"
try {
    $loginData = @{
        username = "admin"
        password = ""
    }
    $response = Invoke-RestMethod -Uri "$baseUrl/user/login" -Method POST -Headers @{"Content-Type"="application/json"} -Body ($loginData | ConvertTo-Json) -ErrorAction Stop
    Write-Host "状态: 成功"
    Write-Host "响应: $($response | ConvertTo-Json -Depth 3)"
} catch {
    Write-Host "状态: 失败"
    Write-Host "错误: $($_.Exception.Message)"
}

Write-Host "\n=== RBAC权限控制测试完成 ==="
