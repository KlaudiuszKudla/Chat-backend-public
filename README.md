Docker command to run database: docker run --name chatdb -p 5432:5432 -e POSTGRES_PASSWORD=chat -d postgres
docker run -d --name ftp -p 8001:21 -p 21000-21010:21000-21010 -e USERS="chat|12345678|/home/chat|10000" delfer/alpine-ftp-server

