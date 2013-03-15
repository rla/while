Some code I wrote in 2011 to study the problem of dataflow analyses. All ideas were
taken from the article [Lecture Notes in Static Analysis](http://www.itu.dk/people/brabrand/UFPE/Data-Flow-Analysis/static.pdf)
by M.I.Schwartzbach.

The parser for the toy language "while" is implemented using the [JavaCC](http://en.wikipedia.org/wiki/JavaCC)
parser generator. The dataflow analyzer is type-safe and implements a worklist solver algorithm. I implemented constraints for
the simpler examples (initialized vars, liveness, sign).

Building
--------

The AST nodes have to be compiled first:

    cd language
    ant jar
    cd ..

Then the parser can be compiled:

    cd parser
    ant jar
    cd ..
    
Then the analyzer code can be compiled:

    ant jar
    
And finally, examples can be run:

    ant liveness iv sign
    
Example
-------

Here is the sign example input source:

    var [x, y, z];
    x=1;
    y=0-2;
    z=0;
    x=10;
    while (x>0) {
        x=x-1;
    }
    
The parser turns this into AST which is transformed to CFG. Constraints
for the CFG are then derived and the constraint system is produced:

    [[exit]] = ALLJOIN([[x>0]])
    [[entry]] = []
    [[x>0]] = ALLJOIN([[x=10]],[[x=x-1]])
    [[z=0]] = ALLJOIN([[y=0-2]]){z->eval(0)}
    [[var [x, y, z]]] = ALLJOIN([[entry]]){z=null, y=null, x=null}
    [[x=10]] = ALLJOIN([[z=0]]){x->eval(10)}
    [[x=1]] = ALLJOIN([[var [x, y, z]]]){x->eval(1)}
    [[x=x-1]] = ALLJOIN([[x>0]]){x->eval(x-1)}
    [[y=0-2]] = ALLJOIN([[x=1]]){y->eval(0-2)}
    
All constraint variables are here in the form [[node name]]. After solving
the system, node values are dumped:

    entry = {}
    var [x, y, z] = {z=null, y=null, x=null}
    x=1 = {z=null, y=null, x=POS}
    y=0-2 = {z=null, y=NEG, x=POS}
    z=0 = {z=ZERO, y=NEG, x=POS}
    x=10 = {z=ZERO, y=NEG, x=POS}
    x=x-1 = {z=ZERO, y=NEG, x=TOP}
    x>0 = {z=ZERO, y=NEG, x=TOP}
    exit = {z=ZERO, y=NEG, x=TOP}
    
License
-------

    The MIT License (MIT)
    Copyright (c) 2013 Raivo Laanemets
    
    Permission is hereby granted, free of charge, to any person obtaining a copy of this software
    and associated documentation files (the "Software"), to deal in the Software without restriction,
    including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
    and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
    subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all copies or substantial
    portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
    THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
