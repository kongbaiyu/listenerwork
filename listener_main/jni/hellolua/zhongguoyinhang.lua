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
	if tostring(title) ~= "中国银行" then return money end
	local checkt = split(data,"中国银行")
	if #checkt ~=3 then return money end
	local check=split(data,"收入(网银跨行)人民币")
	if #check~=2 then return money end
	local check2=split(check[2],"交易后余额")
	if #check2==2 then
		money = string.match(check2[1],"[%d.]+")
		return money
	end
end