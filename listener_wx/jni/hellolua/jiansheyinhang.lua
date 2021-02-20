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
	if tostring(title) ~= "95533" then return money end
	local str = string.reverse(data)
	local substr = string.reverse("建设银行")
	if string.find(str,substr) ~=2 then return money end
	local check=split(data,"储蓄卡账户电子汇入收入人民币")
	local err = false
	if #check~=2 then err = true end
	if err == true then 
		check = split(data,"储蓄卡账户转账收入人民币")
		err = false
	end
	if #check~=2 then err = true end
	if err == true then return money end
	local check2=split(check[2],"活期余额")
	if #check2==2 then
		money = string.match(check2[1],"[%d.]+")
		return money
	end
end