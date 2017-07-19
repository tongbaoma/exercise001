#!/bin/sh

#######################################
##########显示时间#######################
#######################################
# 当前时间
echo `date -d today`
echo `date -d now`

# 时间的加减法
echo `date -d yesterday`
echo `date -d "-1 day"
echo `date -d "-1 days"`
echo `date -d "1 day ago"`
echo `date -d "1 days ago"`
echo `date --d="-1 day"`
echo `date --d="-1 days"`
echo `date --date="-1 day"`
echo `date --date="-1 days"`
echo `date --date="-1 week"`
echo `date --date="-1 weeks"`
echo `date --date="-1 month"`
echo `date --date="-1 months"`
echo `date --date="-1 year"`
echo `date --date="-1 years"`
echo `date -d tomorrow`

# 时间格式
echo `date '+%Y-%m-%d`
echo `date '+%F %T'`
echo `date '+%Y-%m-%d %H:%M:%S'`
echo `date --date="-1 year -1 month -1 day" '+%Y-%m-%d %H:%M:%S'`
echo `date '+%F %T'`

# 设置时间
date -s "20170421 15:36:30"