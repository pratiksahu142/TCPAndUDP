PROJECT_NETWORK='project1-network'
SERVER_IMAGE='project1-server-image'
TCP_SERVER_CONTAINER='my-tcp-server'
CLIENT_IMAGE='project1-client-image'
CLIENT_CONTAINER='my-client'

# clean up existing resources, if any
echo "----------Cleaning up existing resources----------"
docker container stop $TCP_SERVER_CONTAINER 2> /dev/null && docker container rm $TCP_SERVER_CONTAINER 2> /dev/null
docker container stop $CLIENT_CONTAINER 2> /dev/null && docker container rm $CLIENT_CONTAINER 2> /dev/null
docker network rm $PROJECT_NETWORK 2> /dev/null

# only cleanup
if [ "$1" == "cleanup-only" ]
then
  exit
fi

# create a custom virtual network
#echo "----------creating a virtual network----------"
#docker network create $PROJECT_NETWORK

# build the images from Dockerfile
echo "----------Building images----------"
docker build -t $CLIENT_IMAGE --target client-build .
docker build -t $SERVER_IMAGE --target server-build .

# run the image and open the required ports
echo "----------Running sever app----------"
#docker run -d -p 1111:1111 --name $TCP_SERVER_CONTAINER --network $PROJECT_NETWORK $SERVER_IMAGE

echo "----------watching logs from server----------"
#docker logs $SERVER_CONTAINER -f


# run the image and open the required ports
echo "----------Running sever app----------"
#docker run -d -p 1111:1111 --name $TCP_SERVER_CONTAINER --network $PROJECT_NETWORK $SERVER_IMAGE
#docker run -it --network $PROJECT_NETWORK IMAGE --name $TCP_SERVER_CONTAINER java server.ServerApp "$1" "tcp"

#docker run -dit --network alpine-net --name my-tcp-server project1-client-server-image ash
echo "----------watching logs from server----------"
#docker logs $TCP_SERVER_CONTAINER -f

#docker rm -f $(docker ps -a -q)