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
	if tostring(title) ~= "95511" then return money end
	if string.find(data,"【平安银行】您存款账户") ~= 1 then return money end
	local check=split(data,"跨行转账转入人民币")
	if #check~=2 then return money end
	local check2=split(check[2],",活期余额人民币")
	if #check2==2 then
		money = string.match(check2[1],"[%d.]+")
		return money
	end
end