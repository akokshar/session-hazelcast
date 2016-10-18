# session-hazelcast
Web sessions backed up by Hazelcast cache to work on OpenShift

Assume that you have more than one web server (A, B, C) with a load balancer in front of it. If server A goes down, your users on that server will be directed to one of the live servers (B or C), but their sessions will be lost.
We need to have all these sessions backed up somewhere if we do not want to lose the sessions upon server crashes. Hazelcast allows you to cluster user HTTP sessions automatically.

This app will be connected to a Hazelcast cluster as a part of a OpenShift project (Client/Server architecture for Filter Based Web Session Replication by Hazelcast). To do that it uses the Hazelcast Discovery Plugin for Kubernetes, see https://github.com/noctarius/hazelcast-kubernetes-discovery 
