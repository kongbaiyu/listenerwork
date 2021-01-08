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
    if title=="收款通知" or title=="收钱码" or title=="收钱码到账通知" then
        local check=split(data,"通过扫码向你付款")
		if #check~=2 then check=split(data,"成功收款") end
        if #check==2 then 
            local check2=split(check[2],"元")
            if #check2[2]==0 or #check2==2 then
                money = string.match(check[2],"[%d.]+")
                print(money)
            end
        end
    end
    return money
end