# k8s-gcp-hello


## Pre-requisites
Create a GCP account
Create a Project (remind the PROJECT_ID)
Then install Docker, and Google Cloud SDK
Install kubectl
```shell
gcloud components install kubectl
```

## Hands-on

Create a registry to store the docker images

```shell
gcloud artifacts repositories create hurban \
    --project=<PROJECT_ID> \
    --repository-format=docker \
    --location=us-east1 \
    --description="Docker repository"
```

Configure credentials for the docker artifact registry

```shell
gcloud gcloud auth configure-docker us-east1-docker.pkg.dev
```

Output:
```shell
 {
  "credHelpers": {
    "asia.gcr.io": "gcloud",
    "eu.gcr.io": "gcloud",
    "gcr.io": "gcloud",
    "marketplace.gcr.io": "gcloud",
    "staging-k8s.gcr.io": "gcloud",
    "us.gcr.io": "gcloud",
    "us-east1-docker.pkg.dev": "gcloud"
  }
}

Do you want to continue (Y/n)?  Y

Docker configuration file updated.
```

## Create the docker image
Let's build a hello-world java/kotlin project from the `hello/` path

```shell
./mvnw clean package
```

After build success, we must build the docker image from the Dockerfile which includes the binaries of hello project. The following line builds and push the image 
```shell
gcloud builds submit \                                 
  --tag us-east1-docker.pkg.dev/PROJECT_ID/hurban/hurban-hello:v1 .
```
**Note that the image is tagged with the repo name plus the docker image name and tag/version

Let's run the project locally as follows  
```shell
docker run -d -p 8080:8080 us-east1-docker.pkg.dev/<PROJECT_ID>/hurban/hurban-hello:v1
```

Give it a try  
```shell
curl http://localhost:8080
```
Output: `{"greetings":"Hello"}`


## Create a GKE cluster

Create Cluster
```shell
gcloud container clusters create-auto hello-cluster-dev \
  --location us-east1
```
Output:
```shell
Note: The Pod address range limits the maximum size of the cluster. Please refer to https://cloud.google.com/kubernetes-engine/docs/how-to/flexible-pod-cidr to learn how to optimize IP address allocation.
Creating cluster hello-cluster-dev in us-east1... Cluster is being health-checked (master is healthy)...done.                                                                                               
Created [https://container.googleapis.com/v1/projects/macro-shadow-396720/zones/us-east1/clusters/hello-cluster-dev.
To inspect the contents of your cluster, go to: https://console.cloud.google.com/kubernetes/workload_/gcloud/us-east1/hello-cluster-dev?project=macro-shadow-396720
kubeconfig entry generated for hello-cluster-dev.
NAME            LOCATION  MASTER_VERSION  MASTER_IP     MACHINE_TYPE  NODE_VERSION    NUM_NODES  STATUS
hello-cluster-dev us-east1  1.27.3-gke.100  35.229.32.85  e2-medium     1.27.3-gke.100  3      
```

To see all the pods running in your cluster, run the following command
```shell
kubectl get nodes
````
output:
```shell
NAME                                            STATUS   ROLES    AGE     VERSION
gk3-hello-cluster-dev-default-pool-fd8eef1d-fsff   Ready    <none>   4m11s   v1.27.3-gke.100
```

Create a Deployment

```shell
kubectl apply -f deployment.yaml
```
Output:
```shell               
deployment.apps/hello-hurban-app created
```

Create a Service
```shell
kubectl apply -f service.yaml
```
Output:
```shell
service/hello-hurban-service created
```

Get the external IP address of the service:
```shell
kubectl get services
```
Output:
```shell
NAME         TYPE           CLUSTER-IP      EXTERNAL-IP     PORT(S)        AGE
hello        LoadBalancer   10.22.222.222   35.111.111.11   80:32341/TCP   1m
kubernetes   ClusterIP      10.22.222.1     <none>          443/TCP        20m
```
It may take up to 60 seconds to assign the IP address. The external IP address will appear in the EXTERNAL-IP column for the hello service.