# Installing Raspberry microk8s cluster

* Use ubuntu server base image.
* update your system
```
$ sudo apt update
$ sudo apt ugrade
```
* install microk8s, first prepare the raspberries, see https://microk8s.io/docs/install-raspberry-pi
* then install microk8s, https://ubuntu.com/tutorials/how-to-kubernetes-cluster-on-raspberry-pi
* I used the 4GB raspberries as "worker" nodes.
* You need to add the local repository addon for microk8s
* Mongodb took a lot of googling and time, see
  https://phoenixnap.com/kb/kubernetes-mongodb
  , but it is up, see mongodb directory, applied with:
```
kubectl apply -n mongodb -k mongodb 
```
* You need to setup your local machine to allow buildx to push to it:

```
$ cat ~/.docker/buildx/buildkitd.default.toml
debug = true
[registry."192.168.0.50:32000"]
  http = true
  insecure = true
$ docker buildx create --name mybuilder --use --bootstrap --config ~/.docker/buildx/buildkitd.default.toml
```
Now you can build multi-arch images, like this:
```
$ cd user-manager/target
$ docker buildx build -f ../src/main/docker/Dockerfile --platform linux/amd64,linux/arm64 --push  --tag 192.168.0.50:32000/cag/user-manager:latest .
```

# Environment

## Networking

* I have setup the network with a TP-Link hotspot router
* so we have our own IP network.
* We will use it in extender mode, so we should connect it to an available network on site.
* rpi-net-5 and rpi-net-24 are the networks to connect to, password is 11223344
* All Raspberries are using DHCP, but have a fixed address setting in the TP-Link router.

* 192.168.0.30: This is the robot raspberry, we have a spare robot raspberry setup at 192.168.0.31
* 192.168.0.44: This is the light sensor raspberry, controlling the track.
* 192.168.0.51: This is the badge scanner raspberry, controlling the scanning, and providing web
  interface on the TV on site.
* 192.168.0.50: Kubernetes master node, this is where all others point.
* 192.168.0.40: Kubernetes
* 192.168.0.41: Kubernetes
* 192.168.0.42: Kubernetes
* 192.168.0.43: Kubernetes worker (4GB ram)