# 実行ディレクトリ
THIS="$0"
#BASEDIR=`dirname "$THIS"`
BASEDIR="/home/zhudantest/test/*.txt"

# Levelを判断する
_calculateLevel(){
  if [ $1 -le 15 ] && [ $1 -ge 0 ];then
     level="lamb"
  elif [ $1 -le 30 ] && [ $1 -ge 16 ]; then
     level="sheep"
  elif [ $1 -le 45 ] && [ $1 -ge 31 ]; then
     level="goat"
  elif [ $1 -le 60 ] && [ $1 -ge 46 ]; then
      level="babyWolf"
  elif [ $1 -le 75 ] && [ $1 -ge 61 ]; then
     level="adultWolf"
  elif [ $1 -le 90 ] && [ $1 -ge 76 ]; then
     level="strongWolf"
  elif [ $1 -gt 91 ]; then
     level="KingWolf"
  fi
}

for file in `ls $BASEDIR`
do
  score=0
  level="lamb"
  while read LINE
  do
    _calculateLevel $score
    victory_flag=`echo "$LINE" | awk '{print $2}' `
    if [ "$victory_flag" == '"1"' ] ; then
      if [ "$level" == "lamb" ]; then
	score=`expr $score + 7`  
      elif [ "$level" == "sheep" ]; then
	score=`expr $score + 6` 
      elif [ "$level" == "goat" ]; then
	score=`expr $score + 5` 
      elif [ "$level" == "babyWolf" ]; then
	score=`expr $score + 4` 
      elif [ "$level" == "adultWolf" ]; then
	score=`expr $score + 3` 
      elif [ "$level" == "strongWolf" ]; then
	score=`expr $score + 2` 
      elif [ "$level" == "KingWolf" ]; then
	score=`expr $score + 1` 
      fi
    else
      if [ "$level" == "lamb" ]; then
	score=`expr $score - 4` 
      elif [ "$level" == "sheep" ]; then
	score=`expr $score - 4` 
      elif [ "$level" == "goat" ]; then
	score=`expr $score - 3` 
      elif [ "$level" == "babyWolf" ]; then
	score=`expr $score - 3` 
      elif [ "$level" == "adultWolf" ]; then
	score=`expr $score - 2` 
      elif [ "$level" == "strongWolf" ]; then
	score=`expr $score - 2` 
      elif [ "$level" == "KingWolf" ]; then
	score=`expr $score - 3` 
      fi
    fi
  done < $file
  echo "$score        $level       $file           "
done

