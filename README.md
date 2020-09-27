# Fhircloud
Fhir coud architecture simulated in local environment

# Steps
Follow this steps on the host machine

### Install Java
Requirements: Install Java version >= 11
Ubuntu: `sudo apt install openjdk-11-jdk openjdk-11-jre`

### Install Apache Maven
Ubuntu: `sudo apt install maven`

### Install Docker & Docker-compose
Docker: Follow this [guide](https://docs.docker.com/engine/install/ubuntu/)
Docker-Compose: Follow this [guide](https://docs.docker.com/compose/install/)

### Start Docker
`sudo systemctl start docker`

### Clone Repository
1. `git clone https://github.com/didacusabella/FhirCloud`
2. `cd FhirCloud`

### Package Server
1. `cd FHIRestful`
2. `mvn package`

### Prepare Database
1. Unzip backup
2. `chmod +x fillDb.sh createDb.sh`
3. `chmod 755 -R ./bak`

### Launch Docker compose
1. `sudo docker-compose up`
2. Wait for the creation of the images
3. `sudo docker-compose up`

### Fill Database
1. `sudo docker exec -it fhirdatabase "bash"`
2. `cd /tmp`
3. `/opt/mssql-tools/bin/sqlcmd -S localhost -U SA -P Abella93`
4. In interacctive mode create database
5. `CREATE DATABASE warehouse` andare a capo `GO` e premere invio
6. `Ctrl-D`
7. `./createDb.sh`
8. `./fillDb.sh`

# Machines info
- Client: http://localhost:8080
- Server: http://localhost:4000
- Database: http://localhost:1433

# For stopping all containers
 `sudo docker-compose stop` in the project directory

# For Cleanup Containers
`sudo docker-compose rm` in the project directory
