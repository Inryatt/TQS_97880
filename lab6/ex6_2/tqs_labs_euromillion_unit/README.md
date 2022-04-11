mvn clean verify sonar:sonar \
  -Dsonar.projectKey=Euromillions \
  -Dsonar.host.url=http://172.17.0.2:9000 \
  -Dsonar.login=3cdf8955e5c74fae1b2b97939a8bdc4edef6b98f
  
  Issue		| Problem Description			|  How to solve
  --------------------------------------------------------------------------------------------------
  Bug		| Using multiple 'Random' objects	| Save and reuse a single 'Random' Object.
  Code Smell	| Adding to loop counter from inside 	| Refactor the code not to do this.
  		| the loop 				|  
  Code Smell	| Unused method parameter		| Remove this parameter
  Code Smell	| Using Sysout/Systerr for logging	| Replace it by a proper Logging tool
  Code Smell	| AssertTrue/False instead of		| Refactor with AssertEquals/NotEquals 
  		| AssertEquals/NotEquals		|
