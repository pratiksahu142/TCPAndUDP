SERVER_IMAGE='project1-server-image'
PROJECT_NETWORK='project1-network'
SERVER_CONTAINER='my-tcp-server'

if [ $# -ne 2 ]
then
  echo "Usage: ./run_server.sh <port-number> <protocol>"
  exit
fi

echo "$1" "$2" "$3"
# run server docker container with cmd args
docker run -it \
 --network $PROJECT_NETWORK $SERVER_IMAGE \
 java server.ServerApp "$1" "$2"
 # cmd to run server locally - java server.ServerApp 1111 tcp
