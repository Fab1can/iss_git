%====================================================================================
% fireflysync description   
%====================================================================================
event( syncronize, syncronize(FREQ) ).
event( desyncronize, desyncronize(X) ).
dispatch( cellstate, cellstate(X,Y,COLOR) ).
dispatch( distance, distance(D) ).
%====================================================================================
context(ctxfireflies, "localhost",  "TCP", "8040").
context(ctxgrid, "127.0.0.1",  "TCP", "8050").
 qactor( griddisplay, ctxgrid, "external").
  qactor( sonar, ctxfireflies, "it.unibo.sonar.Sonar").
 static(sonar).
  qactor( creator, ctxfireflies, "it.unibo.creator.Creator").
 static(creator).
  qactor( firefly, ctxfireflies, "it.unibo.firefly.Firefly").
dynamic(firefly). %%Oct2023 
