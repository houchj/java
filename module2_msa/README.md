demo the module dependecies with maven

3 services are involved: product service, oms service, b2c-shop service.

key demo cases:
1. product service and oms service only exports the interface in a standalone module. Other services don't know the implementation.
2. module transitive. b2c-shop service can call product service by the dependency to oms service.


set java_home=%java9_home%
set path=%java_home%\bin;%path%

