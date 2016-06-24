echo "Migrating database to version $1..."
cd migrations/$1
mysql -u apppreciosclaros -ptjZDOJcRNYkKHPGRxtxC --local-infile < migration-$1.sql
