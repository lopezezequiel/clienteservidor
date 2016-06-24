echo "Building migration $1"
mkdir migrations/$1
cd migrations/$1
mysqldump -u apppreciosclaros -ptjZDOJcRNYkKHPGRxtxC -h localhost preciosclaros --result-file=migration-$1.sql
