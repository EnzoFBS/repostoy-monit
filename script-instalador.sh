#!/bin/bash

getent passwd urubu100
if [ $? -eq 0 ]
then echo \“Usuario ja existente\”
else echo \"Usuario nao existe, criando usuario\"

sudo adduser urubu100
sudo usermod -aG sudo urubu100
fi
su urubu100 -p urubu100

sudo apt-get update && sudo apt-get upgrade -y
sudo apt-get install xrdp gdm3 lxde-core lxde tigervnc-standalone-server -y

sudo apt-get update && sudo apt-get upgrade -y
git clone https://github.com/EnzoFBS/repostoy-monit.git

java --version
if [ $? -eq 0 ]
then echo \"java instalado\"
else echo \"java não instalado\"

echo \"Gostaria de instalar o jAVA? S/n \"
read inst
if [ \"$inst\" == \"s\" ]
then
        echo \"voce escolheu instalar o java\"
        echo \"instalado repositorio\"
sleep 2
sudo add-apt-repository ppa:linuxuprising/java -y
clear
echo \"Atualizando repositorio\"
sleep 2
sudo apt-get update -y
clear
echo \"Para prosseguirmos com a instalacao sera necessario instalar o java 11, prosseguir com o download? S/n \"
read inst1
if [ \"$inst1\" == \"s\" ]
then
sudo apt insd
else echo \"você escolheu não instalar\"
break
fi
fi
fi
clear


echo \"Para finalizar a instalacao, sera feito o download do nosso app Monisystem e um banco de dados local, prosseguir com a instalacao? S/n \"
read inst2
if [ \"$inst2\" == \"s\" ]
then
sudo apt update && sudo apt upgrade -y
clear
sudo apt-get install docker.io -y
sudo systemctl start docker
sudo systemctl enable docker
sudo docker pull mysql:5.7
sudo docker build -t jar_monitotem:1.0 .
sudo docker build -t banco_img/banco-mysql:1.0 .
sudo docker run -d -p 3306:3306 --name java-monitotem jar_monitotem:1.0



sudo docker ps -a


fi

