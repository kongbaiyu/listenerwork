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
	if tostring(title) ~= "工商银行" then return money end
	local str = string.reverse(data)
	local substr = string.reverse("【工商银行】")
	print(string.find(str,substr))
	if string.find(str,substr) ~=1 then return money end
	local check=split(data,"工商银行收入(他行汇入)")
	if #check~=2 then return money end
	local check2=split(check[2],"余额")
	if #check2==2 then
		money = string.match(check2[1],"[%d.]+")
		return money
	end
	return money
end