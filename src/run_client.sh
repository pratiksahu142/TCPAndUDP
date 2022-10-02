CLIENT_IMAGE='client-image'
TCP_CLIENT_CONTAINER='my-tcp-client'

echo "----------Running client app----------"
docker run -it --rm --name $TCP_CLIENT_CONTAINER --network="host" $CLIENT_IMAGE