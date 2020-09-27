#!/bin/bash
FILES=/tmp/bak/*
for f in $FILES
do
    if [[ $f =~ "schema.sql" ]]
    then
        echo "Ignore schema.sql"
    else
        echo "Processing $f"
        /opt/mssql-tools/bin/sqlcmd -S localhost -U SA -P Abella93 -i $f
        echo "Wait ..."
        sleep 2s
    fi
done

