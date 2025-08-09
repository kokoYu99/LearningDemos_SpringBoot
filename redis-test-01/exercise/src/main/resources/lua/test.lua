-- 此脚本逻辑：
-- 1. 获取key的value，
-- 2.1 如果value值为arg1，将其改为arg2，并返回true
-- 2.2 如果value值不为arg1，返回false
local current = redis.call('GET', KEYS[1])
if current == ARGV[1]
then
    redis.call('SET', KEYS[1], ARGV[2])
    return true
end
    return false