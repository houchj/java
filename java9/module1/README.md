demo the simpliest module:

1. compile 
"%java9_home%\bin\javac" -d mods\com.sap.mymodule src\com.sap.mymodule\module-info.java src\com.sap.mymodule\Main.java

2. run
"%java9_home%\bin\java" --module-path mods -m com.sap.mymodule/com.sap.mymodule.Main


