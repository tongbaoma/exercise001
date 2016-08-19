# Bash
du：查询档案或目录的磁盘使用空间（du看的大小是计算实际使用的空间。）
    a：显示全部目录和其次目录下的每个档案所占的磁盘空间
    b：大小用bytes来表示 (默认值为k bytes)
    c：最后再加上总计 (默认值)
    s：只显示各档案大小的总合 (summarize)
    x：只计算同属同一个档案系统的档案
    L：计算所有的档案大小
ls:（显示的是文件的大小）
    -a 列出文件下所有的文件，包括以“.“开头的隐藏文件（linux下文件隐藏文件是以.开头的，如果存在..代表存在着父目录）。
    -l 列出文件的详细信息，如创建者，创建时间，文件的读写权限列表等等。
    -F 在每一个文件的末尾加上一个字符说明该文件的类型。"@"表示符号链接、"|"表示FIFOS、"/"表示目录、"="表示套接字。
    -s 在每个文件的后面打印出文件的大小。  size(大小)
    -t 按时间进行文件的排序  Time(时间)
    -A 列出除了"."和".."以外的文件。
    -R 将目录下所有的子目录的文件都列出来，相当于我们编程中的“递归”实现
    -L 列出文件的链接名。Link（链接）
    -S 以文件的大小进行排序
  
  注：取得某一个属性的时候可以和awk联合使用。（例：ls -l $filename | awk '{print $5}'）
