# directeur
Company employees manager app developed specially for Braintri recrutation purposes

# Startup
To run the application, all you need to to is to execute this command:
```
mvn clean package && mvn spring-boot:run
```

# Getting to know the API
If you want to take a quick look at how the API works, after running the app go into
```
http://localhost:8080/swagger-ui.html
```
to browse through and try out all the methods- it's all described there :)

# Database configuration
I've performed the configuration on a clean postgres, so you might find that useful. 
To be able to use the database as my custom-created user and a custom-created database (if you want to do it your own way you can change the database config in application.properties), do this:

1. Login as postgres superuser
``` 
sudo su
su - postgres 
```
  
2. Enter local postgres instance
```
psql
```
  
3. Set password for postgres user
```
\password
Enter new password:
Enter it again:
```
  
4. Disconnect from postgres
```
\q
```
  
5. Go back to root and editing postgres config
```
vim /etc/postgresql/9.4/main/pg_hba.conf
```
  
6. Change line 
```
local all all peer
 ```
(the line should be the first uncommented one)
to
```
local all all md5
```
  
7. Restart database to load new configuration:
```
/etc/init.d/postgresql restart
```
8. Log in to postgres:
```
psql -U postgres
```
9. Execute the commands
```
create role directeur login password 'directeur';
create database "directeur-dev" owner "directeur" encoding 'UTF8' lc_collate 'pl_PL.UTF8' lc_ctype 'pl_PL.UTF8' template template0;
```
Then you're good to go- the database is up and running - now you can deploy the app, and the schema will be taken care of :D
