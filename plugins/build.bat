cd verhaar
call ant
cd ..
cd sicret
call ant
cd ..
cd mutant
call ant
cd..

copy verhaar\dist\*.jar ..\toxTree\dist\ext
copy sicret\dist\*.jar ..\toxTree\dist\ext
copy mutant\dist\*.jar ..\toxTree\dist\ext
