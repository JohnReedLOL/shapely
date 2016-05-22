# shapely

######Dependent types demo - Nat, HList

- This is a demo of dependent types in Scala using a combination of dependent method types (passing types from method parameters), type constructors/values, type projections (#), implicits, and macros.

- Comments in code are based on [this talk](https://vimeo.com/165837504).

- Calls to [SDebug.traceExpression()](https://github.com/JohnReedLOL/scala-trace-debug) are used to "desugar" the code. The same effect can be acheived by compiling with "-Xprint:typer". This prints the program tree just after the typer compiler phase.
