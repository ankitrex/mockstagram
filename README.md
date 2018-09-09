# mockstagram
Mockstagram influencer statistics

<h2>Springboot project</h2>

<b>External Dependencies</b> - Java, Redis, Cassandra, Maven

<b>Java 1.8 or above - </b>
Can be downloaded from https://www.java.com/en/download/ <br/>
On mac - can be installed using homebrew using command 'brew cask install java' <br/>
Java version used - 1.8.0_121

<b>Redis</b> - 
Can be downloaded from https://redis.io/download <br/>
On mac - can be installed using homebrew using command 'brew install redis' <br/>
Redis version used - 4.0.11

<b>Cassandra</b> - 
Can be downloaded from http://cassandra.apache.org/download/ <br/>
On mac - can be installed using homebrew using command 'brew install cassandra' <br/>
Cassandra version used - 3.11.3

<b>Maven</b> - 
Can be installed from https://maven.apache.org/install.html <br/>
On mac - can be installed using homebrew using command 'brew install maven' <br/>
Maven version used - 3.5.2

<h3>Setting up and running the project</h3>
<ul>
<li>Clone the repository</li>
<li>Install Redis, Cassandra and Maven.
<li>Update Redis and Cassandra properties in 'src/main/resources/application.properties'<br/>
spring.data.cassandra.contact-points={cassandra host ip}<br/>
spring.data.cassandra.port={cassandra port}<br/>
spring.data.cassandra.username={cassandra user}<br/>
spring.data.cassandra.password={cassandra password}<br/>
spring.data.cassandra.keyspace-name={cassandra keyspace name}<br/>
spring.redis.host={redis host ip}<br/>
spring.redis.port={redis port}<br/>
</li>
<li>Create a user and keyspace in cassandra (as defined in application.properties)</li>
<li>Start Mockstagram-api-master using npm</li>
<li>Update API server properties in 'src/main/resources/application.properties'<br/>
mockstagram.api.base-url={host:port}
<li>Starting spring-boot application - In root directory of mockstagram project, execute command 'mvn spring-boot:run' </li>
</ul>

<h3>Other configurations</h3>
<ul>
<li><b>mockstagram.user.id.start=?</b> and <b>mockstagram.user.id.end=?</b> can be configured to run smaller set of influencers.<br/>
For example - mockstagram.user.id.start=1000000 and mockstagram.user.id.end=1010000 will only run the code for first 10000 influencers.
</li>
</li><b>mockstagram.cassandra.max.batch</b> - for configuring records in a batch query in cassandra</li>
</li><b>mockstagram.java.max.threads.main</b> - Number of threads for data updating process</li>
</li><b>mockstagram.java.max.threads.proc-1</b> - Number of threads for is_suspicious process. Usually a bigger number since api response time is much higher</li>
</ul>


<h3>Controller Endpoints</h3>
<ul>
<li><b>/influencer/{influencerId}/timeseries-data</b> - Timeseries data of an influencer</li>
<li><b>/influencer/{influencerId}</b> - Latest data of an influencer</li>
<li><b>/average-follower-count</b> - Average follower count across influencers</li>
<li><b>/start-main</b> - To start the data refreshing process. <b>Not to be used directly. Just for debugging purposes.</b> Its already been replaced by a scheduler.</li>
<li><b>/start-main</b> - To start the is_suspicious process. <b>Not to be used directly. Just for debugging purposes.</b> Its already been replaced by a scheduler.</li>
</ul>
