#!/bin/bash
/opt/mssql-tools/bin/sqlcmd -S localhost -U SA -P Abella93 -i /tmp/bak/schema.sql
