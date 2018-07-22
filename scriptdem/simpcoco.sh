#!/usr/bin/env bash

txt=$1

total=`grep -e "gaucher" -e "parkinson" ${txt} | wc -l`
part=`grep -e "gaucher.*parkison\|parkinson.*gaucher" ${txt} | wc -l `

echo "intersection = ${part}"
echo "total = ${total} "

python -c "print ${part}/${total}.0"
