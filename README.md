# Instructions

this is an example Java repo on demonstatring how to use a custom load balancer with Datastax java driver version 4.9

**Please note that this is dev tested only for prepared statements on a single table- this is, by no means, a production ready code.**


# setup

create a cassandra cluster with two datacenters having **three nodes each** . 

run the contents of `/fiserv-Custom-LBP-DCFailover/src/main/resources/cyclist_semi_pro.cql`  file, from Datastax Studio or cqlsh console, to generate your schema and load some smaple data. please change the datacenters names in the replication factor creation command to match your cassandra setup.

please make sure to retain the replication factor of three.

## configuration

change the `/fiserv-Custom-LBP-DCFailover/src/main/resources/application.conf` file as needed - 

> datastax-java-driver.basic.load-balancing-policy.local-datacenter =   
> DC1 datastax-java-driver.basic.request.consistency = LOCAL_QUORUM   
> datastax-java-driver.basic.contact-points = [   
> "54.67.24.25:9042","50.18.97.114:9042" ]   
> datastax-java-driver.advanced.reconnection-policyclass =   
> ConstantReconnectionPolicy   
> datastax-java-driver.advanced.reconnection-policy.base-delay = 10   
> datastax-java-driver.advanced.speculative-execution-policy.class =   
> ConstantSpeculativeExecutionPolicy   
> datastax-java-driver.advanced.speculative-execution-policy.max-executions
> = 3 datastax-java-driver.advanced.speculative-execution-policy.delay = 10

please note the test cluster does not have authentication or SSL set up - please add relevant configurations if using them. refer to [documentation](https://docs.datastax.com/en/developer/java-driver/4.9/manual/core/configuration/reference/).

## steps for running the test

run the main method in  `com.datastax.custom_lbp.customlbp.test.TestCustomLBP` class
after the code is running, it will print the ip of the coordinator it is contacting in the system console

 
              found row : <some value>
              coordinator node : <ip>
              coordinator node DC : DC1

shutdown two nodes your local datacenter DC1
the code, once the driver has realised that the local datacenter is down( that is, the local datacenter **does not have** two replicas available for statisfying LOCAL_QOURUM requests ), will start sending requests to the remote datcenter DC2

     found row : <some value>
     coordinator node : <ip>
     coordinator node DC : DC2
bring up the two nodes your local datacenter DC1
the code, once the driver has realised that the local datacenter is up , will start sending requests to the local datcenter DC1 again.

     found row : <some value>
     coordinator node : <ip>
     coordinator node DC : DC1
