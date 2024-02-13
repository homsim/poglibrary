# install MariaDB

###################
#    variables    #
###################

db_username=pog_user
MARIA_DB_REPO=https://downloads.mariadb.com/MariaDB/mariadb_repo_setup

###################
#     mariadb     #
###################

if [[ -e /etc/os-release ]]
then 
    . /etc/os-release
    case $ID in 
        ubuntu|debain)
            curl -sS $MARIA_DB_REPO | sudo bash
            sudo apt-get install mariadb-server mariadb-client mariadb-backup -y
            ;;
        sles)
            curl -sS $MARIA_DB_REPO | sudo bash
            sudo zypper install MariaDB-server MariaDB-client MariaDB-backup -y
            ;;
        rhel|centos|rocky*)
            curl -sS $MARIA_DB_REPO | sudo bash
            sudo yum install MariaDB-server MariaDB-client MariaDB-backup -y
            ;;
    esac
else
    error "Could not identify OS type or version myseld (/etc/os-release not found). "
fi        
