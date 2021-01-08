function split(s, delim)
    if type(delim) ~= "string" or string.len(delim) <= 0 then return end
    local start = 1
    local t = { }
    while true do
        local pos = string.find(s, delim, start, true)
        if not pos then break end
        table.insert(t, string.sub(s, start, pos - 1))
        start = pos + string.len(delim)
    end
    table.insert(t, string.sub(s, start))
    return t
end
function mathdx(data,title)
	local money
	if tostring(title) ~= "95580" then return money end
	if string.find(data,"【邮储银行】") ~= 1 then return money end
	local check0 = split(data,"向您尾号")
	if #check0~=2 then return money end
	local check=split(check0[2],"账户他行来账金额")
	if #check~=2 then return money end
	local check2=split(check[2],"，余额")
	if #check2==2 then
		money = string.match(check2[1],"[%d.]+")
		return money
	end
end