# scala-services

规划中的一个服务端开发工具箱。

1. service action framework & design-by-contract support 
  1. Sevice Action
  2. Entity Action
  3. support ACL
  4. DBC invariable support
2. assert macro ( borrow from scalatest )
  Now, you can simple using assert( expr == expect ) and if there is any error, it will report in human-readable message
3. some useful operators like in, between etc.
4. Tools to generate Database-Table Mapping case class, avoid writing the code
5. cache support.
   using thrift codec. but register metadata in redis? and provide a redis LUA function to convert a binary-value to json?
6. Case-Class-Copy.already implements in scala-sql, will moved to scala-services
7. implements schedule & crontab tasks in services.
8. implements message-queue-endpoints in services.
9. implements an event-publish-listener framework in services.
10. Binlog
