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
function mathhtml(data)
	local mmoney=""
	local mtype ="NA"
	local title=""
	local result = string.format('{"type":"%s","money":"%s","title":"%s"}',mtype,mmoney,title)
	local checkdata = split(data, ">闲鱼商品不支持7天无理由<")
	if #checkdata~=2 then return result end
	local m,n
	local i,j = string.find(data, ">聊一聊<")
	if j~=nil then 
		m,n = string.find(data,"<span",j)
	else
		return result
	end
	if n~=nil then
		i,j = string.find(data,"</span>",n)
	else
		return result
	end
	local checktype = string.sub(data,n,i)
	if j~=nil then
		if string.match(checktype,"立即购买") then
			mtype = "FAIL"
		elseif string.match(checktype,"卖掉了") then
			mtype = "OK"
		elseif string.match(checktype,"已下架") then
			mtype = "INV"	
		end
	else
		return result
	end
	local check = split(checkdata[1],">￥</span>")
	if #check~=2 then return result end
	local checkm = string.match(check[2],"[%>%d.]+%<")
	if checkm~=nil then
		mmoney = string.match(checkm,"[%d.]+")
	end
	
	local checktitle = checkdata[2]
	local a1,a2 = string.find(checktitle,"overflow: hidden;\">")
	print(a1,a2)
	local b1,b2
	if a2~=nil then
		b1,b2 = string.find(checktitle,"</span></div>",a2)
		print(b1,b2)
	end
	title = string.sub(checktitle,a2+1,b1-1)
	print(title)
	title = string.gsub(title," ","")
	result = string.format('{"type":"%s","money":"%s","title":"%s"}',mtype,mmoney,title)
	print(result)
	return result
end