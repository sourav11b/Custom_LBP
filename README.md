# Instructions

this is an example Java repo on demonstatring how to use a custom load balancer with your datastax java driver version 4.9


# setup

create a cassandra cluster with two datacenters having **three nodes each** . 

run the `/fiserv-Custom-LBP-DCFailover/src/main/resources/cyclist_semi_pro.cql` file to generate your schema and load some smaple data. please change the datacenters names in the replication factor creation command to what your cassandra setup has.

please make sure to retain the replciation factor of three.

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

please note the test cluster does not have authention or SSL set up - please add relevant configurations if using them. refer to [documentation](https://docs.datastax.com/en/developer/java-driver/4.9/manual/core/configuration/reference/).

## steps for running the test

run the main method in  `com.datastax.custom_lbp.customlbp.test.TestCustomLBP` class
after the code is running it will print the ip of the coordinator it is contacting in the system console

 
              found row : <some value>
              coordinator node : <ip>
              coordinator node DC : DC1

shutdown two nodes your local datacenter
the code, once the driver has realised that the local datacenter is down, will start sedning requests to the remote datcenter

     found row : <some value>
     coordinator node : <ip>
     coordinator node DC : DC2
bring up the two nodes your local datacenter
the code, once the driver has realised that the local datacenter is ip , will start sedning requests to the local datcenter again

     found row : <some value>
     coordinator node : <ip>
     coordinator node DC : DC1
