-- KEYS[1] = rate limit key
-- ARGV[1] = max requests
-- ARGV[2] = window in seconds

local current = redis.call("INCR", KEYS[1])

if current == 1 then
    redis.call("EXPIRE", KEYS[1], ARGV[2])
end

if current > tonumber(ARGV[1]) then
    return 0
end

return 1
