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
	if tostring(title) ~= "95566" then return money end
	if string.find(data,"您的借记卡账户") ~= 1 then return money end
	local str = string.reverse(data)
	local substr = string.reverse("【中国银行】")
	if string.find(str,substr) ~=1 then return money end
	local check=split(data,"收入(网银跨行)人民币")
	if #check~=2 then return money end
	local check2=split(check[2],"元，交易后余额")
	if #check2==2 then
		money = string.match(check2[1],"[%d.]+")
		return money
	end
end