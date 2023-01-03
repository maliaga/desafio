#!/bin/bash
HOST=$1
PROJECT_DIRECTORY=$2
FILES=$(ls ${PROJECT_DIRECTORY}/database/*.json | sort -n -t _ -k 2)



echo "[$(date)] Connected! 🤙"

for AFILE in ${FILES[@]}
do
    echo -e "[$(date)] Processing \t$AFILE"
    COLLECTION=`echo $AFILE | sed -n 's/.*\-\(.*\).json/\1/p'`
    mongoimport --host ${HOST} --username brandDiscountsUser --password brandDiscountsPassword --authenticationDatabase admin --db desafio --collection ${COLLECTION} --mode upsert --file ${AFILE}
    echo -e "[$(date)] Done \t\t$AFILE"
done

echo "[$(date)] No more files to process 🍻"
