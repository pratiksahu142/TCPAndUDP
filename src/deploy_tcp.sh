CLIENT_IMAGE='client-image'
SERVER_IMAGE='server-image'
TCP_SERVER_CONTAINER='my-tcp-server'
TCP_CLIENT_CONTAINER='my-tcp-client'

# clean up existing resources, if any
echo "----------Cleaning up existing resources----------"
docker container stop $TCP_SERVER_CONTAINER 2> /dev/null && docker container rm $TCP_SERVER_CONTAINER 2> /dev/null
docker container stop $TCP_CLIENT_CONTAINER 2> /dev/null && docker container rm $TCP_CLIENT_CONTAINER 2> /dev/null
docker image rm $CLIENT_IMAGE
docker image rm $SERVER_IMAGE

# only cleanup
if [ "$1" == "cleanup-only" ]
then
  exit
fi

# build the images from Dockerfile
echo "----------Building images----------"
docker build -t $CLIENT_IMAGE --target client-build client/.
docker build -t $SERVER_IMAGE --target server-build server/.

# run the image and open the required ports
echo "----------Running server app----------"
docker run -it --rm -p 5555:5555 --name $TCP_SERVER_CONTAINER $SERVER_IMAGE