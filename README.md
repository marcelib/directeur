# directeur
Company employees manager app developed specially for Braintri recrutation purposes

#Startup
To run the application, all you need to to is to write
mvn clean package && java -jar target/directeur-0.0.1-SNAPSHOT.jar

#Database configuration
I've performed the configuration on a clean postgres, so you might find that useful. 
To be able to use the database as default postgres user (otherwise you can change it in application.properties), do this:

sudo su
su - postgres
  
1. Enter local postgres instance
psql
  
2. Setting password for postgres user
\password
Enter new password:
Enter it again:
  
3. Disconnecting
\q
  
4. Going back to root and editing postgres config
vim /etc/postgresql/9.4/main/pg_hba.conf
  
5. Changing line "local all all peer" na "local all all md5"
  
6. Restart database to load new configuration:
/etc/init.d/postgresql restart
