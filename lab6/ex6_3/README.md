Technical Debt: 38min (12 code smells)
	- This is the time it'd take to fix the found errors, which will have to (almost) certainly be refactored.
	
	
	  Issue		| Problem Description			|  How to solve
  --------------------------------------------------------------------------------------------------
  Vulnerability		| Persistent entity as an argument 	| Replace entity with a regular object
  			| of @PostMapping			| 
  Code Smell(Blocker)   | No assertions in test case.		| Add an assertion to test case.
  			
  d) There's 30 lines not covered by code (20% of total code)
  	Uncovered Conditions show as -- (Is this 0?)
  	
