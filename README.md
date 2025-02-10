# microservices-spring-boot
***

### ```wsl --install```
### ```minikube start --driver=docker```
### ```minikube status```
### ```minikube stop```
### ```minikube delete```

# kubectl commands below:
***

### ```kubectl get all```
### ```kubectl get pods```
### ```kubectl get deploy```
### ```kubectl get deployment```
### ```kubectl get services```
### ```kubectl get namespaces```
### ```kubectl delete all --all```
### ```kubectl get all```

# creating commands below:
***
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


# apply .yaml commands below:
***
``` 
kubectl apply -f deploy.yaml

***

apiVersion: apps/v1
kind: Deployment
metadata:
    name: service-registry
spec:
    selector:
        matchLabels:
            app: service-registry
    template:
        metadata:
            labels:
                app: service-registry
        spec:
            containers:
              - name: service-registry
                image: guvalakat/service-registry:latest
                imagePullPolicy: Always
                ports:
                  - containerPort: 8761

***

kubectl delete -f deploy.yaml
```    

```
kubectl apply -f svc.yaml

***

apiVersion: v1
kind: Service
metadata:
    name: service-registry-svc
spec:
    selector:
        app: service-registry
    ports:
        - protocol: TCP
          port: 80
          targetPort: 8761 # This is the port that the service is listening on inside the pod
    type: NodePort
    
***

kubectl delete -f svc.yaml
```

# namespace
***
```
kubectl create namespace my-namespace
```

``` 
kubectl apply -f deploy.yaml -n my-namespace 
```

```
kubectl get all -n my-namespace
```

# minikube dashboard
***
```
minikube dashboard
```

```
kubectl apply -f .\k8s\  # deploying all .yaml files
```

```kubernetes
minikube service [service-name]
```

```kubernetes
kubectl get pv
```

```kubernetes
kubectl get pvc
```

# service-regeistry-statefulset.yaml
***
```kubernetes
apiVersion: apps/v1
kind: StatefulSet
metadata:
  name: eureka
spec:
  selector:
    matchLabels:
      app: eureka
  serviceName: "eureka"
  replicas: 1
  template:
    metadata:
      labels:
        app: eureka
    spec:
      containers:
        - name: eureka
          image: guvalakat/service-registry_v2:latest
          ports:
            - containerPort: 8761

---

apiVersion: v1
kind: Service
metadata:
  name: eureka
spec:
  clusterIP: None
  selector:
    app: eureka
  ports:
    - port: 8761
      name: eureka

---

apiVersion: v1
kind: Service
metadata:
  name: eureka-lb
spec:
  type: NodePort
  selector:
    app: eureka
  ports:
    - port: 80
      targetPort: 8761
```

# config-server-deployment.yaml
***
```kubernetes
apiVersion: apps/v1
kind: Deployment
metadata:
  name: config-server-app
spec:
  selector:
    matchLabels:
      app: config-server-app
  template:
    metadata:
      labels:
        app: config-server-app
    spec:
      containers:
        - name: config-server-app
          image: guvalakat/config-server_v2:latest
#          resources:
#            limits:
#              memory: "128Mi"
#              cpu: "500m"
          ports:
            - containerPort: 9296
          env:
            - name: EUREKA_SERVER_ADDRESS
              valueFrom:
                configMapKeyRef:
                  key: eureka_service_address
                  name: eureka-cm

---

apiVersion: v1
kind: Service
metadata:
  name: config-server-svc
spec:
  selector:
    app: config-server-app
  ports:
    - port: 80
      targetPort: 9296
```

# config-maps.yaml
***
```kubernetes
apiVersion: v1
kind: ConfigMap
metadata:
  name: eureka-cm
data:
  eureka_service_address: "http://eureka-0.eureka:8761/eureka"

---

apiVersion: v1
kind: ConfigMap
metadata:
  name: config-cm
data:
  config_url: "http://config-server-svc"

---

apiVersion: v1
kind: ConfigMap
metadata:
  name: mysql-cm
data:
  hostname: "mysql-0.mysql"

```

# cloud-gateway-deployment.yaml
***
```kubernetes
apiVersion: apps/v1
kind: Deployment
metadata:
  name: cloud-gateway-app
spec:
  selector:
    matchLabels:
      app: cloud-gateway-app
  template:
    metadata:
      labels:
        app: cloud-gateway-app
    spec:
      containers:
        - name: cloud-gateway-app
          image: guvalakat/cloudgateway_v2:latest
          ports:
            - containerPort: 9090
          env:
            - name: EUREKA_SERVER_ADDRESS
              valueFrom:
                configMapKeyRef:
                  key: eureka_service_address
                  name: eureka-cm
            - name: CONFIG_SERVER_URL
              valueFrom:
                configMapKeyRef:
                  key: config_url
                  name: config-cm

---

apiVersion: v1
kind: Service
metadata:
  name: cloud-gateway-svc
spec:
  type: LoadBalancer
  selector:
    app: cloud-gateway-app
  ports:
    - port: 80
      targetPort: 9090
```

# product-service-deployment.yaml
***
```kubernetes
apiVersion: apps/v1
kind: Deployment
metadata:
  name: product-service-app
spec:
  selector:
    matchLabels:
      app: product-service-app
  template:
    metadata:
      labels:
        app: product-service-app
    spec:
      containers:
        - name: product-service-app
          image: guvalakat/product-service:latest
          ports:
            - containerPort: 8080
          env:
            - name: EUREKA_SERVER_ADDRESS
              valueFrom:
                configMapKeyRef:
                  key: eureka_service_address
                  name: eureka-cm
            - name: CONFIG_SERVER_URL
              valueFrom:
                configMapKeyRef:
                  key: config_url
                  name: config-cm
            - name: DB_HOST
              valueFrom:
                configMapKeyRef:
                  key: hostname
                  name: mysql-cm

---

apiVersion: v1
kind: Service
metadata:
  name: product-service-svc
spec:
  selector:
    app: product-service-app
  ports:
    - port: 80
      targetPort: 8080
```

# order-service-deployment.yaml
***
```kubernetes
apiVersion: apps/v1
kind: Deployment
metadata:
  name: order-service-app
spec:
  selector:
    matchLabels:
      app: order-service-app
  template:
    metadata:
      labels:
        app: order-service-app
    spec:
      containers:
        - name: order-service-app
          image: guvalakat/order-service:latest
          ports:
            - containerPort: 8082
          env:
            - name: EUREKA_SERVER_ADDRESS
              valueFrom:
                configMapKeyRef:
                  key: eureka_service_address
                  name: eureka-cm
            - name: CONFIG_SERVER_URL
              valueFrom:
                configMapKeyRef:
                  key: config_url
                  name: config-cm
            - name: DB_HOST
              valueFrom:
                configMapKeyRef:
                  key: hostname
                  name: mysql-cm
---

apiVersion: v1
kind: Service
metadata:
  name: order-service-svc
spec:
  selector:
    app: order-service-app
  ports:
    - port: 80
      targetPort: 8082
```

# payment-service-deployment.yaml
***
```kubernetes
apiVersion: apps/v1
kind: Deployment
metadata:
  name: payment-service-app
spec:
  selector:
    matchLabels:
      app: payment-service-app
  template:
    metadata:
      labels:
        app: payment-service-app
    spec:
      containers:
        - name: payment-service-app
          image: guvalakat/payment-service:latest
          ports:
            - containerPort: 8081
          env:
            - name: EUREKA_SERVER_ADDRESS
              valueFrom:
                configMapKeyRef:
                  key: eureka_service_address
                  name: eureka-cm
            - name: CONFIG_SERVER_URL
              valueFrom:
                configMapKeyRef:
                  key: config_url
                  name: config-cm
            - name: DB_HOST
              valueFrom:
                configMapKeyRef:
                  key: hostname
                  name: mysql-cm

---

apiVersion: v1
kind: Service
metadata:
  name: payment-service-svc
spec:
  selector:
    app: payment-service-app
  ports:
    - port: 80
      targetPort: 8081
```

# redis-deployment.yaml
***
```kubernetes
apiVersion: apps/v1
kind: Deployment
metadata:
  name: redis-app
spec:
  selector:
    matchLabels:
      app: redis-app
  template:
    metadata:
      labels:
        app: redis-app
    spec:
      containers:
        - name: redis-app
          image: redis:7.4.2
          ports:
            - containerPort: 6379
          command:
            - "redis-server"
          args:
            - "--protected-mode"
            - "no"

---

apiVersion: v1
kind: Service
metadata:
  name: redis
spec:
  selector:
    app: redis-app
  ports:
    - port: 6379
      targetPort: 6379
```

# zipkin-deployment.yaml
***
```kubernetes
apiVersion: apps/v1
kind: Deployment
metadata:
  name: zipkin
spec:
  selector:
    matchLabels:
      app: zipkin
  template:
    metadata:
      labels:
        app: zipkin
    spec:
      containers:
        - name: zipkin
          image: openzipkin/zipkin
          ports:
            - containerPort: 9411

---

apiVersion: v1
kind: Service
metadata:
  name: zipkin-svc
spec:
  selector:
    app: zipkin
  ports:
    - port: 9411
      targetPort: 9411

---
apiVersion: v1
kind: Service
metadata:
  name: zipkin-lb-svc
spec:
  type: LoadBalancer
  selector:
    app: zipkin
  ports:
    - port: 9411
      targetPort: 9411
```