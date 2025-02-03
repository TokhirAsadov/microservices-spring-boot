# microservices-spring-boot

### ```wsl --install```
### ```minikube start --driver=docker```
### ```minikube status```
### ```minikube stop```
### ```minikube delete```

# kubectl commands below:

### ```kubectl get all```
### ```kubectl get pods```
### ```kubectl get deploy```
### ```kubectl get deployment```
### ```kubectl get services```
### ```kubectl get namespaces```
### ```kubectl delete all --all```
### ```kubectl get all```

# creating commands below:
### ```kubectl create -h```                    Create a resource from a file or from stdin
### ```kubectl create clusterrole ```          Create a cluster role
### ```kubectl create clusterrolebinding ```   Create a cluster role binding for a particular cluster role
### ```kubectl create configmap  ```           Create a config map from a local file, directory or literal value
### ```kubectl create cronjob  ```             Create a cron job with the specified name
### ```kubectl create deployment  ```          Create a deployment with the specified name
### ```kubectl create ingress ```              Create an ingress with the specified name
### ```kubectl create job  ```                 Create a job with the specified name
### ```kubectl create namespace  ```           Create a namespace with the specified name
### ```kubectl create poddisruptionbudget ```  Create a pod disruption budget with the specified name
### ```kubectl create priorityclass ```        Create a priority class with the specified name
### ```kubectl create quota ```                Create a quota with the specified name
### ```kubectl create role  ```                Create a role with single rule
### ```kubectl create rolebinding  ```         Create a role binding for a particular role or cluster role
### ```kubectl create secret  ```              Create a secret using a specified subcommand
### ```kubectl create service  ```             Create a service using a specified subcommand
### ```kubectl create serviceaccount  ```      Create a service account with the specified name
### ```kubectl create token    ```             Request a service account token

### ```kubectl create deployment nginx --image=nginx ```

### ```kubectl get all```
### ```kubectl get all -o wide```
### ```kbuectl get pod pod_name```
### ```kbuectl logs pod_name```
### ```kbuectl exec -it pod_name -- /bin/bash```
### ```kbuectl edit deployment```